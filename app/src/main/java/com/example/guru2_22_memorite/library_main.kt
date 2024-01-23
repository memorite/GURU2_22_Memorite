package com.example.guru2_22_memorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class library_main : AppCompatActivity() {
    private var nextCellIndex = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_library_main)

        val firstCellButton: Button = findViewById(R.id.bookShelf1)
        firstCellButton.setOnClickListener {
            // 첫 번째 버튼 클릭 시 실행되는 로직
            addNewButton()
        }
    }

    private fun addNewButton() {
        val newButton = Button(this)
        newButton.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        newButton.text = "버튼 $nextCellIndex"
        newButton.setOnClickListener(onNewButtonClickListener)

        // 현재 액티비티를 종료하고 새로운 액티비티를 시작
        startActivity(Intent(this, library_main::class.java))

        nextCellIndex++
    }

    private val onNewButtonClickListener = View.OnClickListener {
        // 동적으로 추가된 버튼 클릭 시 실행되는 로직
        addNewButton()
    }
}