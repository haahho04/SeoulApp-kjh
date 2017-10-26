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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.kjh.seoulapp.data.UserData;

import static com.kjh.seoulapp.PopupActivity.POPUP_TYPE;

public class TourMainActivity extends GoogleAuthActivity
        implements NavigationView.OnNavigationItemSelectedListener
{
    private static final String TAG = "TourMainActivity";
    static final String USER_REF = "user";

    private DatabaseReference ref;

    static String regionFlag = "0";
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

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            View nav_header = navigationView.getHeaderView(0);
            ImageView user_photo = nav_header.findViewById(R.id.user_photo);
            TextView user_name = nav_header.findViewById(R.id.user_name);
            TextView user_email = nav_header.findViewById(R.id.user_email);

            String strName = user.getDisplayName();
            String strEmail = user.getEmail();

//            if (user_photo != null) {
//                Glide
//                        .with(nav_header.getContext())
//                        .load(user.getPhotoUrl()) // the uri you got from Firebase
//                        .override(200,200)
//                        .into(user_photo); // Your imageView variable
//            }
            if (user_name != null && strName != null)
                user_name.setText(strName);
            if (user_email != null && strEmail != null)
                user_email.setText(strEmail);
        }
    }

    @Override
    public void onStart()
    {
        super.onStart();

        Log.d(TAG, "onStart()");

        if (uid != null) {
            Log.d(TAG, uid);
            ref = database.getReference(USER_REF).child(uid);
        }
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

    @Override
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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_sign_out)
            signOut();
        else if (id == R.id.nav_app_info)
            popupActivity(POPUP_TYPE.APP_INFO);
        else if (id == R.id.nav_contact)
            popupActivity(POPUP_TYPE.CONTACT);
        else if (id == R.id.nav_donate)
            popupActivity(POPUP_TYPE.DONATE);

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
                regionFlag = "1";
                Intent intent = new Intent(this, TourRegionActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_test:
                testDB();
                break;
        }
    } // onClick()

    void testDB()
    {
        Log.d(TAG, ref.toString());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserData value = dataSnapshot.getValue(UserData.class);
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

    void popupActivity(POPUP_TYPE type)
    {
        Intent intent = new Intent (TourMainActivity.this, PopupActivity.class);
        intent.putExtra("POPUP_TYPE", type);
        startActivity(intent);
    }

} // class
