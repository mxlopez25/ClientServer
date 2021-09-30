package com.mlopez.clientapp;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Address;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {
    public final static String TAG = "DetailsActivity";
    public String userId = "0";
    public String firstName = "";
    public String lastName = "";
    ListView lvAddresses;
    ProgressBar loading;
    TextView tvUserName, txtNoAddress;
    ArrayList<AddressModel> addressList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        lvAddresses = (ListView) findViewById(R.id.lv_addresses);
        tvUserName = (TextView) findViewById(R.id.txt_user_name);
        txtNoAddress = (TextView) findViewById(R.id.txt_no_address);
        loading = (ProgressBar) findViewById(R.id.loading);
        Bundle extras = getIntent().getExtras();
        userId = extras.getString("userId");
        firstName = extras.getString("firstName");
        lastName = extras.getString("lastName");
        tvUserName.setText(new StringBuilder()
        .append(firstName)
        .append(" ")
        .append(lastName));

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://clientserver.azurewebsites.net/api/Address/user/" + userId;

        // Request a string response from the provided URL.
        StringRequest request = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if(!response.equals("[]")) {
                                txtNoAddress.setVisibility(View.GONE);
                                JSONArray array = new JSONArray(response);
                                for(int i = 0 ; i < array.length() ; i++) {
                                    JSONObject address = array.getJSONObject(i);
                                    Log.d(TAG, address.toString());
                                    addressList.add(new AddressModel(
                                            address.getInt("addressId"),
                                            address.getInt("userId"),
                                            address.getString("street1"),
                                            address.getString("street2"),
                                            address.getString("city"),
                                            address.getString("zipCode"),
                                            address.getString("phone")
                                    ));
                                }
                                AddressAdapter adapter = new AddressAdapter(getApplicationContext(), addressList);
                                lvAddresses.setAdapter(adapter);
                                loading.setVisibility(View.GONE);
                            } else {
                                loading.setVisibility(View.GONE);
                                txtNoAddress.setVisibility(View.VISIBLE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
//                            txtResult.setText("Error: " + response);
                            loading.setVisibility(View.GONE);
                            Log.e(TAG, "Error: " + response);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.setVisibility(View.GONE);
                Log.e(TAG, "Error: " + error.getMessage());
            }
        });
        // Add the request to the RequestQueue.
        queue.add(request);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}