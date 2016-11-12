def maxCosts = new HashMap()
def minCosts = new HashMap()
def totalCosts = new HashMap()
def times_map = new HashMap()
def averageCosts = new HashMap()

def s_pattern = ~/\d+s/
def ms_pattern = ~/\d+ms/
def sql_pattern = ~/sql => .+/

def logPaht = "C:\\logs\\ggj\\performance_dao.log"
new File(logPaht).eachLine { line ->
    def time_ms = 0
    def ms_matcher = ms_pattern.matcher(line)
    if(ms_matcher.count > 0){
        time_ms = time_ms + ms_matcher[0].toString().replace("ms","").toInteger()
        //println ms_matcher[0].toString().replace("ms","");
    }

    def s_matcher = s_pattern.matcher(line)
    if(s_matcher.count > 0){
        time_ms = time_ms + s_matcher[0].toString().replace("s","").toInteger()*1000
        //println s_matcher[0].toString().replace("s","")
    }

    def sql_matcher = sql_pattern.matcher(line)
    def sql = sql_matcher[0].toString()

    def times = times_map.get(sql)
    if(times == null){
        times = 0
    }
    times_map.put(sql,times + 1)

    def max = maxCosts.get(sql)
    if(max == null){
        max = 0
    }
    if(time_ms > max){
        maxCosts.put(sql,time_ms)
    }

    def min = minCosts.get(sql)
    if(min == null){
        min = Integer.MAX_VALUE
    }
    if(time_ms < min){
        minCosts.put(sql,time_ms)
    }

    def total = totalCosts.get(sql)
    if(total == null){
        total = 0
    }
    total = total + time_ms
    totalCosts.put(sql,total)

//    def matcher = pattern.matcher(line)
//    println matcher[0].toString().replace("cost ","").replace(" ,","")
//    def count = matcher.getCount()
//    println "Matches = ${count}"
//    for(i in 0..<count) {
//        println matcher[i]
//    }
//    println "${s}"
}

totalCosts.each { it ->
    def average = it.value / times_map.get(it.key)
    averageCosts.put(it.key,average)
}

//maxCosts = sortMap(maxCosts)
//minCosts = sortMap(minCosts)
averageCosts = sortMap(averageCosts)

averageCosts.each { it ->
    println "average = ${it.value}ms , max = ${maxCosts.get(it.key)}ms , min = ${minCosts.get(it.key)}ms => ${it.key}"
}


public static Map sortMap(Map oldMap) {
    ArrayList<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(oldMap.entrySet());
    Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {

        @Override
        public int compare(Map.Entry<String, Integer> arg0,
                           Map.Entry<java.lang.String, Integer> arg1) {
            return arg1.getValue() - arg0.getValue();
        }
    });
    Map newMap = new LinkedHashMap();
    for (int i = 0; i < list.size(); i++) {
        newMap.put(list.get(i).getKey(), list.get(i).getValue());
    }
    return newMap;
}