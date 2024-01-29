package com.example.guru2_22_memorite

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar

class MovieActivity : AppCompatActivity() {

    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase

    lateinit var btnSave: Button
    lateinit var reg_movie_title: EditText
    lateinit var reg_movie_direc: EditText
    lateinit var reg_movie_actor: EditText
    lateinit var reg_ratingBar: RatingBar
    lateinit var reg_memo: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drama)

        btnSave = findViewById(R.id.btnSave)
        reg_movie_title = findViewById(R.id.reg_movie_title)
        reg_movie_direc = findViewById(R.id.reg_movie_direc)
        reg_movie_actor = findViewById(R.id.reg_movie_actor)
        reg_ratingBar = findViewById(R.id.reg_ratingBar)
        reg_memo = findViewById(R.id.reg_memo)
        //별점선택
        reg_ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
        }
        dbManager = DBManager(this,"MovieDB", null, 1)
        //저장버튼 클릭시 데이터 저장
        btnSave.setOnClickListener {
            val str_drama_title = reg_movie_title.text.toString()
            val str_direc = reg_movie_direc.text.toString()
            val str_actor = reg_movie_actor.text.toString()
            val rating_value = reg_ratingBar.rating
            val str_memo = reg_memo.text.toString()

            val contentValues = ContentValues().apply {
                put("title", str_drama_title)
                put("director", str_direc)
                put("actor", str_actor)
                put("rating", rating_value.toDouble())
                put("memo",str_memo)
            }

            sqlitedb = dbManager.writableDatabase
            sqlitedb.insert("Movie", null, contentValues)
            sqlitedb.close()

            val intent = Intent(this, MovieInfo::class.java)
            intent.putExtra("intent_title", str_drama_title)
            startActivity(intent)
        }

    }
}