package bloodpressuremonitor.bpmonitor.bptracker.app.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import bloodpressuremonitor.bpmonitor.bptracker.app.R
import bloodpressuremonitor.bpmonitor.bptracker.app.databinding.ActivityAddEditRecordBinding
import bloodpressuremonitor.bpmonitor.bptracker.app.databinding.ActivityLanguageBinding
import bloodpressuremonitor.bpmonitor.bptracker.app.databinding.ActivityPrivacyPolicyBinding

class PrivacyPolicy : AppCompatActivity() {

    private lateinit var binding: ActivityPrivacyPolicyBinding

    val TAG = "Privacy policy"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrivacyPolicyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            backBtn.setOnClickListener { finish() }

            ppWebView.settings.javaScriptEnabled = true
            ppWebView.loadUrl("https://rahmatullah.my.canva.site/white-and-orange-simple-light-real-estate-portfolio-resume-website")
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }


}