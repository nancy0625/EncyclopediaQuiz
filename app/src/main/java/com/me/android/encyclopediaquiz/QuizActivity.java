package com.me.android.encyclopediaquiz;

import android.app.Activity;
import android.content.Intent;      //启用answer Activity  Alt Enter自动导入
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class QuizActivity extends Activity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final int REQUEST_CODE_ANSWER = 0;
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mUponButton;
    private Button mNextButton;
    private Button mAnswerButton;
    private TextView mQuestionTextView;

    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_geo, true),
            new Question(R.string.question_edu, false),
            new Question(R.string.question_it, true),
            new Question(R.string.question_phy, true),
            new Question(R.string.question_history, true),
    };

    private int mCurrentIndex = 0;
    private boolean mIsShownAnswer;

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;
        if (mIsShownAnswer) {
            messageResId = R.string.judgement_toast;
        } else {
            if (userPressedTrue == answerIsTrue) {
                mCurrentIndex = (mCurrentIndex +1) % mQuestionBank.length;
                updateQuestion();
                messageResId = R.string.correct_toast;
            } else {
                mCurrentIndex = (mCurrentIndex  +4) % mQuestionBank.length;
                updateQuestion();
                messageResId = R.string.incorrect_toast;
            }
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "调用 onCreate(Bundle) 方法");
        setContentView(R.layout.activity_quiz);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);


        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkAnswer(true);
            }
        });
        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkAnswer(false);
            }
        });

        mUponButton = (Button) findViewById(R.id.upon_button);
        mUponButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 4) % mQuestionBank.length;
                mIsShownAnswer = false;
                updateQuestion();
            }
        });
        updateQuestion();


        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                mIsShownAnswer = false;
                updateQuestion();
            }
        });
        mAnswerButton = (Button) findViewById(R.id.show_answer_button);
        mAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //启动Answer Activity.
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent i = AnswerActivity.newIntent(QuizActivity.this, answerIsTrue);
                startActivityForResult(i, REQUEST_CODE_ANSWER);
                //目前我们还不知道如何启动，但知道应当在这里启动。继续学习！
            }
        });
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }
        updateQuestion();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_ANSWER) {
            if (data == null) {
                return;
            }
            mIsShownAnswer = AnswerActivity.wasAnswerShown(data);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        Log.i(TAG, "调用 onSaveInstanceState() 方法");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);//传入Bundle
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "调用 onStart() 方法");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "调用 onPause() 方法");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "调用 onResume() 方法");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "调用 onStop() 方法");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "调用 onDestroy() 方法");
    }

}