package com.shikharpro.bookshub.fragment

import android.app.Activity
import android.app.AlertDialog
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.shikharpro.bookshub.R
import com.shikharpro.bookshub.adapter.dashboardRecyclerAdapter
import com.shikharpro.bookshub.model.Book
import com.shikharpro.bookshub.util.ConnectionManager
import org.json.JSONException

class DashboardFragment : Fragment() {

    lateinit var recyclerDashboard: RecyclerView
    lateinit var  layoutManager: RecyclerView.LayoutManager
    lateinit var  recyclerAdapter: dashboardRecyclerAdapter
    lateinit var progressLayout:RelativeLayout
    lateinit var progressBar:ProgressBar

     var  bookInfo = arrayListOf<Book>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        recyclerDashboard = view.findViewById (R.id.recyclerDashboard)
        progressLayout=view.findViewById(R.id.progressiveLayout)
        progressBar=view.findViewById(R.id.progressBar)
        progressLayout.visibility=View.VISIBLE


        layoutManager = LinearLayoutManager(activity)

        val queue = Volley.newRequestQueue(activity as Context)
        val url = "http://13.235.250.119/v1/book/fetch_books/"

        if(ConnectionManager().checkConnectivity(activity as Context)){
            val jsonObjectRequest= object :JsonObjectRequest(Request.Method.GET,url,null,Response.Listener {
                try {progressLayout.visibility=View.GONE
                    val success = it.getBoolean("success")
                    if(success){
                        val data= it.getJSONArray("data")
                        for( i in 0 until data.length()){
                            val bookJsonObject=data.getJSONObject(i)
                            val bookObject = Book(
                                bookJsonObject.getString("book_id"),
                                bookJsonObject.getString("name"),
                                bookJsonObject.getString("author"),
                                bookJsonObject.getString("rating"),
                                bookJsonObject.getString("price"),
                                bookJsonObject.getString("image")
                            )
                            bookInfo.add(bookObject)
                            recyclerAdapter = dashboardRecyclerAdapter(activity as Context,bookInfo )
                            recyclerDashboard.adapter= recyclerAdapter
                            recyclerDashboard.layoutManager=layoutManager


                        }
                    }else{

                        Toast.makeText(activity as Context,"Some error occurred",Toast.LENGTH_SHORT).show()
                    }
                }catch (e:JSONException){
                 Toast.makeText(activity as Context,"Some Error occurred",Toast.LENGTH_SHORT).show()


                }
            },Response.ErrorListener {
                if(activity!=null) {
                    Toast.makeText(
                        activity as Context,
                        "Some Volley Error Occurred",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }){
                override fun getHeaders(): MutableMap<String, String> {
                    val headers= HashMap<String,String>()
                    headers["Content-Type"]= "application/json"
                    headers["token"]= "2e14077ddcbc9e"
                    return  headers
                }

            }
            queue.add(jsonObjectRequest)




        }else{val dialog = AlertDialog.Builder(activity as Context)
            dialog.setTitle("Error")
            dialog.setMessage("Internet Connectivity not Found")
            dialog.setPositiveButton("Open Settings") { text, listener ->
                val settingsIntent =Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingsIntent)
                activity?.finish()
            }
            dialog.setNegativeButton("Exit") { text, listener ->
            ActivityCompat.finishAffinity(activity as Activity)

            }
            dialog.create()
            dialog.show()





        }


        return view

    }
}