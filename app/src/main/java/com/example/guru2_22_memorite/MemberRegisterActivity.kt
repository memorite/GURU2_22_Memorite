package com.example.guru2_22_memorite

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.database.getIntOrNull
import androidx.core.widget.addTextChangedListener

class MemberRegisterActivity : AppCompatActivity() {
    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase

    lateinit var edtID: EditText
    lateinit var edtPassword: EditText
    lateinit var edtNickname: EditText
    lateinit var edtEmail: EditText
    lateinit var btnRegi: Button
    lateinit var btnToLogin: Button
    lateinit var btnIDCheck: Button
    lateinit var textIDCheck: TextView
    var idChecked = false

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member_register)

        edtID = findViewById(R.id.edtID)
        edtPassword = findViewById(R.id.edtPassword)
        edtNickname = findViewById(R.id.edtNickname)
        edtEmail = findViewById(R.id.edtEmail)
        btnRegi = findViewById(R.id.btnRegi)
        btnToLogin = findViewById(R.id.btnToLogin)
        btnRegi = findViewById(R.id.btnRegi)
        btnIDCheck = findViewById(R.id.btnIDCheck)
        textIDCheck = findViewById(R.id.textIDCheck)

        dbManager = DBManager(this, "userList", null, 1)


        // id 중복체크
        btnIDCheck.setOnClickListener {
            var id = edtID.text.toString()

            if (id == ""){
                textIDCheck.setText("id를 입력해주세요.")
                return@setOnClickListener
            }

            sqlitedb = dbManager.readableDatabase

            // db에 동일한 id가 이미 등록되어있는지 확인
            var cursor: Cursor
            cursor = sqlitedb.rawQuery("SELECT COUNT(*) FROM userList where id = '" + id + "';", null)

            cursor.moveToFirst()
            if(cursor == null || cursor.getInt(0) == 0){
                // 검색 결과가 없다면(등록되지 않았다면) id체크를 수행했음을 알리고 사용 가능한 id임을 표시
                idChecked = true
                textIDCheck.setText("사용 가능한 ID입니다.")
            } else {
                // 검색 결과가 있다면(등록되어 있다면) id 체크를 다시 해야 함을 알리고 중복되었음을 표시
                idChecked = false
                textIDCheck.setText("ID가 중복되었습니다.")
            }
            cursor.close()
            sqlitedb.close()
        }

        // id 수정시 중복체크 초기화
        edtID.addTextChangedListener {
            idChecked = false
            textIDCheck.setText("")
        }


        // 회원가입
        btnRegi.setOnClickListener {
            // id 중복체크 여부 확인
            if (idChecked){
                // 체크된 경우 마저 진행. id를 키값으로 삼을 예정이므로 중복체크가 필수.

                var str_id : String = edtID.text.toString()
                var str_pw : String = edtPassword.text.toString()
                var str_name : String = edtNickname.text.toString()
                var str_email : String = edtEmail.text.toString()

                sqlitedb = dbManager.writableDatabase
                sqlitedb.execSQL("INSERT INTO userList VALUES('"+ str_id + "', '" + str_pw + "', '" + str_name + "', '" + str_email + "');")
                sqlitedb.close()

                // 완료 메세지 표시
                Toast.makeText(this@MemberRegisterActivity, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                // 로그인 화면으로
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)

            } else {
                // 체크되지 않았을 경우
                Toast.makeText(this@MemberRegisterActivity, "id 중복확인을 완료해주세요.", Toast.LENGTH_SHORT).show()
            }
        }


        // 로그인 화면으로
        btnToLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}