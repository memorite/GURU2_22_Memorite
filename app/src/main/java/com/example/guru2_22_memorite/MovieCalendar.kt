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

        // 데이터베이스 관리자 초기화
        dbManager = DBManager(this, "MovieDB", null, 1)

        // 뷰 초기화
        calendarView = findViewById(R.id.CalendarView)
        logTextView = findViewById(R.id.logTextView)
        SelectButton = findViewById(R.id.SelectButton)

        // TextView의 백그라운드 초기화
        updateTextViewBackGround("")

        // 캘린더뷰 리스너 설정
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = getDate(year, month, dayOfMonth)
            val logContent = getLogContent(selectedDate)
            logTextView.text = logContent
        }

        // 플로팅 액션 버튼 리스너 설정
        SelectButton.setOnClickListener {
            val intent = Intent(this, MovieRegister::class.java)
            startActivity(intent)
        }

        // TextView 백그라운드 설정
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

    // 메뉴바 설정
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
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // 년월일을 문자열로 변환
    private fun getDate(year: Int, month: Int, dayOfMonth: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(calendar.time)
    }
    // 선택한 날짜에 해당하는 로그 내용 가져오기
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

        cursor.close()

        return logContent.toString().trim()
    }

    // 뒤로가기 두 번 눌러야 앱 종료
    @SuppressLint("MissingSuperCall")
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

    // TextView의 백그라운드 업데이트
    private fun updateTextViewBackGround(text: String) {
        if (text.isNotEmpty()) {
            logTextView.setBackgroundResource(R.color.theme_green)
        } else{
            logTextView.setBackgroundResource(android.R.color.transparent)
        }
    }
}