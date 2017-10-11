package com.kjh.seoulapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TourMainActivity extends GoogleSignInActivity
        implements NavigationView.OnNavigationItemSelectedListener
{
    private static final String TAG = "TourMainActivity";

    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    @Override
    public void onStart()
    {
        super.onStart();
    } // onStart()

    @Override // button event: open drawer
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);

        return  initSearch(menu);
    }

    public static boolean initSearch(Menu menu)
    {
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                System.out.println(s);
                return false;
            }
        });
        return true;
    }

    @Override // TODO
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override // TODO
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.sign_out) {
            signOut();
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    } // onNavigationItemSelected()

    public void onClick(View v)
    {
        int id = v.getId();

        switch(id)
        {
            case R.id.btn_next:
                Intent intent = new Intent(this, TourRegionActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_test:
                printMemberData();
                break;
        }
    } // onClick()

    void printMemberData()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://seoulapp-kjh.firebaseio.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FirebaseAPI firebaseAPI = retrofit.create(FirebaseAPI.class);

        Call<MemberData> call = firebaseAPI.getMemberData(uid);
        //Call<String> call = firebaseAPI.getMemberData();

        call.enqueue(new Callback<MemberData>() {
            @Override
            public void onResponse(Call<MemberData> call, Response<MemberData> response) {
                Log.d(TAG, response.body().toString());
            }

            @Override
            public void onFailure(Call<MemberData> call, Throwable t) {
                Log.d(TAG, "URL: " + call.request().url().toString());
                Log.d(TAG, "onFailure(): " + t.getMessage());
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError e) {
                Log.w(TAG, "Failed to read value.", e.toException());
            }
        });
    }

    private void signOut() {
        // Firebase sign out
        mAuth.signOut();

        // Google sign out
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>()
                {
                    @Override
                    public void onResult(@NonNull Status status)
                    {
                        //Log.d(TAG, "onResult()");
                        Intent intent = new Intent(TourMainActivity.this, SocialLoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
    } // signOut()

} // class
