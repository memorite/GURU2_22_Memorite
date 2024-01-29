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

class DramaInfo : AppCompatActivity() {

    lateinit var dbManager:DBManager
    lateinit var sqlitedb: SQLiteDatabase

    private lateinit var tvDramaTitle: TextView
    private lateinit var tvDramaDirec: TextView
    private lateinit var tvDramaActor: TextView
    private lateinit var ratingBarInfo: RatingBar
    private lateinit var tvMemo: TextView
    //수정 삭제 버튼
    lateinit var btnEdit: Button
    lateinit var btnDelete: Button

    lateinit var str_drama_title: String
    lateinit var str_drama_direc: String
    lateinit var str_drama_actor: String
    var rating_value: Double = 0.0
    lateinit var str_memo: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drama_info)

        tvDramaTitle = findViewById(R.id.reg_drama_title)
        tvDramaDirec = findViewById(R.id.reg_drama_direc)
        tvDramaActor = findViewById(R.id.reg_drama_actor)
        ratingBarInfo = findViewById(R.id.reg_ratingBar)
        tvMemo = findViewById(R.id.reg_memo)
        btnEdit = findViewById(R.id.btnEdit)
        btnDelete = findViewById(R.id.btnDelete)

        //Intent를 통해 도서 정보를 전달 받음
        val intent = intent
        str_drama_title = intent.getStringExtra("intent_title").toString()

        dbManager = DBManager(this, "DramaDB", null, 1)
        sqlitedb = dbManager.readableDatabase

        var cursor: Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM Drama WHERE title = '" + str_drama_title + "';", null)
        if (cursor.moveToNext()) {
            str_drama_direc = cursor.getString((cursor.getColumnIndex("direc"))).toString()
            str_drama_actor = cursor.getString((cursor.getColumnIndex("actor"))).toString()
            rating_value = cursor.getDouble((cursor.getColumnIndex("rating")))
            str_memo = cursor.getString((cursor.getColumnIndex("memo"))).toString()
        }
        cursor.close()
        sqlitedb.close()
        dbManager.close()

        // 각 TextView 및 RatingBar에 도서 정보 설정
        tvDramaTitle.text = str_drama_title
        tvDramaDirec.text = str_drama_direc
        tvDramaActor.text = str_drama_actor
        ratingBarInfo.rating = rating_value.toFloat()
        tvMemo.text = str_memo + "\n"

        // 수정 버튼 클릭시 DramaEdit로 이동
        btnEdit.setOnClickListener {
            val intent = Intent(this, DramaEdit::class.java)
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
            if (dbManager.deleteDrama(str_drama_title)) {
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