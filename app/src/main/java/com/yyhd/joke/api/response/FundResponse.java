package com.yyhd.joke.api.response;

/**
 * 功能:
 * 作者: 崔兴旺
 * 日期: 2018/1/25
 * 备注:
 */
public class FundResponse {

    /**
     * yesterdayIncome : {"yesterdayCashIncome":0,"yesterdayGoldIncome":0}
     * fundInfo : {"userId":"c7bdf7a0-678c-438b-a926-b72ab9aed769","cash":0,"gold":330}
     */

    private YesterdayIncome yesterdayIncome;
    private FundInfo fundInfo;

    public YesterdayIncome getYesterdayIncome() {
        return yesterdayIncome;
    }

    public void setYesterdayIncome(YesterdayIncome yesterdayIncome) {
        this.yesterdayIncome = yesterdayIncome;
    }

    public FundInfo getFundInfo() {
        return fundInfo;
    }

    public void setFundInfo(FundInfo fundInfo) {
        this.fundInfo = fundInfo;
    }

    public static class YesterdayIncome {
        /**
         * yesterdayCashIncome : 0
         * yesterdayGoldIncome : 0
         */

        private double yesterdayCashIncome;
        private int yesterdayGoldIncome;

        public double getYesterdayCashIncome() {
            return yesterdayCashIncome;
        }

        public void setYesterdayCashIncome(double yesterdayCashIncome) {
            this.yesterdayCashIncome = yesterdayCashIncome;
        }

        public int getYesterdayGoldIncome() {
            return yesterdayGoldIncome;
        }

        public void setYesterdayGoldIncome(int yesterdayGoldIncome) {
            this.yesterdayGoldIncome = yesterdayGoldIncome;
        }
    }

    public static class FundInfo {
        /**
         * userId : c7bdf7a0-678c-438b-a926-b72ab9aed769
         * cash : 0
         * gold : 330
         */

        private String userId;
        private double cash;
        private int gold;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public double getCash() {
            return cash;
        }

        public void setCash(double cash) {
            this.cash = cash;
        }

        public int getGold() {
            return gold;
        }

        public void setGold(int gold) {
            this.gold = gold;
        }
    }
}
