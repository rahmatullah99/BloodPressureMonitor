package bloodpressuremonitor.bpmonitor.bptracker.app.Adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.res.stringResource
import androidx.recyclerview.widget.RecyclerView
import bloodpressuremonitor.bpmonitor.bptracker.app.Models.Record
import bloodpressuremonitor.bpmonitor.bptracker.app.R
import bloodpressuremonitor.bpmonitor.bptracker.app.databinding.RecordListItemBinding

class RecordRVAdapter(val editTapped: (Int) -> Unit, val context:Context) : RecyclerView.Adapter<RecordViewHolder>() {

    private val recordList=ArrayList<Record>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {

        val binding = RecordListItemBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false)
        return RecordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        holder.bind(recordList[position], editTapped, context)
    }

    override fun getItemCount(): Int {
        return recordList.size
    }

    fun setRecordList(records:List<Record>){
        recordList.clear()
        recordList.addAll(records)
    }
}

class RecordViewHolder(val binding:RecordListItemBinding):RecyclerView.ViewHolder(binding.root){

    val TAG = "RecordViewHolder"

    fun bind(record: Record,editTapped: (Int) -> Unit, context: Context){
        Log.d(TAG,"In bind")

        binding.apply {

            editRecordBtn.setOnClickListener {
                //opening editrecord activity from activity
                Log.d(TAG, "Record id is ${record.recordId}")
                editTapped(record.recordId)
            }

            if(record.systolic<90 || record.diastolic<60){
                Log.d(TAG,"Record is hypotension")
                category.text = context.resources.getString(R.string.hypotension)
                categoryColor.setBackgroundResource(R.drawable.category_hypotension)
                category.text=context.resources.getString(R.string.hypotension)
            }else if(record.systolic in 90..119 && record.diastolic in 60..79){
                Log.d(TAG,"Record is normal")
                category.text = context.resources.getString(R.string.normal)
                categoryColor.setBackgroundResource(R.drawable.category_normal)
                category.text=context.resources.getString(R.string.normal)
            }else if(record.systolic in 120..129 && record.diastolic in 60..79){
                Log.d(TAG,"Record is elevate")
                category.text = context.resources.getString(R.string.elevated)
                categoryColor.setBackgroundResource(R.drawable.category_elevated)
                category.text=context.resources.getString(R.string.elevated)
            }else if(record.systolic in 130..139 || record.diastolic in 80..89){
                Log.d(TAG,"Record is hypertension stage 1")
                category.text = context.resources.getString(R.string.hypertension_1)
                categoryColor.setBackgroundResource(R.drawable.category_hypertension1)
                category.text=context.resources.getString(R.string.hypertension_1)
            }else if(record.systolic in 140..180 || record.diastolic in 90..120){
                Log.d(TAG,"Record is hypertension stage 2")
                category.text = context.resources.getString(R.string.hypertension_2)
                categoryColor.setBackgroundResource(R.drawable.category_hypertension2)
                category.text=context.resources.getString(R.string.hypertension_2)
            }else if(record.systolic >180 || record.diastolic >120){
                Log.d(TAG,"Record is hypotensive")
                category.text = context.resources.getString(R.string.hypertensive)
                categoryColor.setBackgroundResource(R.drawable.category_hypertensive)
                category.text=context.resources.getString(R.string.hypertensive)
            }

            systolic.text=record.systolic.toString()
            diastolic.text=record.diastolic.toString()
            timeAndBPM.text="${record.day} ${record.year}, ${record.hour}:${record.minutes}, " +
                    "${record.pulse} ${context.resources.getString(R.string.bpm)}"
        }
    }
}