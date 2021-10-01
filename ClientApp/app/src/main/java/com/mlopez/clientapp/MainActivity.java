package com.mlopez.clientapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
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
    ListView lvMain;
    ArrayList<UserModel> userList = new ArrayList<>();
    ProgressBar pbLoading;
    Button btnAddUser, btnAddUserDialog;
    AlertDialog.Builder addUserDialog;
    EditText edtFirstName, edtLastName;
    View dView;
    AlertDialog addDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        txtResult = findViewById(R.id.txt_result);
        lvMain = findViewById(R.id.lv_main_list);
        btnAddUser = findViewById(R.id.btn_add_user);
        pbLoading = findViewById(R.id.loading_anim);
        pbLoading.setVisibility(View.VISIBLE);

        LayoutInflater inflater = MainActivity.this.getLayoutInflater();
        dView = inflater.inflate(R.layout.add_user_dialog_layout, null);
        edtFirstName = (EditText) dView.findViewById(R.id.edt_first_name);
        edtLastName = (EditText) dView.findViewById(R.id.edt_last_name);
        btnAddUserDialog = (Button) dView.findViewById(R.id.btn_add_user_dialog);

        requestData();

        addUserDialog = new AlertDialog.Builder(this);
        addUserDialog.setView(dView);


//        addUserDialog.setPositiveButton("Add User", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                addUser();
//            }
//        });

        addUserDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                ((ViewGroup)dView.getParent()).removeView(dView);
            }
        });

        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDialog = addUserDialog.create();
                addDialog.show();
            }
        });

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

        btnAddUserDialog.setOnClickListener(v -> {
            if(validateField(edtFirstName, "Please insert Firstname") && validateField(edtLastName, "Please insert Lastname")) {
                addUser();
            }
            return;
        });
    }

    public boolean validateField(EditText field, String message) {
        if(!field.getText().toString().isEmpty()) {
            field.setError(null);
            return true;
        }
        field.setError(message);
        return false;
    }

    public void addUser() {
        pbLoading.setVisibility(View.VISIBLE);
        lvMain.setAdapter(null);
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://clientserver.azurewebsites.net/api/User/";
        Map<String, String> params = new HashMap<>();
        params.put("firstName", edtFirstName.getText().toString());
        params.put("lastName", edtLastName.getText().toString());

        JSONObject parameters = new JSONObject(params);
        // Request a string response from the provided URL.
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        addDialog.cancel();
                        ((ViewGroup)dView.getParent()).removeView(dView);
                        requestData();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        addDialog.cancel();
                        ((ViewGroup)dView.getParent()).removeView(dView);
                        Log.d(TAG, error.getMessage());
                    }
                }) {};

        // Add the request to the RequestQueue.
        queue.add(request);
    }

    public void requestData() {
        userList.clear();
        pbLoading.setVisibility(View.VISIBLE);
        lvMain.setAdapter(null);
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
                            pbLoading.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            pbLoading.setVisibility(View.GONE);
                            Log.e(TAG, "Error: " + response);
                            Toast.makeText(MainActivity.this, "Error: " + response, Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                pbLoading.setVisibility(View.GONE);
            }
        });
        // Add the request to the RequestQueue.
        queue.add(request);
    }
}