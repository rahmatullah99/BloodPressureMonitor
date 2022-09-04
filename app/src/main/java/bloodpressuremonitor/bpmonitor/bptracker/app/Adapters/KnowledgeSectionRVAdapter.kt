package bloodpressuremonitor.bpmonitor.bptracker.app.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.unit.dp
import androidx.core.view.isVisible
import androidx.core.view.marginTop
import androidx.recyclerview.widget.RecyclerView
import bloodpressuremonitor.bpmonitor.bptracker.app.Models.KnowledgeSection
import bloodpressuremonitor.bpmonitor.bptracker.app.databinding.KnowledgeSectionCardBinding

class KnowledgeSectionRVAdapter(): RecyclerView.Adapter<KnowledgeSectionViewHolder>()  {

    private val knowledgeSectionList = ArrayList<KnowledgeSection>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KnowledgeSectionViewHolder {
        val binding = KnowledgeSectionCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return KnowledgeSectionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: KnowledgeSectionViewHolder, position: Int) {
        holder.bind(knowledgeSectionList[position])
    }

    override fun getItemCount(): Int {
        return knowledgeSectionList.size
    }

    fun setList(knowledgeSections:List<KnowledgeSection>){
        knowledgeSectionList.clear()
        knowledgeSectionList.addAll(knowledgeSections)
    }
}

class KnowledgeSectionViewHolder(private val binding: KnowledgeSectionCardBinding)
    : RecyclerView.ViewHolder(binding.root){
    val TAG="KSeViewHolder"
    fun bind(knowledgeSection: KnowledgeSection){
        Log.d(TAG,"In bind")
        if(knowledgeSection.sub){
            binding.apply {
                headingView.isVisible=false
                headingSub.isVisible=true
                headingSub.text=knowledgeSection.section
                paragraph.text=knowledgeSection.paragraph
            }
        }else if(knowledgeSection.section == ""){
            binding.apply {
                headingView.isVisible=false
                headingSub.isVisible=false
                paragraph.text=knowledgeSection.paragraph
            }
        }else{
            binding.apply {
                headingMain.isVisible=true
                headingSub.isVisible=false
                headingMain.text=knowledgeSection.section
                paragraph.text=knowledgeSection.paragraph
            }
        }
    }

}