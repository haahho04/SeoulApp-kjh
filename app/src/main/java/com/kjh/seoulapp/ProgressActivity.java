package com.kjh.seoulapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

public abstract class ProgressActivity extends AppCompatActivity
{
	protected ProgressBar progressBar;
	protected boolean isProgress;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	public void showProgressDialog() { progressBar.setVisibility(View.VISIBLE); isProgress = true; }
	public void hideProgressDialog() { progressBar.setVisibility(View.GONE); isProgress = false; }
}
