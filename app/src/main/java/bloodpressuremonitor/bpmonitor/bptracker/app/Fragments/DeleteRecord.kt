package bloodpressuremonitor.bpmonitor.bptracker.app.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import bloodpressuremonitor.bpmonitor.bptracker.app.Database.RecordDatabase
import bloodpressuremonitor.bpmonitor.bptracker.app.MainActivity
import bloodpressuremonitor.bpmonitor.bptracker.app.ViewModel.RecordViewModel
import bloodpressuremonitor.bpmonitor.bptracker.app.ViewModel.RecordViewModelFactory
import bloodpressuremonitor.bpmonitor.bptracker.app.databinding.FragmentDeleteRecordBinding

class DeleteRecord : DialogFragment() {

    val TAG  = "Delete Record"

    private lateinit var binding: FragmentDeleteRecordBinding
    private lateinit var recordViewModel: RecordViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentDeleteRecordBinding.inflate(inflater,container,false)

        Log.d(TAG, "In on create view")

        val recordId = arguments?.getInt("recordId")

        val dao = RecordDatabase.getInstance(requireActivity().application).recordDAO
        val factory = RecordViewModelFactory(dao)
        recordViewModel = ViewModelProvider(this, factory)[RecordViewModel::class.java]

        binding.apply {
            Log.d(TAG, "Applying binding")
            cancelRecordBtn.setOnClickListener {
                requireActivity().supportFragmentManager.beginTransaction().remove(this@DeleteRecord).commit()
                Log.d(TAG,"Cancel tapped, fragment destroyed")
            }

            deleteRecordButton.setOnClickListener {
                if (recordId != null) {
                    recordViewModel.deleteRecord(recordId)
                    requireActivity().supportFragmentManager.beginTransaction().remove(this@DeleteRecord).commit()
                    val intent =Intent(activity,MainActivity::class.java)
                    Log.d(TAG,"Delete tapped, fragment destroyed, going to main activity")
                    startActivity(intent)
                }else{
                    Toast.makeText(activity,"Somethings wrong",Toast.LENGTH_SHORT).show()
                }
            }
        }

        return binding.root
    }
}