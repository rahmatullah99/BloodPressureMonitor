package bloodpressuremonitor.bpmonitor.bptracker.app

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import bloodpressuremonitor.bpmonitor.bptracker.app.Utils.LocaleHelper
import bloodpressuremonitor.bpmonitor.bptracker.app.databinding.ActivityMainBinding
import com.facebook.ads.*
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    val TAG ="Main Activity"
    //Ads
    private var interstitialAd: InterstitialAd? = null

    private lateinit var binding: ActivityMainBinding
    private var currentFragment=1
    private lateinit var editor : SharedPreferences.Editor
    private lateinit var preferences:SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        interstitialAd = InterstitialAd(this, "1406327383213198_1406342489878354");
        showInterstialAd()
        actionBar?.elevation = 0f
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(TAG, "In Main activity")


        binding.apply {
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
            val navController = navHostFragment.navController
            bottomNavigationView.setupWithNavController(navController)
            bottomNavViewListener(bottomNavigationView,navController)}

        //get language from shared pref
        preferences = applicationContext.getSharedPreferences("sp", Context.MODE_PRIVATE)
        editor=preferences.edit()
        val langCode = preferences.getString(LocaleHelper.SELECTED_LANGUAGE,null)
        if (langCode != null) {
            clickedLanguage(langCode) }


    }

    private fun clickedLanguage(language:String){
        Log.d(TAG,"clicked language is $language")
        LocaleHelper.setLocale(this, language);
        Toast.makeText(this,
            resources.getString(R.string.selected_language),
            Toast.LENGTH_LONG).show()
    }

    private fun bottomNavViewListener(bottomNavigationView:BottomNavigationView, navController: NavController) {
        bottomNavigationView.setOnItemSelectedListener {
                Log.d(TAG,"Item id is ${it.itemId}")
                when (it.itemId) {
                    R.id.tracker -> {
                        Log.d(TAG,"Navigating to tracker fragment")

                        if(currentFragment==2){
                            currentFragment=1
                            navController.navigate(R.id.action_infoFragment_to_trackerFragment)

                        }else if(currentFragment==3){
                            currentFragment=1
                            navController.navigate(R.id.action_settingsFragment_to_trackerFragment)
                        }
                        true
                    }
                    R.id.info -> {
                        Log.d(TAG,"Navigating to info fragment")

                        if(currentFragment==1){
                            currentFragment=2
                            navController.navigate(R.id.action_trackerFragment_to_infoFragment)

                        }else if(currentFragment==3){
                            currentFragment=2
                            navController.navigate(R.id.action_settingsFragment_to_infoFragment)
                        }
                        true
                    }
                    R.id.settings -> {
                        Log.d(TAG,"Navigating to settings fragment")

                        if(currentFragment==1){
                            currentFragment=3
                            navController.navigate(R.id.action_trackerFragment_to_settingsFragment)

                        }else if(currentFragment==2){
                            currentFragment=3
                            navController.navigate(R.id.action_infoFragment_to_settingsFragment)
                        }
                        true

                    }
                    else ->{
                        Log.d(TAG,"Somethings wrong in bottom view click listener")
                        Toast.makeText(applicationContext,
                            "Somethings wrong",
                            Toast.LENGTH_SHORT).show()
                        false
                    }
                }

            }
    }

    //ADS
    private fun showInterstialAd(){
        val interstitialAdListener = object :InterstitialAdListener{
            override fun onError(p0: Ad?, p1: AdError?) {
                //TODO: implement later
            }

            override fun onAdLoaded(p0: Ad?) {
                interstitialAd?.show()
            }

            override fun onAdClicked(p0: Ad?) {
                //TODO: implement later
            }

            override fun onLoggingImpression(p0: Ad?) {
                //TODO: implement later
            }

            override fun onInterstitialDisplayed(p0: Ad?) {
                //TODO: implement later
            }

            override fun onInterstitialDismissed(p0: Ad?) {
                //TODO: implement later
            }

        }

        interstitialAd!!.loadAd(
            interstitialAd!!.buildLoadAdConfig()
                .withAdListener(interstitialAdListener)
                .build())

    }

    private fun showAdWithDelay() {

        Handler(Looper.getMainLooper()).postDelayed( { // Check if interstitialAd has been loaded successfully
            if (interstitialAd == null || !interstitialAd!!.isAdLoaded) {

            }
            // Check if ad is already expired or invalidated, and do not show ad if that is the case. You will not get paid to show an invalidated ad.
            if (interstitialAd!!.isAdInvalidated) {

            }
            // Show the ad
            interstitialAd!!.show()
        }, 1000 * 60 * 3)
    }

    override fun onDestroy() {
      if (interstitialAd != null) {
           interstitialAd!!.destroy()
       }
        super.onDestroy()
    }

}