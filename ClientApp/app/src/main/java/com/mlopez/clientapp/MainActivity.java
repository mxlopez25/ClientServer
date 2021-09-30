package com.mlopez.clientapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public final static String TAG = "MainActivity";
//    TextView txtResult;
    ListView lvMain;
    ArrayList<UserModel> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        txtResult = findViewById(R.id.txt_result);
        lvMain = findViewById(R.id.lv_main_list);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://clientserver.azurewebsites.net/api/User/all";

        // Request a string response from the provided URL.
        StringRequest request = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for(int i = 0 ; i < array.length() ; i++) {
                                JSONObject user = array.getJSONObject(i);
                                Log.d(TAG, user.toString());
                                userList.add(new UserModel(user.getInt("userId"), user.getString("firstName"), user.getString("lastName")));
                            }
                            CustomAdapter adapter = new CustomAdapter(getApplicationContext(), userList);
                            lvMain.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
//                            txtResult.setText("Error: " + response);
                            Log.e(TAG, "Error: " + response);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
            }
        });
        // Add the request to the RequestQueue.
        queue.add(request);


        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "User: " + userList.get(position).getUserId(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("userId", "" + userList.get(position).getUserId());
                intent.putExtra("firstName", userList.get(position).getFirstName());
                intent.putExtra("lastName", userList.get(position).getLastName());
                startActivity(intent);
            }
        });
    }
}