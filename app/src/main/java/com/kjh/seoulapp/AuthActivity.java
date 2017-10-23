package com.kjh.seoulapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public abstract class AuthActivity extends AppCompatActivity
{
    protected static FirebaseAuth mAuth;
    protected static FirebaseDatabase database;
    protected static String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (mAuth == null)
            mAuth = FirebaseAuth.getInstance();
        if (database == null)
            database = FirebaseDatabase.getInstance();
    }
}
