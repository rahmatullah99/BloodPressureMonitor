package bloodpressuremonitor.bpmonitor.bptracker.app.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import bloodpressuremonitor.bpmonitor.bptracker.app.Adapters.RecordRVAdapter
import bloodpressuremonitor.bpmonitor.bptracker.app.Database.RecordDatabase
import bloodpressuremonitor.bpmonitor.bptracker.app.Models.Record
import bloodpressuremonitor.bpmonitor.bptracker.app.ViewModel.RecordViewModel
import bloodpressuremonitor.bpmonitor.bptracker.app.ViewModel.RecordViewModelFactory
import bloodpressuremonitor.bpmonitor.bptracker.app.databinding.ActivityRecordHistoryBinding

class Records : AppCompatActivity() {

    private lateinit var binding: ActivityRecordHistoryBinding
    private lateinit var adapter: RecordRVAdapter
    private lateinit var recordViewModel: RecordViewModel

    val TAG ="Records"

    private var recordsList = ArrayList<Record>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecordHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dao = RecordDatabase.getInstance(application).recordDAO
        val factory = RecordViewModelFactory(dao)
        recordViewModel = ViewModelProvider(this, factory)[RecordViewModel::class.java]

        binding.backBtn.setOnClickListener { cancel() }
        getRecords()

    }

    fun cancel(){
        Log.d(TAG,"Going back")
        finish()
    }

    fun initRecyclerView(){
        Log.d(TAG,"init recycler view")
        binding.recordsRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = RecordRVAdapter({selectedRecord: Int -> editTapped(selectedRecord)},this)
        binding.recordsRecyclerView.adapter = adapter
        adapter.setRecordList(recordsList)
        adapter.notifyDataSetChanged()
    }

    fun getRecords(){

        Log.d(TAG,"display records")
        recordsList.clear()
        binding.apply { recordViewModel.records.observe(this@Records, Observer {
                for (i in it){
                    recordsList.add(i)
                    Log.d(TAG, "Record is $i")
                }
            initRecyclerView()
        })}

    }

    fun editTapped(recordId:Int){
        Log.d(TAG,"edit tapped")
        intent = Intent(this, EditRecord::class.java)
        intent.putExtra("recordId",recordId)
        startActivity(intent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }


}