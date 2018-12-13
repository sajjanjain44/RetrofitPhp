package com.zxsofficials.retrofitphp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.zxsofficials.retrofitphp.activity.MainActivity;
import com.zxsofficials.retrofitphp.fragments.HomeFragment;
import com.zxsofficials.retrofitphp.fragments.SettingsFragment;
import com.zxsofficials.retrofitphp.fragments.UsersFragment;
import com.zxsofficials.retrofitphp.model.User;
import com.zxsofficials.retrofitphp.storage.SharedPrefManager;

public class ProfileActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        User user = SharedPrefManager.getInstance(this).getUser();

        bottomNavigationView = findViewById(R.id.bottomNavigationView2);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        displayFragment(new HomeFragment());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        Fragment fragment = null;
        switch (id) {
            case R.id.menu_home:
                fragment = new HomeFragment();
                break;

            case R.id.menu_users:
                fragment = new UsersFragment();
                break;

            case R.id.menu_settings:
                fragment = new SettingsFragment();
                break;
        }

        if(fragment != null){
            displayFragment(fragment);
        }
        return false;
    }


    private void displayFragment(Fragment fragment){

//        Log.d("Testing123",fragment.toString());
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();

    }
}
