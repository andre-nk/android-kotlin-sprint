package com.example.busschedule.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.busschedule.database.Schedule
import com.example.busschedule.database.ScheduleDao

class BusScheduleViewModel(private val scheduleDao: ScheduleDao) : ViewModel() {
    fun getAll() : List<Schedule> = scheduleDao.getAll()
    fun getByStop(stopName: String) : List<Schedule> = scheduleDao.getByStopName(stopName)
}

class BusScheduleViewModelFactory(private val scheduleDao: ScheduleDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BusScheduleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BusScheduleViewModel(scheduleDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}