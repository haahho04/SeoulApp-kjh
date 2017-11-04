package com.kjh.seoulapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import static com.kjh.seoulapp.data.SharedData.*;

public class PopupActivity extends AppCompatActivity implements View.OnClickListener
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_popup);

		TextView titleView = (TextView) findViewById(R.id.popup_title);
		TextView msgView = (TextView) findViewById(R.id.popup_msg);

		titleView.setText(popupTitle);
		msgView.setText(popupMsg);
	}

	@Override
	public void onClick(View v)
	{
		int id = v.getId();

		switch (id)
		{
			case R.id.popup_close:
				Intent intent = new Intent();
				setResult( RESULT_OK, intent);
				finish();
				break;
		}
	}
}
