package com.example.tofesmakom;
import android.os.Bundle;
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

        // חיבור ה-RecyclerView מה-XML
        rvLeaderboard = findViewById(R.id.rvLeaderboard);
        rvLeaderboard.setLayoutManager(new LinearLayoutManager(this));

        // נתוני דמי לבדיקת הרשימה
        scoreList = new ArrayList<>();
        scoreList.add(new ScoreItem("דביר קורן", 1500, "01/11/2026"));
        scoreList.add(new ScoreItem("אריאל קורן", 1250, "31/10/2026"));
        scoreList.add(new ScoreItem("ישראל ישראלי", 900, "30/10/2026"));

        // חיבור ה-Adapter ל-RecyclerView
        adapter = new LeaderboardAdapter(scoreList);
        rvLeaderboard.setAdapter(adapter);
    }
}