package com.kjh.seoulapp;

import android.*;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.kjh.seoulapp.data.ProblemData;

import java.util.ArrayList;
import java.util.List;

import static com.kjh.seoulapp.PopupActivity.POPUP_TYPE;
import static com.kjh.seoulapp.data.GlobalVariables.EXTRA_CORRECT_CNT;
import static com.kjh.seoulapp.data.GlobalVariables.EXTRA_POPUP_TYPE;

public class QuizProblemEndActivity extends AppCompatActivity
    implements View.OnClickListener
{
    final String TAG = "QuizProblemEndActivity";
	final int CAM_PERMISSION_REQUEST = 1236;
	private Camera mCamera;
	private CameraPreview mPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_problem_end);

		Log.d(TAG, ""+checkCameraHardware(getApplicationContext()));

		if (ContextCompat.checkSelfPermission(QuizProblemEndActivity.this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED)
			ActivityCompat.requestPermissions(QuizProblemEndActivity.this, new String[]{android.Manifest.permission.CAMERA}, CAM_PERMISSION_REQUEST);
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
        }
    } // onClick()

	/** Check if this device has a camera */
	private boolean checkCameraHardware(Context context) {
		if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
			// this device has a camera
			return true;
		} else {
			// no camera on this device
			return false;
		}
	}
}
