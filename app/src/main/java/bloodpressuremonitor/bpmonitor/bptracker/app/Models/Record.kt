package bloodpressuremonitor.bpmonitor.bptracker.app.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity(tableName = "record_data_table")
data class Record(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="record_id")
    val recordId:Int,
    @ColumnInfo(name="systolic")
    val systolic:Int,
    @ColumnInfo(name="diastolic")
    val diastolic:Int,
    @ColumnInfo(name="pulse")
    val pulse:Int,
    @ColumnInfo(name="year")
    val year:String,
    @ColumnInfo(name="day")
    val day:String,
    @ColumnInfo(name="hour")
    val hour:String,
    @ColumnInfo(name="minutes")
    val minutes:String,
    @ColumnInfo(name="timeAdded")
    val timeAdded:String)