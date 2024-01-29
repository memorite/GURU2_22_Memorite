package com.example.guru2_22_memorite

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast

class MovieCalendar : AppCompatActivity() {

    lateinit var SelectButton: Button
    private var doubleBackToExit = false        // 뒤로가기로 인한 자동 앱 종료 방지용

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

    // 뒤로가기를 더블클릭 했을 때만 앱이 종료되도록 설정
    override fun onBackPressed() {
        if(doubleBackToExit){
            finishAffinity()
        } else{
            Toast.makeText(this, "종료하시려면 뒤로가기를 한번 더 눌러주세요.", Toast.LENGTH_SHORT).show()
            doubleBackToExit = true
            runDelayed(1500L){
                doubleBackToExit = false
            }
        }
    }

    private fun runDelayed(millis: Long, function: () -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed(function, millis)
    }
}