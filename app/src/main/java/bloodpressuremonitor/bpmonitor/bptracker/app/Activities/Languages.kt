package bloodpressuremonitor.bpmonitor.bptracker.app.Activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import bloodpressuremonitor.bpmonitor.bptracker.app.Adapters.LanguageRVAdapter
import bloodpressuremonitor.bpmonitor.bptracker.app.MainActivity
import bloodpressuremonitor.bpmonitor.bptracker.app.Models.Language
import bloodpressuremonitor.bpmonitor.bptracker.app.R
import bloodpressuremonitor.bpmonitor.bptracker.app.Utils.LocaleHelper
import bloodpressuremonitor.bpmonitor.bptracker.app.databinding.ActivityLanguageBinding


class Languages : AppCompatActivity() {

    val TAG="Language"

    private lateinit var adapter: LanguageRVAdapter
    private lateinit var binding: ActivityLanguageBinding
    private var languageList = ArrayList<Language>()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLanguageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener { finish() }

        displayLanguages()
        initRecyclerView()

    }

    private fun initRecyclerView(){
        Log.d(TAG,"init recyclerview")
        binding.languageRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = LanguageRVAdapter(this) {
                selectedLanguage: String -> clickedLanguage(selectedLanguage)
        }
        binding.languageRecyclerView.adapter = adapter
        adapter.setList(languageList)
        adapter.notifyDataSetChanged()
    }

    private fun displayLanguages(){

        Log.d(TAG,"display language")

        languageList.add(Language("en","English"))
        languageList.add(Language("zh","中国人"))//chinese
        languageList.add(Language("hi","हिन्दी"))
        languageList.add(Language("es","Español"))
        languageList.add(Language("fr","français"))
        languageList.add(Language("ar","عربى"))
        languageList.add(Language("bn","বাংলা"))//bengal
        languageList.add(Language("ru","Русский"))//russia
        languageList.add(Language("pt","португальский"))//portugese
        languageList.add(Language("in","bahasa Indonesia"))//indonesia
        languageList.add(Language("pa","ਪੰਜਾਬੀ"))//punjabi
        languageList.add(Language("vi","Tiếng Việt"))//vietnamese
        languageList.add(Language("mr","मराठी"))//marathi
        languageList.add(Language("te","తెలుగు"))//telugu
        languageList.add(Language("tr","Türk"))
        languageList.add(Language("ta","தமிழ்"))//tamil
        languageList.add(Language("ur","اردو"))//urdu

    }

    private fun clickedLanguage(language:String){
        Log.d(TAG,"clicked language is $language")
        LocaleHelper.setLocale(this, language);
        Toast.makeText(this,
            resources.getString(R.string.selected_language),
            Toast.LENGTH_LONG).show()
        recreate()
        navigateUpTo(Intent(this@Languages, MainActivity::class.java))
        startActivity(intent)

        // triggerRebirth(this)
    }

    private fun triggerRebirth(context: Context) {
        val packageManager = context.packageManager
        val intent = packageManager.getLaunchIntentForPackage(context.packageName)
        val componentName = intent!!.component
        val mainIntent = Intent.makeRestartActivityTask(componentName)
        context.startActivity(mainIntent)
        Runtime.getRuntime().exit(0)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}