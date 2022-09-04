package bloodpressuremonitor.bpmonitor.bptracker.app.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import bloodpressuremonitor.bpmonitor.bptracker.app.Database.RecordDatabase
import bloodpressuremonitor.bpmonitor.bptracker.app.Fragments.DeleteRecord
import bloodpressuremonitor.bpmonitor.bptracker.app.MainActivity
import bloodpressuremonitor.bpmonitor.bptracker.app.Models.Record
import bloodpressuremonitor.bpmonitor.bptracker.app.R
import bloodpressuremonitor.bpmonitor.bptracker.app.ViewModel.RecordViewModel
import bloodpressuremonitor.bpmonitor.bptracker.app.ViewModel.RecordViewModelFactory
import bloodpressuremonitor.bpmonitor.bptracker.app.databinding.ActivityAddEditRecordBinding
import java.util.*

class EditRecord : AppCompatActivity() {

    val TAG  = "Edit Record"

    private lateinit var binding: ActivityAddEditRecordBinding
    private lateinit var recordViewModel: RecordViewModel

    //Record
    var pickedSystolic:Int = 0
    var pickedDiastolic:Int = 0
    var pickedPulse:Int = 0
    var pickedYear:String = ""
    var pickedDay:String = ""
    var pickedHour:String = ""
    var pickedMinute:String = ""

