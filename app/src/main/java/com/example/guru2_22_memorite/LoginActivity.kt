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
    lateinit var cBoxLoginSave: CheckBox
    lateinit var btnLogin: Button
    lateinit var btnToRegi: Button

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        edtID = findViewById(R.id.edtID)
        edtPassword = findViewById(R.id.edtPassword)
        cBoxLoginSave = findViewById(R.id.cBoxLoginSave)
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
            cursor.moveToFirst()
            var data_pw = cursor.getString(cursor.getColumnIndex("password")).toString()

            if(data_pw.equals(login_pw)){
                // 일치할 경우 로그인
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("id", login_id)

                // 로그인 저장이 체크되었을 경우 로그인 정보 저장
                if(cBoxLoginSave.isActivated){
                    var pref = getSharedPreferences("pref", Activity.MODE_PRIVATE)
                    var editor = pref.edit()

                    editor.putString("KEY_MEMORITE_ID", login_id).apply()
                    editor.putString("KEY_MEMORITE_PW", login_pw).apply()
                    editor.putBoolean("KEY_MEMORITE_LOGINSAVE", true).apply()
                }

                // 메인 액티비티로
                startActivity(intent)
            } else {
                // 아닐 경우 다시 입력을 유도
                Toast.makeText(this@LoginActivity, "id 또는 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            }

            cursor.close()
            sqlitedb.close()
        }

        // 회원가입 화면으로
        btnToRegi.setOnClickListener {
            val intent = Intent(this, MemberRegisterActivity::class.java)
            startActivity(intent)
        }
    }
}