package com.example.tofesmakom;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardActivity extends AppCompatActivity {

    private RecyclerView rvLeaderboard;
    private LeaderboardAdapter adapter;
    private List<ScoreItem> scoreList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        rvLeaderboard = findViewById(R.id.rvLeaderboard);
        rvLeaderboard.setLayoutManager(new LinearLayoutManager(this));

        scoreList = new ArrayList<>();
        adapter = new LeaderboardAdapter(scoreList);
        rvLeaderboard.setAdapter(adapter);

        // אתחול Firebase Firestore והאזנה בזמן אמת
        db = FirebaseFirestore.getInstance();
        listenToLeaderboardUpdates();

        Button btnBackToMain = findViewById(R.id.btnBackToMain);
        btnBackToMain.setOnClickListener(v -> finish());
    }

    private void listenToLeaderboardUpdates() {
        db.collection("scores")
                .orderBy("score", Query.Direction.DESCENDING)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Toast.makeText(this, "שגיאה בטעינת נתונים: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (value != null) {
                        scoreList.clear();
                        for (QueryDocumentSnapshot doc : value) {
                            ScoreItem item = doc.toObject(ScoreItem.class);
                            scoreList.add(item);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}