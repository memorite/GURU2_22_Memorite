package com.example.guru2_22_memorite

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button

class MovieCalendar : AppCompatActivity() {

    lateinit var SelectButton: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_calendar)

        // 선택 버튼
        SelectButton = findViewById<Button>(R.id.SelectButton)

        SelectButton.setOnClickListener {
            val intent = Intent(this, MovieRegister::class.java)
            startActivity(intent)
        }
    }

    // 목록으로 이동(책, 드라마)
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.calendar_movie_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId) {
            R.id.action_book -> {
                val intent = Intent (this, BookActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.action_drama -> {
                val intent = Intent (this, DramaActivity::class.java)
                startActivity(intent)
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }
}