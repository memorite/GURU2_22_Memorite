package com.example.guru2_22_memorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView

class SearchActivity : AppCompatActivity() {

    private lateinit var searchEditText: EditText
    private lateinit var resultLayout: LinearLayout
    private lateinit var dbManager: DBManager
    private lateinit var btnSearch: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        searchEditText = findViewById(R.id.search_movie)
        resultLayout = findViewById(R.id.result_layout)
        dbManager = DBManager(this, "MovieDB", null, 1)
        btnSearch = findViewById(R.id.btnSearch)

        btnSearch.setOnClickListener {
            // 검색 버튼 클릭 시 검색 수행
            performSearch(searchEditText.text.toString())
        }

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                // 입력이 변경되면 검색 수행
                performSearch(s.toString())
            }
        })
    }

    private fun performSearch(query: String) {
        // title, direc, actor, memo 테이블에서 두 글자 이상 일치하는 항목 검색
        val searchResults = dbManager.searchMovies(query)

        // 검색 결과를 화면에 표시
        updateSearchResults(searchResults)
    }
    private fun updateSearchResults(results: List<Movie>) {
        // 결과를 화면에 표시
        resultLayout.removeAllViews()
        for (result in results) {
            val resultTextView = TextView(this)
            resultTextView.text = " ${result.str_title}, 별점: ${result.rating}\n"
            resultLayout.addView(resultTextView)
        }
    }
}