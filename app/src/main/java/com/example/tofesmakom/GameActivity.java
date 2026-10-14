package com.example.tofesmakom;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GameActivity extends AppCompatActivity {
    private TextView tvWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        tvWelcome = findViewById(R.id.tvWelcome);
        Intent intent = getIntent();
        String playerName = intent.getStringExtra("PLAYER_NAME");

        if (playerName != null && !playerName.isEmpty()) {
            tvWelcome.setText("בהצלחה " + playerName + "! המשחק מתחיל...");
        }


    }
    @Override
    protected void onPause() {
        super.onPause();
        // כאן נעצור טיימרים או מוזיקה כשהמשתמש יוצא מהאפליקציה או מקבל שיחה
        Toast.makeText(this, "המשחק הושהה (onPause)", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // כאן נחדש את המשחק כשהמשתמש חוזר למסך
        Toast.makeText(this, "המשחק חזר לפעולה (onResume)", Toast.LENGTH_SHORT).show();
    }
}