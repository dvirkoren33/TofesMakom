package com.example.tofesmakom;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class LeaderboardActivity extends AppCompatActivity {

    private RecyclerView rvLeaderboard;
    private LeaderboardAdapter adapter;
    private List<ScoreItem> scoreList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        rvLeaderboard = findViewById(R.id.rvLeaderboard);
        rvLeaderboard.setLayoutManager(new LinearLayoutManager(this));

        // הזנת נתוני דמי
        scoreList = new ArrayList<>();
        scoreList.add(new ScoreItem("דניאל שרעבי", 1500, "01/11/2026"));
        scoreList.add(new ScoreItem("עדי מורג", 1250, "31/10/2026"));
        scoreList.add(new ScoreItem("עידו לוי", 900, "30/10/2026"));

        // מיון הרשימה מהניקוד הגבוה ביותר לנמוך ביותר
        scoreList.sort((item1, item2) -> Integer.compare(item2.getScore(), item1.getScore()));

        // חיבור ל-Adapter לאחר המיון
        adapter = new LeaderboardAdapter(scoreList);
        rvLeaderboard.setAdapter(adapter);

        // כפתור חזרה לתפריט הראשי
        Button btnBackToMain = findViewById(R.id.btnBackToMain);
        btnBackToMain.setOnClickListener(v -> finish());
    }
}