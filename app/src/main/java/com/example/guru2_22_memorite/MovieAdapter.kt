package com.example.guru2_22_memorite

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RatingBar
import android.widget.TextView

class MovieAdapter(context: Context, resource: Int, private val movies: List<Movie>) :
    ArrayAdapter<Movie>(context, resource, movies) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val movie = movies[position]

        // 커스텀 리스트 아이템 레이아웃을 사용
        val itemView = LayoutInflater.from(context).inflate(R.layout.custom_list_item, parent, false)

        // 각 항목을 원하는 형태로 표시
        val displayText = "${movie.str_date}\n< ${movie.str_title} >"
        val textView = itemView.findViewById<TextView>(R.id.customItemTextView)
        textView.text = displayText

        // RatingBar를 사용하여 별점 표시
        val ratingBar = itemView.findViewById<RatingBar>(R.id.customItemRatingBar)
        ratingBar.rating = movie.rating.toFloat()

        return itemView
    }
}
