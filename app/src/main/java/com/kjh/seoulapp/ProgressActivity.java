package com.kjh.seoulapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

public abstract class ProgressActivity extends AppCompatActivity
{
	protected ProgressBar progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	protected void showProgressDialog() { progressBar.setVisibility(View.VISIBLE); }
	protected void hideProgressDialog() { progressBar.setVisibility(View.GONE); }
}
