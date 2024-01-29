package com.example.guru2_22_memorite
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

    // 영화 수정
    fun updateMovie(
        date: String
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
}