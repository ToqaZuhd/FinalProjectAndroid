package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {

    private EditText edtUserName,edtEmail,edtPassword,edtConfirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        edtUserName = findViewById(R.id.UserName);
        edtEmail = findViewById(R.id.editTextTextEmailAddress);
        edtPassword = findViewById(R.id.Password);
        edtConfirm=findViewById(R.id.ConfirmPassword);
    }


    public void btnClkRegister(View view) {
        String UserName = edtUserName.getText().toString();
        String Email = edtEmail.getText().toString();
        String Password = edtPassword.getText().toString();
        String confirmPassword = edtConfirm.getText().toString();

        if (UserName.isEmpty())
            Toast.makeText(Registration.this,("Please fill username field"), Toast.LENGTH_SHORT).show();

        else if (Email.isEmpty())
            Toast.makeText(Registration.this,("Please fill email field"), Toast.LENGTH_SHORT).show();

        else if (Password.isEmpty())
            Toast.makeText(Registration.this,("Please fill password field"), Toast.LENGTH_SHORT).show();
        else if (confirmPassword.isEmpty())
            Toast.makeText(Registration.this,("Please fill Confirm password field"), Toast.LENGTH_SHORT).show();

        else if (!Password.equals(confirmPassword))
            Toast.makeText(Registration.this,("No matching between password field and confirm password field"), Toast.LENGTH_SHORT).show();

        else addPerson(UserName, Email, Password);
    }

    private void addPerson(String UserName, String Email, String Password){
        String url = "http://10.0.2.2:80/FinalProject/AddUsers.php";
        RequestQueue queue = Volley.newRequestQueue(Registration.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "RESPONSE IS " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(Registration.this,
                            jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(Registration.this,
                        "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                // as we are passing data in the form of url encoded
                // so we are passing the content type below
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {

                // below line we are creating a map for storing
                // our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our
                // key and value pair to our parameters.
                params.put("username", UserName);
                params.put("email", Email);
                params.put("password", Password);

                // at last we are returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }



    public void btnClkForLogin(View view) {
        Intent intent =new Intent(Registration.this,SignIn.class);
        startActivity(intent);
    }
}