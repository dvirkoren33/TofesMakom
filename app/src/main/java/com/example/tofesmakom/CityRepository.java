package com.example.tofesmakom;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

// מחלקה זו אחראית על טעינת מאגר היישובים מתוך קובץ ה-JSON ב-assets ועל בדיקת תקינות הניחושים
public class CityRepository {

    // רשימה שתחזיק את כל אובייקטי היישובים בזיכרון ה-RAM של הטלפון
    private List<City> cityList;

    // בנאי המחלקה - מקבל Context (הקשר של ה-Activity) כדי שנוכל לגשת לקובצי ה-assets
    public CityRepository(Context context) {
        cityList = new ArrayList<>(); // יצירת רשימה ריקה
        loadCitiesFromAssets(context); // הפעלת פונקציית הטעינה מיד עם יצירת ה-Repository
    }

    // פונקציה פרטית לקריאת קובץ ה-JSON מתיקיית assets והמרתו לרשימת אובייקטים
    private void loadCitiesFromAssets(Context context) {
        try {
            // 1. פתיחת צינור קריאה (InputStream) לקובץ cities.json מתוך תיקיית assets
            InputStream is = context.getAssets().open("cities.json");

            // 2. בדיקת גודל הקובץ ויצירת מערך בייטים בגודל המתאים לקליטת הנתונים
            int size = is.available();
            byte[] buffer = new byte[size];

            // 3. קריאת כל הנתונים מהקובץ לתוך המערך וסגירת צינור הקריאה
            is.read(buffer);
            is.close();

            // 4. המרת מערך הבייטים למחרוזת טקסט בפורמט UTF-8 (תמיכה מלאה בעברית)
            String jsonString = new String(buffer, StandardCharsets.UTF_8);

            // 5. המרת המחרוזת לאובייקט JSONArray שמכיל את כל רשימת היישובים
            JSONArray jsonArray = new JSONArray(jsonString);

            // 6. לולאה שעוברת יישוב-יישוב במערך ה-JSON וממירה אותו לאובייקט City ב-Java
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i); // שליפת אובייקט ה-JSON הנוכחי

                String name = obj.getString("name");              // שליפת שם היישוב
                String region = obj.getString("region");          // שליפת המחוז
                int year = obj.getInt("establishedYear");         // שליפת שנת ההקמה

                // יצירת אובייקט City חדש והוספתו לרשימה בזיכרון
                cityList.add(new City(name, region, year));
            }
        } catch (Exception e) {
            // הדפסת השגיאה ל-Logcat במידה ומשהו נכשל בטעינת הקובץ
            e.printStackTrace();
        }
    }

    // פונקציה שבודקת האם שם יישוב שהוקלד ע"י השחקן קיים במאגר
    public boolean isCityExists(String cityName) {
        // אם הטקסט ריק או null - מחזירים false מיד
        if (cityName == null || cityName.trim().isEmpty()) {
            return false;
        }

        // ניקוי רווחים מיותרים בהתחלה ובסוף המחרוזת
        String cleanInput = cityName.trim();

        // מעבר על כל היישובים ברשימה ובדיקה אם השם תואם (ללא הבדל בין אותיות גדולות/קטנות)
        for (City city : cityList) {
            if (city.getName().equalsIgnoreCase(cleanInput)) {
                return true; // היישוב נמצא במאגר!
            }
        }

        return false; // הגיע לסוף הרשימה ולא מצא את היישוב
    }

    // פונקציה לשליפת אובייקט City המלא לפי השם שלו (משמש לבדיקת מחוז, שנת הקמה וכדומה)
    public City getCityByName(String cityName) {
        if (cityName == null) return null;

        String cleanInput = cityName.trim();
        for (City city : cityList) {
            if (city.getName().equalsIgnoreCase(cleanInput)) {
                return city; // מחזיר את האובייקט המלא של היישוב (שם, מחוז, שנת הקמה)
            }
        }

        return null; // לא נמצא יישוב כזה
    }
}