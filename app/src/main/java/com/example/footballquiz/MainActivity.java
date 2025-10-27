package com.example.footballquiz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etName;
    private Button btnEasy, btnNormal, btnHard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.edtName);
        btnEasy = findViewById(R.id.btnEasy);
        btnNormal = findViewById(R.id.btnNormal);
        btnHard = findViewById(R.id.btnHard);

        btnEasy.setOnClickListener(v -> startGame("easy"));
        btnNormal.setOnClickListener(v -> startGame("normal"));
        btnHard.setOnClickListener(v -> startGame("hard"));
    }

    private void startGame(String mode) {
        String name = etName.getText().toString().trim();
        if (name.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tên người chơi!", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(MainActivity.this, QuizActivity.class);
        intent.putExtra("playerName", name);
        intent.putExtra("mode", mode);
        startActivity(intent);
    }
}
