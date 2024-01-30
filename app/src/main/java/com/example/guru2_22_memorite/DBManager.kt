package com.example.guru2_22_memorite
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.io.Serializable

data class Movie(
    var str_date: String ="",
    var str_title: String = "",
    var str_direc: String = "",
    var str_actor: String = "",
    var rating: Double = 0.0,
    var memo: String = ""
) : Serializable

class DBManager(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("CREATE TABLE userList (id text primary key, password text, nickname text, email text)")
        db!!.execSQL("CREATE TABLE Movie (date TEXT,title Text, direc Text, actor Text, rating Text, memo Text)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun addMovie(movie: Movie): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("date", movie.str_date)
            put("title", movie.str_title)
            put("direc", movie.str_direc)
            put("actor", movie.str_actor)
            put("rating", movie.rating)
            put("memo", movie.memo)
        }
        val result = db.insert("Movie", null, values)
        db.close()
        return result != 1L
    }
    // 검색하기
    fun searchMovies(query: String): List<Movie> {
        val movies = mutableListOf<Movie>()
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM Movie WHERE title LIKE ? OR direc LIKE ? OR actor LIKE ? OR memo LIKE ?",
            arrayOf("%$query%", "%$query%", "%$query%", "%$query%")
        )

        while (cursor.moveToNext()) {
            val date = cursor.getString(cursor.getColumnIndex("date"))
            val title = cursor.getString(cursor.getColumnIndex("title"))
            val direc = cursor.getString(cursor.getColumnIndex("direc"))
            val actor = cursor.getString(cursor.getColumnIndex("actor"))
            val rating = cursor.getDouble(cursor.getColumnIndex("rating"))
            val memo = cursor.getString(cursor.getColumnIndex("memo"))
            val movie = Movie(date, title, direc, actor, rating, memo)
            movies.add(movie)
        }

        cursor.close()
        db.close()

        return movies
    }

    // 영화 수정
    fun updateMovie(
        date: String,
        oldTitle: String,
        newTitle: String,
        direc: String,
        actor: String,
        rating: Double,
        memo: String
    ): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("date", date)
            put("title", newTitle)
            put("direc", direc)
            put("actor", actor)
            put("rating", rating)
            put("memo", memo)
        }

        val result = db.update("Movie", values, "title = ?", arrayOf(oldTitle))
        db.close()
        return result > 0
    }

    //영화 삭제
    fun deleteMovie(title: String): Boolean {
        val db = writableDatabase
        val result = db.delete("Movie", "title = ?", arrayOf(title))
        db.close()
        return result > 0
    }


    // 특정 날짜의 영화 가져오기
    @SuppressLint("Range")
    fun getMoviesByDate(date: String): List<Movie> {
        val movies = mutableListOf<Movie>()
        val db = readableDatabase
        val cursor = db.query(
            "Movie",
            arrayOf("title", "direc", "actor", "rating", "memo"),
            "date = ?",
            arrayOf(date),
            null, null, null
        )

        while (cursor.moveToNext()) {
            val title = cursor.getString(cursor.getColumnIndex("title"))
            val direc = cursor.getString(cursor.getColumnIndex("direc"))
            val actor = cursor.getString(cursor.getColumnIndex("actor"))
            val rating = cursor.getDouble(cursor.getColumnIndex("rating"))
            val memo = cursor.getString(cursor.getColumnIndex("memo"))

            val movie = Movie(date, title, direc, actor, rating, memo)
            movies.add(movie)
        }

        cursor.close()
        db.close()

        return movies
    }
}