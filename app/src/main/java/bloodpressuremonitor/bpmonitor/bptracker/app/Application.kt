package bloodpressuremonitor.bpmonitor.bptracker.app

import android.app.Application
import com.facebook.ads.*;

class ThisApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AudienceNetworkAds.initialize(this)
    }

}