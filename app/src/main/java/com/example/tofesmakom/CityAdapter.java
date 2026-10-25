package com.example.tofesmakom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

// ה-Adapter מנהל את החיבור בין רשימת הניחושים (הנתונים) לבין ה-RecyclerView (התצוגה)
public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> {

    // רשימת היישובים שהמתאם יציג ברשימה
    private List<City> cityList;

    // בנאי המקבל את רשימת היישובים להצגה
    public CityAdapter(List<City> cityList) {
        this.cityList = cityList;
    }

    // 1. ניפוח (Inflate) קובץ ה-XML של השורה הבודדת (item_city.xml) ויצירת ViewHolder חדש
    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_city, parent, false);
        return new CityViewHolder(view);
    }

    // 2. חיבור הנתונים של יישוב ספציפי (לפי מיקום) לרכיבי ה-TextView של השורה
    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int position) {
        City currentCity = cityList.get(position); // שליפת היישוב הנוכחי

        // עדכון הטקסטים ברכיבי הממשק של השורה
        holder.tvCityName.setText(currentCity.getName());
        holder.tvCityRegion.setText("מחוז: " + currentCity.getRegion());
        holder.tvCityYear.setText("הוקם: " + currentCity.getEstablishedYear());
    }

    // 3. החזרת מספר הפריטים הכולל ברשימה
    @Override
    public int getItemCount() {
        return cityList.size();
    }

    // מחלקת ה-ViewHolder: תופסת את רכיבי הממשק מ-item_city.xml ב-findViewById
    public static class CityViewHolder extends RecyclerView.ViewHolder {

        TextView tvCityName;
        TextView tvCityRegion;
        TextView tvCityYear;

        public CityViewHolder(@NonNull View itemView) {
            super(itemView);
            // קישור הרכיבים מתוך item_city.xml
            tvCityName = itemView.findViewById(R.id.tvCityName);
            tvCityRegion = itemView.findViewById(R.id.tvCityRegion);
            tvCityYear = itemView.findViewById(R.id.tvCityYear);
        }
    }
}