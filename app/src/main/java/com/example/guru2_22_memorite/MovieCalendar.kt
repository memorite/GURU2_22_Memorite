package com.example.guru2_22_memorite

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.CalendarView
import java.text.SimpleDateFormat
import java.time.Month
import java.util.Calendar
import java.util.Locale

class MovieCalendar : AppCompatActivity() {
    private lateinit var dbManager: MovieActivity

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_calendar)

        dbManager = MovieActivity()

        val calendarView: CalendarView = findViewById(R.id.CalendarView)

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val date = getDate(year, month, dayOfMonth)
            val content = getLogContent(date)
            val intent = Intent(this, MovieActivity::class.java)
            intent.putExtra("date", date)
            intent.putExtra("content", content)
            startActivity(intent)
        }
    }

    private fun getDate(year: Int, month: Int, dayOfMonth: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(calendar.time)
    }

    private fun getLogContent(date: String): String {
        return "기록"
    }
}