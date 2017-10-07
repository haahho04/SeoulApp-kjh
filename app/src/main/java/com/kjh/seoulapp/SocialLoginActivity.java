package com.kjh.seoulapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SocialLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_login);
    }

    public void onClick(View v)
    {
        int id = v.getId();

        if (id == R.id.GoogleLoginButton)
        {
            Intent intent = new Intent(this, TourMainActivity.class);
            startActivity(intent);

            // start the TourMainActivity & finish and SocialLoginActivity
            finish();
        }
    }
}
