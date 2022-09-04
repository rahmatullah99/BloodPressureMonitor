package bloodpressuremonitor.bpmonitor.bptracker.app.Database

import androidx.lifecycle.LiveData
import androidx.room.*
import bloodpressuremonitor.bpmonitor.bptracker.app.Models.Record

@Dao
interface RecordDAO {

    @Insert
    suspend fun addRecord(record: Record)

    @Update
    suspend fun updateRecord(record: Record)

    @Query("DELETE FROM record_data_table WHERE record_id == :recordId")
    suspend fun deleteRecord(recordId: Int)

    @Query("SELECT * FROM record_data_table WHERE record_id == :recordId")
    fun getRecord(recordId: Int): LiveData<Record>

    @Query("SELECT * FROM record_data_table")
    fun getRecords(): LiveData<List<Record>>


}