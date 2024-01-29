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

class DramaEdit : AppCompatActivity() {

    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase

    private lateinit var edit_drama_title: EditText
    private lateinit var edit_drama_direc: EditText
    private lateinit var edit_drama_actor: EditText
    private lateinit var edit_ratingBar: RatingBar
    private lateinit var edit_memo: EditText
    private lateinit var edit_save: Button

    lateinit var str_drama_title: String
    lateinit var str_drama_direc: String
    lateinit var str_drama_actor: String
    var rating_value: Double = 0.0
    lateinit var str_memo: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drama_edit)

        edit_drama_title = findViewById(R.id.edit_drama_title)
        edit_drama_direc = findViewById(R.id.edit_drama_direc)
        edit_drama_actor = findViewById(R.id.edit_drama_actor)
        edit_ratingBar = findViewById(R.id.edit_ratingBar)
        edit_memo = findViewById(R.id.edit_memo)
        edit_save = findViewById(R.id.edit_save)

        // BookInfo에서 전달받은 도서 정보
        val intent = intent
        str_drama_title = intent.getStringExtra("intent_title").toString()

        dbManager = DBManager(this, "DramaDB", null, 1)
        sqlitedb = dbManager.readableDatabase

        var cursor: Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM Drama WHERE title = ?", arrayOf(str_drama_title))
        if (cursor.moveToNext()) {
            str_drama_direc = cursor.getString((cursor.getColumnIndex("direc"))).toString()
            str_drama_actor = cursor.getString((cursor.getColumnIndex("actor"))).toString()
            rating_value = cursor.getDouble((cursor.getColumnIndex("rating")))
            str_memo = cursor.getString((cursor.getColumnIndex("memo"))).toString()
        }
        cursor.close()
        sqlitedb.close()
        dbManager.close()

        // 책 정보를 EditText에 설정
        edit_drama_title.setText(str_drama_title)
        edit_drama_direc.setText(str_drama_direc)
        edit_drama_actor.setText(str_drama_actor)
        edit_ratingBar.rating = rating_value.toFloat()
        edit_memo.setText(str_memo)

        // 수정완료 클릭시 수정된 정보를 DB에 저장
        edit_save.setOnClickListener {
            saveBookInfo()
            Toast.makeText(this, "저장되었습니다.", Toast.LENGTH_SHORT).show()
            finish() // 현재 액티비티 종료
        }
    }

    private fun saveBookInfo() {
        val newDramaTitle = edit_drama_title.text.toString()
        val newDirec = edit_drama_direc.text.toString()
        val newActor = edit_drama_actor.text.toString()
        val newRating = edit_ratingBar.rating.toDouble()
        val newMemo = edit_memo.text.toString()

        // DB에 수정된 정보 업데이트
        val contentValues = ContentValues().apply {
            put("title", newDramaTitle)
            put("direc", newDirec)
            put("actor", newActor)
            put("rating", newRating)
            put("memo", newMemo)
        }

        sqlitedb = dbManager.writableDatabase
        sqlitedb.update("Drama", contentValues, "title = ?", arrayOf(str_drama_title))
        sqlitedb.close()
        dbManager.close()
    }
}