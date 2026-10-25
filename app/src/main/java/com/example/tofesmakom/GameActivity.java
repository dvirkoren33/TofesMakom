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

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    private TextView tvWelcome;
    private EditText etCityGuess;
    private Button btnSubmitGuess;

    // רכיבי ה-RecyclerView
    private RecyclerView rvGuesses;
    private CityAdapter cityAdapter;
    private List<City> correctGuessesList; // רשימה שתשמור את הניחושים התקנים של השחקן

    private CityRepository cityRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // 1. קישור רכיבי הממשק
        tvWelcome = findViewById(R.id.tvWelcome);
        etCityGuess = findViewById(R.id.etCityGuess);
        btnSubmitGuess = findViewById(R.id.btnSubmitGuess);
        rvGuesses = findViewById(R.id.rvGuesses);

        // 2. אתחול מאגר הנתונים
        cityRepository = new CityRepository(this);

        // 3. הגדרת ה-RecyclerView וה-Adapter
        correctGuessesList = new ArrayList<>(); // רשימה ריקה בהתחלה
        cityAdapter = new CityAdapter(correctGuessesList); // יצירת ה-Adapter

        // הגדרת מנהל פריסה (LinearLayoutManager) שמציג את הפריטים ברשימה אנכית
        rvGuesses.setLayoutManager(new LinearLayoutManager(this));
        rvGuesses.setAdapter(cityAdapter); // חיבור ה-Adapter ל-RecyclerView

        // 4. קבלת שם השחקן
        Intent intent = getIntent();
        String playerName = intent.getStringExtra("PLAYER_NAME");

        if (playerName != null && !playerName.isEmpty()) {
            tvWelcome.setText("בהצלחה " + playerName + "! המשחק מתחיל...");
        } else {
            tvWelcome.setText("בהצלחה! המשחק מתחיל...");
        }

        // 5. מאזין לבלחיצה על כפתור הניחוש
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

        // בדיקה מול ה-Repository
        boolean exists = cityRepository.isCityExists(userGuess);

        if (exists) {
            City city = cityRepository.getCityByName(userGuess);

            // בדיקה שטרם ניחשו את היישוב הזה בסיבוב הנוכחי
            if (isAlreadyGuessed(city.getName())) {
                Toast.makeText(this, "כבר ניחשת את " + city.getName() + "!", Toast.LENGTH_SHORT).show();
                return;
            }

            // הוספת היישוב לרשימת הניחושים הנכונים של השחקן
            correctGuessesList.add(0, city); // הוספה לראש הרשימה כדי שהחדש יופיע למעלה

            // עדכון ה-Adapter שהתווסף פריט חדש במיקום 0
            cityAdapter.notifyItemInserted(0);
            rvGuesses.scrollToPosition(0); // גלילה אוטומטית לראש הרשימה

            Toast.makeText(this, "תשובה נכונה!", Toast.LENGTH_SHORT).show();
            etCityGuess.setText(""); // איפוס תיבת ההקלדה
        } else {
            Toast.makeText(this, "היישוב '" + userGuess + "' לא נמצא במאגר!", Toast.LENGTH_SHORT).show();
        }
    }

    // פונקציית עזר לבדיקה אם יישוב כבר קיים ברשימת הניחושים התקנים
    private boolean isAlreadyGuessed(String cityName) {
        for (City c : correctGuessesList) {
            if (c.getName().equalsIgnoreCase(cityName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}