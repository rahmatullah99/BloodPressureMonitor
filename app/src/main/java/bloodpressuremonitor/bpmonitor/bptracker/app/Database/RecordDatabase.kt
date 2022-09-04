package bloodpressuremonitor.bpmonitor.bptracker.app.Database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import bloodpressuremonitor.bpmonitor.bptracker.app.Models.Record

@Database(entities = [Record::class], version = 1, exportSchema = false)
abstract class RecordDatabase:RoomDatabase() {

    abstract val recordDAO:RecordDAO

    companion object {
        @Volatile
        private var INSTANCE:RecordDatabase?=null
        fun getInstance(context:Context):RecordDatabase {
            Log.d("Record Database","In getinstance")
            synchronized(this) {
                Log.d("Record Database","In syncronized")
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        RecordDatabase::class.java,
                        "records_database").build()
                }
                return instance
            }

        }
    }



}