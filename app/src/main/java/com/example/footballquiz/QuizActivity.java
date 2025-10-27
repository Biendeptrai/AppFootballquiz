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

    // ⏳ Thời gian giới hạn mỗi câu (15 giây)
    private static final long TIME_LIMIT = 15000;
    private long timeLeft = TIME_LIMIT;

    private String playerName; // ✅ Lưu tên người chơi truyền từ MainActivity

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

        // 🧍‍♂️ Nhận tên người chơi từ MainActivity
        playerName = getIntent().getStringExtra("playerName");
        if (playerName == null || playerName.trim().isEmpty()) {
            playerName = "Người chơi"; // Dự phòng
        }

        // 🧩 Lấy 10 câu hỏi ngẫu nhiên từ cơ sở dữ liệu
        DBHelper dbHelper = new DBHelper(this);
        questionList = dbHelper.getRandomQuestions(10);

        // Nếu không có câu hỏi
        if (questionList.isEmpty()) {
            tvQuestion.setText("❌ Không có câu hỏi nào trong cơ sở dữ liệu!");
            btnNext.setEnabled(false);
            return;
        }

        // Hiển thị câu hỏi đầu tiên
        showQuestion();

        // 👉 Sự kiện nút “Câu tiếp theo”
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
        startTimer();

        Question q = questionList.get(currentQuestionIndex);
        tvQuestion.setText((currentQuestionIndex + 1) + ". " + q.getQuestion());
        rbOption1.setText(q.getOption1());
        rbOption2.setText(q.getOption2());
        rbOption3.setText(q.getOption3());
        rbOption4.setText(q.getOption4());
        rgOptions.clearCheck();
    }

    // ✅ Kiểm tra đáp án và cộng điểm
    private void checkAnswer() {
        int selectedId = rgOptions.getCheckedRadioButtonId();
        if (selectedId == -1) return; // chưa chọn đáp án

        int selectedAnswer = 0;
        if (selectedId == R.id.rbOption1) selectedAnswer = 1;
        else if (selectedId == R.id.rbOption2) selectedAnswer = 2;
        else if (selectedId == R.id.rbOption3) selectedAnswer = 3;
        else if (selectedId == R.id.rbOption4) selectedAnswer = 4;

        // So sánh với đáp án đúng
        if (selectedAnswer == questionList.get(currentQuestionIndex).getCorrectAnswer()) {
            score++;
        }
    }

    // ✅ Khi kết thúc quiz
    private void finishQuiz() {
        if (countDownTimer != null) countDownTimer.cancel();

        Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
        intent.putExtra("score", score);
        intent.putExtra("total", questionList.size());
        intent.putExtra("playerName", playerName); // ✅ Gửi tên người chơi
        startActivity(intent);
        finish();
    }

    // ⏱️ Bộ đếm thời gian cho mỗi câu hỏi
    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                tvTimer.setText("⏳ " + (timeLeft / 1000) + "s");
            }

            @Override
            public void onFinish() {
                // Nếu hết giờ mà chưa chọn thì tự chuyển câu
                currentQuestionIndex++;
                if (currentQuestionIndex < questionList.size()) {
                    showQuestion();
                } else {
                    finishQuiz();
                }
            }
        }.start();
    }

    // 🔄 Reset lại đồng hồ mỗi lần sang câu mới
    private void resetTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        timeLeft = TIME_LIMIT;
    }

    // 🧹 Giải phóng bộ đếm khi thoát activity
    @Override
    protected void onDestroy() {
        if (countDownTimer != null) countDownTimer.cancel();
        super.onDestroy();
    }
}
