package com.example.footballquiz;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {

    // 🧩 Khai báo các thành phần UI
    private TextView tvQuestion, tvTimer;
    private RadioGroup rgOptions;
    private RadioButton rbOption1, rbOption2, rbOption3, rbOption4;
    private Button btnNext;

    // 🧠 Dữ liệu câu hỏi
    private ArrayList<Question> questionList;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private CountDownTimer countDownTimer;

    // ⏳ Thời gian mặc định
    private long TIME_LIMIT = 15000; // 15s mặc định
    private long timeLeft = TIME_LIMIT;

    private String playerName;
    private String mode; // ⚙️ Chế độ người chơi (easy / normal / hard)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // 🔗 Ánh xạ view
        tvQuestion = findViewById(R.id.tvQuestion);
        tvTimer = findViewById(R.id.tvTimer);
        rgOptions = findViewById(R.id.rgOptions);
        rbOption1 = findViewById(R.id.rbOption1);
        rbOption2 = findViewById(R.id.rbOption2);
        rbOption3 = findViewById(R.id.rbOption3);
        rbOption4 = findViewById(R.id.rbOption4);
        btnNext = findViewById(R.id.btnNext);

        // 🧍‍♂️ Nhận dữ liệu từ MainActivity
        playerName = getIntent().getStringExtra("playerName");
        mode = getIntent().getStringExtra("mode");

        if (playerName == null || playerName.trim().isEmpty()) playerName = "Người chơi";
        if (mode == null) mode = "normal"; // Mặc định là “Trung bình”

        // 🧠 Áp dụng cấu hình theo chế độ
        DBHelper dbHelper = new DBHelper(this);

        switch (mode) {
            case "easy":
                TIME_LIMIT = 0; // không giới hạn thời gian
                questionList = dbHelper.getRandomQuestions(10);
                break;

            case "hard":
                TIME_LIMIT = 10000; // 10s mỗi câu
                questionList = dbHelper.getRandomQuestions(15);
                break;

            default: // normal
                TIME_LIMIT = 15000; // 15s
                questionList = dbHelper.getRandomQuestions(10);
                break;
        }

        timeLeft = TIME_LIMIT;

        // Nếu không có câu hỏi
        if (questionList.isEmpty()) {
            tvQuestion.setText("❌ Không có câu hỏi nào trong cơ sở dữ liệu!");
            btnNext.setEnabled(false);
            return;
        }

        // 🖥️ Hiển thị câu hỏi đầu tiên
        showQuestion();

        // 👉 Nút “Câu tiếp theo”
        btnNext.setOnClickListener(v -> {
            checkAnswer();
            currentQuestionIndex++;
            if (currentQuestionIndex < questionList.size()) {
                showQuestion();
            } else {
                finishQuiz();
            }
        });
    }

    // 🖥️ Hiển thị câu hỏi hiện tại
    private void showQuestion() {
        resetTimer();

        // Nếu không giới hạn thời gian (chế độ easy)
        if (TIME_LIMIT > 0) {
            startTimer();
            tvTimer.setText("⏳ " + (TIME_LIMIT / 1000) + "s");
        } else {
            tvTimer.setText("♾️ Không giới hạn");
        }

        Question q = questionList.get(currentQuestionIndex);
        tvQuestion.setText((currentQuestionIndex + 1) + ". " + q.getQuestion());
        rbOption1.setText(q.getOption1());
        rbOption2.setText(q.getOption2());
        rbOption3.setText(q.getOption3());
        rbOption4.setText(q.getOption4());
        rgOptions.clearCheck();
    }

    // ✅ Kiểm tra đáp án
    private void checkAnswer() {
        int selectedId = rgOptions.getCheckedRadioButtonId();
        if (selectedId == -1) return;

        int selectedAnswer = 0;
        if (selectedId == R.id.rbOption1) selectedAnswer = 1;
        else if (selectedId == R.id.rbOption2) selectedAnswer = 2;
        else if (selectedId == R.id.rbOption3) selectedAnswer = 3;
        else if (selectedId == R.id.rbOption4) selectedAnswer = 4;

        if (selectedAnswer == questionList.get(currentQuestionIndex).getCorrectAnswer()) {
            score++;
        }
    }

    // ✅ Kết thúc quiz
    private void finishQuiz() {
        if (countDownTimer != null) countDownTimer.cancel();

        Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
        intent.putExtra("score", score);
        intent.putExtra("total", questionList.size());
        intent.putExtra("playerName", playerName);
        intent.putExtra("mode", mode); // ⚙️ Gửi thêm chế độ để hiện ở ResultActivity
        startActivity(intent);
        finish();
    }

    // ⏱️ Bộ đếm thời gian
    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                tvTimer.setText("⏳ " + (timeLeft / 1000) + "s");
            }

            @Override
            public void onFinish() {
                currentQuestionIndex++;
                if (currentQuestionIndex < questionList.size()) {
                    showQuestion();
                } else {
                    finishQuiz();
                }
            }
        }.start();
    }

    // 🔄 Reset đồng hồ
    private void resetTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        timeLeft = TIME_LIMIT;
    }

    @Override
    protected void onDestroy() {
        if (countDownTimer != null) countDownTimer.cancel();
        super.onDestroy();
    }
}
