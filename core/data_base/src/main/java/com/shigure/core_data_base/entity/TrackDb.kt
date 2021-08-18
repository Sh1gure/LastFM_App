package com.shigure.core_data_base.entity

import androidx.room.Entity

@Entity
data class TrackDb(

    val name: String,

    val duration: Long

)
