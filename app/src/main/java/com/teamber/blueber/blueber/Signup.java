package com.teamber.blueber.blueber;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Администратор on 05.02.2017.
 */
public class Signup {
    private static Signup mInstance;
    private RequestQueue requestQueue;
    private static Context mCtx;
    private Signup(Context context){
        mCtx = context;
        requestQueue = getRequestQueue();

    }
    public static synchronized Signup getmInstance(Context context){
        if(mInstance==null){
            mInstance = new Signup(context);
        }
     return mInstance;
    }


        public RequestQueue getRequestQueue(){
            if(requestQueue == null){
                requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
            }
         return requestQueue;


        }

        public <T> void addTorequestque(Request<T> request){

        requestQueue.add(request);

        }





}

