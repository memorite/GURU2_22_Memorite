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

// 캘린더 기능 추가
//class MovieCalendar : AppCompatActivity() {
//    private lateinit var dbManager: MovieActivity
//
//    @SuppressLint("MissingInflatedId")
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_movie_calendar)
//
//        dbManager = MovieActivity()
//
//        val calendarView: CalendarView = findViewById(R.id.CalendarView)
//
//        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
//            val date = getDate(year, month, dayOfMonth)
//            val content = getLogContent(date)
//            val intent = Intent(this, MovieActivity::class.java)
//            intent.putExtra("date", date)
//            intent.putExtra("content", content)
//            startActivity(intent)
//        }
//    }
//
//    private fun getDate(year: Int, month: Int, dayOfMonth: Int): String {
//        val calendar = Calendar.getInstance()
//        calendar.set(year, month, dayOfMonth)
//        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
//        return sdf.format(calendar.time)
//    }
//
//    private fun getLogContent(date: String): String {
//        return "기록"
//    }
//}

// 캘린더 기존
class MovieCalendar : AppCompatActivity() {

    lateinit var SelectButton: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_calendar)

        // 선택 버튼
        SelectButton = findViewById<Button>(R.id.SelectButton)

        SelectButton.setOnClickListener {
            val intent = Intent(this, MovieActivity::class.java)
            startActivity(intent)
        }
    }
}
//
//    // 목록으로 이동(전체, 영화, 책) -> 분야에 맞게 기록된 항목들을 보여줌
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu_home_list, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when(item?.itemId) {
//            R.id.action_all_list -> {
//                val intent = Intent (this, AllListActivity::class.java)
//                startActivity(intent)
//                return true
//            }
//            R.id.action_movie_list -> {
//                val intent = Intent (this, MovieListActivity::class.java)
//                startActivity(intent)
//                return true
//            }
//
//            R.id.action_book_list -> {
//                val intent = Intent (this, BookListActivity::class.java)
//                startActivity(intent)
//                return true
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }
//}