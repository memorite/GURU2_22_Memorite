package com.example.guru2_22_memorite

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import com.example.guru2_22_memorite.DBManager
import com.example.guru2_22_memorite.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MovieEdit : AppCompatActivity() {

    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase

    lateinit var edit_date: TextView
    lateinit var edit_movie_title: EditText
    lateinit var edit_movie_direc: EditText
    lateinit var edit_movie_actor: EditText
    lateinit var edit_ratingBar: RatingBar
    lateinit var edit_memo: EditText
    lateinit var edit_save: Button

    lateinit var str_date: String
    lateinit var str_movie_title: String
    lateinit var str_movie_direc: String
    lateinit var str_movie_actor: String
    var rating_value: Double = 0.0
    lateinit var str_memo: String

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_edit)

        edit_date = findViewById(R.id.edit_date)
        edit_movie_title = findViewById(R.id.edit_movie_title)
        edit_movie_direc = findViewById(R.id.edit_movie_direc)
        edit_movie_actor = findViewById(R.id.edit_movie_actor)
        edit_ratingBar = findViewById(R.id.edit_ratingBar)
        edit_memo = findViewById(R.id.edit_memo)
        edit_save = findViewById(R.id.edit_save)

        // MovieInfo에서 전달받은 영화정보
        val intent = intent
        str_movie_title = intent.getStringExtra("intent_title").toString()

        dbManager = DBManager(this, "MovieDB", null, 1)
        sqlitedb = dbManager.readableDatabase

        var cursor: Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM Movie WHERE title = ?", arrayOf(str_movie_title))
        if (cursor.moveToNext()) {
            str_date = cursor.getString((cursor.getColumnIndex("date"))).toString()
            str_movie_direc = cursor.getString((cursor.getColumnIndex("direc"))).toString()
            str_movie_actor = cursor.getString((cursor.getColumnIndex("actor"))).toString()
            rating_value = cursor.getDouble((cursor.getColumnIndex("rating")))
            str_memo = cursor.getString((cursor.getColumnIndex("memo"))).toString()
        }
        cursor.close()
        sqlitedb.close()
        dbManager.close()

        // str_date 초기화 추가 - 0130 5pm 추가된 부분
        str_date = if (::str_date.isInitialized) str_date else ""
        str_movie_direc = if (::str_movie_direc.isInitialized) str_movie_direc else ""
        str_movie_actor = if (::str_movie_actor.isInitialized) str_movie_actor else ""
        str_memo = if (::str_memo.isInitialized) str_memo else ""

        // 영화 정보를 EditText에 설정
        edit_date.setText(str_date)
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
        // 날짜 입력 부분을 수정하여 캘린더에서 선택한 날짜를 사용하도록 함
        edit_date.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(
                this,
                { _, year, monthOfYear, dayOfMonth ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(year, monthOfYear, dayOfMonth)

                    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    edit_date.text = dateFormat.format(selectedDate.time) // 선택한 날짜를 TextView에 표시
                },
                year,
                month,
                dayOfMonth
            )
            datePicker.show()
        }
    }

    private fun saveMovieInfo() {
        val newDate = edit_date.text.toString()
        val newMovieTitle = edit_movie_title.text.toString()
        val newDirec = edit_movie_direc.text.toString()
        val newActor = edit_movie_actor.text.toString()
        val newRating = edit_ratingBar.rating.toDouble()
        val newMemo = edit_memo.text.toString()

        // DB에 수정된 정보 업데이트
        val contentValues = ContentValues().apply {
            put("date", newDate)
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