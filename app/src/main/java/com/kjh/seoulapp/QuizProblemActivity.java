package com.kjh.seoulapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kjh.seoulapp.data.ProblemData;

import java.util.ArrayList;
import java.util.List;

import static com.kjh.seoulapp.data.SharedData.*;

public class QuizProblemActivity extends AppCompatActivity
    implements View.OnClickListener
{
    final String TAG = "QuizProblemActivity";
    final int LAST_PROB_NUM = 3;
    final int END_QUIZ = 1234;
	int nowIndex;
	ProblemData nowProb;
	List<ProblemData> probList;
    TextView descTxtView;
	ImageView popupImgView;
	ImageButton btnX, btnO;
    boolean waitNextProb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_problem);

		// SharedData.members init
		correctCnt = 0;

		nowIndex = 0;
		nowProb = null;
		probList = new ArrayList<>();
		probList.add(new ProblemData(culturalData.prob1, culturalData.ans1));
		probList.add(new ProblemData(culturalData.prob2, culturalData.ans2));
		probList.add(new ProblemData(culturalData.prob3, culturalData.ans3));
        descTxtView = (TextView) findViewById(R.id.prob_desc);
        popupImgView = (ImageView) findViewById(R.id.result_image);
		btnX = (ImageButton) findViewById(R.id.answer_x);
		btnO = (ImageButton) findViewById(R.id.answer_o);
		Log.d(TAG, "probList: " + probList);

        waitNextProb = false;
		updateNextProb();
    }

    @Override
    public void onClick(View view)
    {
        int id = view.getId();

        if (waitNextProb && id != R.id.result_image)
            return;

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

    void answerProb(boolean answer)
    {
		boolean correct = nowProb.answer;

        if (answer == correct)
        {
            Log.d(TAG, "정답입니다");
            Toast.makeText(this, "정답입니다!", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "팝업된 O를 누르세요!", Toast.LENGTH_LONG).show();

            popupImgView.setImageResource(R.drawable.main_3_1_o);

            correctCnt++;
        }
        else
        {
            Log.d(TAG, "오답입니다");
            Toast.makeText(this, "오답입니다!", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "팝업된 X를 누르세요!", Toast.LENGTH_LONG).show();
            popupImgView.setImageResource(R.drawable.main_3_1_x);
        }

        waitNextProb = true;
		popupImgView.setVisibility(View.VISIBLE);
    }

    void updateNextProb()
    {
		if (nowIndex == LAST_PROB_NUM)
			endQuiz();
		else
		{
			nowProb = probList.get(nowIndex);
			nowIndex++;
			descTxtView.setText("Q" + nowIndex + ". " + nowProb.description);

            waitNextProb = false;
			popupImgView.setVisibility(View.GONE);
		}
    }

    void endQuiz()
    {
        Intent intent = new Intent(QuizProblemActivity.this, PopupActivity.class);
		popupTitle = "퀴즈 결과";
        popupMsg = "맞춘 갯수: " + correctCnt + "\n" +
				"축하드립니다! 다음 화면에서 스탬프를 획득하세요!";
        startActivityForResult(intent, END_QUIZ);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == END_QUIZ)
        {
			stampLevel = 1;
			if (0 < correctCnt)
				stampLevel = 2;
			if (correctCnt == 3)
				stampLevel = 3;

            Intent intent = new Intent(QuizProblemActivity.this, ARActivity.class);
			startActivity(intent);
			finish();
        }
    }
}
