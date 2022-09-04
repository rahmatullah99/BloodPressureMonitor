package bloodpressuremonitor.bpmonitor.bptracker.app.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import bloodpressuremonitor.bpmonitor.bptracker.app.Activities.AddRecord
import bloodpressuremonitor.bpmonitor.bptracker.app.Activities.EditRecord
import bloodpressuremonitor.bpmonitor.bptracker.app.Activities.KnowledgeTopic
import bloodpressuremonitor.bpmonitor.bptracker.app.Activities.Records
import bloodpressuremonitor.bpmonitor.bptracker.app.Adapters.RecordRVAdapter
import bloodpressuremonitor.bpmonitor.bptracker.app.Database.RecordDatabase
import bloodpressuremonitor.bpmonitor.bptracker.app.Models.Record
import bloodpressuremonitor.bpmonitor.bptracker.app.R
import bloodpressuremonitor.bpmonitor.bptracker.app.ViewModel.RecordViewModel
import bloodpressuremonitor.bpmonitor.bptracker.app.ViewModel.RecordViewModelFactory
import bloodpressuremonitor.bpmonitor.bptracker.app.databinding.FragmentNestedTrackerBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class NestedTracker : Fragment() {

    private lateinit var binding:FragmentNestedTrackerBinding
    private lateinit var recordRvAdapter: RecordRVAdapter
    private lateinit var recordViewModel: RecordViewModel
    private var recordList = ArrayList<Record>()
    private var fewRecordList = ArrayList<Record>()
    private var dateList = ArrayList<String>()

    //Bp Cards
    private var filterTimeNo=1

    private lateinit var latestRecord:Record

    //Bar chart
    private var recordBarList = ArrayList<BarEntry>()
    private val labelNames = ArrayList<String>()

    val TAG ="Nested Tracker Fragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        Log.d(TAG, "In on create view")
        binding = FragmentNestedTrackerBinding.inflate(inflater, container, false)

        getRecordsData()

        return binding.root
    }

    private fun checkRecordList(){

        val unlockStats: Fragment = UnlockStats()
        lifecycleScope.launchWhenResumed {
            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            if(recordList.size>0)
            { for (i in recordList) { dateList.add(i.day) }

                Log.d(TAG, "Record list is initialized, now removing unlock stats")

                transaction.remove(unlockStats)
                transaction.commit()
                Log.d(TAG, "Unlock stats removed")

                initOnClicks()
                getBpCardsInfo()
                setBarChart()
                initRecyclerView()

            }else{

                Log.d(TAG, "Record list is not initialized, now adding unlock stats")
                transaction.add(R.id.fragment_container,unlockStats)
                transaction.commit()
            }
        }



    }

    private fun initOnClicks() {
        binding.apply {

            seeAllRecordsBtn.setOnClickListener {
                val intent = Intent(activity, Records::class.java)
                startActivity(intent) }

            addRecordButton.setOnClickListener {
                val intent = Intent(activity, AddRecord::class.java)
                startActivity(intent) }

            topic1Card.setOnClickListener {
                val intent = Intent(activity, KnowledgeTopic::class.java)
                intent.getStringExtra("topicTitle")
                intent.putExtra("topicTitle",resources.getString(R.string.topic_1))
                intent.putExtra("topicImage", R.drawable.bp_range)
                intent.putExtra("topicPosition",0)
                startActivity(intent) }

            topic2Card.setOnClickListener {
                val intent = Intent(activity, KnowledgeTopic::class.java)
                intent.getStringExtra("topicTitle")
                intent.putExtra("topicTitle",resources.getString(R.string.topic_2))
                intent.putExtra("topicImage", R.drawable.learn_bp)
                intent.putExtra("topicPosition",1)
                startActivity(intent) }

            topic3Card.setOnClickListener {
                val intent = Intent(activity, KnowledgeTopic::class.java)
                intent.getStringExtra("topicTitle")
                intent.putExtra("topicTitle",resources.getString(R.string.topic_3))
                intent.putExtra("topicImage", R.drawable.bp_type)
                intent.putExtra("topicPosition",2)
                startActivity(intent) }

            topic9Card.setOnClickListener {
                val intent = Intent(activity, KnowledgeTopic::class.java)
                intent.getStringExtra("topicTitle")
                intent.putExtra("topicTitle",resources.getString(R.string.topic_10))
                intent.putExtra("topicImage", R.drawable.healthy_lifestyle)
                intent.putExtra("topicPosition",9)
                startActivity(intent) }
        }
    }

    private fun getBpCardsInfo() {
        Log.d(TAG, "In get bp cards info")

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.HOUR_OF_DAY, -24)

        binding.apply {
            latestRecord = recordList.last()
            bpCardFilterTime.text=resources.getString(R.string.latest)
            bpCardSystolic.text=latestRecord.systolic.toString()
            bpCardDiastolic.text=latestRecord.diastolic.toString()
            bpCardPulse.text="${latestRecord.pulse} ${resources.getString(R.string.bpm)}"

            bpCardFilterTime.setOnClickListener {

                when(filterTimeNo){
                    1->{
                        //Max
                        filterTimeNo=2
                        bpCardFilterTime.text=resources.getString(R.string.max)
                        var maxSys=0
                        var maxDias=0
                        var maxPulse=0
                        for(i in recordList){
                            if(i.systolic>maxSys){
                                maxSys=i.systolic
                            }
                            if(i.diastolic>maxDias){
                                maxDias=i.diastolic
                            }
                            if(i.pulse>maxPulse){
                                maxPulse=i.pulse
                            }
                        }
                        bpCardSystolic.text=maxSys.toString()
                        bpCardDiastolic.text=maxDias.toString()
                        bpCardPulse.text="$maxPulse ${resources.getString(R.string.bpm)}"
                    }
                    2->{
                        //Min
                        filterTimeNo=3
                        bpCardFilterTime.text=resources.getString(R.string.min)
                        var minSys=300
                        var minDias=300
                        var minPulse=200
                        for(i in recordList){
                            if(i.systolic<minSys){
                                minSys=i.systolic
                            }
                            if(i.diastolic<minDias){
                                minDias=i.diastolic
                            }
                            if(i.pulse<minPulse){
                                minPulse=i.pulse
                            }
                        }
                        bpCardSystolic.text=minSys.toString()
                        bpCardDiastolic.text=minDias.toString()
                        bpCardPulse.text="$minPulse ${resources.getString(R.string.bpm)}"
                    }
                    3->{
                        //24HourAverage
                        filterTimeNo=4

                        val dayRecordList = ArrayList<Record>()
                        var systolicTotal = 0
                        var diastolicTotal = 0
                        var pulseTotal = 0

                        for(i in recordList){

                            val timeAddedString1 = i.timeAdded
                            Log.d(TAG, "Time added strng is $timeAddedString1")
                            val timeAddedString = timeAddedString1.split(" ")
                            val timeAddedMonth = timeAddedString[1]
                            val timeAddedDate = timeAddedString[2]
                            val timeAddedHours = timeAddedString[3]
                            val timeAddedYear = timeAddedString[timeAddedString.size-1]
                            val timeAdded1 = "$timeAddedMonth $timeAddedDate $timeAddedHours $timeAddedYear"

                            val format = SimpleDateFormat("MMM dd HH:mm:ss yyyy")
                            try {
                                val timeAdded = format.parse(timeAdded1)
                                val timeDayAgo = calendar.time
                                Log.d(TAG,"Time added is ${timeAdded?.time} and time day ago is ${timeDayAgo?.time}")
                                if (timeAdded != null) {
                                    if(timeAdded.time>timeDayAgo.time){
                                        dayRecordList.add(i)
                                        systolicTotal+=i.systolic
                                        diastolicTotal+=i.diastolic
                                        pulseTotal+=i.pulse
                                    }
                                }else{
                                    Log.d(TAG,"Time added is null")
                                }
                            } catch (e: ParseException) {
                                e.printStackTrace()
                            }
                        }

                        bpCardFilterTime.text=resources.getString(R.string.twofour_hour_average)
                        bpCardSystolic.text=(systolicTotal/dayRecordList.size).toString()
                        bpCardDiastolic.text=(diastolicTotal/dayRecordList.size).toString()
                        bpCardPulse.text="${(pulseTotal/dayRecordList.size)} ${resources.getString(R.string.bpm)}"
                    }
                    4->{
                        //Average
                        filterTimeNo=5

                        var systolicTotal = 0
                        var diastolicTotal = 0
                        var pulseTotal = 0

                        for(i in recordList){
                            systolicTotal+=i.systolic
                            diastolicTotal+=i.diastolic
                            pulseTotal+=i.pulse
                        }

                        bpCardFilterTime.text=resources.getString(R.string.average)
                        bpCardSystolic.text=(systolicTotal/recordList.size).toString()
                        bpCardDiastolic.text=(diastolicTotal/recordList.size).toString()
                        bpCardPulse.text="${(pulseTotal/recordList.size)} ${resources.getString(R.string.bpm)}"
                    }
                    5->{
                        //Latest
                        filterTimeNo=1

                        latestRecord = recordList.last()
                        bpCardFilterTime.text=resources.getString(R.string.latest)
                        bpCardSystolic.text=latestRecord.systolic.toString()
                        bpCardDiastolic.text=latestRecord.diastolic.toString()
                        bpCardPulse.text="${latestRecord.pulse} ${resources.getString(R.string.bpm)}"
                    }
                }
            }
        }
    }

    private fun getRecordsData() {
        Log.d(TAG, "Getting records data")
        val dao = RecordDatabase.getInstance(requireActivity()).recordDAO
        val factory = RecordViewModelFactory(dao)
        recordViewModel = ViewModelProvider(this, factory)[RecordViewModel::class.java]
        recordViewModel.records.observe(requireActivity(), Observer {
            recordList.clear()
            for (i in it){
                Log.d(TAG, "record is $i")
                recordList.add(i)}
            Log.d(TAG, "record size is ${recordList.size}")
            checkRecordList()
        })
    }

    private fun setBarChart(){
        Log.d(TAG, "Setting bar chart")
        binding.apply {

            recordBarList.clear()
            labelNames.clear()

            getBarEntries()
            getLabelNames()

            val barDataSet = BarDataSet(recordBarList,"Systolic")
            val bardata = BarData(barDataSet)
            recordBarChart.data = bardata
            val xAxis: XAxis = recordBarChart.xAxis

            xAxis.valueFormatter = IndexAxisValueFormatter(labelNames)
            xAxis.valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return labelNames[value.toInt()]
                }
            }

            xAxis.setDrawGridLines(false)
            xAxis.setDrawAxisLine(false)
            xAxis.granularity=1f
            xAxis.labelCount=labelNames.size

            recordBarChart.xAxis.textColor=resources.getColor(R.color.white)
            recordBarChart.axisLeft.textColor = resources.getColor(R.color.white)
            recordBarChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
            recordBarChart.setVisibleXRangeMaximum(4f)
            recordBarChart.description.text=""

            barDataSet.color=resources.getColor(R.color.greyLight)
            recordBarChart.setNoDataTextColor(R.color.white)
            recordBarChart.animateY(1000)
            recordBarChart.invalidate()
        }
    }

    private fun getBarEntries(): MutableList<BarEntry> {
        Log.d(TAG, "Getting records data")

        sortDates()

        Log.d(TAG,"Record after sort is $recordList")
        for (i in recordList) {
            recordBarList.add(BarEntry(recordList.indexOf(i).toFloat(), i.systolic.toFloat()))
        }
        return recordBarList

    }

    private fun sortDates() {
//        if (Build.VERSION.SDK_INT >= O) {
//            recordList.sortBy {
//                LocalDate.parse(
//                    "${it.day} ${it.hour}:${it.minutes} ${it.year}",
//                    DateTimeFormatter.ISO_INSTANT
//                ) }
//        }else{
//
//
//
//        }

        val df = SimpleDateFormat("MMM dd HH:mm yyyy")
        try {
            recordList.sortBy {
                df.parse("${it.day} ${it.hour}:${it.minutes} ${it.year}") }
        } catch (e: ParseException) {
            e.printStackTrace()
        }

    }

    private fun getLabelNames(){
        for (i in recordList) {
            Log.d(TAG, "Day - ${i.day}")
            labelNames.add("${i.day} ${i.year}")
        }
    }

    private fun initRecyclerView(){
        Log.d(TAG,"init recycler view")
        fewRecordList.clear()
        for(i in recordList){ if(fewRecordList.size<4){ fewRecordList.add(i) } }
        binding.recordsRv.layoutManager = LinearLayoutManager(activity)
        recordRvAdapter = RecordRVAdapter({ selectedRecord: Int -> editTapped(selectedRecord)},
            requireActivity()
        )
        Log.d(TAG,"Few record list $fewRecordList")
        binding.recordsRv.adapter = recordRvAdapter
        recordRvAdapter.setRecordList(fewRecordList)
        recordRvAdapter.notifyDataSetChanged()
    }

    private fun editTapped(recordId:Int){
        Log.d(TAG,"edit tapped")
        Log.d(TAG, "record id is $recordId")
        val intent = Intent(activity, EditRecord::class.java)
        intent.putExtra("recordId",recordId)
        startActivity(intent)
    }

  }