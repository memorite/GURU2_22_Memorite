package com.example.guru2_22_memorite

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import library_main

class ThreeButtons : AppCompatActivity() {

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.library_main_menu, menu)
        return true
    }
    // 인아님 캘린더 페이지 이름이랑 연결해서 menu item 클릭하면 화면 전환되도록.
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.action_library -> {
                val intent = Intent(this, library_main::class.java)
                startActivity(intent)
                return true
            }
//            R.id.action_calendar -> {
//                val intent = Intent(this, calendar_main::class.java) //인아님 페이지(캘린더 페이지 이름으로 변경)
//                startActivity(intent)
//                return true
//            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_three_buttons)

        // 책 버튼 클릭 시 이벤트 처리
        val bookButton: Button = findViewById(R.id.bookButton)
        bookButton.setOnClickListener {
            openBookActivity()
        }

        // 영화 버튼 클릭 시 이벤트 처리
        val movieButton: Button = findViewById(R.id.movieButton)
        movieButton.setOnClickListener {
            openMovieActivity()
        }

        // 드라마 버튼 클릭 시 이벤트 처리
        val dramaButton: Button = findViewById(R.id.dramaButton)
        dramaButton.setOnClickListener {
            openDramaActivity()
        }
    }

    //시영님이 만드신 감상평 3가지 페이지로 각각 연결하는 코드.
    private fun openBookActivity() {
        val intent = Intent(this, BookCalendar::class.java)
        startActivity(intent)
    }

    private fun openMovieActivity() {
        val intent = Intent(this, MovieActivity::class.java)
        startActivity(intent)
    }

    private fun openDramaActivity() {
        val intent = Intent(this, DramaActivity::class.java)
        startActivity(intent)
    }
}