package com.cgellner.yomm.Objects;


public class TransactionItem {


        private String id;
        private String value;
        private String debtor;
        private String creditor;
        private String category;
        private String details;
        private String date;
        private String time;
        private String type;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public TransactionItem(String id, String value, String debtor, String creditor, String category, String details, String date, String time, String type){

            this.id = id;
            this.value = value;
            this.debtor = debtor;
            this.creditor = creditor;
            this.category = category;
            this.details = details;
            this.date = date;
            this.time = time;
            this.type  = type;

        }

        @Override
        public String toString() {

            return "TransactionItem{" +
                    "id='" + id + '\'' +
                    ", value='" + value + '\'' +
                    ", debtor='" + debtor + '\'' +
                    ", creditor='" + creditor + '\'' +
                    ", category='" + category + '\'' +
                    ", details='" + details + '\'' +
                    ", date='" + date + '\'' +
                    ", time='" + time + '\'' +
                    ", type='" + type + '\'' +
                    '}';
        }

}
