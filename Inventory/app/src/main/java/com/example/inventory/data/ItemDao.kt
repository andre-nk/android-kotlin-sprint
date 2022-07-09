package com.example.inventory.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    //Ignores new item if it's primary key already in the database (conflict)
    //Room will generate the code for insertion
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item)

    @Update
    suspend fun update(item: Item)

    @Delete
    suspend fun delete(item: Item)

    //Write a SQL query as the parameter, : (refers to variable / fun param)
    @Query("SELECT * from item where id = :id")
    fun getItem(id: Int) : Flow<Int>

    @Query("SELECT * from item ORDER BY name ASC")
    fun getItems() : Flow<List<Item>>
}
