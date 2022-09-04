package bloodpressuremonitor.bpmonitor.bptracker.app.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import bloodpressuremonitor.bpmonitor.bptracker.app.Adapters.KnowledgeRVAdapter
import bloodpressuremonitor.bpmonitor.bptracker.app.MainActivity
import bloodpressuremonitor.bpmonitor.bptracker.app.Models.Knowledge
import bloodpressuremonitor.bpmonitor.bptracker.app.R
import bloodpressuremonitor.bpmonitor.bptracker.app.databinding.ActivityKnowledgeBinding
import bloodpressuremonitor.bpmonitor.bptracker.app.databinding.ActivityRecordHistoryBinding

class Knowledge : AppCompatActivity() {

    val TAG = "Knowledge"
    
    private lateinit var adapter: KnowledgeRVAdapter
    private lateinit var binding: ActivityKnowledgeBinding
    private var knowledgeList = ArrayList<Knowledge>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKnowledgeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "on create")

        binding.backBtn.setOnClickListener { finish() }

        displayknowledgeList()
        initRecyclerView()

    }

    fun initRecyclerView(){
        Log.d(TAG, "Init recycler view")
        binding.knowledgeRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = KnowledgeRVAdapter(this)
        binding.knowledgeRecyclerView.adapter = adapter
        adapter.setList(knowledgeList)
        adapter.notifyDataSetChanged()
    }

    fun displayknowledgeList(){
        Log.d(TAG, "Displaying knowledge")
        knowledgeList.add(Knowledge(resources.getString(R.string.topic_1), R.drawable.background_4, R.drawable.bp_range))
        knowledgeList.add(Knowledge(resources.getString(R.string.topic_2), R.drawable.background_5, R.drawable.learn_bp))
        knowledgeList.add(Knowledge(resources.getString(R.string.topic_3), R.drawable.background_6, R.drawable.bp_type))
        knowledgeList.add(Knowledge(resources.getString(R.string.topic_4), R.drawable.background_7, R.drawable.bp_misbelief))
        knowledgeList.add(Knowledge(resources.getString(R.string.topic_5), R.drawable.background_8, R.drawable.hypertension_type))
        knowledgeList.add(Knowledge(resources.getString(R.string.topic_6), R.drawable.background_4, R.drawable.hypertension_symptoms))
        knowledgeList.add(Knowledge(resources.getString(R.string.topic_7), R.drawable.background_5, R.drawable.hypertension_problems))
        knowledgeList.add(Knowledge(resources.getString(R.string.topic_8), R.drawable.background_6, R.drawable.understand_hypotension))
        knowledgeList.add(Knowledge(resources.getString(R.string.topic_9), R.drawable.background_7, R.drawable.drugs_hypertension))
        knowledgeList.add(Knowledge(resources.getString(R.string.topic_10), R.drawable.background_8, R.drawable.healthy_lifestyle))
        knowledgeList.add(Knowledge(resources.getString(R.string.topic_11), R.drawable.background_4, R.drawable.bp_exercise))
        knowledgeList.add(Knowledge(resources.getString(R.string.topic_12), R.drawable.background_5, R.drawable.diagonise_hypertension))
        knowledgeList.add(Knowledge(resources.getString(R.string.topic_13), R.drawable.background_6, R.drawable.drugs_hypotension))
        knowledgeList.add(Knowledge(resources.getString(R.string.topic_14), R.drawable.background_7, R.drawable.first_aid_hypertensive))
        knowledgeList.add(Knowledge(resources.getString(R.string.topic_15), R.drawable.background_8, R.drawable.first_aid_hypotensive))

    }

    /* fun search(){
     binding.apply {
         faqSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
             // Override onQueryTextSubmit method which is call when submit query is searched
             override fun onQueryTextSubmit(query: String): Boolean {
                 // If the list contains the search query than filter the adapter
                 // using the filter method with the query as its argument

                 for (i in faqList){
                     if (i.question.contains(query)) {
                         adapter.filter.filter(query)
                     } else {
                         // Search query not found in List View
                         Toast.makeText(applicationContext, "Not found", Toast.LENGTH_LONG).show()

                     }
                 }
                 return false
             }

             // This method is overridden to filter the adapter according
             // to a search query when the user is typing search
             override fun onQueryTextChange(newText: String): Boolean {
                 adapter.filter.filter(newText)
                 return false
             }
         })
     }
 }*/

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}