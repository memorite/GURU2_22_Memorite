package com.example.guru2_22_memorite

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.guru2_22_memorite.MainActivity
import com.example.guru2_22_memorite.R

class BookInfo : AppCompatActivity() {

    lateinit var dbManager:DBManager
    lateinit var sqlitedb: SQLiteDatabase

    private lateinit var tvBookTitle: TextView
    private lateinit var tvAuthor: TextView
    private lateinit var tvPublisher: TextView
    private lateinit var ratingBarInfo: RatingBar
    private lateinit var tvMemo: TextView
    //수정 삭제 버튼
    lateinit var btnEdit: Button
    lateinit var btnDelete: Button

    lateinit var str_book_title: String
    lateinit var str_author: String
    lateinit var str_publisher: String
    var rating_value: Double = 0.0
    lateinit var str_memo: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_info)

        tvBookTitle = findViewById(R.id.reg_book_title)
        tvAuthor = findViewById(R.id.reg_author)
        tvPublisher = findViewById(R.id.reg_publisher)
        ratingBarInfo = findViewById(R.id.reg_ratingBar)
        tvMemo = findViewById(R.id.reg_memo)
        btnEdit = findViewById(R.id.btnEdit)
        btnDelete = findViewById(R.id.btnDelete)

        //Intent를 통해 도서 정보를 전달 받음
        val intent = intent
        str_book_title = intent.getStringExtra("intent_title").toString()

        dbManager = DBManager(this, "BookDB", null, 1)
        sqlitedb = dbManager.readableDatabase

        var cursor: Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM Book WHERE title = '" + str_book_title + "';", null)
        if (cursor.moveToNext()) {
            str_author = cursor.getString((cursor.getColumnIndex("author"))).toString()
            str_publisher = cursor.getString((cursor.getColumnIndex("publisher"))).toString()
            rating_value = cursor.getDouble((cursor.getColumnIndex("rating")))
            str_memo = cursor.getString((cursor.getColumnIndex("memo"))).toString()
        }
        cursor.close()
        sqlitedb.close()
        dbManager.close()

        // 각 TextView 및 RatingBar에 도서 정보 설정
        tvBookTitle.text = str_book_title
        tvAuthor.text = str_author
        tvPublisher.text = str_publisher
        ratingBarInfo.rating = rating_value.toFloat()
        tvMemo.text = str_memo + "\n"

        // 수정 버튼 클릭시 BookEdit로 이동
        btnEdit.setOnClickListener {
            val intent = Intent(this, BookEdit::class.java)
            startActivity(intent)
        }
        // 삭제 버튼 클릭시 팝업창
        btnDelete.setOnClickListener {
            DeleteDialog()
        }
    }
    // 삭제 팝업창
    fun DeleteDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("확인")
        alertDialogBuilder.setMessage("기록을 삭제하시겠습니까?")

        alertDialogBuilder.setPositiveButton("Yes") { dialog, which ->
            if (dbManager.deleteBook(str_book_title)) {
                Toast.makeText(this, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
                finish()
            }
            //삭제 후 메인화면?으로 돌아가기
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        alertDialogBuilder.setNegativeButton("No") { _, _ ->
        }

        alertDialogBuilder.create().show()
    }
}