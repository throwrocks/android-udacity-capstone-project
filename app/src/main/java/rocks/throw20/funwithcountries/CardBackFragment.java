package rocks.throw20.funwithcountries;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import rocks.throw20.funwithcountries.Data.Contract;

/**
 * Created by joselopez on 7/21/16.
 */
public class CardBackFragment extends android.app.Fragment {

    private static final String LOG_TAG = CardFrontFragment.class.getSimpleName();
    private SharedPreferences sharedPref;

    private TextView answerResultDisplay;
    private TextView answerResultQuestion;
    private TextView answerResultCountry;
    private TextView answerResultCorrectAnswer;

    private LinearLayout nextQuestionView;
    private Button nextQuestionButtonView;

    private LinearLayout viewScoresView;
    private Button viewScoresButtonView;

    public CardBackFragment() {
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Log.e(LOG_TAG, "onCreate " + true);
        Log.e(LOG_TAG, "onCreate " + true);
        if (savedInstanceState == null) {
            getArguments().putString("savedInstanceState", null);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_card_back, container, false);

        answerResultDisplay = (TextView) rootView.findViewById(R.id.game_answer_result_display);
        answerResultQuestion = (TextView) rootView.findViewById(R.id.game_answer_result_question_part1);
        answerResultCountry = (TextView) rootView.findViewById(R.id.game_answer_result_question_country);
        answerResultCorrectAnswer = (TextView) rootView.findViewById(R.id.game_answer_result_correct_answer);

        nextQuestionView = (LinearLayout) rootView.findViewById(R.id.game_next_question_view);
        nextQuestionButtonView = (Button) rootView.findViewById(R.id.game_next_question_button);

        viewScoresView = (LinearLayout) rootView.findViewById(R.id.game_view_scores_view);
        viewScoresButtonView = (Button) rootView.findViewById(R.id.game_view_scores_button);
        answerQuestionView();

        return rootView;
    }

    /**
     * selectedAnswerView
     * This method builds the confirmation answer text, and button to submit the asnwer
     */
    private void answerQuestionView() {
        String resultText = getArguments().getString("answer_result");
        String countryName = getArguments().getString("country_name");
        String countryCapital = getArguments().getString("country_capital");
        String gameMode = sharedPref.getString("game_mode", "");
        int gameProgress = sharedPref.getInt("game_progress", 0);
        int gameProgressMax = sharedPref.getInt("game_progress_max", 0);
        Log.e(LOG_TAG, "game progress " + gameProgress);
        Log.e(LOG_TAG, "game max " + gameProgressMax);
        //------------------------------------------------------------------------------------------
        // answerResultView
        //------------------------------------------------------------------------------------------
        if (resultText != null && resultText.equals("Incorrect")) {
            answerResultDisplay.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.badge_incorrect) );
        } else if (resultText != null && resultText.equals("Time up!")) {
            answerResultDisplay.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.badge_timeup) );
        } else if (resultText != null && resultText.equals("Correct")) {
            answerResultDisplay.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.badge_correct) );
        }
        answerResultDisplay.setText(resultText);
        //------------------------------------------------------------------------------------------
        // nextQuestionTextView
        //------------------------------------------------------------------------------------------
        if (gameMode.equals("capitals")) {
            answerResultQuestion.setText("The capital of");
            answerResultCountry.setText(countryName);
            answerResultCorrectAnswer.setText(countryCapital);
        } /* else if (gameMode.equals("flags")) {

                nextQuestionTextView.setText(resultTextDescription);
                answerFlag = new ImageView(getActivity());
                //Log.e(LOG_TAG,"Next/answer " + answer);
                // Display the correct flag
                int flagDrawable = util.getDrawable(getContext(), "flag_" + currentAnswer.toLowerCase());
                Picasso.with(getContext()).load(flagDrawable)
                        .resize(220, 140)
                        .onlyScaleDown()
                        .into(answerFlag);
                gameAnswer.addView(answerFlag);
            }
        }*/
        //------------------------------------------------------------------------------------------
        // The game is over, show the "View Score" button instead of the "Next Question" button
        //------------------------------------------------------------------------------------------
        if (gameProgressMax == gameProgress - 1) {
            nextQuestionView.setVisibility(View.GONE);
            viewScoresView.setVisibility(View.VISIBLE);
            viewScoresButtonView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.e(LOG_TAG, "endGame " + "lel");
                    endGame();
                }
            });
        }

        //------------------------------------------------------------------------------------------
        // Game in progress, show the "Next Question" button
        //------------------------------------------------------------------------------------------
        else {
            //------------------------------------------------------------------------------------------
            // nextQuestionButtonView: Set on ClickListeners
            //------------------------------------------------------------------------------------------
            nextQuestionButtonView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    nextButtonOnClickListener();
                }
            });
            nextQuestionButtonView.setText(R.string.action_next_question);
            // Display the Score
            int correctAnswers = sharedPref.getInt("correct_answers", 0);

        }
    }
    private void nextButtonOnClickListener() {
        getArguments().clear();
        ((GameActivity)getActivity()).flipCard(getArguments());
    }

    /**
     * endGame
     * Method to end the game, submit the scores, and start the scores activity
     */
    private void endGame(){
        // Launch the ScoresActivity and end the GameActivity
        final Intent intent = new Intent(getContext(), ScoresActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActivity().finish();
    }

}