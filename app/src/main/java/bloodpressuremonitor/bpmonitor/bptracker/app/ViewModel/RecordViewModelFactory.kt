package bloodpressuremonitor.bpmonitor.bptracker.app.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import bloodpressuremonitor.bpmonitor.bptracker.app.Database.RecordDAO

class RecordViewModelFactory(private val dao: RecordDAO): ViewModelProvider.Factory {

    val TAG = "RVMFactory"

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        Log.d(TAG,"In create")
        if(modelClass.isAssignableFrom(RecordViewModel::class.java)){
            return RecordViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }

}
