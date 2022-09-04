package bloodpressuremonitor.bpmonitor.bptracker.app.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import bloodpressuremonitor.bpmonitor.bptracker.app.Database.RecordDatabase
import bloodpressuremonitor.bpmonitor.bptracker.app.MainActivity
import bloodpressuremonitor.bpmonitor.bptracker.app.Models.Record
import bloodpressuremonitor.bpmonitor.bptracker.app.R
import bloodpressuremonitor.bpmonitor.bptracker.app.ViewModel.RecordViewModel
import bloodpressuremonitor.bpmonitor.bptracker.app.ViewModel.RecordViewModelFactory
import bloodpressuremonitor.bpmonitor.bptracker.app.databinding.ActivityAddEditRecordBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddRecord : AppCompatActivity() {

    val TAG  = "Add Record"

    private lateinit var binding: ActivityAddEditRecordBinding
    private lateinit var recordViewModel: RecordViewModel
    private var recordsList = ArrayList<Record>()

    //Record
    var pickedSystolic:Int = 0
    var pickedDiastolic:Int = 0
    var pickedPulse:Int = 0
    var pickedYear:String = ""
    var pickedDay:String = ""
    var pickedHour:String = ""
    var pickedMinute:String = ""

    //record check
    var canAddRecord=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "In on create")

        window.navigationBarColor = resources.getColor(R.color.grey)

        val dao = RecordDatabase.getInstance(application).recordDAO
        val factory = RecordViewModelFactory(dao)
        recordViewModel = ViewModelProvider(this, factory)[RecordViewModel::class.java]

        setSystolic()
        setDiastolic()
        setPulse()
        setCategory()
        setDateAndTime()

        binding.apply {
            Log.d(TAG, "Applying binding")
            backBtn.setOnClickListener { cancel() }
            deleteRecordButton.isVisible = false

            helpBtn.setOnClickListener {
                val intent = Intent(this@AddRecord, KnowledgeTopic::class.java)
                intent.getStringExtra("topicTitle")
                intent.putExtra("topicTitle",resources.getString(R.string.topic_1))
                intent.putExtra("topicImage", R.drawable.bp_range)
                intent.putExtra("topicPosition",0)
                startActivity(intent)
            }

            saveRecordBtn.setOnClickListener { checkRecord() }
        }
    }

    private fun cancel(){
        finish()
    }

    private fun setSystolic(){
        binding.apply {
            Log.d(TAG, "Applying systolic max, min and picked one")
            systolicPicker.minValue = 20
            systolicPicker.maxValue = 300
            pickedSystolic = systolicPicker.value
            systolicPicker.setOnValueChangedListener(
                NumberPicker.OnValueChangeListener { numberPicker, i, i2 ->
                    pickedSystolic=i2
                    Log.d(TAG,"Picked systolic is $pickedSystolic")
                    setCategory()
                })
        }
    }

    private fun setDiastolic(){
        binding.apply {
            Log.d(TAG, "Applying diastolic max, min and picked one")
            diastolicPicker.minValue = 20
            diastolicPicker.maxValue = 300
            pickedDiastolic = diastolicPicker.value
            diastolicPicker.setOnValueChangedListener(
                NumberPicker.OnValueChangeListener { numberPicker, i, i2 ->
                    pickedDiastolic=i2
                    Log.d(TAG,"Picked diastolic is $pickedDiastolic")
                    setCategory()
                })
        }
    }

    private fun setPulse(){
        Log.d(TAG, "Applying pulse max, min and picked one")
        binding.apply {
            pulsePicker.minValue = 20
            pulsePicker.maxValue = 200
            //pulsePicker.value=70
            pickedPulse = pulsePicker.value
            pulsePicker.setOnValueChangedListener(
                NumberPicker.OnValueChangeListener { numberPicker, i, i2 ->
                    pickedPulse=i2
                    Log.d(TAG,"Picked pulse is $pickedPulse")
                })
        }
    }

    private fun setDateAndTime(){

        Log.d(TAG, "Setting date and time")

        val timeString = Calendar.getInstance().time.toString()
        val currentTime = timeString.split(" ")
        val cTime = currentTime[2].toInt()
        val currentDay = "${currentTime[1]} $cTime"
        Log.d(TAG, "Current day is $currentDay")
        val time3 = currentTime[3]
        val time = time3.split(":")
        val currentHour = time[0].toInt()
        val currentMinutes = time[1].toInt()
        val currentYear = currentTime[currentTime.size-1].toInt()

        Log.d(TAG,"Current day is $currentDay")
        Log.d(TAG,"Current hour is $currentHour")
        Log.d(TAG,"Current minutes is $currentMinutes")
        Log.d(TAG,"Current year is $currentYear")

        binding.apply {

            Log.d(TAG, "Applying picked date and time")

            //year
            yearPicker.minValue = currentYear-100
            yearPicker.maxValue = currentYear
            yearPicker.value = currentYear
            pickedYear = yearPicker.value.toString()
            Log.d(TAG, "Picked year is $pickedYear")

            yearPicker.setOnValueChangedListener(
                NumberPicker.OnValueChangeListener { numberPicker, i, i2 ->
                    pickedYear=i2.toString()
                    Log.d(TAG, "Picked year is $pickedYear") })

            //days
            dayPicker.displayedValues=resources.getStringArray(R.array.days)
            dayPicker.minValue = 0
            dayPicker.maxValue = 365

            val days = resources.getStringArray(R.array.days)

            for (i in days){
                Log.d(TAG, "i value is $i")
                Log.d(TAG, "currentday value is $currentDay")
                if(i.equals(currentDay)){
                    dayPicker.value=days.indexOf(i)
                    pickedDay=i
                    Log.d(TAG,"Day index is ${days.indexOf(i)}")
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
            Log.d(TAG, "In hour pick")
            hourPicker.minValue = 0
            hourPicker.maxValue = 23
            hourPicker.value=currentHour
            pickedHour = hourPicker.value.toString()
            Log.d(TAG, "Hour picker value is ${hourPicker.value}")
            Log.d(TAG, "Picked hour is $pickedHour")

            hourPicker.setOnValueChangedListener(
                NumberPicker.OnValueChangeListener { numberPicker, i, i2 ->
                    pickedHour = i2.toString()
                    Log.d(TAG, "Picked hour is $pickedHour")
                })

            //minutes
            minutePicker.displayedValues=resources.getStringArray(R.array.minutes)
            minutePicker.minValue = 0
            minutePicker.maxValue = 59
            minutePicker.value=currentMinutes

            val minutes = resources.getStringArray(R.array.minutes)

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

    private fun checkRecord(){
        recordsList.clear()
//        binding.apply { recordViewModel.records.observe(this@AddRecord){
//            for(i in it){
//                if(((i.year) == pickedYear)
//                    &&((i.day) == pickedDay)
//                    &&((i.hour) == pickedHour)
//                    &&((i.minutes) == pickedMinute)){
//                    canAddRecord=false
//                }
//            }
//        }
//    }
        if(pickedDiastolic>=pickedSystolic){
            canAddRecord=false
            Toast.makeText(applicationContext, getString(R.string.dias_greater), Toast.LENGTH_LONG).show()
        }else{
            canAddRecord=true
        }
        addRecord()
    }

    private fun addRecord(){
        val timeAdded = Calendar.getInstance().time.toString()
        if(canAddRecord){
            recordViewModel.addRecord(
                Record(
                    0,
                    pickedSystolic,
                    pickedDiastolic,
                    pickedPulse,
                    pickedYear,
                    pickedDay,
                    pickedHour,
                    pickedMinute,
                    timeAdded
                )
            )
            Log.d(TAG, "Picked day is $pickedDay")

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}