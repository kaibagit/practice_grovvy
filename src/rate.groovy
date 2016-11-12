def rateArr = [0.2d,0.1d,0.0499d,0.0010d,0.0001d]

def players = 50000

def playDays = 6

def timesPerPlay = 30

def totalPlayTimesPerPlayer = playDays * timesPerPlay

def winRate = 1

rateArr.each {
    def rate = it
    def not_rate = 1d - rate    //不中的概率
    def all_not_rate = 1;       //全部不中的概率
    1.upto(totalPlayTimesPerPlayer){
        all_not_rate = not_rate * all_not_rate
    }
    finalRate = 1- all_not_rate //最后中的概率
    printf("每次抽中概率：%f，抽%d天，每天%d次，的概率 = %f \n",it,playDays,timesPerPlay,finalRate)
    winRate = finalRate * finalRate
}
printf("每天抽%d次，活动持续%d天，最终中奖概率：%f \n",timesPerPlay,playDays,winRate)

def winPlayers = players * winRate
printf("参与人数：%d，每天抽%d次，活动持续%d天，最终中奖人数：%f",players,timesPerPlay,playDays,winPlayers)

