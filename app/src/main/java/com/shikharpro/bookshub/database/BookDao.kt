package com.shikharpro.bookshub.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookDao {
    @Insert
    fun insertBook(bookEntity: BookEntity)

    @Delete
    fun deleteBook(bookEntity: BookEntity)

    @Query(value = "SELECT * FROM books")
    fun getallBooks():List<BookEntity>

    @Query(value = "SELECT * FROM books WHERE book_id= :bookId")
    fun getBooksById(bookId :String ):BookEntity



}