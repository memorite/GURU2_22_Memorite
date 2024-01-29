package com.example.guru2_22_memorite

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button

class BookCalendar : AppCompatActivity() {

    lateinit var SelectButton: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_calendar)

        // 선택 버튼
        SelectButton = findViewById<Button>(R.id.SelectButton)

        SelectButton.setOnClickListener {
            val intent = Intent(this,// 책 기록 페이지 ::class.java)
                startActivity(intent)
        }
    }

    // 목록으로 이동(책, 드라마)
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.calendar_book_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId) {
            R.id.action_calendar -> {
                val intent = Intent (this, // 영화 기록 페이지::class.java)
                    startActivity(intent)
                    return true
            }
            R.id.action_drama -> {
                val intent = Intent (this, // 드라마 기록 페이지::class.java)
                    startActivity(intent)
                    return true
            }

        }
        return super.onOptionsItemSelected(item)
    }
}