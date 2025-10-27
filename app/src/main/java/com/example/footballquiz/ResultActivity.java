package com.example.footballquiz;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import android.content.Intent;
import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    private TextView tvResult, tvLeaderboard;
    private Button btnReplay, btnExit;
    private DBHelper dbHelper;
    private void loadLeaderboard() {
        ArrayList<String> topList = dbHelper.getTopScores();
        StringBuilder leaderboardText = new StringBuilder();
        int rank = 1;
        for (String s : topList) {
            leaderboardText.append(rank).append(". ").append(s).append("\n");
            rank++;
        }
        tvLeaderboard.setText(leaderboardText.toString());
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadLeaderboard();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Ánh xạ view
        tvResult = findViewById(R.id.tvResult);
        tvLeaderboard = findViewById(R.id.tvLeaderboard);
        btnReplay = findViewById(R.id.btnReplay);
        btnExit = findViewById(R.id.btnExit);

        dbHelper = new DBHelper(this);

        // Lấy điểm từ Intent
        int score = getIntent().getIntExtra("score", 0);
        tvResult.setText("Điểm của bạn: " + score + " / 20");


        // Lưu điểm (tạm đặt tên mặc định là "Người chơi")
        // Nhận tên người chơi từ Intent
        String playerName = getIntent().getStringExtra("playerName");
        if (playerName == null || playerName.trim().isEmpty()) {
            playerName = "Người chơi"; // phòng trường hợp không nhập
        }

        // Hiển thị điểm kèm tên
        tvResult.setText("Điểm của " + playerName + ": " + score + " / 20");

        // Lưu điểm vào cơ sở dữ liệu
        dbHelper.saveScore(playerName, score);


        // Lấy danh sách top điểm
        ArrayList<String> topList = dbHelper.getTopScores();
        StringBuilder leaderboardText = new StringBuilder();
        int rank = 1;
        for (String s : topList) {
            leaderboardText.append(rank).append(". ").append(s).append("\n");
            rank++;
        }
        tvLeaderboard.setText(leaderboardText.toString());
        Button btnViewScores = findViewById(R.id.btnViewScores);
        btnViewScores.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this, ScoreListActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });

        // Nút chơi lại
        btnReplay.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        // Nút thoát
        btnExit.setOnClickListener(v -> {
            finishAffinity(); // Đóng toàn bộ app
        });
        // Nút làm mới
        Button btnReload = findViewById(R.id.btnReload);
        btnReload.setOnClickListener(v -> loadLeaderboard());

    }
}
