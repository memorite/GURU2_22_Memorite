package com.example.guru2_22_memorite

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Global.putString
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase

    lateinit var edtID: EditText
    lateinit var edtPassword: EditText
    lateinit var btnLogin: Button
    lateinit var btnToRegi: Button

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        edtID = findViewById(R.id.edtID)
        edtPassword = findViewById(R.id.edtPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnToRegi = findViewById(R.id.btnToRegi)

        dbManager = DBManager(this, "userList", null, 1)

        // 로그인
        btnLogin.setOnClickListener {
            var login_id = edtID.text.toString()
            var login_pw = edtPassword.text.toString()

            // 유저 정보 db에서 로그인 정보가 맞는지 체크
            sqlitedb = dbManager.readableDatabase
            var cursor: Cursor
            cursor = sqlitedb.rawQuery("SELECT password FROM userList where id = '" + login_id + "';", null)
            var data_pw: String=""
            if( cursor != null && cursor.moveToFirst()){
                data_pw = cursor.getString(cursor.getColumnIndex("password")).toString()
                cursor.close()
            }

            if(data_pw.equals(login_pw)){
                // 일치할 경우 로그인
                val intent = Intent(this, MovieCalendar::class.java)
                intent.putExtra("id", login_id)

                // 메인 액티비티로
                startActivity(intent)
                // 이전 키로 로딩화면으로 돌아오는 것 방지
                finish()
            } else {
                // 아닐 경우 다시 입력을 유도
                Toast.makeText(this@LoginActivity, "id 또는 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            }

            sqlitedb.close()
        }

        // 회원가입 화면으로
        btnToRegi.setOnClickListener {
            val intent = Intent(this, MemberRegisterActivity::class.java)
            startActivity(intent)
        }
    }
}