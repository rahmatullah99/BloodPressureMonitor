package bloodpressuremonitor.bpmonitor.bptracker.app.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bloodpressuremonitor.bpmonitor.bptracker.app.Database.RecordDAO
import bloodpressuremonitor.bpmonitor.bptracker.app.Models.Record
import kotlinx.coroutines.launch

class RecordViewModel(private val recordDAO: RecordDAO):ViewModel() {

    val TAG = "RecordViewModel"

    val records = recordDAO.getRecords()

    val getRecord = { editRecordId:Int-> recordDAO.getRecord(editRecordId) }

    fun addRecord(record: Record){
        Log.d(TAG,"In add record")
        viewModelScope.launch {
            recordDAO.addRecord(record)
        }
    }

    fun updateRecord(record: Record){
        Log.d(TAG,"In update record")
        viewModelScope.launch {
            recordDAO.updateRecord(record)
        }
    }

    fun deleteRecord(recordId: Int){
        Log.d(TAG,"In delete record")
        viewModelScope.launch {
            recordDAO.deleteRecord(recordId)
        }
    }

}