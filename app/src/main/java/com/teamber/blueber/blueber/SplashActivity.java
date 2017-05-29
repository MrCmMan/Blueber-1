package com.teamber.blueber.blueber;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.teamber.blueber.blueber.AutorisationActivity.APP_PREFERENCES;
import static com.teamber.blueber.blueber.AutorisationActivity.APP_PREFERENCES1;

/**
 * Created by Администратор on 25.01.2017.
 */
public class SplashActivity extends AppCompatActivity {
    RequestQueue requestQueue;
    String serverURL = "http://blueber.ru/users_read.php";
    private String user_i;
    private String rands;

    String pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        final String user_id = mSettings.getString(APP_PREFERENCES, "");
        final String rand = mSettings.getString(APP_PREFERENCES1, "");

        Bundle bundle = new Bundle();
        bundle.putString("us_id", user_id);
        bundle.putString("us_ps", rand);
// set Fragmentclass Arguments
        ProfileFragments obj = new ProfileFragments();
        obj.setArguments(bundle);

       requestQueue= Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                serverURL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray sign = response.getJSONArray("users");//array of DataBase

                    for(int i = 0 ; i < sign.length();i++){
                        JSONObject signs = sign.getJSONObject(i);
                        user_i = signs.getString("sid");
                        rands = signs.getString("pass");

                        if ((user_id.equals(user_i))&&(rands.equals(rand))) { //authorisation success
                            System.out.println("NORAMLEK");

                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                            startActivity(intent);
                            break;
                        }else{
                            if(i==sign.length()-1){
                            Intent intent = new Intent(getApplicationContext(), AutorisationActivity.class);
                            startActivity(intent);

                            System.out.println("ERROR AUTORISATION");}
                        }



                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }
                 }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        });



        requestQueue.add(jsonObjectRequest);




    }


}