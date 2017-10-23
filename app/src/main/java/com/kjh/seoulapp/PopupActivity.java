package com.kjh.seoulapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class PopupActivity extends AppCompatActivity
    implements View.OnClickListener
{
    enum POPUP_TYPE { APP_INFO, CONTACT, DONATE, END_QUIZ }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        Intent intent = getIntent();
        //POPUP_TYPE type = (POPUP_TYPE) intent.getSerializableExtra("POPUP_TYPE");
        POPUP_TYPE type = intent.getParcelableExtra("POPUP_TYPE");
        TextView titleView = (TextView) findViewById(R.id.popup_title);
        TextView msgView = (TextView) findViewById(R.id.popup_msg);

        switch(type)
        {
            case APP_INFO:
                titleView.setText("APP_INFO");
                break;
            case CONTACT:
                titleView.setText("CONTACT");
                break;
            case DONATE:
                titleView.setText("DONATE");
                break;
            case END_QUIZ: // from QuizProblemActivity
                titleView.setText("퀴즈 결과");
                break;
            default: assert false;
        }
    }

    @Override
    public void onStart()
    {
        super.onStart();
    }

    @Override
    public void onClick(View v)
    {
        int id = v.getId();

        switch(id)
        {
            case R.id.popup_close:
                Intent intent = new Intent();
                //intent.putExtra("result", "Close Popup");
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}
