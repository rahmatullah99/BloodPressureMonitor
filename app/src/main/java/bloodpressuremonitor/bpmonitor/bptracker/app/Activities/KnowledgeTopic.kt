package bloodpressuremonitor.bpmonitor.bptracker.app.Activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import bloodpressuremonitor.bpmonitor.bptracker.app.Adapters.KnowledgeSectionRVAdapter
import bloodpressuremonitor.bpmonitor.bptracker.app.Models.Knowledge
import bloodpressuremonitor.bpmonitor.bptracker.app.Models.KnowledgeSection
import bloodpressuremonitor.bpmonitor.bptracker.app.R
import bloodpressuremonitor.bpmonitor.bptracker.app.databinding.ActivityKnowledgeTopicBinding
import bloodpressuremonitor.bpmonitor.bptracker.app.databinding.KnowledgeTopic1Binding
import com.facebook.ads.Ad
import com.facebook.ads.AdError
import com.facebook.ads.InterstitialAd
import com.facebook.ads.InterstitialAdListener
import java.time.Duration

class KnowledgeTopic : AppCompatActivity() {

    val TAG = "Knowledge Topic"

    private var interstitialAd: InterstitialAd? = null

    private lateinit var adapter: KnowledgeSectionRVAdapter
    private lateinit var binding: ActivityKnowledgeTopicBinding
    private var knowledgeSectionList = ArrayList<KnowledgeSection>()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "In on create view")
        binding = ActivityKnowledgeTopicBinding.inflate(layoutInflater)
        setContentView(binding.root)
        interstitialAd = InterstitialAd(this, "1406327383213198_1406342489878354");

        val intent = intent
        val topicTitle = intent.getStringExtra("topicTitle")
        val topicImage = intent.getIntExtra("topicImage",0)
        val topicPosition = intent.getIntExtra("topicPosition",0)

        Log.d(TAG, "Topic title is $topicTitle, topic image is $topicImage and topic position is" +
                "$topicPosition")

        binding.backBtn.setOnClickListener {
            showAdWithDelay()
            finish() }
        getknowledgeSectionList(topicPosition)
        initRecyclerView()

        binding.apply {
            Log.d(TAG, "Applying binding")
            if(topicImage!=0){
                Log.d(TAG,"Topic image is not 0")
                knowledgeImage.background=resources.getDrawable(topicImage)
            }

            if(topicTitle!=null){
                Log.d(TAG,"Topic title is not null, setting topic title")
                knowledgeTopicTitle.text=topicTitle
            }else{
                Log.d(TAG,"Topic title is null")
                knowledgeTopicTitle.text=".."
            }
        }
    }

    private fun initRecyclerView(){
        Log.d(TAG, "Init recyclerview")
        binding.knowledgeSectionRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter=KnowledgeSectionRVAdapter()
        binding.knowledgeSectionRecyclerView.adapter = adapter
        adapter.setList(knowledgeSectionList)
        adapter.notifyDataSetChanged()
    }

    private fun getknowledgeSectionList(position:Int){

        Log.d(TAG, "Displaying knowledge")
        Log.d(TAG,"Adding knowledge for topic position $position")
        knowledgeSectionList.clear()
        when (position){
            0->{
                Log.d(TAG,"Adding knowledge for topic position 0")
                knowledgeSectionList.add(
                KnowledgeSection(
                    "",
                    resources.getString(R.string.paragraph_1),
                    false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_2),
                        resources.getString(R.string.paragraph_2),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_3),
                        resources.getString(R.string.paragraph_3),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_4),
                        resources.getString(R.string.paragraph_4),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_5),
                        resources.getString(R.string.paragraph_5),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_6),
                        resources.getString(R.string.paragraph_6),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_7),
                        resources.getString(R.string.paragraph_7),
                        false))

            }
            1->{
                Log.d(TAG,"Adding knowledge for topic position 1")
                knowledgeSectionList.add(
                    KnowledgeSection(
                        "",
                        resources.getString(R.string.paragraph_8),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_9),
                        resources.getString(R.string.paragraph_9),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_10),
                        resources.getString(R.string.paragraph_10),
                        false))

            }
            2->{
                Log.d(TAG,"Adding knowledge for topic position 2")
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_11),
                        resources.getString(R.string.paragraph_11),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_12),
                        resources.getString(R.string.paragraph_12),
                        false))

            }
            3->{
                Log.d(TAG,"Adding knowledge for topic position 3")
                knowledgeSectionList.add(
                    KnowledgeSection(
                        "",
                        resources.getString(R.string.paragraph_13),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_14),
                        resources.getString(R.string.paragraph_14),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_15),
                        resources.getString(R.string.paragraph_15),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_16),
                        resources.getString(R.string.paragraph_16),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_17),
                        resources.getString(R.string.paragraph_17),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_18),
                        resources.getString(R.string.paragraph_18),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_19),
                        resources.getString(R.string.paragraph_19),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_20),
                        resources.getString(R.string.paragraph_20),
                        false))

            }
            4->{
                Log.d(TAG,"Adding knowledge for topic position 3")
                knowledgeSectionList.add(
                    KnowledgeSection(
                        "",
                        resources.getString(R.string.paragraph_21),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_22),
                        resources.getString(R.string.paragraph_22),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_23),
                        resources.getString(R.string.paragraph_23),
                        false))
            }
            5->{
                Log.d(TAG,"Adding knowledge for topic position 5")
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_24),
                        resources.getString(R.string.paragraph_24),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_25),
                        resources.getString(R.string.paragraph_25),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_26),
                        resources.getString(R.string.paragraph_26),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_27),
                        resources.getString(R.string.paragraph_27),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_28),
                        resources.getString(R.string.paragraph_28),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_29),
                        resources.getString(R.string.paragraph_29),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_89),
                        resources.getString(R.string.paragraph_89),
                        false))

            }
            6->{
                Log.d(TAG,"Adding knowledge for topic position 3")
                knowledgeSectionList.add(
                    KnowledgeSection(
                        "",
                        resources.getString(R.string.paragraph_30),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_31),
                        resources.getString(R.string.paragraph_31),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_32),
                        resources.getString(R.string.paragraph_32),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_33),
                        resources.getString(R.string.paragraph_33),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_34),
                        resources.getString(R.string.paragraph_34),
                        false))
            }
            7->{
                Log.d(TAG,"Adding knowledge for topic position 7")
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_35),
                        resources.getString(R.string.paragraph_35),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_36),
                        resources.getString(R.string.paragraph_36),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.sub_37),
                        resources.getString(R.string.paragraph_37),
                        true))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.sub_38),
                        resources.getString(R.string.paragraph_38),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.sub_39),
                        resources.getString(R.string.paragraph_39),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_40),
                        resources.getString(R.string.paragraph_40),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_41),
                        resources.getString(R.string.paragraph_41),
                        false))

            }
            8->{
                Log.d(TAG,"Adding knowledge for topic position 8")
                knowledgeSectionList.add(
                    KnowledgeSection(
                        "",
                        resources.getString(R.string.paragraph_42),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_43),
                        resources.getString(R.string.paragraph_43),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.sub_44),
                        resources.getString(R.string.paragraph_44),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_45),
                        resources.getString(R.string.paragraph_45),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.sub_46),
                        resources.getString(R.string.paragraph_46),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_47),
                        resources.getString(R.string.paragraph_47),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.sub_48),
                        resources.getString(R.string.paragraph_48),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_49),
                        resources.getString(R.string.paragraph_49),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.sub_50),
                        resources.getString(R.string.paragraph_50),
                        false))

            }
            9->{
                Log.d(TAG,"Adding knowledge for topic position 9")
                knowledgeSectionList.add(
                    KnowledgeSection(
                        "",
                        resources.getString(R.string.paragraph_51),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_52),
                        resources.getString(R.string.paragraph_52),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_53),
                        resources.getString(R.string.paragraph_53),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_54),
                        resources.getString(R.string.paragraph_54),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_55),
                        resources.getString(R.string.paragraph_55),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_56),
                        resources.getString(R.string.paragraph_56),
                        false))

            }
            10->{
                Log.d(TAG,"Adding knowledge for topic position 10")
                knowledgeSectionList.add(
                    KnowledgeSection(
                        "",
                        resources.getString(R.string.paragraph_57),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_58),
                        resources.getString(R.string.paragraph_58),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.sub_59),
                        resources.getString(R.string.paragraph_59),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_60),
                        resources.getString(R.string.paragraph_60),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.sub_61),
                        resources.getString(R.string.paragraph_61),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.sub_62),
                        resources.getString(R.string.paragraph_62),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_63),
                        resources.getString(R.string.paragraph_63),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.sub_64),
                        resources.getString(R.string.paragraph_64),
                        false))

            }
            11->{
                Log.d(TAG,"Adding knowledge for topic position 11")
                knowledgeSectionList.add(
                    KnowledgeSection("",
                        resources.getString(R.string.paragraph_65),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_66),
                        resources.getString(R.string.paragraph_66),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_67),
                        resources.getString(R.string.paragraph_67),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_68),
                        resources.getString(R.string.paragraph_68),
                        false))

            }
            12->{
                Log.d(TAG,"Adding knowledge for topic position 12")
                knowledgeSectionList.add(
                    KnowledgeSection(
                        "",
                        resources.getString(R.string.paragraph_69),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_70),
                        resources.getString(R.string.paragraph_70),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.sub_71),
                        resources.getString(R.string.paragraph_71),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.sub_72),
                        resources.getString(R.string.paragraph_72),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_73),
                        resources.getString(R.string.paragraph_73),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.sub_74),
                        resources.getString(R.string.paragraph_74),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.sub_75),
                        resources.getString(R.string.paragraph_74),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_76),
                        resources.getString(R.string.paragraph_74),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.sub_77),
                        resources.getString(R.string.paragraph_74),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.sub_78),
                        resources.getString(R.string.paragraph_78),
                        false))
            }
            13->{
                Log.d(TAG,"Adding knowledge for topic position 13")
                knowledgeSectionList.add(
                    KnowledgeSection(
                        "",
                        resources.getString(R.string.paragraph_79),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_80),
                        resources.getString(R.string.paragraph_80),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_81),
                        resources.getString(R.string.paragraph_81),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_82),
                        resources.getString(R.string.paragraph_82),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_83),
                        resources.getString(R.string.paragraph_83),
                        false))

            }
            14->{
                Log.d(TAG,"Adding knowledge for topic position 14")
                knowledgeSectionList.add(
                    KnowledgeSection(
                        "",
                        resources.getString(R.string.paragraph_84),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_85),
                        resources.getString(R.string.paragraph_85),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_86),
                        resources.getString(R.string.paragraph_86),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_87),
                        resources.getString(R.string.paragraph_87),
                        false))
                knowledgeSectionList.add(
                    KnowledgeSection(
                        resources.getString(R.string.main_88),
                        resources.getString(R.string.paragraph_88),
                        false))

            }
        }

    }

    private fun showInterstialAd(){
        val interstitialAdListener = object : InterstitialAdListener {
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
                showInterstialAd()
            }
            // Check if ad is already expired or invalidated, and do not show ad if that is the case. You will not get paid to show an invalidated ad.
            if (interstitialAd!!.isAdInvalidated) {

            }
            // Show the ad
            interstitialAd!!.show()
        }, 1000 * 60 * 5)
    }

    override fun onDestroy() {
        if (interstitialAd != null) {
            interstitialAd!!.destroy()
        }
        super.onDestroy()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        showAdWithDelay()
        finish()
    }

}