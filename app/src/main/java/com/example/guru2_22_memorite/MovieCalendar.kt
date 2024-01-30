package com.example.guru2_22_memorite

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.time.Month
import java.util.Calendar
import java.util.Locale

class MovieCalendar : AppCompatActivity() {

    private lateinit var dbManager: DBManager
    lateinit var SelectButton: Button
    lateinit var calendarView: CalendarView
    lateinit var logTextView: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_calendar)

        dbManager = DBManager(this, "MovieDB", null, 1)
        calendarView = findViewById(R.id.CalendarView)
        logTextView = findViewById(R.id.logTextView)

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = getDate(year, month, dayOfMonth)
            val logContent = getLogContent(selectedDate)
            logTextView.text = logContent
        }

        val selectButton: Button = findViewById<Button>(R.id.SelectButton)
        selectButton.setOnClickListener {
            val intent = Intent(this, MovieRegister::class.java)
            startActivity(intent)
        }

        logTextView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateTextViewBackGround(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    // 목록으로 이동(영화)
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.calendar_movie_menu, menu)
        return true
    }

    private fun getDate(year: Int, month: Int, dayOfMonth: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(calendar.time)
    }
    private fun getLogContent(date: String): String {
        val cursor = dbManager.readableDatabase.query(
            "Movie",
            arrayOf("title"),
            "date = ?",
            arrayOf(date),
            null, null, null
        )

        var logContent = StringBuilder()

        while (cursor.moveToNext()) {
            val titleIndex = cursor.getColumnIndex("title")
            val titleValue = cursor.getString(titleIndex)
            logContent.append("$titleValue\n")
        }
//        }
//
//        if (cursor.moveToFirst()) {
//            val memoIndex = cursor.getColumnIndex("title")
//            val memoValue = cursor.getString(memoIndex)
//            logContent = memoValue ?: ""
//        }

        cursor.close()

        return logContent.toString().trim()
    }

    private fun updateTextViewBackGround(text: String) {
        if (text.isNotEmpty()) {
            logTextView.setBackgroundResource(R.color.thema_green)
        } else{
            logTextView.setBackgroundResource(android.R.color.transparent)
        }
    }
}