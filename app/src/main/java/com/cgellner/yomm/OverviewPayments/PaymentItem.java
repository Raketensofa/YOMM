package com.cgellner.yomm.OverviewPayments;


/**
 *
 */
public class PaymentItem {

    //region Fields

        private String id;
        private String value;
        private String debtor;
        private String creditor;
        private String category;
        private String details;
        private String date;
        private String time;
        private String state;
        private String repaymentDate;
        private String repaymentTime;
        private String mainMoneyValue;

    //endregion


    //region Getter & Setter

        public String getRepaymentDate() {
            return repaymentDate;
        }

        public void setRepaymentDate(String repaymentDate) {
            this.repaymentDate = repaymentDate;
        }

        public String getRepaymentTime() {
            return repaymentTime;
        }

        public void setRepaymentTime(String repaymentTime) {
            this.repaymentTime = repaymentTime;
        }

        public String getMainMoneyValue() {
        return mainMoneyValue;
    }

        public void setMainMoneyValue(String mainMoneyValue) {
        this.mainMoneyValue = mainMoneyValue;
    }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getDebtor() {
            return debtor;
        }

        public void setDebtor(String debtor) {
            this.debtor = debtor;
        }

        public String getCreditor() {
            return creditor;
        }

        public void setCreditor(String creditor) {
            this.creditor = creditor;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getDetails() {
             return details;
        }

        public void setDetails(String details) {
        this.details = details;
    }

        public String getDate() {
        return date;
    }

        public void setDate(String date) {
        this.date = date;
    }

        public String getTime() {
        return time;
    }

        public void setTime(String time) {
        this.time = time;
    }

        public String getState() {
        return state;
    }

        public void setState(String state) {
        this.state = state;
    }

    //endregion


    //region Constructor

    /**
     *
     */
    public PaymentItem(){}


    //endregion


    //region Methods

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "PaymentItem{" +
                "id='" + id + '\'' +
                ", value='" + value + '\'' +
                ", debtor='" + debtor + '\'' +
                ", creditor='" + creditor + '\'' +
                ", category='" + category + '\'' +
                ", details='" + details + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", state='" + state + '\'' +
                ", repaymentDate='" + repaymentDate + '\'' +
                ", repaymentTime='" + repaymentTime + '\'' +
                ", mainMoneyValue='" + mainMoneyValue + '\'' +
                '}';
    }

    //endregion
}
