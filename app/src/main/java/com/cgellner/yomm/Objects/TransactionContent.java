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

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(TransactionItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static TransactionItem createDummyItem(int position) {
        return new TransactionItem(String.valueOf(position), "Item " + position, makeDetails(position));
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
        public final String content;
        public final String details;

        public TransactionItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }





    private  List<TransactionItem> createItems(){



    }
}
