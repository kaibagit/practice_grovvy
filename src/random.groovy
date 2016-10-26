def prefixArr = [133,153,180,181,189,130,131,132,145,155,156,185,186,134,135,136,137,138,139,147,150,151,152,157,158,159,182,183,184,187,188]

def random = new Random()
1.upto(50){
    def telphone = prefixArr[random.nextInt(prefixArr.size())] + "****" + random.nextInt(10) + random.nextInt(10) + random.nextInt(10) + random.nextInt(10)
    def winContent = null
    def winType = random.nextInt(100)
    if(winType < 80){
        winContent = " 集齐童年记忆系列 奖励10元现金券~"
    }else if(winType < 95){
        winContent = " 集齐格格是吃货系列 奖励30元现金券~"
    }else{
        winContent = " 集齐超级英雄系列 奖励100元现金券~"
    }
    def sql = String.format("insert into activity_gashapon_win(account_id,content,create_time) values(0,'%s',now());",telphone + winContent)
    println(sql)
}

