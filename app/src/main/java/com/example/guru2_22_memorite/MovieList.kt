package com.example.guru2_22_memorite

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView

class MovieList : AppCompatActivity() {

    val theme_darkgreen = Color.parseColor("#72ABA1")

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.list_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId) {
            R.id.action_home -> {
                val intent = Intent(this, MovieCalendar::class.java)
                startActivity(intent)
                return true
            }
            R.id.action_search -> {
                val intent = Intent(this, SearchActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase
    lateinit var layout: LinearLayout
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)

        dbManager = DBManager(this,"MovieDB",null,1)
        sqlitedb = dbManager.readableDatabase

        layout = findViewById(R.id.movieList)

        var cursor : Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM Movie", null)

        var num: Int = 0
        while(cursor.moveToNext()){

            var str_movie_date = cursor.getString((cursor.getColumnIndex("date"))).toString()
            var str_movie_title = cursor.getString((cursor.getColumnIndex("title"))).toString()
            var str_movie_direc = cursor.getString((cursor.getColumnIndex("direc"))).toString()
            var str_movie_actor = cursor.getString((cursor.getColumnIndex("actor"))).toString()
            var rating_value = cursor.getDouble((cursor.getColumnIndex("rating")))
            var layout_item: LinearLayout = LinearLayout(this)
            layout_item.orientation = LinearLayout.VERTICAL
            layout_item.id = View.generateViewId()

            //목록 테스트 변경하는 부분
            var tvMovieTitle: TextView = TextView(this)
            tvMovieTitle.text = str_movie_title
            tvMovieTitle.textSize = 30f
            tvMovieTitle.setTextColor(Color.WHITE)
            tvMovieTitle.setBackgroundColor(theme_darkgreen) // 글자 배경색 변경
            tvMovieTitle.gravity = Gravity.CENTER
            layout_item.addView(tvMovieTitle)

            var tvMovieDate : TextView = TextView(this)
            tvMovieDate.text = str_movie_date
            tvMovieDate.gravity = Gravity.CENTER
            layout_item.addView(tvMovieDate)

            var tvMovieDirec: TextView = TextView(this)
            tvMovieDirec.text = str_movie_direc
            tvMovieDirec.gravity = Gravity.CENTER
            layout_item.addView(tvMovieDirec)

            var tvMovieActor: TextView = TextView(this)
            tvMovieActor.text = str_movie_actor
            tvMovieActor.gravity= Gravity.CENTER
            layout_item.addView(tvMovieActor)

            var tvRatingBar: RatingBar = RatingBar(this)
            tvRatingBar.numStars = 5
            tvRatingBar.stepSize = 1f
            tvRatingBar.rating = rating_value.toFloat()

            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            layoutParams.gravity = Gravity.CENTER
            tvRatingBar.layoutParams = layoutParams

            tvRatingBar.isEnabled = false
            layout_item.addView(tvRatingBar)

            layout_item.setOnClickListener{
                val intent = Intent(this, MovieInfo::class.java)
                intent.putExtra("intent_title", str_movie_title)
                startActivity(intent)
            }
            layout.addView(layout_item)
            num++;
        }
        cursor.close()
        sqlitedb.close()
        dbManager.close()
    }

}