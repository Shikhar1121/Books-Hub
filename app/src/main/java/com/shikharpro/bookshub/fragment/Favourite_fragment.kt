package com.shikharpro.bookshub.fragment

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.shikharpro.bookshub.R
import com.shikharpro.bookshub.adapter.favouriteRecyclerAdapter
import com.shikharpro.bookshub.database.BookDatabase
import com.shikharpro.bookshub.database.BookEntity


class Favourite_fragment : Fragment() {

    lateinit var  recyclerFavaourite:RecyclerView
    lateinit var progressBar:ProgressBar
    lateinit var progressLayout:RelativeLayout
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var recycleradapter:favouriteRecyclerAdapter
    var dbBookList = listOf<BookEntity>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favourite_fragment, container, false)
        recyclerFavaourite= view.findViewById(R.id.recyclerFavourite)
        progressLayout = view.findViewById(R.id.progressLayout)
        progressBar = view.findViewById(R.id.progressBar)
        layoutManager= GridLayoutManager(activity as Context,2)
        dbBookList = RetrieveFavourites(activity as Context).execute().get()
        if(activity != null){
           progressLayout.visibility= View.GONE
            recycleradapter = favouriteRecyclerAdapter(activity as Context,dbBookList)
            recyclerFavaourite.adapter= recycleradapter
            recyclerFavaourite.layoutManager=layoutManager

        }
        return view
    }
        class RetrieveFavourites(val context: Context): AsyncTask<Void, Void, List<BookEntity>>() {
            override fun doInBackground(vararg params: Void?): List<BookEntity> {
                val db= Room.databaseBuilder(context,BookDatabase::class.java,"book-db").build()
                return db.booDao().getallBooks()
            }
        }
}