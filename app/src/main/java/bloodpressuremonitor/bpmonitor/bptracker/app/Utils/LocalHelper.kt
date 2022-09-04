package bloodpressuremonitor.bpmonitor.bptracker.app.Utils

import android.annotation.TargetApi
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.preference.PreferenceManager
import android.util.Log
import android.view.Display
import java.util.*


object LocaleHelper {
    const val SELECTED_LANGUAGE = "Locale.Helper.Selected.Language"
    const val TAG = "LocaleHelper"

    // the method is used to set the language at runtime
    fun setLocale(context: Context, language: String): Context {
        Log.d(TAG,"in locale helper")
        persist(context, language)
        // updating the language for devices above android nougat
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateResources(context, language)
        } else updateResourcesLegacy(context, language)
        // for devices having lower version of android os
    }

    private fun persist(context: Context, language: String) {
        Log.d(TAG,"In persist")
        val preferences = context.getSharedPreferences("sp",MODE_PRIVATE)
        val editor = preferences.edit()
        Log.d(TAG,"Putting $language as a language in shared preference")
        editor.putString(SELECTED_LANGUAGE, language)
        editor.apply()
    }

    // the method is used update the language of application by creating
    // object of inbuilt Locale class and passing language argument to it
    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResources(context: Context, language: String): Context {
        Log.d(TAG,"In update resource, Language is $language")
        val locale = Locale(language)
        Log.d(TAG,"Locale is $locale")
        Locale.setDefault(locale)
        val configuration: Configuration = context.resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)
        Log.d(TAG,"configuration is $configuration")
        context.resources.updateConfiguration (configuration, context.resources.displayMetrics)
        return context
    }

    private fun updateResourcesLegacy(context: Context, language: String): Context {
        Log.d(TAG,"In update resource legacy, Language is $language")
        val locale = Locale(language)
        Locale.setDefault(locale)
        Log.d(TAG,"Locale is $locale")
        val resources: Resources = context.resources
        val configuration: Configuration = resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)
        Log.d(TAG,"configuration is $configuration")
        resources.updateConfiguration (configuration, resources.displayMetrics)
        return context
    }
}