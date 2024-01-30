package com.example.guru2_22_memorite

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.time.Month
import java.util.Calendar
import java.util.Locale

class MovieCalendar : AppCompatActivity() {

    private lateinit var dbManager: DBManager
    lateinit var SelectButton: FloatingActionButton
    lateinit var calendarView: CalendarView
    lateinit var logTextView: TextView

    private var doubleBackToExit = false        // 뒤로가기 두번 눌러야 앱 종료

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_calendar)

        dbManager = DBManager(this, "MovieDB", null, 1)
        calendarView = findViewById(R.id.CalendarView)
        logTextView = findViewById(R.id.logTextView)
        SelectButton = findViewById(R.id.SelectButton)

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = getDate(year, month, dayOfMonth)
            val logContent = getLogContent(selectedDate)
            logTextView.text = logContent
        }

        SelectButton.setOnClickListener {
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId) {
            R.id.action_search -> {
                val intent = Intent(this, SearchActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.action_list -> {
                val intent = Intent(this, MovieList::class.java)
            }
        }
        return super.onOptionsItemSelected(item)
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

    // 뒤로가기 두 번 눌러야 앱 종료
    override fun onBackPressed() {
        if(doubleBackToExit){
            finishAffinity()
        } else {
            Toast.makeText(this, "앱을 종료하려면 뒤로가기를 한번 더 눌러주세요.", Toast.LENGTH_SHORT).show()
            doubleBackToExit = true
            runDelayed(1500L){
                doubleBackToExit = false
            }
        }
    }
    fun runDelayed(millis: Long, function: () -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed(function, millis)
    }

    private fun updateTextViewBackGround(text: String) {
        if (text.isNotEmpty()) {
            logTextView.setBackgroundResource(R.color.thema_green)
        } else{
            logTextView.setBackgroundResource(android.R.color.transparent)
        }
    }
}