package com.example.tofesmakom;
import android.view.LayoutInflater; // ייבוא מחלקה שטוענת קובצי XML והופכת אותם לרכיבי תצוגה בקוד
import android.view.View; // ייבוא המחלקה הבסיסית של כל רכיבי ה-UI (כפתור, טקסט וכו')
import android.view.ViewGroup; // ייבוא מחלקה שמייצגת מיכל של רכיבי תצוגה (כמו Layout)
import android.widget.TextView; // ייבוא רכיב להצגת טקסט במסך
import androidx.annotation.NonNull; // ייבוא אנוטציה שמסמנת למערכת שמשתנה או ערך מוחזר אינם יכולים להיות null
import androidx.recyclerview.widget.RecyclerView; // ייבוא הרכיב הראשי של הרשימה הדינמית
import java.util.List; // ייבוא מחלקת רשימה (List) מ-Java לשמירת הנתונים

// הגדרת המחלקה LeaderboardAdapter שיורשת מ-Adapter ומנהלת את ה-ViewHolder של השיאים
public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.ScoreViewHolder> {

    private List<ScoreItem> scoreList; // משתנה פרטי שמחזיק את רשימת אובייקטי הנתונים (השיאים)

    // בנאי (Constructor) שמקבל את רשימת הנתונים ושומר אותה במשתנה המחלקה
    public LeaderboardAdapter(List<ScoreItem> scoreList) {
        this.scoreList = scoreList; // שמירת הרשימה שהתקבלה בתוך המשתנה המקומי
    }

    @NonNull // סימון שהפונקציה חייבת להחזיר אובייקט ולא null
    @Override // דריסת פונקציית האב של אנדרואיד ליצירת ViewHolder
    public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // טעינת קובץ העיצוב XML של שורה בודדת (item_score.xml) והפיכתו לאובייקט View בקוד
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_score, parent, false);
        return new ScoreViewHolder(view); // החזרת אובייקט ViewHolder חדש שמחזיק את העיצוב הטעון
    }

    @Override // דריסת פונקציית האב של אנדרואיד לקישור הנתונים ל-UI
    public void onBindViewHolder(@NonNull ScoreViewHolder holder, int position) {
        ScoreItem currentItem = scoreList.get(position); // שליפת אובייקט הנתונים לפי המיקום (index) הנוכחי ברשימה
        holder.tvPlayerName.setText(currentItem.getPlayerName()); // הזנת שם השחקן לתוך רכיב הטקסט המתאים
        holder.tvScore.setText(String.valueOf(currentItem.getScore())); // המרת הניקוד המספרי למחרוזת והזנתו לרכיב הטקסט
        holder.tvDate.setText(currentItem.getDate()); // הזנת התאריך לתוך רכיב הטקסט המתאים
    }

    @Override // דריסת פונקציית האב שמחזירה את כמות הפריטים ברשימה
    public int getItemCount() {
        return scoreList.size(); // מחזיר את מספר הפריטים הכולל שנמצאים ברשימה
    }

    // מחלקה פנימית שמחזיקה את רכיבי ה-UI של שורה בודדת בזיכרון
    public static class ScoreViewHolder extends RecyclerView.ViewHolder {
        public TextView tvPlayerName; // הצהרה על משתנה לרכיב טקסט עבור שם השחקן
        public TextView tvScore; // הצהרה על משתנה לרכיב טקסט עבור הניקוד
        public TextView tvDate; // הצהרה על משתנה לרכיב טקסט עבור התאריך

        // בנאי ל-ViewHolder המקבל את תצוגת השורה הבודדת (itemView)
        public ScoreViewHolder(@NonNull View itemView) {
            super(itemView); // קריאה לבנאי של מחלקת האב RecyclerView.ViewHolder
            tvPlayerName = itemView.findViewById(R.id.tvPlayerName); // מציאת רכיב ה-XML של השם לפי ה-ID שלו
            tvScore = itemView.findViewById(R.id.tvScore); // מציאת רכיב ה-XML של הניקוד לפי ה-ID שלו
            tvDate = itemView.findViewById(R.id.tvDate); // מציאת רכיב ה-XML של התאריך לפי ה-ID שלו
        }
    }
}