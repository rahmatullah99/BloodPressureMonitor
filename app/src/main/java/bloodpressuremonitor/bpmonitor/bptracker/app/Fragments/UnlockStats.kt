package bloodpressuremonitor.bpmonitor.bptracker.app.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bloodpressuremonitor.bpmonitor.bptracker.app.Activities.AddRecord
import bloodpressuremonitor.bpmonitor.bptracker.app.Activities.Records
import bloodpressuremonitor.bpmonitor.bptracker.app.R
import bloodpressuremonitor.bpmonitor.bptracker.app.databinding.FragmentTrackerBinding
import bloodpressuremonitor.bpmonitor.bptracker.app.databinding.FragmentUnlockStatsBinding

class UnlockStats : Fragment() {

    val TAG = "Unlock Stats"

    private lateinit var binding: FragmentUnlockStatsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d(TAG, "In on create view")
        binding = FragmentUnlockStatsBinding.inflate(inflater, container, false)

        binding.addRecordButton.setOnClickListener {
            val intent = Intent(activity, AddRecord::class.java)
            startActivity(intent)
        }

        return binding.root
    }


}