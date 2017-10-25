package com.kjh.seoulapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.kjh.seoulapp.data.ProblemData;

import java.util.List;

import static com.kjh.seoulapp.PopupActivity.POPUP_TYPE;

public class QuizProblemActivity extends AuthActivity
    implements View.OnClickListener
{
    static final String TAG = "QuizProblemActivity";
    static final int LAST_PROB_NUM = 3;
    static final int END_QUIZ = 1234;
    TextView probView;
    RadioGroup answerGroup;
    Button btnAnswer, btnNext;
    ImageView resultImage;
    int probNum;
    boolean correct;
    int correctCnt;
    int regionID;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_problem);

        probView = (TextView) findViewById(R.id.prob_desc);
        answerGroup = (RadioGroup) findViewById(R.id.answer_group);
        btnAnswer = (Button) findViewById(R.id.btn_answer);
        btnNext = (Button) findViewById(R.id.btn_next);
        resultImage = (ImageView) findViewById(R.id.result_image);
        probNum = 0;
        correct = true;
        correctCnt = 0;

        Intent intent = getIntent();
        regionID = intent.getIntExtra("regionID", 1);

        ref = database.getReference("regionData").child(""+regionID).child("problem");

        Log.d(TAG, ref.toString());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                @SuppressWarnings("unchecked")
                List<ProblemData> value = (List<ProblemData>) dataSnapshot.getValue();
                Log.d(TAG, "Value is: " + value);
                // TODO: get data from db
            }

            @Override
            public void onCancelled(DatabaseError e) {
                Log.w(TAG, "Failed to read value.", e.toException());
                // TODO: cannot get data from db
            }
        });

        updateProbContent();
    }

    @Override
    public void onClick(View view)
    {
        int id = view.getId();

        switch (id)
        {
            case R.id.btn_prev:
                prevProb();
                break;
            case R.id.btn_answer:
                answerProb();
                break;
            case R.id.btn_next:
                if (probNum < LAST_PROB_NUM) nextProb();
                else endQuiz();
                break;
        }
    } // onClick()

    void prevProb()
    {
        // TODO
    }

    void answerProb()
    {
        int btn_checked = answerGroup.getCheckedRadioButtonId();

        if (btn_checked == -1)
        {
            Log.d (TAG, "눌린 버튼이 없습니다");
            // TODO: UI Update
        }
        else if ((correct && btn_checked == R.id.answer_o) || // 정답 O를 맞췄을 경우 또는
                (!correct && btn_checked == R.id.answer_x)) // 정답 X를 맞췄을 경우
        {
            Log.d(TAG, "정답입니다");
            // TODO: UI Update
            // resultImage.setimage (O)
            correctCnt++;
            enableBtnNext(true);
        }
        else
        {
            Log.d(TAG, "오답입니다");
            // TODO: UI Update
            // resultImage.setimage (X)
            enableBtnNext(true);
        }
    }

    void nextProb()
    {
        updateProbContent();
        enableBtnNext(false);
    }

    void enableBtnNext(boolean enable)
    {
        if (enable)
        {
            answerGroup.clearCheck();
            btnAnswer.setEnabled(false);
            btnNext.setEnabled(true);
            resultImage.setVisibility(View.VISIBLE);
        }
        else
        {
            btnAnswer.setEnabled(true);
            btnNext.setEnabled(false);
            resultImage.setVisibility(View.GONE);
        }
    }

    void updateProbContent()
    {
        probNum++;
        probView.setText("Q" + probNum + ". 문제");

        if (probNum == LAST_PROB_NUM)
            btnNext.setText("최종 제출");
    }

    void endQuiz()
    {
        Intent intent = new Intent(QuizProblemActivity.this, PopupActivity.class);
        intent.putExtra("POPUP_TYPE", POPUP_TYPE.END_QUIZ);
        startActivityForResult(intent, END_QUIZ);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == END_QUIZ)
        {
            // TODO: go to AR Activity with finish()
        }
    }
}
