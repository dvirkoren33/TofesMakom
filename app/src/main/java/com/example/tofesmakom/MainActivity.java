package com.example.tofesmakom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText etPlayerName;
    private Button btnStartGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etPlayerName = findViewById(R.id.etPlayerName);
        btnStartGame = findViewById(R.id.btnStartGame);

        btnStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String playerName = etPlayerName.getText().toString().trim();
                if (playerName.isEmpty()) {
                    Toast.makeText(MainActivity.this, "אנא הכנס שם כדי להתחיל", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(MainActivity.this, GameActivity.class);
                    intent.putExtra("PLAYER_NAME", playerName);
                    startActivity(intent);
                }
            }
        });
    }
}