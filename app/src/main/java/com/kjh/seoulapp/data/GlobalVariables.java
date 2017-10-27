package com.kjh.seoulapp.data;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class GlobalVariables
{
	public static FirebaseAuth mAuth;
	public static FirebaseDatabase database;
	public static String uid;
	public static GoogleApiClient mGoogleApiClient;
	public static UserData userData;
}
