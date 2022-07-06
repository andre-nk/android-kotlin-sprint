package com.example.busschedule.database

import androidx.room.Dao
import androidx.room.Query

@Dao
interface ScheduleDao {
    @Query("SELECT * FROM schedule ORDER BY arrival_time ASC")
    fun getAll(): List<Schedule>

    @Query("SELECT * FROM schedule WHERE stop_name = :stopNameParam ORDER BY arrival_time ASC")
    fun getByStopName(stopNameParam: String) : List<Schedule>
}