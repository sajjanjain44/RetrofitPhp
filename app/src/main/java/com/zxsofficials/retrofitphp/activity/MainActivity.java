package com.zxsofficials.retrofitphp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.zxsofficials.retrofitphp.ProfileActivity;
import com.zxsofficials.retrofitphp.model.DefaultResponse;
import com.zxsofficials.retrofitphp.R;
import com.zxsofficials.retrofitphp.api.RetrofitClient;
import com.zxsofficials.retrofitphp.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextEmail, editTextPassword, editTextName, editTextSchool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextName = findViewById(R.id.editTextName);
        editTextSchool = findViewById(R.id.editTextSchool);

        findViewById(R.id.textViewLogin).setOnClickListener(this);
        findViewById(R.id.buttonSignUp).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textViewLogin:
                userLogin();
                break;
            case R.id.buttonSignUp:
                userSignUp();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (SharedPrefManager.getInstance(this).isLoggedIn()){
            Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private void userLogin() {
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
    }

    private void userSignUp() {

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        String school = editTextSchool.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("Email is Required");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Enter a valid Email.");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password is Required");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Password Length is too Short");
            editTextPassword.requestFocus();
            return;
        }
        if (name.isEmpty()) {
            editTextName.setError("Name is Required");
            editTextName.requestFocus();
            return;
        }

        if (school.isEmpty()) {
            editTextSchool.setError("School is Required");
            editTextSchool.requestFocus();
            return;
        }

        /*do user regestration by API call*/

//        Call<ResponseBody> call =
//                RetrofitClient.getInstance()
//                        .getApi()
//                        .createUser(email, password, name, school);
//
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//
//                String s = null;
//
//                try {
//                    if (response.code() == 201) {
//
//
//                        s = response.body().string();
//                        Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
//
//                    } else {
//
//                        s = response.errorBody().string();
//                        Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
//
//                    }
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                if(s != null){
//
//                    try {
//                        JSONObject jsonObject= new JSONObject(s);
//                        if(jsonObject.getBoolean("error")){
//                            Toast.makeText(MainActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        });


        Call<DefaultResponse> call = RetrofitClient.getInstance().getApi().createUser(email,password,name,school);
        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {


                if(response.code() == 201){

                   DefaultResponse defaultResponse =  response.body();
                    Toast.makeText(MainActivity.this, defaultResponse.getMsg(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "User not created", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Not connected to database!", Toast.LENGTH_SHORT).show();


            }
        });

    }
}