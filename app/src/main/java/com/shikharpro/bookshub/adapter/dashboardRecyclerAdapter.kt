package com.shikharpro.bookshub.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.shikharpro.bookshub.R
import com.shikharpro.bookshub.activity.DescriptionActivity
import com.shikharpro.bookshub.model.Book
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recycler_dashboard_single_row.view.*

class dashboardRecyclerAdapter(val context: Context,val itemList: ArrayList<Book>):RecyclerView.Adapter<dashboardRecyclerAdapter.DashboardViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_dashboard_single_row,parent,false)
        return DashboardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size

    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        val book= itemList[position]
        holder.txtBookName.text= book.bookName
        holder.txtBookAuthor.text= book.bookAuthor
        holder.txtBookRating.text= book.bookRating
        holder.txtBookPrice.text= book.bookPrice
        Picasso.get().load(book.bookImg).error(R.drawable.default_book_cover).into(holder.imgBook)
        holder.llcontent.setOnClickListener {
           val intent = Intent(context,DescriptionActivity::class.java)
            intent.putExtra("book_id",book.bookId)
            context.startActivity(intent)

        }
    }

class DashboardViewHolder(view: View):RecyclerView.ViewHolder(view) {

    val txtBookName: TextView = view.findViewById(R.id.txtBookName)
    val txtBookAuthor : TextView= view.findViewById(R.id.txtBookAuthor)
    val txtBookPrice : TextView= view.findViewById(R.id.txtBookPrice)
    val txtBookRating : TextView= view.findViewById(R.id.txtBookRating)
    val imgBook :ImageView = view.findViewById(R.id.imgBookImage)
    val llcontent :LinearLayout= view.findViewById(R.id.llcontent)
}


}