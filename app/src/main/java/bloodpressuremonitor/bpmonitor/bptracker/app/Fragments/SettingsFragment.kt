package bloodpressuremonitor.bpmonitor.bptracker.app.Fragments

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import bloodpressuremonitor.bpmonitor.bptracker.app.Activities.Languages
import bloodpressuremonitor.bpmonitor.bptracker.app.Activities.PrivacyPolicy
import bloodpressuremonitor.bpmonitor.bptracker.app.R
import bloodpressuremonitor.bpmonitor.bptracker.app.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {

    val TAG  = "Settings Fragment"

    private lateinit var binding: FragmentSettingsBinding
    private val packageName: String = "bloodpressuremonitor.bpmonitor.bptraker.app"
    private var vName:String="1.0"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentSettingsBinding.inflate(inflater,container,false)

        Log.d(TAG, "In on create view")

        binding.apply {
            Log.d(TAG, "Applying binding")
            languageButton.setOnClickListener { goToLanguages() }
            exportButton.setOnClickListener { exportAsFile() }
            rateUsButton.setOnClickListener { rateApp() }
            shareButton.setOnClickListener { share() }
            feedbackButton.setOnClickListener { giveFeedback() }
            privacyPolicyButton.setOnClickListener { goToPrivacyPolicy() }


            try {
                val pInfo = requireContext().packageManager.getPackageInfo(
                    requireContext().packageName, 0
                )
                vName = pInfo.versionName
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }

            version.text="${resources.getString(R.string.version) } $vName"

        }

        return binding.root
    }

    private fun goToLanguages(){
        Log.d(TAG, "Going to languages")
        val intent = Intent(activity, Languages::class.java)
        startActivity(intent)
    }

    private fun exportAsFile(){
        Log.d(TAG, "Exporting as file")
       Toast.makeText(activity,
           "We are working on export functionality currenltly",
            Toast.LENGTH_SHORT).show()
    }

    private fun rateApp(){
        Log.d(TAG, "Going to rate app")
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
        } catch (e: ActivityNotFoundException) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageName")))
        }
    }

    private fun share(){
        Log.d(TAG, "Going to share")
        val sharingIntent = Intent(Intent.ACTION_SEND)

        // type of the content to be shared
        sharingIntent.type = "text/plain"
        // Body of the content
        val shareBody = resources.getString(R.string.sharebody) +"https://play.google.com/store/apps/details?id=$packageName"
        // subject of the content. you can share anything
        val shareSubject = resources.getString(R.string.app_name)
        // passing body of the content
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
        // passing subject of the content
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject)
        startActivity(Intent.createChooser(sharingIntent, "Share using"))

    }

    private fun giveFeedback(){
        Log.d(TAG, "Going to give feedback")
        val feedbackEmail = Intent(Intent.ACTION_SEND)
        feedbackEmail.type = "text/email"
        feedbackEmail.putExtra(Intent.EXTRA_EMAIL, arrayOf("byrahmatullah@gmail.com"))
        feedbackEmail.putExtra(Intent.EXTRA_SUBJECT, "Feedback")
        startActivity(Intent.createChooser(feedbackEmail, "Send Feedback:"))
    }

    private fun goToPrivacyPolicy(){
        Log.d(TAG, "Going to privacy policy")
        val intent = Intent(activity, PrivacyPolicy::class.java)
        startActivity(intent)
    }

}