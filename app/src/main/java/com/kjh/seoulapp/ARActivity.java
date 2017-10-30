package com.kjh.seoulapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import static com.kjh.seoulapp.data.SharedData.USER_REF;
import static com.kjh.seoulapp.data.SharedData.regionIndex;
import static com.kjh.seoulapp.data.SharedData.stampLevel;
import static com.kjh.seoulapp.data.SharedData.userData;

public class ARActivity extends ProgressActivity
    implements View.OnClickListener
{
    final String TAG = "ARActivity";
	final int CAM_PERMISSION_REQUEST = 1236;
	Camera mCamera;
	CameraPreview mPreview;
	Button btnGetStamp;
	FirebaseDatabase database;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_problem_end);

		btnGetStamp = (Button) findViewById(R.id.btn_get_stamp);
		btnGetStamp.setOnClickListener(this);
		database = FirebaseDatabase.getInstance();

		if (ContextCompat.checkSelfPermission(ARActivity.this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED)
			ActivityCompat.requestPermissions(ARActivity.this, new String[]{android.Manifest.permission.CAMERA}, CAM_PERMISSION_REQUEST);
		else
			openCam();
    }

	@Override
	public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
	{
		switch (requestCode)
		{
			case CAM_PERMISSION_REQUEST:
			{
				if (grantResults.length > 0)
				{
					for (int i = 0; i < grantResults.length; i++)
					{
						if (grantResults[i] == PackageManager.PERMISSION_GRANTED)
						{
							openCam();
						}
						else
						{
							// permission denied
						}
					}
				}
				break;
			}
		} // switch()
	} // onRequestPermissionsResult()

	void openCam()
	{
		// Create an instance of Camera
		mCamera = CameraPreview.getCameraInstance();

		// Create our Preview view and set it as the content of our activity.
		mPreview = new CameraPreview(this, mCamera);
		FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
		preview.addView(mPreview);
		CameraPreview.setCameraDisplayOrientation(this, 0, mCamera);
	}

    @Override
    public void onClick(View view)
    {
        int id = view.getId();

        switch (id)
        {
			case R.id.btn_get_stamp:
				sendStampInfo();
				Intent intent = new Intent(ARActivity.this, TourRegionActivity.class);
				startActivity(intent);
				finish();
				break;
        }
    } // onClick()

	void sendStampInfo()
	{
		FirebaseAuth auth = FirebaseAuth.getInstance();
		FirebaseDatabase database = FirebaseDatabase.getInstance();

		String uid = auth.getCurrentUser().getUid();

		Log.d(TAG, "before: "+userData);
		userData.stampList.set(regionIndex, stampLevel);
		Log.d(TAG, "after: "+userData);

		database.getReference(USER_REF).child(uid).setValue(userData);
	}
}
