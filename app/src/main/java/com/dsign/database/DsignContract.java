package com.dsign.database;

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

    public static class MediaInfo implements BaseColumns{
        public static final String TABLE_NAME = "mediainfo";

        public static final String COLUMN_NAME_SCREEN_ID = "sid";
        public static final String COLUMN_NAME_GROUP_ID = "gid";
        public static final String COLUMN_NAME_MEDIA_ID = "mid";
        public static final String COLUMN_NAME_PLAY_INDEX = "playindex";
        public static final String COLUMN_NAME_MEDIA_URL = "mediaurl";
        public static final String COLUMN_NAME_MEDIA_FILENAME = "mediafilename";

        public static final String COLUMN_NAME_MEDIA_LOCAL_PATH = "medialocalpath";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COLUMN_NAME_DURATION = "duration";
        public static final String COLUMN_NAME_DOWNLOAD_STATUS = "downloadstatus";
        public static final String COLUMN_NAME_DOWNLOAD_MANAGER_ID = "downloadid";
    }

/**************Create Table Start*********************************/
    public static final String CREATE_TABLE_MEDIA_INFO =
            "CREATE TABLE " + MediaInfo.TABLE_NAME + " (" +
                    MediaInfo._ID + " INTEGER PRIMARY KEY," +
                    MediaInfo.COLUMN_NAME_SCREEN_ID + " INTEGER," +
                    MediaInfo.COLUMN_NAME_GROUP_ID + " INTEGER," +
                    MediaInfo.COLUMN_NAME_MEDIA_ID+ " INTEGER," +
                    MediaInfo.COLUMN_NAME_PLAY_INDEX+ " INTEGER," +
                    MediaInfo.COLUMN_NAME_MEDIA_URL+ " TEXT," +
                    MediaInfo.COLUMN_NAME_MEDIA_FILENAME+ " TEXT," +
                    MediaInfo.COLUMN_NAME_MEDIA_LOCAL_PATH+ " TEXT," +
                    MediaInfo.COLUMN_NAME_TYPE+ " TEXT," +
                    MediaInfo.COLUMN_NAME_DURATION+ " INTEGER," +
                    MediaInfo.COLUMN_NAME_DOWNLOAD_MANAGER_ID+ " REAL," +
                    MediaInfo.COLUMN_NAME_DOWNLOAD_STATUS+ " TEXT)";

    public static final String CREATE_TABLE_DEVICE =
            "CREATE TABLE " + Device.TABLE_NAME + " (" +
            Device._ID + " INTEGER PRIMARY KEY," +
            Device.COLUMN_NAME_DEVICE_ID + " TEXT," +
            Device.COLUMN_NAME_IS_ACTIVE + " TEXT," +
            Device.COLUMN_NAME_DATETIME + " TEXT)";

/****************Create Table End*****************************/


    /**************Insert Table  Start*********************************/
    public static final String INSERT_TABLE_DEVICE =
            "INSERT into " + Device.TABLE_NAME + " (" +
                    Device.COLUMN_NAME_DEVICE_ID + " ," +
                    Device.COLUMN_NAME_IS_ACTIVE + " ," +
                    Device.COLUMN_NAME_DATETIME + ") values ('DID56256',1, '2023-07-24')";

    /**************Insert Table End*********************************/
    public static final String DELETE_TABLE_DEVICE =
            "DROP TABLE IF EXISTS " + Device.TABLE_NAME;



}
