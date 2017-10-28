package com.kjh.seoulapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.kjh.seoulapp.data.ProblemData;

import java.util.ArrayList;
import java.util.List;

import static com.kjh.seoulapp.PopupActivity.POPUP_TYPE;
import static com.kjh.seoulapp.data.GlobalVariables.EXTRA_CORRECT_CNT;
import static com.kjh.seoulapp.data.GlobalVariables.EXTRA_POPUP_TYPE;

public class QuizProblemActivity extends AppCompatActivity
    implements View.OnClickListener
{
    final String TAG = "QuizProblemActivity";
    final int LAST_PROB_NUM = 3;
    final int END_QUIZ = 1234;
    static List<ProblemData> probList = new ArrayList<>();
    TextView probView;
    Button btnPrev, btnNext;
    ImageView resultImage;
    int probNum;
    int correctCnt;
	ProblemData nowProb;
	ImageButton answerX, answerO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_problem);

        probView = (TextView) findViewById(R.id.prob_desc);
        btnPrev = (Button) findViewById(R.id.btn_prev);
        btnNext = (Button) findViewById(R.id.btn_next);
        resultImage = (ImageView) findViewById(R.id.result_image);
		answerX = (ImageButton) findViewById(R.id.answer_x);
		answerO = (ImageButton) findViewById(R.id.answer_o);
        probNum = 0;
        correctCnt = 0;

        btnPrev.setEnabled(false);

		updateNextProb();
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
            case R.id.btn_next:
                if (probNum < LAST_PROB_NUM) updateNextProb();
                else endQuiz();
                break;
            case R.id.answer_x:
                answerProb(false);
                break;
            case R.id.answer_o:
                answerProb(true);
                break;
			case R.id.result_image:
				updateNextProb();
				break;
        }
    } // onClick()

    void prevProb()
    {
        // TODO
    }

    void answerProb(boolean answer)
    {
		boolean correct = nowProb.answer;

        if (answer == correct)
        {
            Log.d(TAG, "정답입니다");
            // TODO: UI Update
            // resultImage.setimage (O)
            correctCnt++;
            //enableBtnNext(true); // resultImage 클릭 시 next
        }
        else
        {
            Log.d(TAG, "오답입니다");
            // TODO: UI Update
            // resultImage.setimage (X)
            //enableBtnNext(true);  // resultImage 클릭 시 next
        }

		resultImage.setVisibility(View.VISIBLE);
    }

    void updateNextProb()
    {
		if (probNum == LAST_PROB_NUM)
		{
			endQuiz();
		}
		else
		{
			nowProb = probList.get(probNum);
			probNum++;
			probView.setText("Q" + probNum + ". " + nowProb.description);

			resultImage.setVisibility(View.GONE);
		}
    }

    void endQuiz()
    {
        Intent intent = new Intent(QuizProblemActivity.this, PopupActivity.class);
        intent.putExtra(EXTRA_POPUP_TYPE, POPUP_TYPE.END_QUIZ);
        intent.putExtra(EXTRA_CORRECT_CNT, correctCnt);
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
