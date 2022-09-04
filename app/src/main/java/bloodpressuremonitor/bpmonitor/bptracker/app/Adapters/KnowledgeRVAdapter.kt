package bloodpressuremonitor.bpmonitor.bptracker.app.Adapters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bloodpressuremonitor.bpmonitor.bptracker.app.Models.FAQ
import bloodpressuremonitor.bpmonitor.bptracker.app.Models.Knowledge
import bloodpressuremonitor.bpmonitor.bptracker.app.databinding.KnowledgeCardBinding
import java.io.Serializable

class KnowledgeRVAdapter(val context: Context): RecyclerView.Adapter<KnowledgeViewHolder>(),
    Serializable {

    private val KnowledgeList = ArrayList<Knowledge>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KnowledgeViewHolder {
        val binding = KnowledgeCardBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false)
        return KnowledgeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: KnowledgeViewHolder, position: Int) {
        holder.bind(KnowledgeList[position], position, context)
    }

    override fun getItemCount(): Int {
        return KnowledgeList.size
    }

    fun setList(Knowledges:List<Knowledge>){
        KnowledgeList.clear()
        KnowledgeList.addAll(Knowledges)
    }
}

class KnowledgeViewHolder(private val binding: KnowledgeCardBinding)
    :Serializable, RecyclerView.ViewHolder(binding.root) {
    val TAG ="FAQViewHolder"
    fun bind(knowledge: Knowledge, position:Int, context: Context){
        Log.d(TAG,"in bind")
        binding.apply {
            knowledgeCardTopic.text=knowledge.topic
            knowledgeCardImage.background=context.getDrawable(knowledge.image)
            knowledgeBackground.background=context.getDrawable(knowledge.background)
             knowledgeCardBtn.setOnClickListener {
                 //go to knowledge topic of that position
                     val intent = Intent(context,
                     bloodpressuremonitor.bpmonitor.bptracker.app.Activities.KnowledgeTopic::class.java)
                     intent.putExtra("topicPosition",position)
                     intent.putExtra("topicImage",knowledge.image)
                     intent.putExtra("topicTitle",knowledge.topic)
                     context.startActivity(intent)
                }
            }
        }
    }
