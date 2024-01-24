package com.example.guru2_22_memorite

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.guru2_22_memorite.R

class ThreeButtons : AppCompatActivity() {
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
        val intent = Intent(this, BookActivity::class.java)
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