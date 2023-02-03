package com.undeniabledreams.cloudbudgettrackerbyjava.dao;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.undeniabledreams.cloudbudgettrackerbyjava.core.BudgetTrackerUserDto;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class BudgetTrackerUserDao {
    private Context mContext;

    public BudgetTrackerUserDao(Context context) {
        mContext = context.getApplicationContext();
    }

    public int insertIntoLoginTbl(BudgetTrackerUserDto budgetTrackerUserDto) throws IOException {

        try {
            Properties properties = new Properties();
            InputStream inputStream = mContext.getAssets().open("server_config.properties");
            properties.load(inputStream);
            String serverUrl = properties.getProperty("server_url");
            String phpInsertFile = properties.getProperty("insert_php_file");
            String insert_url = serverUrl + phpInsertFile;
            // do something with the url
            // Log the URL to check if it's correct
            Log.d("insert_url", insert_url);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, insert_url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");

                                if (success.equals("1")) {
                                    Toast.makeText(mContext, "User data has been inserted", Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                Log.e("JSONException", e.toString());
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error instanceof ServerError) {
                                // handle detailed error here
                                NetworkResponse networkResponse = error.networkResponse;
                                if (networkResponse != null) {
                                    Log.e("Volley", "Error. HTTP Status Code:" + networkResponse.statusCode);
                                }
                            }
                            Toast.makeText(mContext, "Unable to insert data" + error.toString(), Toast.LENGTH_SHORT).show();
                            Log.e("VolleyError", error.toString());
                        }
                    }) {
                //We need to set Parameters that will also match with the API
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    //We need hashmap, this is equivalent to array
                    Map<String, String> params = new HashMap<>();
                    params.put("user_name", budgetTrackerUserDto.getId());
                    params.put("password", budgetTrackerUserDto.getPassword());

                    return params;
                }
            };
            //Let a requestQue which enables us to use Volley.
            RequestQueue requestQueue = Volley.newRequestQueue(mContext);
            requestQueue.add(stringRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int logIn(BudgetTrackerUserDto budgetTrackerUserDto) throws IOException {
        final int[] result = {0};
        try {
            Properties properties = new Properties();
            InputStream inputStream = mContext.getAssets().open("server_config.properties");
            properties.load(inputStream);
            String serverUrl = properties.getProperty("server_url");
            String phpLoginFile = properties.getProperty("login_php_file");
            String login_url = serverUrl + phpLoginFile;
            // do something with the url
            // Log the URL to check if it's correct
            Log.d("login_url", login_url);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, login_url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");
                                if (success.equals("1")) {
                                    result[0] = 1;
                                }
                            } catch (JSONException e) {
                                Log.e("JSONException", e.toString());
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
//                            Toast.makeText(mContext, "Unable to send data" + error.toString(), Toast.LENGTH_SHORT).show();
                            if (mContext != null) {
                                Toast.makeText(mContext, "Unable to send data" + error.toString(), Toast.LENGTH_SHORT).show();
                            }
                            Log.e("VolleyError", error.toString());
                        }
                    }) {
                //We need to set Parameters that will also match with the API
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    //We need hashmap, this is equivalent to array
                    Map<String, String> params = new HashMap<>();
                    params.put("user_name", budgetTrackerUserDto.getId());
                    params.put("password", budgetTrackerUserDto.getPassword());

                    return params;
                }
            };
            //Let a requestQue which enables us to use Volley.
            RequestQueue requestQueue = Volley.newRequestQueue(mContext);
            requestQueue.add(stringRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result[0];
    }
}
