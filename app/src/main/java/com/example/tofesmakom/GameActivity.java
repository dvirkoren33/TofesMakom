package com.example.tofesmakom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GameActivity extends AppCompatActivity {

    private TextView tvWelcome;
    private EditText etCityGuess;
    private Button btnSubmitGuess;

    private RecyclerView rvGuesses;
    private CityAdapter cityAdapter;
    private List<City> correctGuessesList;
    private CityRepository cityRepository;

    // משתנים לניקוד ולענן
    private FirebaseFirestore db;
    private String playerName;
    private int currentScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // אתחול Firebase Firestore
        db = FirebaseFirestore.getInstance();

        tvWelcome = findViewById(R.id.tvWelcome);
        etCityGuess = findViewById(R.id.etCityGuess);
        btnSubmitGuess = findViewById(R.id.btnSubmitGuess);
        rvGuesses = findViewById(R.id.rvGuesses);

        cityRepository = new CityRepository(this);
        correctGuessesList = new ArrayList<>();
        cityAdapter = new CityAdapter(correctGuessesList);

        rvGuesses.setLayoutManager(new LinearLayoutManager(this));
        rvGuesses.setAdapter(cityAdapter);

        Intent intent = getIntent();
        playerName = intent.getStringExtra("PLAYER_NAME");

        if (playerName != null && !playerName.isEmpty()) {
            tvWelcome.setText("בהצלחה " + playerName + "! המשחק מתחיל...");
        } else {
            playerName = "שחקן אנונימי";
            tvWelcome.setText("בהצלחה! המשחק מתחיל...");
        }

        btnSubmitGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUserGuess();
            }
        });
    }

    private void checkUserGuess() {
        String userGuess = etCityGuess.getText().toString().trim();

        if (userGuess.isEmpty()) {
            Toast.makeText(this, "אנא הקלד שם של יישוב", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean exists = cityRepository.isCityExists(userGuess);

        if (exists) {
            City city = cityRepository.getCityByName(userGuess);

            if (isAlreadyGuessed(city.getName())) {
                Toast.makeText(this, "כבר ניחשת את " + city.getName() + "!", Toast.LENGTH_SHORT).show();
                return;
            }

            correctGuessesList.add(0, city);
            cityAdapter.notifyItemInserted(0);
            rvGuesses.scrollToPosition(0);

            // הוספת 100 נקודות על תשובה נכונה ושמירה לענן
            currentScore += 100;
            saveScoreToFirestore(playerName, currentScore);

            Toast.makeText(this, "תשובה נכונה! הניקוד: " + currentScore, Toast.LENGTH_SHORT).show();
            etCityGuess.setText("");
        } else {
            Toast.makeText(this, "היישוב '" + userGuess + "' לא נמצא במאגר!", Toast.LENGTH_SHORT).show();
        }
    }

    // פונקציה לשמירת התוצאה בענן (Firestore)
    private void saveScoreToFirestore(String name, int score) {
        String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        ScoreItem scoreItem = new ScoreItem(name, score, currentDate);

        db.collection("scores")
                .add(scoreItem)
                .addOnSuccessListener(documentReference -> {
                    // נשמר בהצלחה בענן
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "שגיאה בשמירה לענן: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private boolean isAlreadyGuessed(String cityName) {
        for (City c : correctGuessesList) {
            if (c.getName().equalsIgnoreCase(cityName)) {
                return true;
            }
        }
        return false;
    }
}