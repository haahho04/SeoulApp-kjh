package com.kjh.seoulapp.data;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GlobalVariables
{
	static final String TAG = "GloblVariables";
	public static final String EXTRA_POPUP_TYPE = "POPUP_TYPE";
	public static final String EXTRA_CORRECT_CNT = "correctCnt";
	public static final String USER_REF = "user";
	public static FirebaseAuth auth;
	public static FirebaseDatabase database;
	public static UserData userData;

	public static void readUserData()
	{
		FirebaseUser currentUser = auth.getCurrentUser();
		String uid = currentUser.getUid();

		DatabaseReference ref = database.getReference(USER_REF).child(uid);
		Log.d(TAG, ref.toString());
		ref.addListenerForSingleValueEvent(new ValueEventListener()
		{
			@Override
			public void onDataChange(DataSnapshot dataSnapshot)
			{
				userData = dataSnapshot.getValue(UserData.class);
				if (userData == null)
				{
					// 새로운 유저 데이터 Insert
					FirebaseUser currentUser = auth.getCurrentUser();
					userData = new UserData(currentUser.getDisplayName());

					String uid = currentUser.getUid();
					database.getReference(USER_REF).child(uid).setValue(userData);
				}
				Log.d(TAG, "Value is: " + userData);

				// TODO 스탬프 리스트 UI 업데이트
			}

			@Override
			public void onCancelled(DatabaseError e)
			{
				Log.w(TAG, "Failed to read value.", e.toException());
				// TODO
			}
		});
	} // readUserData()
}
