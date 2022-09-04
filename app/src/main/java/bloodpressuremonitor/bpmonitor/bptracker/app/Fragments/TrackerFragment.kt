package bloodpressuremonitor.bpmonitor.bptracker.app.Fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import bloodpressuremonitor.bpmonitor.bptracker.app.Activities.AddRecord
import bloodpressuremonitor.bpmonitor.bptracker.app.Activities.EditRecord
import bloodpressuremonitor.bpmonitor.bptracker.app.Activities.KnowledgeTopic
import bloodpressuremonitor.bpmonitor.bptracker.app.Activities.Records
import bloodpressuremonitor.bpmonitor.bptracker.app.Adapters.RecordRVAdapter
import bloodpressuremonitor.bpmonitor.bptracker.app.Models.Record
import bloodpressuremonitor.bpmonitor.bptracker.app.R
import bloodpressuremonitor.bpmonitor.bptracker.app.ViewModel.RecordViewModel
import bloodpressuremonitor.bpmonitor.bptracker.app.databinding.FragmentTrackerBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class TrackerFragment : Fragment() {

    private lateinit var binding:FragmentTrackerBinding
    private lateinit var recordList: ArrayList<Record>

    val TAG ="Tracker Fragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        Log.d(TAG, "In on create view")
        binding = FragmentTrackerBinding.inflate(inflater, container, false)

        val nestedTracker = NestedTracker()

        Log.d(TAG, "Now adding Nested tracker fragment")
        val transaction: FragmentTransaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container,nestedTracker)
        transaction.commit()

        return binding.root
    }

}