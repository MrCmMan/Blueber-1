package com.teamber.blueber.blueber;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.facebook.*;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.*;
import com.vk.sdk.api.model.VKApiUser;
import com.vk.sdk.api.model.VKList;
import com.vk.sdk.dialogs.VKCaptchaDialog;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


public class AutorisationActivity extends AppCompatActivity {
    private String[] scope = new String[]{VKScope.MESSAGES, VKScope.FRIENDS, VKScope.WALL};
    Button btnA;
    String pos;
    String logF;
    LoginButton loginButton;
    CallbackManager callbackManager;
    public static String LOG_TAG = "my_log";
    public static final String APP_PREFERENCES = "you_gay";
    public static final String APP_PREFERENCES1 = "you_gay1";

    SharedPreferences mSettings;
    String name;
    Integer user_id;
    String fl;
    String first_name;
    String last_name;
    String ll;
    String f = "f";
    String pas;
    TextView txtView;
    String serverURL = "http://blueber.ru/users_add.php";
    RequestQueue requestQueue;
    String serverURL1 = "http://blueber.ru/users_read.php";
    AlertDialog.Builder builder;
    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";
    static String jjson = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autorisation_layout);
        builder = new AlertDialog.Builder(AutorisationActivity.this);
        btnA = (Button) findViewById(R.id.btnA);
        loginButton = (LoginButton)findViewById(R.id.login_btn);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();// sign facebook api
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                // login ok get access token
                GraphRequest request = GraphRequest.newMeRequest(
                        AccessToken.getCurrentAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object,
                                                    GraphResponse response) {

                                if (BuildConfig.DEBUG) {
                                    FacebookSdk.setIsDebugEnabled(true);
                                    FacebookSdk
                                            .addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);


                                    logF = Profile.getCurrentProfile().getId();

                                    System.out.println(logF);
                                    fl = Profile.getCurrentProfile().getFirstName();
                                    ll = Profile.getCurrentProfile().getLastName(); //download preferenses
                                    Double rand = Math.random();  //random password
                                    //String pos = rand.toString();
                                    mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
                                    String strNickName = logF;

                                    String strPassword = rand.toString();
                                    pos = strPassword;
                                    SharedPreferences.Editor editor = mSettings.edit();
                                    editor.putString(APP_PREFERENCES, strNickName);
                                    editor.putString(APP_PREFERENCES1, strPassword);

                                    editor.apply();
                                    //Profile.getCurrentProfile().getProfilePictureUri(50, 50);
                                    //String email=UserManager.asMap().get(“email”).toString();

                                    StringRequest stringRequest = new StringRequest(Request.Method.POST, serverURL,
                                            new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {


                                                }
                                            }

                                            , new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(AutorisationActivity.this,"ERROR...",Toast.LENGTH_LONG).show();
                                            error.printStackTrace();
                                        }
                                    }){

                                        @Override
                                        protected Map<String,String> getParams() throws AuthFailureError {
                                            Map<String,String> params = new HashMap<String,String>();
                                                //array on input data on BackEnd.
                                            params.put("sid", logF);
                                            params.put("fname", fl);
                                            params.put("lname",ll);
                                            params.put("stype",f);
                                            params.put("pass",pos);

                                            return params;



                                        }
                                    };

                                    Signup.getmInstance(AutorisationActivity.this).addTorequestque(stringRequest);

                                    Intent intent = new Intent(AutorisationActivity.this, MainActivity.class);
                                    intent.putExtra("sid",logF);
                                    startActivity(intent);






















                                }
                            }
                        });
                request.executeAsync();


               /*AccessToken accessToken = loginResult.getAccessToken();
               // logF = loginResult.getAccessToken().getUserId();
                Profile profile= Profile.getCurrentProfile();
                fl = profile.getFirstName();
                 //ll = profile.getLastName();
               // System.out.println(fl);
                /**/

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
        txtView = (TextView) findViewById(R.id.txtviev);
        View.OnClickListener btnACT = new View.OnClickListener() {
            @Override
            public void onClick(View view) {        //sign VK sdk (api)
                VKSdk.login(AutorisationActivity.this, scope);
                String token = VKSdk.getAccessToken().accessToken;
                VKParameters parameters = VKParameters.from(VKApiConst.ACCESS_TOKEN, token);
                VKApi.users().get().executeWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        VKApiUser user = ((VKList<VKApiUser>)response.parsedModel).get(0);
                        user_id = user.id;
                        first_name = user.first_name;
                        last_name = user.last_name;
                        name = user_id.toString();

                        final Double rand = Math.random();  //Рандомный пароль
                        //String pos = rand.toString();
                        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
                        String strNickName = user_id.toString();
                        String strPassword = rand.toString();
                        SharedPreferences.Editor editor = mSettings.edit();
                        editor.putString(APP_PREFERENCES, strNickName);
                        editor.putString(APP_PREFERENCES1, strPassword);
                        pas=rand.toString();

                        editor.apply();
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, serverURL,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {


                                    }
                                }

                                , new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(AutorisationActivity.this,"ERROR...",Toast.LENGTH_LONG).show();
                                error.printStackTrace();
                            }
                        }){

                            @Override
                            protected Map<String,String> getParams() throws AuthFailureError {
                                Map<String,String> params = new HashMap<String,String>();
                                System.out.println(name);
                                params.put("sid", name);
                                params.put("fname", first_name);
                                params.put("lname",last_name);
                                params.put("pass",pas);
                                params.put("stype","v");

                                return params;



                            }
                        };

                        Signup.getmInstance(AutorisationActivity.this).addTorequestque(stringRequest);
                    }
                });
                class VKRequestListener extends VKRequest.VKRequestListener {
                    private final Context mContext;

                    public VKRequestListener(Context context) {
                        mContext = context;
                    }

                    @Override
                    public void onError(VKError error) {
                        super.onError(error);
                        if (error.errorCode == 14) {
                            VKCaptchaDialog vkCaptchaDialog = new VKCaptchaDialog(error);
                            vkCaptchaDialog.show(mContext, new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {

                                }
                            });
                        }
                    }
                }











                }//конец обработчика кнопки

             };
        btnA.setOnClickListener(btnACT);
        Toolbar t = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(t);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode,resultCode,data);
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                Intent intent = new Intent(AutorisationActivity.this, MainActivity.class);
                startActivity(intent);

// Пользователь успешно авторизовался и переходит на функционал.
            }

            @Override
            public void onError(VKError error) {

// Произошла ошибка авторизации (например, пользователь запретил авторизацию)
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }







}