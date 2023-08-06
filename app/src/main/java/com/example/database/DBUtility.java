package com.example.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.models.DeviceInfo;

public class DBUtility {
private Context _context;
    public DBUtility(Context context) {
        _context = context;
    }

    public DeviceInfo getDeviceInfo() {
        SQLiteDatabase db = null;
        DeviceInfo deviceInfo = new DeviceInfo();
        try {
            DatabaseHelper dbHelper = new DatabaseHelper(_context);

            db = dbHelper.getReadableDatabase();

            Cursor cursor = db.query(DsignContract.Device.TABLE_NAME, null, null, null, null, null, null);

            while (cursor.moveToNext()) {
                deviceInfo._ID = cursor.getInt(cursor.getColumnIndexOrThrow(DsignContract.Device._ID));
                deviceInfo.DeviceId = cursor.getString(cursor.getColumnIndexOrThrow(DsignContract.Device.COLUMN_NAME_DEVICE_ID));
                deviceInfo.IsActive = cursor.getInt(cursor.getColumnIndexOrThrow(DsignContract.Device.COLUMN_NAME_IS_ACTIVE));
                deviceInfo.Datetime = cursor.getString(cursor.getColumnIndexOrThrow(DsignContract.Device.COLUMN_NAME_DATETIME));
            }

            cursor.close();
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }

        return deviceInfo;
    }
}
