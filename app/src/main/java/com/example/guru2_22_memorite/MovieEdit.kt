package com.example.guru2_22_memorite

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import com.example.guru2_22_memorite.DBManager
import com.example.guru2_22_memorite.R

class MovieEdit : AppCompatActivity() {

    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase

    private lateinit var edit_movie_title: EditText
    private lateinit var edit_movie_direc: EditText
    private lateinit var edit_movie_actor: EditText
    private lateinit var edit_ratingBar: RatingBar
    private lateinit var edit_memo: EditText
    private lateinit var edit_save: Button

    lateinit var str_movie_title: String
    lateinit var str_movie_direc: String
    lateinit var str_movie_actor: String
    var rating_value: Double = 0.0
    lateinit var str_memo: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_edit)

        edit_movie_title = findViewById(R.id.edit_movie_title)
        edit_movie_direc = findViewById(R.id.edit_movie_direc)
        edit_movie_actor = findViewById(R.id.edit_movie_actor)
        edit_ratingBar = findViewById(R.id.edit_ratingBar)
        edit_memo = findViewById(R.id.edit_memo)
        edit_save = findViewById(R.id.edit_save)

        // DramaInfo에서 전달받은 도서 정보
        val intent = intent
        str_movie_title = intent.getStringExtra("intent_title").toString()

        dbManager = DBManager(this, "MovieDB", null, 1)
        sqlitedb = dbManager.readableDatabase

        var cursor: Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM Movie WHERE title = ?", arrayOf(str_movie_title))
        if (cursor.moveToNext()) {
            str_movie_direc = cursor.getString((cursor.getColumnIndex("direc"))).toString()
            str_movie_actor = cursor.getString((cursor.getColumnIndex("actor"))).toString()
            rating_value = cursor.getDouble((cursor.getColumnIndex("rating")))
            str_memo = cursor.getString((cursor.getColumnIndex("memo"))).toString()
        }
        cursor.close()
        sqlitedb.close()
        dbManager.close()

        // 영화 정보를 EditText에 설정
        edit_movie_title.setText(str_movie_title)
        edit_movie_direc.setText(str_movie_direc)
        edit_movie_actor.setText(str_movie_actor)
        edit_ratingBar.rating = rating_value.toFloat()
        edit_memo.setText(str_memo)

        // 수정완료 클릭시 수정된 정보를 DB에 저장
        edit_save.setOnClickListener {
            saveMovieInfo()
            Toast.makeText(this, "저장되었습니다.", Toast.LENGTH_SHORT).show()
            finish() // 현재 액티비티 종료
        }
    }

    private fun saveMovieInfo() {
        val newMovieTitle = edit_movie_title.text.toString()
        val newDirec = edit_movie_direc.text.toString()
        val newActor = edit_movie_actor.text.toString()
        val newRating = edit_ratingBar.rating.toDouble()
        val newMemo = edit_memo.text.toString()

        // DB에 수정된 정보 업데이트
        val contentValues = ContentValues().apply {
            put("title", newMovieTitle)
            put("direc", newDirec)
            put("actor", newActor)
            put("rating", newRating)
            put("memo", newMemo)
        }

        sqlitedb = dbManager.writableDatabase
        sqlitedb.update("Movie", contentValues, "title = ?", arrayOf(str_movie_title))
        sqlitedb.close()
        dbManager.close()
    }
}