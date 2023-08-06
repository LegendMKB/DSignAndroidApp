package com.example.database;

import android.icu.lang.UProperty;
import android.provider.BaseColumns;

public class DsignContract {
    private DsignContract() {
    }

    public static class Device implements BaseColumns{
        public static final String TABLE_NAME = "device";
        public static final String COLUMN_NAME_DEVICE_ID = "deviceid";
        public static final String COLUMN_NAME_IS_ACTIVE = "isactive";
        public static final String COLUMN_NAME_DATETIME = "datetime";


    }

    public static final String CREATE_TABLE_DEVICE =
            "CREATE TABLE " + Device.TABLE_NAME + " (" +
            Device._ID + " INTEGER PRIMARY KEY," +
            Device.COLUMN_NAME_DEVICE_ID + " TEXT," +
            Device.COLUMN_NAME_IS_ACTIVE + " TEXT," +
            Device.COLUMN_NAME_DATETIME + " TEXT)";


    public static final String INSERT_TABLE_DEVICE =
            "INSERT into " + Device.TABLE_NAME + " (" +
                    Device.COLUMN_NAME_DEVICE_ID + " ," +
                    Device.COLUMN_NAME_IS_ACTIVE + " ," +
                    Device.COLUMN_NAME_DATETIME + ") values ('DID56256',1, '2023-07-24')";


    public static final String DELETE_TABLE_DEVICE =
            "DROP TABLE IF EXISTS " + Device.TABLE_NAME;



}
