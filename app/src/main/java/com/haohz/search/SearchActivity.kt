package com.haohz.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.haohz.search.dto.CompanyDto
import com.haohz.search.dto.SearchDto
import kotlinx.android.synthetic.main.activity_search.*
import java.util.*

class SearchActivity : AppCompatActivity() {

    var list = listOf<SearchDto>()
    var adapter: SearchAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        iv_back.setOnClickListener { finish() }

        list = searchData()
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = SearchAdapter(this, list)
        recyclerView.adapter = adapter

    }

    private fun searchData(): List<SearchDto> {
        val list = mutableListOf<SearchDto>()

        for (i in 1 until 10) {
            val listCompany = mutableListOf<CompanyDto>()
            for (j in 1 until Random().nextInt(5)) {
                listCompany.add(CompanyDto("name_$j", "https://exp-picture.cdn.bcebos.com/d2987775f2c4ec990da93fb5c3fe1e425c6b0742.jpg"))
            }
            val searchDto =
                SearchDto("title_$i", listCompany, "description_$i")
            list.add(searchDto)
        }
        return list
    }

}