    //currentRecordInfo
    var recordId:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "In on create view")

        binding = ActivityAddEditRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.filterTime.text="Edit Record"

        val dao = RecordDatabase.getInstance(application).recordDAO
        val factory = RecordViewModelFactory(dao)
        recordViewModel = ViewModelProvider(this, factory)[RecordViewModel::class.java]

        recordId = intent.getIntExtra("recordId",0)
        Log.d(TAG,"Current record id is $recordId")

        if(recordId!=0){
            recordViewModel.getRecord(recordId).observe(this) {
                setValues(it.systolic,it.diastolic,it.pulse,it.year,it.day,it.hour,it.minutes)
            }
        }else{
            Toast.makeText(this, "Something's wrong",Toast.LENGTH_LONG).show()
        }

        binding.apply {
            backBtn.setOnClickListener { cancel() }
            deleteRecordButton.setOnClickListener { deleteRecord(recordId) }

            helpBtn.setOnClickListener {
                val intent = Intent(this@EditRecord, KnowledgeTopic::class.java)
                intent.getStringExtra("topicTitle")
                intent.putExtra("topicTitle",resources.getString(R.string.topic_1))
                intent.putExtra("topicImage", R.drawable.bp_range)
                intent.putExtra("topicPosition",0)
                startActivity(intent)
            }

            saveRecordBtn.setOnClickListener { updateRecord() }
        }
    }

    private fun setValues(systolic:Int, dialostic:Int, pulse:Int, year:String, day:String,
    hour:String, minute:String){
        Log.d(TAG,"Systolic $systolic, Diastilic $dialostic, Pulse $pulse, Year $year," +
                "Day $day, Hour $hour, Minute $minute")
        setSystolic(systolic)
        setDiastolic(dialostic)
        setPulse(pulse)
        setCategory()
        setDateAndTime(year,day,hour,minute)
    }

    private fun cancel(){
        Log.d(TAG, "Going back")
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun deleteRecord(recordId:Int){

        Log.d(TAG, "Delete Record")
        val bundle = Bundle()
        bundle.putInt("recordId", recordId)
        val deleteRecordFragment = DeleteRecord()
        deleteRecordFragment.arguments = bundle
        deleteRecordFragment.show(supportFragmentManager,"Fragment")

    }

    private fun setSystolic(recordSystolic:Int){
        Log.d(TAG, "Setting systolic")
        binding.apply {
            systolicPicker.minValue = 20
            systolicPicker.maxValue = 300
            systolicPicker.value = recordSystolic
            pickedSystolic = systolicPicker.value
            systolicPicker.setOnValueChangedListener(
                NumberPicker.OnValueChangeListener { numberPicker, i, i2 ->
                    pickedSystolic=i2
                    setCategory()
                })
        }
    }

    private fun setDiastolic(recordDiastolic:Int){
        Log.d(TAG, "Setting diastolic")
        binding.apply {
            diastolicPicker.minValue = 20
            diastolicPicker.maxValue = 300
            diastolicPicker.value = recordDiastolic
            pickedDiastolic = diastolicPicker.value
            diastolicPicker.setOnValueChangedListener(
                NumberPicker.OnValueChangeListener { numberPicker, i, i2 ->
                    pickedDiastolic=i2
                    setCategory()
                })
        }
    }

    private fun setPulse(recordPulse:Int){
        Log.d(TAG, "Setting pulse")
        binding.apply {
            pulsePicker.minValue = 20
            pulsePicker.maxValue = 200
            pulsePicker.value = recordPulse
            pickedPulse=pulsePicker.value
            pulsePicker.setOnValueChangedListener(
                NumberPicker.OnValueChangeListener { numberPicker, i, i2 ->
                    pickedPulse=i2
                })
        }
    }

    private fun setDateAndTime(recordYear:String,
                               recordDay:String,
                               recordHour:String,
                               recordMinute:String){

        Log.d(TAG, "Setting date and time")

        val timeString = Calendar.getInstance().time.toString()
        val currentTime = timeString.split(" ")
        val currentYear = currentTime[currentTime.size-1].toInt()

        Log.d(TAG,"Current year is $currentYear")

        binding.apply {

            Log.d(TAG, "Applying picked date and time")

            //year
            yearPicker.minValue = currentYear - 100
            yearPicker.maxValue = currentYear
            yearPicker.value = recordYear.toInt()
            Log.d(TAG, "Year picker value is ${yearPicker.value}")
            pickedYear = yearPicker.value.toString()
            Log.d(TAG, "Picked year is $pickedYear")

            yearPicker.setOnValueChangedListener(
                NumberPicker.OnValueChangeListener { numberPicker, i, i2 ->
                    pickedYear = i2.toString()
                    Log.d(TAG, "Picked year is $pickedYear")
                })

            //days
            dayPicker.displayedValues = resources.getStringArray(R.array.days)
            dayPicker.minValue = 0
            dayPicker.maxValue = 365

            val days = resources.getStringArray(R.array.days)
            for (i in days) {
                if (i.equals(recordDay)) {
                    dayPicker.value = days.indexOf(i)
                    pickedDay = i
                    Log.d(TAG, "Day index is ${days.indexOf(i)}")
                }
            }

            Log.d(TAG, "Picked day is $pickedDay")

            dayPicker.setOnValueChangedListener(
                NumberPicker.OnValueChangeListener { numberPicker, i, i2 ->
                    pickedDay = days[i2]
                    Log.d(TAG, "Picked day is $pickedDay")
                    Log.d(TAG, "Number picker value is ${numberPicker.value}")
                    Log.d(TAG, "Old value is $i")
                    Log.d(TAG, "New value is $i2")
                })

            //hour
            hourPicker.displayedValues = resources.getStringArray(R.array.hours)
            hourPicker.minValue = 0
            hourPicker.maxValue = 23

            val hours = resources.getStringArray(R.array.hours)

            for (i in hours) {
                if (i.equals(recordHour)) {
                    hourPicker.value = i.toInt()
                    Log.d(TAG, "Hour picker value is ${hourPicker.value}")
                }
            }
            pickedHour = hourPicker.value.toString()
            Log.d(TAG, "Picked hour is $pickedHour")

            hourPicker.setOnValueChangedListener(
                NumberPicker.OnValueChangeListener { numberPicker, i, i2 ->
                    pickedHour = hours[i2]
                    Log.d(TAG, "Picked hour is $pickedHour")
                })

            //minutes

            minutePicker.displayedValues = resources.getStringArray(R.array.minutes)
            minutePicker.minValue = 0
            minutePicker.maxValue = 59

            val minutes = resources.getStringArray(R.array.minutes)

            for (i in minutes) {
                if (i.equals(recordMinute)) {
                    minutePicker.value = i.toInt()
                    Log.d(TAG, "Minute picker value is ${minutePicker.value}")
                }
            }
            Log.d(TAG, "Minute picker value is ${minutePicker.value}")

            pickedMinute = minutePicker.value.toString()
            Log.d(TAG, "Picked minute is $pickedMinute")

            minutePicker.setOnValueChangedListener(
                NumberPicker.OnValueChangeListener { hourPicker, i, i2 ->
                    pickedMinute = minutes[i2]
                    Log.d(TAG, "Picked minute is $pickedMinute")
                })
        }
    }

    private fun setCategory(){
        binding.apply {
            Log.d(TAG, "Applying picked category and color")
            if(pickedSystolic<90 || pickedDiastolic<60){

                categoryName.text = resources.getString(R.string.hypotension)
                categoryColor.setBackgroundResource(R.drawable.category_hypotension)

                categoryRangeWords.text = getString(R.string.hypotension_range)

                categoryHypotension.setBackgroundResource(R.drawable.category_hypotension_stroke)

                categoryNormal.setBackgroundResource(R.drawable.category_normal)
                categoryElevated.setBackgroundResource(R.drawable.category_elevated)
                categoryHypertension1.setBackgroundResource(R.drawable.category_hypertension1)
                categoryHypertension2.setBackgroundResource(R.drawable.category_hypertension2)
                categoryHypertensive.setBackgroundResource(R.drawable.category_hypertensive)

                feedbackWords.text = getString(R.string.hypotension_feedback)

            }else if(pickedSystolic in 90..119 && pickedDiastolic in 60..79){

                categoryName.text = resources.getString(R.string.normal)
                categoryColor.setBackgroundResource(R.drawable.category_normal)

                categoryRangeWords.text = getString(R.string.normal_range)

                categoryHypotension.setBackgroundResource(R.drawable.category_hypotension)
                categoryNormal.setBackgroundResource(R.drawable.category_normal_stroke)
                categoryElevated.setBackgroundResource(R.drawable.category_elevated)
                categoryHypertension1.setBackgroundResource(R.drawable.category_hypertension1)
                categoryHypertension2.setBackgroundResource(R.drawable.category_hypertension2)
                categoryHypertensive.setBackgroundResource(R.drawable.category_hypertensive)

                feedbackWords.text = getString(R.string.normal_feedback)

            }else if(pickedSystolic in 120..129 && pickedDiastolic in 60..79){

                categoryName.text = resources.getString(R.string.elevated)
                categoryColor.setBackgroundResource(R.drawable.category_elevated)

                categoryRangeWords.text = getString(R.string.elevated_range)

                categoryHypotension.setBackgroundResource(R.drawable.category_hypotension)
                categoryNormal.setBackgroundResource(R.drawable.category_normal)
                categoryElevated.setBackgroundResource(R.drawable.category_elevated_stroke)
                categoryHypertension1.setBackgroundResource(R.drawable.category_hypertension1)
                categoryHypertension2.setBackgroundResource(R.drawable.category_hypertension2)
                categoryHypertensive.setBackgroundResource(R.drawable.category_hypertensive)

                feedbackWords.text = getString(R.string.elevated_feedback)

            }else if(pickedSystolic in 130..139 || pickedDiastolic in 80..89){

                categoryName.text = resources.getString(R.string.hypertension_1)
                categoryColor.setBackgroundResource(R.drawable.category_hypertension1)

                categoryRangeWords.text = getString(R.string.hypertension1_range)

                categoryHypotension.setBackgroundResource(R.drawable.category_hypotension)
                categoryNormal.setBackgroundResource(R.drawable.category_normal)
                categoryElevated.setBackgroundResource(R.drawable.category_elevated)
                categoryHypertension1.setBackgroundResource(R.drawable.category_hypertension1_stroke)
                categoryHypertension2.setBackgroundResource(R.drawable.category_hypertension2)
                categoryHypertensive.setBackgroundResource(R.drawable.category_hypertensive)

                feedbackWords.text = getString(R.string.hypertension1_feedback)

            }else if(pickedSystolic in 140..180 || pickedDiastolic in 90..120){

                categoryName.text = resources.getString(R.string.hypertension_2)
                categoryColor.setBackgroundResource(R.drawable.category_hypertension2)

                categoryRangeWords.text = getString(R.string.hypertension2_range)

                categoryHypotension.setBackgroundResource(R.drawable.category_hypotension)
                categoryNormal.setBackgroundResource(R.drawable.category_normal)
                categoryElevated.setBackgroundResource(R.drawable.category_elevated)
                categoryHypertension1.setBackgroundResource(R.drawable.category_hypertension1)
                categoryHypertension2.setBackgroundResource(R.drawable.category_hypertension2_stroke)
                categoryHypertensive.setBackgroundResource(R.drawable.category_hypertensive)

                feedbackWords.text = getString(R.string.hypertension2_feedback)

            }else if(pickedSystolic >180 || pickedDiastolic >120){

                categoryName.text =resources.getString(R.string.hypertensive)
                categoryColor.setBackgroundResource(R.drawable.category_hypertensive)

                categoryRangeWords.text = getString(R.string.hypertensive_range)

                categoryHypotension.setBackgroundResource(R.drawable.category_hypotension)
                categoryNormal.setBackgroundResource(R.drawable.category_normal)
                categoryElevated.setBackgroundResource(R.drawable.category_elevated)
                categoryHypertension1.setBackgroundResource(R.drawable.category_hypertension1)
                categoryHypertension2.setBackgroundResource(R.drawable.category_hypertension2)
                categoryHypertensive.setBackgroundResource(R.drawable.category_hypertension2_stroke)

                feedbackWords.text = getString(R.string.hypertensive_feedback)

            }
        }
    }

    private fun updateRecord(){

        Log.d(TAG, "Updating record")
        val timeUpdated = Calendar.getInstance().time.toString()

        Log.d(TAG,"Updating Record id is $recordId, sys is $pickedSystolic, dias is $pickedDiastolic," +
                "pulse is $pickedPulse, year is $pickedYear, day is $pickedDay, hour is $pickedHour, time updated is $timeUpdated ")

        binding.apply {
            recordViewModel.updateRecord(
                Record(
                    recordId,
                    pickedSystolic,
                    pickedDiastolic,
                    pickedPulse,
                    pickedYear,
                    pickedDay,
                    pickedHour,
                    pickedMinute,
                    timeUpdated
                )
            )
        }
        finish()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}