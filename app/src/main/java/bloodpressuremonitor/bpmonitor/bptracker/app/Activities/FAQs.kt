package bloodpressuremonitor.bpmonitor.bptracker.app.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bloodpressuremonitor.bpmonitor.bptracker.app.Adapters.FAQRVAdapter
import bloodpressuremonitor.bpmonitor.bptracker.app.MainActivity
import bloodpressuremonitor.bpmonitor.bptracker.app.Models.FAQ
import bloodpressuremonitor.bpmonitor.bptracker.app.R
import bloodpressuremonitor.bpmonitor.bptracker.app.databinding.ActivityAddEditRecordBinding
import bloodpressuremonitor.bpmonitor.bptracker.app.databinding.ActivityFaqBinding

class FAQs : AppCompatActivity() {

    val TAG  = "FAQ"

    private lateinit var binding: ActivityFaqBinding
    private lateinit var adapter: FAQRVAdapter

    private var faqList = ArrayList<FAQ>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFaqBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            backBtn.setOnClickListener {cancel()}
        }

        displayFaqList()
        initRecyclerView()

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

    private fun displayFaqList(){
        Log.d(TAG, "Displaying faq list")
        faqList.add(FAQ(resources.getString(R.string.question_1),
            resources.getString(R.string.answer_1),false))
        faqList.add(FAQ(resources.getString(R.string.question_2),
            resources.getString(R.string.answer_2),false))
        faqList.add(FAQ(resources.getString(R.string.question_3),
            resources.getString(R.string.answer_3),false))
        faqList.add(FAQ(resources.getString(R.string.question_4),
            resources.getString(R.string.answer_4),false))
        faqList.add(FAQ(resources.getString(R.string.question_5),
            resources.getString(R.string.answer_5),false))
        faqList.add(FAQ(resources.getString(R.string.question_6),
            resources.getString(R.string.answer_6),false))
        faqList.add(FAQ(resources.getString(R.string.question_7),
            resources.getString(R.string.answer_7),false))
        faqList.add(FAQ(resources.getString(R.string.question_8),
            resources.getString(R.string.answer_8),false))
        faqList.add(FAQ(resources.getString(R.string.question_9),
            resources.getString(R.string.answer_9),false))
        faqList.add(FAQ(resources.getString(R.string.question_10),
            resources.getString(R.string.answer_10),false))
        faqList.add(FAQ(resources.getString(R.string.question_11),
            resources.getString(R.string.answer_11),false))
        faqList.add(FAQ(resources.getString(R.string.question_12),
            resources.getString(R.string.answer_12),false))
        faqList.add(FAQ(resources.getString(R.string.question_13),
            resources.getString(R.string.answer_13),false))
        faqList.add(FAQ(resources.getString(R.string.question_14),
            resources.getString(R.string.answer_14),false))
        faqList.add(FAQ(resources.getString(R.string.question_15),
            resources.getString(R.string.answer_15),false))
        faqList.add(FAQ(resources.getString(R.string.question_16),
            resources.getString(R.string.answer_16),false))
        faqList.add(FAQ(resources.getString(R.string.question_17),
            resources.getString(R.string.answer_17),false))
        faqList.add(FAQ(resources.getString(R.string.question_18),
            resources.getString(R.string.answer_18),false))
        faqList.add(FAQ(resources.getString(R.string.question_19),
            resources.getString(R.string.answer_19),false))
        faqList.add(FAQ(resources.getString(R.string.question_20),
            resources.getString(R.string.answer_20),false))



    }

    private fun cancel(){
        Log.d(TAG, "Going back")
        finish()
    }

    private fun initRecyclerView(){
        Log.d(TAG, "Initializing recycler view")
        binding.faqRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = FAQRVAdapter()
        binding.faqRecyclerView.adapter = adapter
        adapter.setList(faqList)
        adapter.notifyDataSetChanged()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }


}