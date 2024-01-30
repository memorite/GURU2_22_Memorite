package com.example.guru2_22_memorite

import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MovieRegister : AppCompatActivity() {

    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase

    lateinit var reg_date: TextView
    lateinit var reg_movie_title: EditText
    lateinit var reg_movie_direc: EditText
    lateinit var reg_movie_actor: EditText
    lateinit var reg_ratingBar: RatingBar
    lateinit var reg_memo: EditText

    lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_register)

        reg_date = findViewById(R.id.reg_date)
        reg_movie_title = findViewById(R.id.reg_movie_title)
        reg_movie_direc = findViewById(R.id.reg_movie_direc)
        reg_movie_actor = findViewById(R.id.reg_movie_actor)
        reg_ratingBar = findViewById(R.id.reg_ratingBar)
        reg_memo = findViewById(R.id.reg_memo)
        btnSave = findViewById(R.id.btnSave)
        //별점선택
        reg_ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
        }

        dbManager = DBManager(this,"MovieDB", null, 1)

        // 날짜 입력 부분을 수정하여 캘린더에서 선택한 날짜를 사용하도록 함

        reg_date.setOnClickListener {
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
                    reg_date.text = dateFormat.format(selectedDate.time) // 선택한 날짜를 TextView에 표시
                },
                year,
                month,
                dayOfMonth
            )
            datePicker.show()
        }

        //저장버튼 클릭시 데이터 저장
        btnSave.setOnClickListener {
            val str_date = reg_date.text.toString()
            val str_movie_title = reg_movie_title.text.toString()
            val str_direc = reg_movie_direc.text.toString()
            val str_actor = reg_movie_actor.text.toString()
            val rating_value = reg_ratingBar.rating
            val str_memo = reg_memo.text.toString()

            val contentValues = ContentValues().apply {
                put("date", str_date)
                put("title", str_movie_title)
                put("direc", str_direc)
                put("actor", str_actor)
                put("rating", rating_value.toDouble())
                put("memo",str_memo)
            }

            sqlitedb = dbManager.writableDatabase
            sqlitedb.insert("Movie", null, contentValues)
            sqlitedb.close()

            val intent = Intent(this, MovieInfo::class.java)
            intent.putExtra("intent_title", str_movie_title)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.register_movie_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_home -> {
                val intent = Intent(this, MovieCalendar::class.java)
                startActivity(intent)
                return true
            }

            R.id.action_list -> {
                val intent = Intent(this, MovieList::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}