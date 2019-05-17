package com.example.elina.application.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.elina.application.R;
import com.example.elina.application.fragments.AddFragment;
import com.example.elina.application.fragments.ListFragment;
import com.example.elina.application.fragments.ScannerFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private ListFragment listFragment;
    private AddFragment addFragment;
    private ScannerFragment scannerFragment;

    private FragmentTransaction transaction;

    private Toolbar toolbar;
    private FirebaseAuth auth;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_list:
                    loadFragment(listFragment);
                    System.out.println("listFragment clicked");
                    return true;
                case R.id.navigation_add:
                    loadFragment(addFragment);
                    System.out.println("addFragment clicked");
                    return true;
                case R.id.navigation_scanner:
                    loadFragment(scannerFragment);
                    System.out.println("scannerFragment clicked");
                    return true;
            }
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.sign_out_menu) {
            signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("List");

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        listFragment = new ListFragment();
        addFragment = new AddFragment();
        scannerFragment = new ScannerFragment();

        loadFragment(listFragment);


    }

    private void loadFragment(Fragment fragment) {
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, fragment);
        transaction.commit();
    }

    public void signOut() {
        auth.signOut();
    }
}
