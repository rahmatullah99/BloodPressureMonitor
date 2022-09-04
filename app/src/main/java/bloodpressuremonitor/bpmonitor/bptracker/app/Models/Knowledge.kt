package bloodpressuremonitor.bpmonitor.bptracker.app.Models

import android.media.Image
import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class Knowledge(val topic:String, val background: Int, val image:Int) : Serializable