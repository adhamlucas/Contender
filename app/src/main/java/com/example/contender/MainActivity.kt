package com.example.contender

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AutoCompleteTextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.Response.Listener
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.android.volley.toolbox.Volley.newRequestQueue
import com.example.contender.Adapters.AutoCompleteSearchAdapter
import com.example.contender.Model.SmartPhoneItem
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    var selectedItemAutoComplete: String? = ""
    var itensAutoComplete: MutableList<SmartPhoneItem> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var searchAutoComplete: AutoCompleteTextView = findViewById(R.id.auto_complete_search)

        loadItensAutoCompleteSearch(searchAutoComplete, itensAutoComplete)
        searchAutoComplete.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedItemAutoComplete = itensAutoComplete[position].id
            }
        }



    }


    fun loadItensAutoCompleteSearch(searchAutoComplete: AutoCompleteTextView, itens: MutableList<SmartPhoneItem>){
        val queue = newRequestQueue(this)
        //val url = "https://reqres.in/api/users"
        val url = "http://144.217.42.212/contender/products"

        val jsonArrayRequest = JsonObjectRequest(Request.Method.GET, url, null, Listener<JSONObject>{ response ->
                val dados = response.getJSONArray("produtos")
                val objeto = dados.getJSONObject(0)
                val searchAutoComplete: AutoCompleteTextView = findViewById(R.id.auto_complete_search)

                for(data in 0..dados.length()-1){
                    var objeto = dados.getJSONObject(data)

                    val id: String = objeto.getString("idCell")
                    val title: String = objeto.getString("title")
                    itens.add(SmartPhoneItem(id, title))
                }

                Log.d("FUNCIONOU", "$dados")

                searchAutoComplete.adapter = (AutoCompleteSearchAdapter(this, itensAutoComplete))

                //var nome = response.getJSONArray("data").getJSONObject(1)

                //Log.d("FUNCIONOU", "${nome.getString("email")}")

            }, Response.ErrorListener {error ->
                Log.d("ACHEI ERROR", "$error")
            })

        queue.add(jsonArrayRequest)
    }

}
