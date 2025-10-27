package com.example.footballquiz;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText etName;
    private Button btnStart;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ
        etName = findViewById(R.id.etName);
        btnStart = findViewById(R.id.btnStart);
        tvTitle = findViewById(R.id.tvTitle);

        // 🪄 Gọi hiệu ứng fade-in cho tiêu đề
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        tvTitle.startAnimation(fadeIn);

        // Sự kiện bắt đầu quiz
        btnStart.setOnClickListener(v -> {
            String playerName = etName.getText().toString().trim();
            if (playerName.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập tên trước khi bắt đầu!", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                intent.putExtra("playerName", playerName); // 👈 Truyền tên sang QuizActivity
                startActivity(intent);
                finish();
            }
        });
    }
}
