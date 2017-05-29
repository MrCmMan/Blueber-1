package com.teamber.blueber.blueber;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.vk.sdk.VKUIHelper.getApplicationContext;

/**
 * Created by Администратор on 05.03.2017.
 */
public class ProfileFragments extends android.support.v4.app.Fragment {
    private static final String profileFrag = "Profile";
    RequestQueue requestQueue;
    String serverURL = "http://blueber.ru/users_read.php";
     String user_i;
     String rands;
    TextView txtname;
    TextView txtfollrs;
    TextView txtfollors;
    TextView txtfollwng;
    TextView txtfollwing;
    Typeface typeface;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {




        View view = null;
        String fontPath = "fonts/9887.otf";
        typeface = Typeface.createFromAsset(getContext().getAssets(), fontPath);
        view = inflater.inflate(R.layout.profile_layout, null);
         txtname = (TextView) view.findViewById(R.id.frsname);
        txtfollrs = (TextView) view.findViewById(R.id.followrs);
        txtfollwng = (TextView) view.findViewById(R.id.followng);
        txtfollwing = (TextView) view.findViewById(R.id.following);
        txtfollors = (TextView) view.findViewById(R.id.followers);
        txtfollors.setTypeface(typeface);
        txtfollrs.setTypeface(typeface);
        txtfollwing.setTypeface(typeface);
        txtfollwng.setTypeface(typeface);
        final String us_id = getArguments().getString("us_id");
        final String us_ps = getArguments().getString("us_ps");
        requestQueue= Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, serverURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                    }
                }

                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
            }
        }){

            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                //array on input data on BackEnd.
                params.put("sid", us_id);
                params.put("pass", us_ps);


                return params;



            }
        };

        Signup.getmInstance(getActivity()).addTorequestque(stringRequest);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                serverURL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray sign = response.getJSONArray("users");
                    for (int i = 0; i < sign.length(); i++) {
                        JSONObject signs = sign.getJSONObject(i);

                        txtname.setTypeface(typeface);
                        txtname.setText(rands);
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
        return view;
    }


}
