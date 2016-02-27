package com.project.sean.barcodereader;

import android.provider.BaseColumns;

/**
 * Created by Sean on 27/02/2016.
 */
public final class ItemContract {

    public ItemContract() {}

    public static abstract class Item implements BaseColumns {

        public static final String TABLE_NAME = "item_table";
        public static final String COL_ID = "ITEM_ID"; //This will be the barcode number
        public static final String COL_NAME = "ITEM_NAME";
        public static final String COL_PRICE = "ITEM_PRICE";

    }
}
