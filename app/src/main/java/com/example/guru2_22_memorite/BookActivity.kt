package com.example.guru2_22_memorite

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.RatingBar
import android.widget.Toast
import com.example.guru2_22_memorite.BookInfo
import com.example.guru2_22_memorite.DBManager
import com.example.guru2_22_memorite.R

class BookActivity : AppCompatActivity() {

    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase

    lateinit var btnSave: Button
    lateinit var reg_book_title: EditText
    lateinit var reg_author: EditText
    lateinit var reg_publisher: EditText
    lateinit var reg_ratingBar: RatingBar
    lateinit var reg_memo: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)

        btnSave = findViewById(R.id.btnSave)
        reg_book_title = findViewById(R.id.reg_book_title)
        reg_author = findViewById(R.id.reg_author)
        reg_publisher = findViewById(R.id.reg_publisher)
        reg_ratingBar = findViewById(R.id.reg_ratingBar)
        reg_memo = findViewById(R.id.reg_memo)
        //별점선택
        reg_ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
        }
        dbManager = DBManager(this,"BookDB", null, 1)
        //저장버튼 클릭시 데이터 저장
        btnSave.setOnClickListener {
            val str_book_title = reg_book_title.text.toString()
            val str_author = reg_author.text.toString()
            val str_publisher = reg_publisher.text.toString()
            val rating_value = reg_ratingBar.rating
            val str_memo = reg_memo.text.toString()

            val contentValues = ContentValues().apply {
                put("title", str_book_title)
                put("author", str_author)
                put("publisher", str_publisher)
                put("rating", rating_value.toDouble())
                put("memo",str_memo)
            }

            sqlitedb = dbManager.writableDatabase
            sqlitedb.insert("Book", null, contentValues)
            sqlitedb.close()

            val intent = Intent(this, BookInfo::class.java)
            intent.putExtra("intent_title", str_book_title)
            startActivity(intent)
        }

    }
}