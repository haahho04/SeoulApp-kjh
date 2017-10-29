package com.kjh.seoulapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.kjh.seoulapp.data.ProblemData;

import static com.kjh.seoulapp.data.SharedData.EXTRA_CORRECT_CNT;
import static com.kjh.seoulapp.data.SharedData.EXTRA_POPUP_TYPE;
import static com.kjh.seoulapp.data.SharedData.POPUP_TYPE;
import static com.kjh.seoulapp.data.SharedData.correctCnt;
import static com.kjh.seoulapp.data.SharedData.probList;

public class QuizProblemActivity extends AppCompatActivity
    implements View.OnClickListener
{
    final String TAG = "QuizProblemActivity";
    final int LAST_PROB_NUM = 3;
    final int END_QUIZ = 1234;
	int probNum;
    TextView probView;
	ProblemData nowProb;
	ImageView resultImage;
	ImageButton answerX, answerO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_problem);

        probView = (TextView) findViewById(R.id.prob_desc);
        resultImage = (ImageView) findViewById(R.id.result_image);
		answerX = (ImageButton) findViewById(R.id.answer_x);
		answerO = (ImageButton) findViewById(R.id.answer_o);
        probNum = 0;
        correctCnt = 0;

		updateNextProb();
    }

    @Override
    public void onClick(View view)
    {
        int id = view.getId();

        switch (id)
        {
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
			endQuiz();
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
            Intent intent = new Intent(QuizProblemActivity.this, ARActivity.class);
			startActivity(intent);
			finish();
        }
    }
}
