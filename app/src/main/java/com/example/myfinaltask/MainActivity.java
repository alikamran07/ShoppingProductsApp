package com.example.myfinaltask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CategoryAdapterClass.CategoryCallBack {

    RadioGroup btnGroup;
    Spinner spnList;
    RecyclerView recyclerView;
    RecyclerView recyclerView_main;
    ProductAdapterClass myAdapter;
    CategoryAdapterClass categoryAdapter;
    List<MyModelCLassProduct> listData;
    List<MyModelCLassProduct> filterList;
    ArrayList<String> categorys;
    KProgressHUD progBar;
    boolean flag = false;

    CategoryAdapterClass.CategoryCallBack categoryCallBack = this;

    String str[]={"all","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20"};

    private static String JSON_URL_CATEGORY = "https://fakestoreapi.com/products/categories";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progBar=KProgressHUD.create(MainActivity.this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setLabel("InProgress").setCancellable(true);
        progBar.show();

        recyclerView = findViewById(R.id.my_recycler_horizontal);
        recyclerView_main = findViewById(R.id.my_recycler);
        spnList=findViewById(R.id.spinnerList);
        btnGroup=findViewById(R.id.groupbtn);

        listData = new ArrayList<>();
        categorys = new ArrayList<String>();

        extractCategoryData();
        extractProductData("https://fakestoreapi.com/products");

        btnGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.ascendingbtn:
                        if(checkConnection()){
                        listData.clear();
                        extractProductData("https://fakestoreapi.com/products?sort=asc");
                        Toast.makeText(getApplicationContext(), "Ascending", Toast.LENGTH_SHORT).show();}
                        break;
                    case R.id.descendingbtn:
                        if(checkConnection()){
                        listData.clear();
                        extractProductData("https://fakestoreapi.com/products?sort=desc");
                        Toast.makeText(getApplicationContext(), "Descending", Toast.LENGTH_SHORT).show();}
                        break;
                }
            }
        });

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, str);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view

        spnList.setAdapter(spinnerArrayAdapter);
        spnList.setSelection(0);

        spnList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (flag){
                    int items = spnList.getSelectedItemPosition();
                    listData.clear();
                    String limitValue = str[items];
                    extractProductData("https://fakestoreapi.com/products?limit=" + limitValue);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void extractCategoryData() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, JSON_URL_CATEGORY, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                categorys.add("all");
                for (int i = 0; i < response.length(); i++) {

                    try {

                        categorys.add("" + response.getString(i));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
                categoryAdapter = new CategoryAdapterClass(getApplicationContext(), categorys, categoryCallBack);
                recyclerView.setAdapter(categoryAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", "Getting Error " + error);
            }
        });
//          for increase response time
//        request.setRetryPolicy(new DefaultRetryPolicy(8000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

    private void extractProductData(String url) {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject jsonObj = response.getJSONObject(i);

                        listData.add(new MyModelCLassProduct(Integer.parseInt(jsonObj.getString("id")),
                                jsonObj.getString("title"), Double.parseDouble(jsonObj.getString("price")),
                                jsonObj.getString("description"), jsonObj.getString("category"),
                                jsonObj.getString("image"),
                                jsonObj.getJSONObject("rating").getDouble("rate"),
                                jsonObj.getJSONObject("rating").getInt("count")));
                        filterList = listData;
                        flag = true;
                        myAdapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                recyclerView_main.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
                myAdapter = new ProductAdapterClass(getApplicationContext(), listData);
                recyclerView_main.setAdapter(myAdapter);

                progBar.dismiss();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", "Getting Error " + error);
            }
        });
//        for increase response time
//        request.setRetryPolicy(new DefaultRetryPolicy(8000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

    public void filter(String text) {
        List<MyModelCLassProduct> filteredList = new ArrayList<>();

        if (text.equals("all")) {
            filteredList = filterList;

        } else {
            for (MyModelCLassProduct f : listData) {

                if (f.getCategory().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(f);
                }
            }
        }
        myAdapter.filterList(filteredList);
    }

    @Override
    public void CatergotyClick(String s) {
        filter(s);
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