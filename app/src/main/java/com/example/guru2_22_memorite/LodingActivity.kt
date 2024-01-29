package com.example.guru2_22_memorite

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log

class LodingActivity : AppCompatActivity() {
    var logined:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loding)

        // 로딩화면 실행
        LodingStart()
    }

    fun LodingStart() {
        Handler(Looper.getMainLooper()).postDelayed({
            // 로그인 화면으로 연결
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

            // 이전 키로 로딩화면으로 돌아오는 것 방지
            finish()
        }, 2000)    // 2초 후 메인화면으로

    }
}