package com.shigure.core_data_base.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.shigure.core_data_base.entity.TrackDb

class TracksListConverter {
    @TypeConverter
    fun fromList(list: List<TrackDb>): String =
        Gson().toJson(list)

    @TypeConverter
    fun fromJson(json: String): List<TrackDb> {
        val listType = object : TypeToken<List<TrackDb>>() {}.type
        return Gson().fromJson(json, listType)
    }
}