package com.cgellner.yomm.Objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TransactionContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<TransactionItem> ITEMS = new ArrayList<TransactionItem>();


    public static final Map<String, TransactionItem> ITEM_MAP = new HashMap<String, TransactionItem>();




    static {

            //addItem(createTransactionItem());
    }

    private static void addItem(TransactionItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }



    private static String makeDetails(int position) {

        StringBuilder builder = new StringBuilder();

        builder.append("Details about Item: ").append(position);


        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }

        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class TransactionItem {

        public final String id;
        public final String value;
        public final String debtor;
        public final String creditor;
        public final String category;
        public final String details;
        public final String date;
        public final String time;
        public final String type;


        //public final String content;
        //public final String details;


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


        /**
        public TransactionItem(String id, String content, String details) {
            this.id = id;

             this.content = content;
            this.details = details;
        }*/



    }



}
