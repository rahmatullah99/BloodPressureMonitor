package bloodpressuremonitor.bpmonitor.bptracker.app.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bloodpressuremonitor.bpmonitor.bptracker.app.Activities.FAQs
import bloodpressuremonitor.bpmonitor.bptracker.app.Activities.Knowledge
import bloodpressuremonitor.bpmonitor.bptracker.app.Activities.Languages
import bloodpressuremonitor.bpmonitor.bptracker.app.R
import bloodpressuremonitor.bpmonitor.bptracker.app.databinding.FragmentInfoBinding
import bloodpressuremonitor.bpmonitor.bptracker.app.databinding.FragmentSettingsBinding

class InfoFragment : Fragment() {

    val TAG  = "Info Fragment"

    private lateinit var binding: FragmentInfoBinding

    override fun onCreateView(
       inflater: LayoutInflater,
       container: ViewGroup?,
       savedInstanceState: Bundle?): View? {
       binding = FragmentInfoBinding.inflate(inflater,container,false)

       binding.apply {
           Log.d(TAG, "Applying binding")
           faqCardBtn.setOnClickListener { goToFaq() }
           knowledgeCardBtn.setOnClickListener { goToKnowledge() }
       }

      return binding.root
   }

    fun goToFaq(){
        Log.d(TAG, "Going to faq")
        val intent = Intent(activity, FAQs::class.java)
        startActivity(intent)
    }

    fun goToKnowledge(){
        Log.d(TAG, "Going to knowledge")
        val intent = Intent(activity, Knowledge::class.java)
        startActivity(intent)
    }
}