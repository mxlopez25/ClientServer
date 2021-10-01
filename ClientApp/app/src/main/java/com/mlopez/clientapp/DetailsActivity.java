package com.mlopez.clientapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetailsActivity extends AppCompatActivity {
    public final static String TAG = "DetailsActivity";
    public String userId = "0";
    public String firstName = "";
    public String lastName = "";
    ListView lvAddresses;
    ProgressBar loading;
    Button btnAddAddress, btnAddAddressDialog;
    TextView tvUserName, txtNoAddress;
    EditText edtStreet1, edtStreet2, edtCity, edtZipCode, edtPhone;
    ArrayList<AddressModel> addressList = new ArrayList<>();
    View dView;
    AlertDialog addDialog;
    AlertDialog.Builder addAddressDialog;
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
        btnAddAddress = (Button) findViewById(R.id.btn_add_address);
        tvUserName.setText(new StringBuilder()
        .append(firstName)
        .append(" ")
        .append(lastName));

        LayoutInflater inflater = DetailsActivity.this.getLayoutInflater();
        dView = inflater.inflate(R.layout.add_address_dialog_layout, null);
        edtStreet1 = (EditText) dView.findViewById(R.id.edt_street_1);
        edtStreet2 = (EditText) dView.findViewById(R.id.edt_street_2);
        edtCity = (EditText) dView.findViewById(R.id.edt_city);
        edtPhone = (EditText) dView.findViewById(R.id.edt_phone);
        edtZipCode = (EditText) dView.findViewById(R.id.edt_zip_code);
        btnAddAddressDialog = (Button) dView.findViewById(R.id.btn_add_address_dialog);
        addAddressDialog = new AlertDialog.Builder(this);
        addAddressDialog.setView(dView);
//        addAddressDialog.setPositiveButton("Add Address", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                if(!edtStreet1.getText().toString().isEmpty()) {
//                    addAddress();
//                }
//                return;
//            }
//        });

        addAddressDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                ((ViewGroup)dView.getParent()).removeView(dView);
            }
        });

        btnAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDialog = addAddressDialog.create();
                addDialog.show();
            }
        });

        btnAddAddressDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateField(edtStreet1, "Please enter Street 1") &&
                    validateField(edtStreet2, "Please enter Street 2") &&
                    validateField(edtCity, "Please enter City") &&
                    validateField(edtZipCode, "Please enter ZipCode") &&
                    validateField(edtPhone, "Please enter Phone number")) {
                    addAddress();
                }
                return;
            }
        });

        requestData();
    }

    public void addAddress() {
        loading.setVisibility(View.VISIBLE);
        lvAddresses.setAdapter(null);
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://clientserver.azurewebsites.net/api/Address/";
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("street1", edtStreet1.getText().toString());
        params.put("street2", edtStreet2.getText().toString());
        params.put("city", edtCity.getText().toString());
        params.put("zipCode", edtZipCode.getText().toString());
        params.put("phone", edtPhone.getText().toString());

        JSONObject parameters = new JSONObject(params);
        // Request a string response from the provided URL.
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        addDialog.cancel();
                        ((ViewGroup)dView.getParent()).removeView(dView);
                        edtCity.getText().clear();
                        edtPhone.getText().clear();
                        edtStreet1.getText().clear();
                        edtStreet2.getText().clear();
                        edtZipCode.getText().clear();
                        requestData();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        addDialog.cancel();
                        ((ViewGroup)dView.getParent()).removeView(dView);
                        Log.e(TAG, error.getMessage());
                        loading.setVisibility(View.GONE);
                    }
                }) {};

        // Add the request to the RequestQueue.
        queue.add(request);
    }

    public boolean validateField(EditText field, String message) {
        if(!field.getText().toString().isEmpty()) {
            field.setError(null);
            return true;
        }
        field.setError(message);
        return false;
    }

    public void requestData() {
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