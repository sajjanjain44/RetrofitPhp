package com.zxsofficials.retrofitphp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.zxsofficials.retrofitphp.ProfileActivity;
import com.zxsofficials.retrofitphp.R;
import com.zxsofficials.retrofitphp.api.RetrofitClient;
import com.zxsofficials.retrofitphp.model.DefaultResponse;
import com.zxsofficials.retrofitphp.model.LoginResponse;
import com.zxsofficials.retrofitphp.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextEmail, editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        findViewById(R.id.buttonLogin).setOnClickListener(this);
        findViewById(R.id.textViewLogin).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonLogin:
                userLogin();
                break;
            case R.id.textViewLogin:
                userLoginToSignUp();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (SharedPrefManager.getInstance(this).isLoggedIn()){
            Intent intent = new Intent(LoginActivity.this,ProfileActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Enter a Valid Email ");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password is Required!");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Enter a Valid Password");
            editTextPassword.requestFocus();
            return;
        }

        /*check login from api*/

        Call<LoginResponse> call = RetrofitClient.getInstance().getApi().userLogin(email, password);

       call.enqueue(new Callback<LoginResponse>() {
           @Override
           public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
               LoginResponse loginResponse = response.body();

               if (!loginResponse.isError()){
                   /*prodeed to login*/

                   /*save user*/
                   /*open profile*/

                   SharedPrefManager.getInstance(LoginActivity.this).saveUser(loginResponse.getUser());
                   Intent intent = new Intent(LoginActivity.this,ProfileActivity.class);
                   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                   startActivity(intent);
               }
               else {
                   Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
               }
           }

           @Override
           public void onFailure(Call<LoginResponse> call, Throwable t) {

           }
       });





    }

    private void userLoginToSignUp() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
