package com.me.android.encyclopediaquiz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AnswerActivity extends Activity {
    private static final String EXTRA_ANSWER_IS_TRUE =
            "com.me.android.encyclopediaquiz.answer_is_true";
    private static String EXTRA_ANSWER_SHOWN =
            "com.me.android.encylopediaquiz.answer_is_shown";
    private boolean mAnswerIsTrue;
    private TextView mAnswerTextView;
    private Button mShowAnswer;

    public static Intent newIntent(Context packageContext,boolean answerIsTrue){
        Intent i = new Intent(packageContext,AnswerActivity.class);
        i.putExtra(EXTRA_ANSWER_IS_TRUE,answerIsTrue);
        return i;
    }
    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE,false);
        mAnswerTextView = (TextView)findViewById(R.id.answer_text_view);
        mShowAnswer = (Button)findViewById(R.id.continue_button);
        mShowAnswer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (mAnswerIsTrue){
                    mAnswerTextView.setText(R.string.true_button);
                }else {
                    mAnswerTextView.setText(R.string.false_button);
                }
                setAnswerShownResult(true);
            }
        });
    }
    private void setAnswerShownResult(boolean isAnswerShown){
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN,isAnswerShown);
        setResult(RESULT_OK,data);
    }
}
