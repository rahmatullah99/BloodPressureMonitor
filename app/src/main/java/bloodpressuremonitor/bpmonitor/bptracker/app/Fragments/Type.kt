package bloodpressuremonitor.bpmonitor.bptracker.app.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bloodpressuremonitor.bpmonitor.bptracker.app.R
import com.github.mikephil.charting.data.BarEntry

class Type : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_type, container, false)
    }



}