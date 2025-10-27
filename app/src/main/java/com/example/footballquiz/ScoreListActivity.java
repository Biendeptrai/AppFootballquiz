package com.example.footballquiz;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import androidx.appcompat.app.AppCompatActivity;

public class ScoreListActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private ListView lvScores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_list);

        lvScores = findViewById(R.id.lvScores);
        dbHelper = new DBHelper(this);

        Cursor cursor = dbHelper.getAllScores();

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_2,
                cursor,
                new String[]{"playerName", "score"},
                new int[]{android.R.id.text1, android.R.id.text2},
                0
        );

        lvScores.setAdapter(adapter);
    }
}
