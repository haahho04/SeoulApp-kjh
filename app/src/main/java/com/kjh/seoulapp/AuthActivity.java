package com.kjh.seoulapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public abstract class AuthActivity extends AppCompatActivity
{
    private static final String TAG = "AuthActivity";

    protected static FirebaseAuth mAuth;
    protected static String uid;
    protected static FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
    }
}
