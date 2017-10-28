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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseUser;

import static com.kjh.seoulapp.PopupActivity.POPUP_TYPE;
import static com.kjh.seoulapp.data.GlobalVariables.auth;

public class TourMainActivity extends GoogleApiClientActivity
		implements NavigationView.OnNavigationItemSelectedListener
{
	private static final String TAG = "TourMainActivity";
	static String regionFlag = "0";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		android.util.Log.d("TAG","TOTAL MEMORY : "+(Runtime.getRuntime().totalMemory() / (1024 * 1024)) + "MB");
		android.util.Log.d("TAG","MAX MEMORY : "+(Runtime.getRuntime().maxMemory() / (1024 * 1024)) + "MB");
		android.util.Log.d("TAG","FREE MEMORY : "+(Runtime.getRuntime().freeMemory() / (1024 * 1024)) + "MB");
		android.util.Log.d("TAG","ALLOCATION MEMORY : "+((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024)) + "MB");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tour_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
		setSupportActionBar(toolbar);

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.setDrawerListener(toggle);
		toggle.syncState();

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);

		FirebaseUser user = auth.getCurrentUser();
		if (user != null)
		{
			View nav_header = navigationView.getHeaderView(0);
			ImageView user_photo = nav_header.findViewById(R.id.user_photo);
			TextView user_name = nav_header.findViewById(R.id.user_name);
			TextView user_email = nav_header.findViewById(R.id.user_email);

			String strName = user.getDisplayName();
			String strEmail = user.getEmail();

//            if (user_photo != null) {
//                Glide
//                        .with(nav_header.getContext())
//                        .load(currentUser.getPhotoUrl()) // the uri you got from Firebase
//                        .override(200,200)
//                        .into(user_photo); // Your imageView variable
//            }
			if (user_name  != null && strName  != null) user_name.setText(strName);
			if (user_email != null && strEmail != null) user_email.setText(strEmail);
		}
	} // onCreate()

	@Override
	public void onStart() { super.onStart(); }

	@Override // button event: open drawer
	public void onBackPressed()
	{
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START))
		{
			drawer.closeDrawer(GravityCompat.START);
		} else
		{
			super.onBackPressed();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_search, menu);

		return initSearch(menu);
	}

	public static boolean initSearch(Menu menu)
	{
		MenuItem searchItem = menu.findItem(R.id.action_search);
		SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
		{
			@Override
			public boolean onQueryTextSubmit(String s)
			{
				return false;
			}

			@Override
			public boolean onQueryTextChange(String s)
			{
				System.out.println(s);
				return false;
			}
		});
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_search)
		{
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onNavigationItemSelected(MenuItem item)
	{
		// Handle navigation view item clicks here.
		int id = item.getItemId();

		if (id == R.id.nav_sign_out) signOut();
		else if (id == R.id.nav_app_info) popupActivity(POPUP_TYPE.APP_INFO);
		else if (id == R.id.nav_contact) popupActivity(POPUP_TYPE.CONTACT);
		else if (id == R.id.nav_donate) popupActivity(POPUP_TYPE.DONATE);

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);

		return true;
	} // onNavigationItemSelected()

	public void onClick(View v)
	{
		int id = v.getId();

		Intent intent = new Intent(this, TourRegionActivity.class);
		ImageView map_full = (ImageView) findViewById(R.id.map_full);
		ImageView map_1 = (ImageView) findViewById(R.id.map_1);
		ImageView map_2 = (ImageView) findViewById(R.id.map_2);
		ImageView map_3 = (ImageView) findViewById(R.id.map_3);
		ImageView map_4 = (ImageView) findViewById(R.id.map_4);
		ImageView map_5 = (ImageView) findViewById(R.id.map_5);
		Button map_mid_button = (Button) findViewById(R.id.map_mid_button);
		Button map_east_button = (Button) findViewById(R.id.map_east_button);
		Button map_west_button = (Button) findViewById(R.id.map_west_button);
		Button map_south_button = (Button) findViewById(R.id.map_south_button);
		Button map_north_button = (Button) findViewById(R.id.map_north_button);
		ImageButton icon_indepen = (ImageButton) findViewById(R.id.icon_indepen);
		switch (id)
		{
			case R.id.btn_next:
				regionFlag = "1";
				startActivity(intent);
				break;
			case R.id.btn_test:
				break;
			case R.id.icon_indepen:
				regionFlag = "3";
				startActivity(intent);
				break;
			case R.id.map_mid_button:
				map_full.setVisibility(View.GONE);
				map_mid_button.setVisibility(View.GONE);
				map_east_button.setVisibility(View.GONE);
				map_west_button.setVisibility(View.GONE);
				map_south_button.setVisibility(View.GONE);
				map_north_button.setVisibility(View.GONE);
				map_1.setVisibility(View.VISIBLE);
				icon_indepen.setVisibility(View.VISIBLE);
				break;
			case R.id.map_east_button:
				map_full.setVisibility(View.GONE);
				map_mid_button.setVisibility(View.GONE);
				map_east_button.setVisibility(View.GONE);
				map_west_button.setVisibility(View.GONE);
				map_south_button.setVisibility(View.GONE);
				map_north_button.setVisibility(View.GONE);
				map_2.setVisibility(View.VISIBLE);
				break;
			case R.id.map_west_button:
				map_full.setVisibility(View.GONE);
				map_mid_button.setVisibility(View.GONE);
				map_east_button.setVisibility(View.GONE);
				map_west_button.setVisibility(View.GONE);
				map_south_button.setVisibility(View.GONE);
				map_north_button.setVisibility(View.GONE);
				map_3.setVisibility(View.VISIBLE);
				break;
			case R.id.map_south_button:
				map_full.setVisibility(View.GONE);
				map_mid_button.setVisibility(View.GONE);
				map_east_button.setVisibility(View.GONE);
				map_west_button.setVisibility(View.GONE);
				map_south_button.setVisibility(View.GONE);
				map_north_button.setVisibility(View.GONE);
				map_4.setVisibility(View.VISIBLE);
				break;
			case R.id.map_north_button:
				map_full.setVisibility(View.GONE);
				map_mid_button.setVisibility(View.GONE);
				map_east_button.setVisibility(View.GONE);
				map_west_button.setVisibility(View.GONE);
				map_south_button.setVisibility(View.GONE);
				map_north_button.setVisibility(View.GONE);
				map_5.setVisibility(View.VISIBLE);
				break;

		}
	} // onClick()

	private void signOut()
	{
		// Firebase sign out
		auth.signOut();

		// Google sign out
		while(mGoogleApiClient.isConnecting());
		Log.d(TAG, "isConnected(): " + mGoogleApiClient.isConnected());
		Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>()
		{
			@Override
			public void onResult(@NonNull Status status)
			{
				Intent intent = new Intent(TourMainActivity.this, SocialLoginActivity.class);
				startActivity(intent);
				finish();
			}
		});
	} // signOut()

	void popupActivity(POPUP_TYPE type)
	{
		Intent intent = new Intent(TourMainActivity.this, PopupActivity.class);
		intent.putExtra("POPUP_TYPE", type);
		startActivity(intent);
	}

} // class
