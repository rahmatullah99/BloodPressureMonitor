package bloodpressuremonitor.bpmonitor.bptracker.app.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import bloodpressuremonitor.bpmonitor.bptracker.app.Models.FAQ
import bloodpressuremonitor.bpmonitor.bptracker.app.databinding.FaqListItemBinding

class FAQRVAdapter: RecyclerView.Adapter<FAQViewHolder>() {

    private val FAQList = ArrayList<FAQ>()

    val TAG ="FAQRVAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FAQViewHolder {
        val binding = FaqListItemBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false)
        return FAQViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FAQViewHolder, position: Int) {
        holder.bind(FAQList[position])
        
    }

    override fun getItemCount(): Int {
        return FAQList.size
    }

    fun setList(FAQs:List<FAQ>){
        FAQList.clear()
        FAQList.addAll(FAQs)
    }

   /* override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val queryString = p0?.toString()?.toLowerCase()

                val filterResults = FilterResults()
                filterResults.values = if (queryString==null || queryString.isEmpty())

                else
                    allPois.filter {
                        it.name.toLowerCase().contains(queryString) ||
                                it.city.toLowerCase().contains(queryString) ||
                                it.category_name.toLowerCase().contains(queryString)
                    }
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {

            }


        }
    }*/
}

class FAQViewHolder(private val binding: FaqListItemBinding)
    :RecyclerView.ViewHolder(binding.root){

    val TAG ="FAQViewHolder"

    fun bind(faq: FAQ){
        Log.d(TAG,"in bind")
        binding.apply {
            faqQuestionText.text = faq.question
            faqAnswerText.text = faq.answer

            var isExpandable:Boolean = faq.isExpandable
            faqAnswer.isVisible=false

            faqQuestion.setOnClickListener {
                Log.d(TAG,"Setting expandable in question")
                isExpandable=!isExpandable
                setExpand(faqAnswer,isExpandable)}

            faqAnswer.setOnClickListener {
                Log.d(TAG,"Setting expandable in answer")
                isExpandable=!isExpandable
                setExpand(faqAnswer,isExpandable)}

        }
    }

    fun setExpand(faqAnswer: LinearLayout, isExpandable: Boolean){
        Log.d(TAG,"IsExpandable is $isExpandable")
        faqAnswer.isVisible = isExpandable
    }

}