package com.kjh.seoulapp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterViewFlipper;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.ImageView;
import android.widget.BaseAdapter;
import android.widget.ViewFlipper;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

//import net.daum.mf.map.api.MapView;

public class TourRegionActivity extends AuthActivity
        implements View.OnClickListener
{
    private static final String TAG = "TourRegionActivity";
    String inputdata =  TourMainActivity.regionflag;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference("cultural").child(inputdata);



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


    public ViewFlipper flipper;
    public ToggleButton toggle_flipping;

    public String info_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_region);

       /*for(int num1=0 ; num1 < 2 ; num1++){
            ImageView img = new ImageView(this);
            img.setImageResource(R.drawable.t4+num1);
            flipper.addView(img);
        }

        Animation showIn= AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);

        flipper.setInAnimation(showIn);

        flipper.setOutAnimation(this, android.R.anim.slide_out_right);
*/

        /*
        toggle_flipping=(ToggleButton)findViewById(R.id.toggle_auto);



        toggle_flipping.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    flipper.setFlipInterval(2000);
                    flipper.startFlipping();
                }else{
                    flipper.stopFlipping();
                }
            }
        });
*/
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

/*
        TextView info_textview = (TextView) findViewById(R.id.infotext);
        info_textview.setText(info_content);
*/
    }
    @Override
    public void onStart()
    {
        super.onStart();
        printMemberData();
    } // onStart()

    void printMemberData()
    {
        Log.v(TAG, ref.toString());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                CulturalData value = dataSnapshot.getValue(CulturalData.class);
                info_content = value.getContent();
                Log.d(TAG, "Value is: " + value);
                // TODO
            }

            @Override
            public void onCancelled(DatabaseError e) {
                Log.w(TAG, "Failed to read value.", e.toException());
                // TODO
            }
        });
    }

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
        flipper = (ViewFlipper) findViewById(R.id.ViewFlipperID);
        switch(v.getId())
        {
            case R.id.quiz_start:
                Intent intent = new Intent(TourRegionActivity.this, QuizProblemActivity.class);

                startActivity(intent);
                break;
            case R.id.flipper_pre:
                flipper.showPrevious();
                break;
            case R.id.flipper_next:
                flipper.showNext();
                break;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment
        //implements MapView.OpenAPIKeyAuthenticationResultListener
    {

        ViewFlipper flipper;
        ToggleButton toggle_flipping;

        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final String TAG = "PlaceholderFragment";

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
            int sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
            View rootView = null;

            switch(sectionNumber)
            {
                case 1:
                    rootView = inflater.inflate(R.layout.fragment_region_info, container, false);
                    break;
                case 2:
                    rootView = inflater.inflate(R.layout.fragment_region_road, container, false);

//                    MapView mapView = new MapView(getContext());
//                    mapView.setOpenAPIKeyAuthenticationResultListener(this);
//
//                    ViewGroup mapViewContainer = rootView.findViewById(R.id.map_view);
//                    mapViewContainer.addView(mapView);

                    break;
                case 3:
                    rootView = inflater.inflate(R.layout.fragment_region_quiz_start, container, false);
                    break;
            }

            return rootView;
        }

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
