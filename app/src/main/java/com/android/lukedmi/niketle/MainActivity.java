package com.android.lukedmi.niketle;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.lukedmi.niketle.Fragment.CariFragment;
import com.android.lukedmi.niketle.Fragment.TrackFragment;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.PhoneNumber;
import com.android.lukedmi.niketle.Fragment.HomeFragment;
import com.android.lukedmi.niketle.Fragment.MoreFragment;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

CallbackManager callbackManager;
LoginButton fblogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        //final BottomBarTab tabFavorit = (BottomBarTab)findViewById(R.id.tab_friends2);
        //tabFavorit.setBadgeCount(5);

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_home){
                    //tabFavorit.removeBadge();
                    getSupportFragmentManager().beginTransaction().replace(R.id.layarframelayout,new HomeFragment()).commit();
                }
                else if (tabId == R.id.tab_cari_tiket){
                    getSupportFragmentManager().beginTransaction().replace(R.id.layarframelayout,new CariFragment()).commit();
                }else if(tabId == R.id.tab_track){
                    getSupportFragmentManager().beginTransaction().replace(R.id.layarframelayout,new TrackFragment()).commit();
                }else if (tabId == R.id.tab_more){
                    getSupportFragmentManager().beginTransaction().replace(R.id.layarframelayout,new MoreFragment()).commit();
                }

            }
        });

        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(int tabId) {
                if (tabId == R.id.tab_home){
                    getSupportFragmentManager().beginTransaction().replace(R.id.layarframelayout,new MoreFragment()).commit();
                }
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.layarframelayout,new HomeFragment()).commit();

        if (AccessToken.getCurrentAccessToken() != null){
            fblogin = (LoginButton)findViewById(R.id.fb_login);
            fblogin.setVisibility(View.VISIBLE);
            graphApiFetchData();
        }

    }

    private void graphApiFetchData() {
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.e("aa", ""+response.toString());
                        try {
                            TextView tvid = (TextView)findViewById(R.id.tvid);
                            tvid.setText(object.getString("id"));

                            TextView tvemail = (TextView)findViewById(R.id.tvemail);
                            tvemail.setText(object.getString("name"));

                            TextView phone = (TextView)findViewById(R.id.tvphone);
                            phone.setText(object.getInt("birthday"));

                            Toast.makeText(getApplicationContext(), "Hi, " + object.getString("name"), Toast.LENGTH_LONG).show();
                        } catch(JSONException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,birthday");
        request.setParameters(parameters);
        request.executeAsync();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);

    }



    @Override
    protected void onResume() {
        super.onResume();

        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
            @Override
            public void onSuccess(final Account account) {
                // Get Account Kit ID
                TextView tvid = (TextView)findViewById(R.id.tvid);
                String accountKitId = account.getId().toString().trim();
                tvid.setText(accountKitId);

                // Get phone number
                TextView phone = (TextView)findViewById(R.id.tvphone);
                PhoneNumber phoneNumber = account.getPhoneNumber();
                if (phoneNumber != null) {
                    String phoneNumberString = phoneNumber.toString();
                    phone.setText(phoneNumberString);
                }else {
                    phone.setText("telp");
                }

                // Get email
                TextView tvemail = (TextView)findViewById(R.id.tvemail);
                String email = account.getEmail();
                tvemail.setText(email);
            }

            @Override
            public void onError(final AccountKitError error) {
                // Handle Error
                //Toast.makeText(MainActivity.this, "Account Kit ERROR ", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void Logout(View view) {
        AccountKit.logOut();
        finish();
    }
}
