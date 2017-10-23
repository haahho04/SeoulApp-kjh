package com.kjh.seoulapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

//import net.daum.mf.map.api.MapView;

public class TourRegionActivity extends AuthActivity
        implements View.OnClickListener
{
    static final int GPS_PERMISSION_REQUEST = 1235;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    int regionID;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_region);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        // check permission gps & internet
        boolean flag = true;

        if (ContextCompat.checkSelfPermission(TourRegionActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED)
        {
            flag = false;
        }
        if (ContextCompat.checkSelfPermission(TourRegionActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED)
        {
            Log.d("PERMISSION_DENIED", "ACCESS_FINE_LOCATION");
            flag = false;
        }
//        if (ContextCompat.checkSelfPermission(TourRegionActivity.this,
//                Manifest.permission.INTERNET) == PackageManager.PERMISSION_DENIED) {
//            Log.d("PERMISSION_DENIED", "INTERNET");
//            flag = false;
//        }

        if (!flag) {

            // ask permissions here using below code
            ActivityCompat.requestPermissions(TourRegionActivity.this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    GPS_PERMISSION_REQUEST);
        }

        Intent intent = getIntent();
        regionID = intent.getIntExtra("regionID", 1);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults)
    {
        switch (requestCode) {
            case GPS_PERMISSION_REQUEST:
            {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0)
                {
                    for (int i=0;i<grantResults.length;i++)
                    {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED)
                        {
                            // permission was granted, yay! Do the
                            // contacts-related task you need to do.
                        }
                        else
                        {
                            // permission denied, boo! Disable the
                            // functionality that depends on this permission.
                        }
                    }
                }
                break;
            }
            // other 'case' lines to check for other
            // permissions this app might request

        } // switch()
    } // onRequestPermissionsResult()

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
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
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.quiz_start:
                Intent intent = new Intent(TourRegionActivity.this, QuizProblemActivity.class);
                intent.putExtra("regionID", regionID);
                startActivity(intent);
                break;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment
            //implements MapView.OpenAPIKeyAuthenticationResultListener
    {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState)
        {
            Activity activity = getActivity();
            int sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
            View rootView = null;

            switch(sectionNumber)
            {
                case 1:
                    rootView = inflater.inflate(R.layout.fragment_region_info, container, false);
                    break;
                case 2:
                    rootView = inflater.inflate(R.layout.fragment_region_road, container, false);

//                    MapView mapView = new MapView(activity);
//                    mapView.setOpenAPIKeyAuthenticationResultListener(this);
//
//                    ViewGroup mapViewContainer = rootView.findViewById(R.id.map_view);
//                    mapViewContainer.addView(mapView);

                    break;
                case 3:
                    rootView = inflater.inflate(R.layout.fragment_region_quiz_start, container, false);

                    TextView logView = rootView.findViewById(R.id.my_stamp_desc);
                    logView.setText("GPS 가 잡혀야 좌표가 구해짐");

                    // Acquire a reference to the system Location Manager
                    LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);

                    // GPS 프로바이더 사용가능여부
                    boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                    // 네트워크 프로바이더 사용가능여부
                    boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                    Log.d("Main", "isGPSEnabled="+ isGPSEnabled);
                    Log.d("Main", "isNetworkEnabled="+ isNetworkEnabled);

                    LocationListener locationListener = new LocationListener() {
                        public void onLocationChanged(Location location) {
                            double lat = location.getLatitude();
                            double lng = location.getLongitude();

                            logView.setText("latitude: "+ lat +", longitude: "+ lng);
                        }

                        public void onStatusChanged(String provider, int status, Bundle extras) {
                            logView.setText("onStatusChanged");
                        }

                        public void onProviderEnabled(String provider) {
                            logView.setText("onProviderEnabled");
                        }

                        public void onProviderDisabled(String provider) {
                            logView.setText("onProviderDisabled");
                        }
                    };


                    if (ContextCompat.checkSelfPermission(activity,
                            Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission(activity,
                                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                    {
                        // Register the listener with the Location Manager to receive location updates
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

                        // 수동으로 위치 구하기
                        String locationProvider = LocationManager.GPS_PROVIDER;
                        Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
                        if (lastKnownLocation != null) {
                            double lng = lastKnownLocation.getLatitude();
                            double lat = lastKnownLocation.getLatitude();
                            Log.d("Main", "longtitude=" + lng + ", latitude=" + lat);
                        }
                    }
                    break;
            }

            return rootView;
        } // onCreateView()

//        @Override
//        public void onDaumMapOpenAPIKeyAuthenticationResult(MapView mapView, int i, String s) {
//            Log.d(TAG, "Daum Map API Auth: " + s);
//        }

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "유적지 설명";
                case 1:
                    return "가는 길";
                case 2:
                    return "문제풀이 & 스탬프 얻기";
            }
            return null;
        }
    }
}
