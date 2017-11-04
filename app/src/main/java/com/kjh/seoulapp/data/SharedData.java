package com.kjh.seoulapp.data;

import android.location.Location;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.kjh.seoulapp.R;

public class SharedData
{
	public enum DATA_NAME { USER_DATA, CULTURAL_DATA }
	public enum CULTURAL { NONE,
		JONGMYO, 	NAKSUNGDAE, 	INDEPEN, 	GYUNGBOK,
		CHANGDUCK, 	CHANGGYUNG, 	GYUNGHEE, 	DUCKSU,
		BUSIN, 		DONGDAEMUN, 	NAMDAEMUN, 	BUKDAEMUN,
		TAEREUNG, 	HYUNINREUNG, 	YANGCHUN, 	AMSADONG }

	public static final String USER_REF = "user";
	public static final String CULTURAL_REF = "culturalData";

	public static String popupTitle;
	public static String popupMsg;

	public static UserData     userData;
	public static CulturalData culturalData;
	public static int          correctCnt;
	public static CULTURAL     cultural;
	public static int          stampLevel; // 획득한 도장갯수 (문제풀기 결과)
	public static Location     locRegion, locNow;

//	public static boolean initSearch(Menu menu)
//	{
//		MenuItem searchItem = menu.findItem(R.id.action_search);
//		SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
//		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
//		{
//			@Override
//			public boolean onQueryTextSubmit(String s)
//			{
//				return false;
//			}
//
//			@Override
//			public boolean onQueryTextChange(String s)
//			{
//				System.out.println(s);
//				return false;
//			}
//		});
//		return true;
//	}

	public static int culturalIndex(CULTURAL cultural)
	{
		switch(cultural)
		{
			case JONGMYO: 		return 1;
			case NAKSUNGDAE: 	return 2;
			case INDEPEN: 		return 3;
			case GYUNGBOK: 		return 4;
			case CHANGDUCK: 	return 5;
			case CHANGGYUNG: 	return 6;
			case GYUNGHEE: 		return 7;
			case DUCKSU: 		return 8;
			case BUSIN: 		return 9;
			case DONGDAEMUN: 	return 10;
			case NAMDAEMUN: 	return 12;
			case BUKDAEMUN: 	return 13;
			case TAEREUNG: 		return 14;
			case HYUNINREUNG: 	return 15;
			case YANGCHUN: 		return 16;
			case AMSADONG: 		return 17;
			default: 			return 0;
		}
	}
}
