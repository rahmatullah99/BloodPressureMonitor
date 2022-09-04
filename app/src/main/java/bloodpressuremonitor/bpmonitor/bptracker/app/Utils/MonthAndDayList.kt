package bloodpressuremonitor.bpmonitor.bptracker.app.Utils

class MonthAndDayList {

    fun getMonthAndDayList():ArrayList<String>{

        val monthAndDayList=ArrayList<String>()

        for (m in 1..12){
            when(m){
                1->{
                    for (d in 1..31){
                        monthAndDayList.add("Jan $d")
                    }
                }
                2->{
                    for (d in 1..29){
                        monthAndDayList.add("Feb $d")
                    }
                }
                3->{
                    for (d in 1..31){
                        monthAndDayList.add("Mar $d")
                    }
                }
                4->{
                    for (d in 1..30){
                        monthAndDayList.add("Apr $d")
                    }
                }
                5->{
                    for (d in 1..31){
                        monthAndDayList.add("May $d")
                    }
                }
                6->{
                    for (d in 1..30){
                        monthAndDayList.add("June $d")
                    }
                }
                7-> {
                    for (d in 1..31) {
                        monthAndDayList.add("July $d")
                    }
                }
                8-> {
                    for (d in 1..31) {
                        monthAndDayList.add("Aug $d")
                    }
                }
                9-> {
                    for (d in 1..30) {
                        monthAndDayList.add("Sep $d")
                    }
                }
                10->{
                    for (d in 1..31) {
                        monthAndDayList.add("Oct $d")
                    }
                }
                11->{
                    for (d in 1..30) {
                        monthAndDayList.add("Nov $d")
                    }
                }
                12->{
                    for (d in 1..31) {
                        monthAndDayList.add("Sep $d")
                    }
                }
                else->{
                    for (d in 1..31) {
                        monthAndDayList.add("Mon $d")
                    }
                }
            }
        }

        return monthAndDayList

   }

}

//day
/* when(currentMonth){
     1->currentMonthString=getString(R.string.jan)
     2->currentMonthString=getString(R.string.feb)
     3->currentMonthString=getString(R.string.mar)
     4->currentMonthString=getString(R.string.apr)
     5->currentMonthString=getString(R.string.may)
     6->currentMonthString=getString(R.string.june)
     7->currentMonthString=getString(R.string.july)
     8->currentMonthString=getString(R.string.aug)
     9->currentMonthString=getString(R.string.sep)
     10->currentMonthString=getString(R.string.oct)
     11->currentMonthString=getString(R.string.nov)
     12->currentMonthString=getString(R.string.dec)
     else->currentMonthString=getString(R.string.err)
 }*/