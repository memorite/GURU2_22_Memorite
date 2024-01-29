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

class BookEdit : AppCompatActivity() {

    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase

    private lateinit var edit_book_title: EditText
    private lateinit var edit_author: EditText
    private lateinit var edit_publisher: EditText
    private lateinit var edit_ratingBar: RatingBar
    private lateinit var edit_memo: EditText
    private lateinit var edit_save: Button

    lateinit var str_book_title: String
    lateinit var str_author: String
    lateinit var str_publisher: String
    var rating_value: Double = 0.0
    lateinit var str_memo: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_edit)

        edit_book_title = findViewById(R.id.edit_book_title)
        edit_author = findViewById(R.id.edit_author)
        edit_publisher = findViewById(R.id.edit_publisher)
        edit_ratingBar = findViewById(R.id.edit_ratingBar)
        edit_memo = findViewById(R.id.edit_memo)
        edit_save = findViewById(R.id.edit_save)

        // BookInfo에서 전달받은 도서 정보
        val intent = intent
        str_book_title = intent.getStringExtra("intent_title").toString()

        dbManager = DBManager(this, "BookDB", null, 1)
        sqlitedb = dbManager.readableDatabase

        var cursor: Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM Book WHERE title = ?", arrayOf(str_book_title))
        if (cursor.moveToNext()) {
            str_author = cursor.getString((cursor.getColumnIndex("author"))).toString()
            str_publisher = cursor.getString((cursor.getColumnIndex("publisher"))).toString()
            rating_value = cursor.getDouble((cursor.getColumnIndex("rating")))
            str_memo = cursor.getString((cursor.getColumnIndex("memo"))).toString()
        }
        cursor.close()
        sqlitedb.close()
        dbManager.close()

        // 책 정보를 EditText에 설정
        edit_book_title.setText(str_book_title)
        edit_author.setText(str_author)
        edit_publisher.setText(str_publisher)
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
        val newBookTitle = edit_book_title.text.toString()
        val newAuthor = edit_author.text.toString()
        val newPublisher = edit_publisher.text.toString()
        val newRating = edit_ratingBar.rating.toDouble()
        val newMemo = edit_memo.text.toString()

        // DB에 수정된 정보 업데이트
        val contentValues = ContentValues().apply {
            put("title", newBookTitle)
            put("author", newAuthor)
            put("publisher", newPublisher)
            put("rating", newRating)
            put("memo", newMemo)
        }

        sqlitedb = dbManager.writableDatabase
        sqlitedb.update("Book", contentValues, "title = ?", arrayOf(str_book_title))
        sqlitedb.close()
        dbManager.close()
    }
}