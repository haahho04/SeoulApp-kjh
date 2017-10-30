package com.kjh.seoulapp.data;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.kjh.seoulapp.ProgressActivity;

import java.util.Timer;
import java.util.TimerTask;

public class SharedData
{
	public enum POPUP_TYPE { APP_INFO, CONTACT, DONATE, END_QUIZ }
	public enum DATA_NAME { USER_DATA, CULTURAL };

	public static final String USER_REF = "user";
	public static final String CULTURAL_REF = "cultural";

	public static final String EXTRA_POPUP_TYPE = "POPUP_TYPE";
	public static final String EXTRA_CORRECT_CNT = "correctCnt";

	public static UserData userData;
	public static CulturalData cultural;
	public static int correctCnt;
	public static int regionIndex;
	public static int stampLevel;
	static long TIMEOUT = 3000L;

	public static void addListenerWithTimeout(final ProgressActivity activity, final DatabaseReference ref, final ValueEventListener listener, final DATA_NAME dataName)
	{
		ref.addListenerForSingleValueEvent(listener);

		final Timer timer = new Timer();
		TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
				timer.cancel();
				boolean timeoutFlag = false;

				if (dataName == DATA_NAME.USER_DATA)
					timeoutFlag = userData == null;
				else if (dataName == DATA_NAME.CULTURAL)
					timeoutFlag = cultural == null;

				if (timeoutFlag) //  Timeout
				{
					ref.removeEventListener(listener);
					Log.d("Timeout", ref.toString());

					new Handler(Looper.getMainLooper()).post(new Runnable()
					{
						@Override
						public void run()
						{
							Toast.makeText(activity, "데이터 읽기 실패", Toast.LENGTH_SHORT).show();
							activity.hideProgressDialog();
						}
					});
				}
			}
		};
		// Setting timeout of 10 sec to the request
		timer.schedule(timerTask, TIMEOUT);
	}
}
