package com.haohz.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.haohz.search.dto.SearchDto
import kotlinx.android.synthetic.main.activity_search.*

class SearchResultActivity : AppCompatActivity() {

    var adapter: CompanyAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)
        iv_back.setOnClickListener { finish() }

        val bundle = intent?.extras
        val searchDto =
            bundle?.getSerializable("data") as SearchDto?

        searchDto?.company?.apply {
            recyclerView.layoutManager = LinearLayoutManager(this@SearchResultActivity)
            adapter = CompanyAdapter(this@SearchResultActivity, searchDto.company)
            recyclerView.adapter = adapter
        }


    }

}