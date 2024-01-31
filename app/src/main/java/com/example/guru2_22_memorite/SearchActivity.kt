package com.example.guru2_22_memorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import com.example.guru2_22_memorite.MovieAdapter

class SearchActivity : AppCompatActivity() {

    lateinit var dbManager: DBManager
    lateinit var movieListView: ListView
    lateinit var searchView: SearchView
    lateinit var adapter: ArrayAdapter<Movie>

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        dbManager = DBManager(this, "MovieDB", null, 1)
        movieListView = findViewById(R.id.movie_listview)
        searchView = findViewById(R.id.search_movie)

        // 리스트뷰와 연결할 어댑터 초기화
        val movies = mutableListOf<Movie>()
        adapter = MovieAdapter(this, R.layout.custom_list_item, movies)
        movieListView.adapter = adapter

        // SearchView에 리스너 등록
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // 사용자가 검색 버튼을 눌렀을 때 동작할 코드
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // 사용자가 검색어를 입력할 때마다 동작할 코드
                if (newText != null) {
                    // DB에서 검색어에 맞는 영화들을 가져오기
                    val searchedMovies = dbManager.searchMovies(newText)

                    // 어댑터 초기화
                    movies.clear()

                    // 검색된 영화들을 어댑터에 추가
                    movies.addAll(searchedMovies)

                    // 어댑터 갱신
                    adapter.notifyDataSetChanged()
                }
                return true
            }
        })

        // ListView에 아이템 클릭 리스너 등록
        movieListView.setOnItemClickListener { _, _, position, _ ->
            // 클릭한 아이템의 정보 가져오기
            val selectedMovie = movies[position]

            // MovieInfo 액티비티로 이동하는 Intent 생성
            val intent = Intent(this@SearchActivity, MovieInfo::class.java)

            // Intent에 선택한 영화의 정보 전달
            intent.putExtra("intent_date", selectedMovie.str_date)
            intent.putExtra("intent_title", selectedMovie.str_title)
            intent.putExtra("intent_direc", selectedMovie.str_direc)
            intent.putExtra("intent_actor", selectedMovie.str_actor)
            intent.putExtra("intent_rating", selectedMovie.rating)
            intent.putExtra("intent_memo", selectedMovie.memo)

            // MovieInfo 액티비티 시작
            startActivity(intent)
        }
    }
}