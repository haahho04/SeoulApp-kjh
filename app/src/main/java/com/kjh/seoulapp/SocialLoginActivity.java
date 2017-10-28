package com.kjh.seoulapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import static com.kjh.seoulapp.data.GlobalVariables.auth;
import static com.kjh.seoulapp.data.GlobalVariables.database;
import static com.kjh.seoulapp.data.GlobalVariables.readUserData;

public class SocialLoginActivity extends GoogleApiClientActivity implements View.OnClickListener
{
	private static final String TAG = "SocialLoginActivity";
	private static final int RC_SIGN_IN = 9001;
	private ProgressBar progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_social_login);

		progressBar = (ProgressBar) findViewById(R.id.progressBar);

		auth = FirebaseAuth.getInstance();
		database = FirebaseDatabase.getInstance();
	}

	// [START on_start_check_user]
	@Override
	public void onStart()
	{
		super.onStart();
		// Check if currentUser is signed in (non-null) and update UI accordingly.
		FirebaseUser currentUser = auth.getCurrentUser();
		updateUI(currentUser);
	}
	// [END on_start_check_user]

	// [START signin]
	private void signIn()
	{
		Log.d(TAG, "signIn");
		Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
		startActivityForResult(signInIntent, RC_SIGN_IN);
	}
	// [END signin]

	// [START onActivityResult]
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		// Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
		if (requestCode == RC_SIGN_IN)
		{
			GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
			if (result.isSuccess())
			{
				// Google Sign In was successful, authenticate with Firebase
				GoogleSignInAccount account = result.getSignInAccount();
				firebaseAuthWithGoogle(account);
			} else
			{
				// Google Sign In failed, update UI appropriately
				// [START_EXCLUDE]
				Log.d(TAG, "status: " + result.getStatus());
				updateUI(null);
				// [END_EXCLUDE]
			}
		}
	}
	// [END onActivityResult]

	// [START auth_with_google]
	private void firebaseAuthWithGoogle(GoogleSignInAccount acct)
	{
		Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
		// [START_EXCLUDE silent]
		showProgressDialog();
		// [END_EXCLUDE]

		AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
		auth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
		{
			@Override
			public void onComplete(@NonNull Task<AuthResult> task)
			{
				if (task.isSuccessful())
				{
					// Sign in success, update UI with the signed-in currentUser's information
					Log.d(TAG, "signInWithCredential:success");
					Toast.makeText(SocialLoginActivity.this, "Authentication success.", Toast.LENGTH_SHORT).show();

					FirebaseUser currentUser = auth.getCurrentUser();
					readUserData();
					updateUI(currentUser);
				} else
				{
					// If sign in fails, display a message to the currentUser.
					Log.w(TAG, "signInWithCredential:failure", task.getException());
					Toast.makeText(SocialLoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
					updateUI(null);
				}

				// [START_EXCLUDE]
				hideProgressDialog();
				// [END_EXCLUDE]
			}
		});
	}
	// [END auth_with_google]

	private void updateUI(FirebaseUser user)
	{
		hideProgressDialog();

		if (user != null)
		{
			String uid = user.getUid();
			Log.d(TAG, user.getEmail() + ", " + uid);

			// change activity
			Intent intent = new Intent(this, TourMainActivity.class);
			startActivity(intent);
			finish();
		} else
		{
			Log.d(TAG, "non auth state");
			// TODO: 네트워크 등의 이유로 인증 실패
		}
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.GoogleLoginButton:
				signIn();
				break;
		}
	}

	void showProgressDialog()
	{
		progressBar.setVisibility(View.VISIBLE);
	}

	void hideProgressDialog()
	{
		progressBar.setVisibility(View.GONE);
	}

	private void revokeAccess()
	{
		// Firebase sign out
		auth.signOut();

		// Google revoke access
		Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(new ResultCallback<Status>()
		{
			@Override
			public void onResult(@NonNull Status status)
			{
				//enableBtnNext(null);
			}
		});
	}
}
