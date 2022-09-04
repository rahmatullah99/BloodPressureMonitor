package bloodpressuremonitor.bpmonitor.bptracker.app.Adapters

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import bloodpressuremonitor.bpmonitor.bptracker.app.Models.Language
import bloodpressuremonitor.bpmonitor.bptracker.app.Utils.LocaleHelper
import bloodpressuremonitor.bpmonitor.bptracker.app.databinding.LanguageListItemBinding
import java.util.*
import kotlin.collections.ArrayList

class LanguageRVAdapter(val context:Context, private val tappedLanguage: (String) -> Unit)
    : RecyclerView.Adapter<LanguageViewHolder>()  {

    private val languageList = ArrayList<Language>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        val binding = LanguageListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return LanguageViewHolder(binding,context)
    }

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {

        holder.bind(languageList[position],tappedLanguage)

    }

    override fun getItemCount(): Int {
        return languageList.size
    }

    fun setList(languages:List<Language>){
        languageList.clear()
        languageList.addAll(languages)
    }
}

class LanguageViewHolder(private val binding: LanguageListItemBinding, private val context: Context)
    : RecyclerView.ViewHolder(binding.root){

    val TAG="LangaugeVH"

    val preferences: SharedPreferences = context.getSharedPreferences("sp", Context.MODE_PRIVATE)

    fun bind(language: Language, tappedLanguage: (String) -> Unit){

        Log.d(TAG,"In bind")
        binding.apply {

            languageName.text = language.language
            tick.isVisible = language.code == preferences.getString(LocaleHelper.SELECTED_LANGUAGE,"en")

            languagelistItem.setOnClickListener {
                Log.d(TAG,"Selected language is ${language.language}")
                tappedLanguage(language.code)

            }
        }
    }

    private fun selectedLanguage(language:String){
        val locale = Locale(language)
        Locale.setDefault(locale)
        val resources= context.resources
        val dm=resources.displayMetrics
        val config = resources.configuration
        config.setLocale(locale)
        context.resources.updateConfiguration(config,dm)
    }
}