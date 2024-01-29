package com.example.guru2_22_memorite

<<<<<<< HEAD
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.io.Serializable

data class Book(
    var str_title: String = "",
    var author: String = "",
    var publisher: String = "",
    var rating: Double = 0.0,
    var memo: String = ""
) : Serializable
=======
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
>>>>>>> origin/master

class DBManager(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
<<<<<<< HEAD
) : SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("CREATE TABLE Book (title Text, author Text, publisher Text, rating Text, memo Text)")
        db!!.execSQL("CREATE TABLE Drama (title Text, direc Text, actor Text, rating Text, memo Text)")
        db!!.execSQL("CREATE TABLE Movie (title Text, direc Text, actor Text, rating Text, memo Text)")
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
    // 책 수정
    fun updateBook(
        oldTitle: String,
        newTitle: String,
        author: String,
        publisher: String,
        rating: Double,
        memo: String
    ): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("title", newTitle)
            put("author", author)
            put("publisher", publisher)
            put("rating", rating)
            put("memo", memo)
        }

        val result = db.update("Book", values, "title = ?", arrayOf(oldTitle))
        db.close()
        return result > 0
    }

    // 드라마 수정
    fun updateDrama(
        oldTitle: String,
        newTitle: String,
        direc: String,
        actor: String,
        rating: Double,
        memo: String
    ): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("title", newTitle)
            put("direc", direc)
            put("actor", actor)
            put("rating", rating)
            put("memo", memo)
        }

        val result = db.update("Drama", values, "title = ?", arrayOf(oldTitle))
        db.close()
        return result > 0
    }
    // 영화 수정
    fun updateMovie(
        oldTitle: String,
        newTitle: String,
        direc: String,
        actor: String,
        rating: Double,
        memo: String
    ): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
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

    //책 삭제
    fun deleteBook(title: String): Boolean {
        val db = writableDatabase
        val result = db.delete("Book", "title = ?", arrayOf(title))
        db.close()
        return result > 0
    }
    fun deleteDrama(title: String): Boolean {
        val db = writableDatabase
        val result = db.delete("Drama", "title = ?", arrayOf(title))
        db.close()
        return result > 0
    }
    fun deleteMovie(title: String): Boolean {
        val db = writableDatabase
        val result = db.delete("Movie", "title = ?", arrayOf(title))
        db.close()
        return result > 0
    }
=======
) : SQLiteOpenHelper(context, name, factory, version){
    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("CREATE TABLE userList (id text primary key, password text, nickname text, email text)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
>>>>>>> origin/master
}