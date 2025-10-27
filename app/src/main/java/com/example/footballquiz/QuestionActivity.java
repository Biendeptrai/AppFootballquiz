package com.example.footballquiz;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Button;
import android.content.Intent;
import java.util.ArrayList;
import android.os.CountDownTimer;
import android.widget.Toast;

public class QuestionActivity extends AppCompatActivity {

    TextView tvQuestion, tvTimer, tvScore;
    RadioGroup rgOptions;
    RadioButton rb1, rb2, rb3, rb4;
    Button btnNext;

    DBHelper dbHelper;
    ArrayList<Question> questionList;
    int currentQuestion = 0;
    int score = 0;
    int totalQuestions = 10;
    int timePerQuestion = 0; // 0 = không giới hạn
    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        // Ánh xạ
        tvQuestion = findViewById(R.id.tvQuestion);
        rgOptions = findViewById(R.id.rgOptions);
        rb1 = findViewById(R.id.rbOption1);
        rb2 = findViewById(R.id.rbOption2);
        rb3 = findViewById(R.id.rbOption3);
        rb4 = findViewById(R.id.rbOption4);
        btnNext = findViewById(R.id.btnNext);
        tvScore = findViewById(R.id.tvScore);
        tvTimer = findViewById(R.id.tvTimer);

        dbHelper = new DBHelper(this);

        // 🟩 Nhận chế độ chơi
        String mode = getIntent().getStringExtra("mode");
        if (mode == null) mode = "easy";

        if ("easy".equals(mode)) {
            totalQuestions = 10;
        } else if ("hard".equals(mode)) {
            totalQuestions = 20;
            timePerQuestion = 10;
        } else if ("expert".equals(mode)) {
            totalQuestions = 30;
            timePerQuestion = 5;
        } else if ("random".equals(mode)) {
            totalQuestions = 15;
            timePerQuestion = 0;
        }

        // Lấy danh sách câu hỏi
        questionList = dbHelper.getRandomQuestions(totalQuestions);

        if (questionList == null || questionList.isEmpty()) {
            Toast.makeText(this, "Không có câu hỏi trong cơ sở dữ liệu!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        showQuestion();

        btnNext.setOnClickListener(v -> {
            checkAnswer();
            currentQuestion++;
            if (currentQuestion < questionList.size()) {
                showQuestion();
            } else {
                finishQuiz();
            }
        });
    }

    private void showQuestion() {
        if (timer != null) timer.cancel();

        Question q = questionList.get(currentQuestion);
        tvQuestion.setText("Câu " + (currentQuestion + 1) + ": " + q.getQuestion());
        rb1.setText(q.getOption1());
        rb2.setText(q.getOption2());
        rb3.setText(q.getOption3());
        rb4.setText(q.getOption4());
        rgOptions.clearCheck();
        tvScore.setText("Điểm: " + score);

        if (timePerQuestion > 0) {
            startTimer(timePerQuestion);
        } else {
            tvTimer.setText("⏱ Không giới hạn");
        }
    }

    private void checkAnswer() {
        int selectedId = rgOptions.getCheckedRadioButtonId();
        if (selectedId == -1) return; // chưa chọn
        RadioButton selected = findViewById(selectedId);
        int answerIndex = rgOptions.indexOfChild(selected) + 1; // 1–4

        if (answerIndex == questionList.get(currentQuestion).getCorrectAnswer()) {
            score++;
        }

        if (timer != null) timer.cancel();
    }

    private void startTimer(int seconds) {
        timer = new CountDownTimer(seconds * 1000L, 1000) {
            public void onTick(long millisUntilFinished) {
                tvTimer.setText("⏱ " + millisUntilFinished / 1000 + "s");
            }
            public void onFinish() {
                checkAnswer();
                currentQuestion++;
                if (currentQuestion < questionList.size()) {
                    showQuestion();
                } else {
                    finishQuiz();
                }
            }
        }.start();
    }

    private void finishQuiz() {
        Intent intent = new Intent(QuestionActivity.this, ResultActivity.class);
        intent.putExtra("score", score);
        startActivity(intent);
        finish();
    }
}
