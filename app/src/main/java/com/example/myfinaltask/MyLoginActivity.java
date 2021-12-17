package com.example.myfinaltask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONObject;

public class MyLoginActivity extends AppCompatActivity {

    EditText name, password;
    String user_name, user_password;
    Button login;
    RequestQueue queue;
    JsonArrayRequest request;
    KProgressHUD progBar;
    boolean c=true;
    private static String JSON_URL = "https://fakestoreapi.com/users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_login);
        queue = Volley.newRequestQueue(this);
        name = findViewById(R.id.userName);
        password = findViewById(R.id.userPassword);
        login = findViewById(R.id.bLogin);



        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(checkConnection()){
                checkUserExist();
            } else{
                    Toast.makeText(getApplicationContext(), "No Network", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void checkUserExist() {

        user_name = name.getText().toString();
        user_password = password.getText().toString();
        request = new JsonArrayRequest(Request.Method.GET, JSON_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(!user_name.equals("") && !user_password.equals(""))
                {
                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject jsonObj = response.getJSONObject(i);

                        if (user_name.equals(jsonObj.getString("username").toString()) && user_password.equals(jsonObj.getString("password").toString())) {

                            Intent intent = new Intent(MyLoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            c=true;
                            return;

                        }
                        else {
                            c=false;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }}
                else
                {
                    Toast.makeText(getApplicationContext(), "Empty", Toast.LENGTH_SHORT).show();
                }
                if(c==false)
                {
                    Toast.makeText(getApplicationContext(), "Wrong Password/UserName", Toast.LENGTH_SHORT).show();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", "Getting Error " + error);
            }
        });
        queue.add(request);
    }
    boolean checkConnection()
    {
        ConnectivityManager manager= (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork=manager.getActiveNetworkInfo();
        if(activeNetwork!=null)
        {
            if(activeNetwork.getType()==ConnectivityManager.TYPE_WIFI)
            {
              //  Toast.makeText(getApplicationContext(), "Wifi Enabled", Toast.LENGTH_SHORT).show();
                return true;
            }
            else if(activeNetwork.getType()==ConnectivityManager.TYPE_MOBILE)
            {
              //  Toast.makeText(getApplicationContext(), "Mobile Data Enabled", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        else
        {

            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            return false;
        }
        return false;
    }
}
