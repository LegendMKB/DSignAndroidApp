package com.dsign.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dsign.DownloadStatus;
import com.dsign.models.DeviceInfo;
import com.dsign.models.MediaContent;
import com.dsign.models.Medium;
import com.dsign.models.MediaInfo;

import java.util.ArrayList;
import java.util.List;

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
    
    public boolean updateMediaInfo(MediaContent mcontent) {
        SQLiteDatabase db = null;
        try {
            List<Medium> med = mcontent.getMedia();
            DatabaseHelper dbHelper = new DatabaseHelper(_context);

            db = dbHelper.getReadableDatabase();
            for (int i = 0; i < med.size(); i ++) {
                if(med.get(i).getChangestatus() == 1) {
                    Cursor cursor = db.query(DsignContract.MediaInfo.TABLE_NAME, null, DsignContract.MediaInfo.COLUMN_NAME_MEDIA_ID + "=?", new String[]{String.valueOf(med.get(i).getmID())}, null, null, null);
                    int cc = cursor.getCount();
                    if (cursor.getCount() < 1) {
                        ContentValues values = new ContentValues();
                        values.put(DsignContract.MediaInfo.COLUMN_NAME_GROUP_ID, med.get(i).getgID());
                        values.put(DsignContract.MediaInfo.COLUMN_NAME_MEDIA_ID, med.get(i).getmID());
                        values.put(DsignContract.MediaInfo.COLUMN_NAME_SCREEN_ID, med.get(i).getsID());
                        values.put(DsignContract.MediaInfo.COLUMN_NAME_MEDIA_URL, med.get(i).getMediaurl());
                        values.put(DsignContract.MediaInfo.COLUMN_NAME_MEDIA_FILENAME, med.get(i).getMediaFileName());
                        values.put(DsignContract.MediaInfo.COLUMN_NAME_DURATION, med.get(i).getDuration());
                        values.put(DsignContract.MediaInfo.COLUMN_NAME_PLAY_INDEX, med.get(i).getPlayindex());
                        values.put(DsignContract.MediaInfo.COLUMN_NAME_TYPE, med.get(i).getType());
                        values.put(DsignContract.MediaInfo.COLUMN_NAME_DOWNLOAD_STATUS, DownloadStatus.NOT_STARTED.toString());
                        db.insert(DsignContract.MediaInfo.TABLE_NAME, null, values);

                    } else {
                        if(med.get(i).getIsDelete() == 1) {
                            String whereClause = DsignContract.MediaInfo.COLUMN_NAME_SCREEN_ID+ "=? AND " + DsignContract.MediaInfo.COLUMN_NAME_GROUP_ID + "=? AND " + DsignContract.MediaInfo.COLUMN_NAME_MEDIA_ID + "=?";
                            String[] whereArgs = {String.valueOf(med.get(i).getsID()),String.valueOf(med.get(i).getgID()),String.valueOf(med.get(i).getmID())};
                            db.delete(DsignContract.MediaInfo.TABLE_NAME, whereClause, whereArgs );
                        }
                        else if(med.get(i).getUpdatestatus() == 1){
                            ContentValues values = new ContentValues();
                            values.put(DsignContract.MediaInfo.COLUMN_NAME_MEDIA_URL, med.get(i).getMediaurl());
                            values.put(DsignContract.MediaInfo.COLUMN_NAME_MEDIA_FILENAME, med.get(i).getMediaFileName());
                            values.put(DsignContract.MediaInfo.COLUMN_NAME_DURATION, med.get(i).getDuration());
                            values.put(DsignContract.MediaInfo.COLUMN_NAME_PLAY_INDEX, med.get(i).getPlayindex());
                            values.put(DsignContract.MediaInfo.COLUMN_NAME_TYPE, med.get(i).getType());
                            values.put(DsignContract.MediaInfo.COLUMN_NAME_DOWNLOAD_STATUS, DownloadStatus.NOT_STARTED.toString());
                            db.update(DsignContract.MediaInfo.TABLE_NAME, values, DsignContract.MediaInfo.COLUMN_NAME_MEDIA_ID + "=?", new String[]{String.valueOf(med.get(i).getmID())});
                        }
                    }
                    cursor.close();
                }
            }
        } catch (Exception e) {
            //throw new RuntimeException(e);
            return false;
        }finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }

        return true;
    }

    public List<MediaInfo> getMediaInfoForPlay() {
        SQLiteDatabase db = null;
        List<MediaInfo> playList = new ArrayList<MediaInfo>();
        try {

            DatabaseHelper dbHelper = new DatabaseHelper(_context);
            db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(DsignContract.MediaInfo.TABLE_NAME, null, DsignContract.MediaInfo.COLUMN_NAME_DOWNLOAD_STATUS+ "=?", new String[]{DownloadStatus.COMPLETED.toString()}, null, null, null);
            while(cursor.moveToNext()) {
                MediaInfo minfo = new MediaInfo();

                minfo.setgID(cursor.getInt(cursor.getColumnIndexOrThrow(DsignContract.MediaInfo.COLUMN_NAME_GROUP_ID)));
                minfo.setmID(cursor.getInt(cursor.getColumnIndexOrThrow(DsignContract.MediaInfo.COLUMN_NAME_MEDIA_ID)));
                minfo.setMediaLocalPath(cursor.getString(cursor.getColumnIndexOrThrow(DsignContract.MediaInfo.COLUMN_NAME_MEDIA_LOCAL_PATH)));
                minfo.setDuration(cursor.getLong(cursor.getColumnIndexOrThrow(DsignContract.MediaInfo.COLUMN_NAME_DURATION)));
                minfo.setMediaFileName(cursor.getString(cursor.getColumnIndexOrThrow(DsignContract.MediaInfo.COLUMN_NAME_MEDIA_FILENAME)));
                minfo.setType(cursor.getString(cursor.getColumnIndexOrThrow(DsignContract.MediaInfo.COLUMN_NAME_TYPE)));
                playList.add(minfo);
            }

                    cursor.close();

        } catch (Exception e) {
            //throw new RuntimeException(e);
            return null;
        }finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }

        return playList;
    }

    public boolean isMediaAvailableForPlay() {
        boolean isAvailable = false;
        SQLiteDatabase db = null;
        try {

            DatabaseHelper dbHelper = new DatabaseHelper(_context);
            db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(DsignContract.MediaInfo.TABLE_NAME, null, DsignContract.MediaInfo.COLUMN_NAME_DOWNLOAD_STATUS+ "=?", new String[]{DownloadStatus.COMPLETED.toString()}, null, null, null);
            if(cursor.getCount() > 0)
            {
                isAvailable = true;
            }
            else{
                isAvailable = false;
            }

            cursor.close();

        } catch (Exception e) {
            //throw new RuntimeException(e);
            isAvailable = false;
        }finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }

        return isAvailable;
    }

    public List<MediaInfo> getDownloadMediaFileList() {
        SQLiteDatabase db = null;
        List<MediaInfo> downloadList = new ArrayList<MediaInfo>();
        try {

            DatabaseHelper dbHelper = new DatabaseHelper(_context);
            db = dbHelper.getReadableDatabase();

            String selection = DsignContract.MediaInfo.COLUMN_NAME_DOWNLOAD_STATUS+ "=? OR " + DsignContract.MediaInfo.COLUMN_NAME_DOWNLOAD_STATUS + "=?" ;
            String[] projection = {DownloadStatus.NOT_STARTED.toString(), DownloadStatus.FAILED.toString()};

            Cursor cursor = db.query (DsignContract.MediaInfo.TABLE_NAME, null, selection, projection, null, null, null, String.valueOf(1));
            while(cursor.moveToNext()) {
                MediaInfo minfo = new MediaInfo();
                minfo.setsID(cursor.getInt(cursor.getColumnIndexOrThrow(DsignContract.MediaInfo.COLUMN_NAME_SCREEN_ID)));
                minfo.setgID(cursor.getInt(cursor.getColumnIndexOrThrow(DsignContract.MediaInfo.COLUMN_NAME_GROUP_ID)));
                minfo.setmID(cursor.getInt(cursor.getColumnIndexOrThrow(DsignContract.MediaInfo.COLUMN_NAME_MEDIA_ID)));
                minfo.setDuration(cursor.getLong(cursor.getColumnIndexOrThrow(DsignContract.MediaInfo.COLUMN_NAME_DURATION)));
                minfo.setMediaurl(cursor.getString(cursor.getColumnIndexOrThrow(DsignContract.MediaInfo.COLUMN_NAME_MEDIA_URL)));
                minfo.setMediaFileName(cursor.getString(cursor.getColumnIndexOrThrow(DsignContract.MediaInfo.COLUMN_NAME_MEDIA_FILENAME)));

                downloadList.add(minfo);
            }

            cursor.close();

        } catch (Exception e) {
            return null;
        }finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }

        return downloadList;
    }


    public boolean updateFileDownloadStatus(long downloadId, String downloadedFilePath, DownloadStatus status) {
        SQLiteDatabase db = null;
        try {
            DatabaseHelper dbHelper = new DatabaseHelper(_context);

            db = dbHelper.getReadableDatabase();

            if(status == DownloadStatus.COMPLETED){
                String whereClause = DsignContract.MediaInfo.COLUMN_NAME_DOWNLOAD_MANAGER_ID + "=?" ;
                String[] whereArgs = {String.valueOf(downloadId)};
                ContentValues values = new ContentValues();
                values.put(DsignContract.MediaInfo.COLUMN_NAME_DOWNLOAD_STATUS, status.toString());
                values.put(DsignContract.MediaInfo.COLUMN_NAME_MEDIA_LOCAL_PATH, downloadedFilePath);
                db.update(DsignContract.MediaInfo.TABLE_NAME, values, whereClause, whereArgs);
            }
            else{
                String whereClause = DsignContract.MediaInfo.COLUMN_NAME_DOWNLOAD_MANAGER_ID + "=?" ;
                String[] whereArgs = {String.valueOf(downloadId)};
                ContentValues values = new ContentValues();

                values.put(DsignContract.MediaInfo.COLUMN_NAME_DOWNLOAD_STATUS, status.toString());
                db.update(DsignContract.MediaInfo.TABLE_NAME, values, whereClause, whereArgs);
            }
        } catch (Exception e) {
            //throw new RuntimeException(e);
            return false;
        }finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }

        return true;
    }

    public boolean updateFileDownloadInfo(MediaInfo mediaInfo, long downloadId, DownloadStatus status) {
        SQLiteDatabase db = null;
        try {
            DatabaseHelper dbHelper = new DatabaseHelper(_context);

            db = dbHelper.getReadableDatabase();

            String whereClause = DsignContract.MediaInfo.COLUMN_NAME_SCREEN_ID + "=? AND " + DsignContract.MediaInfo.COLUMN_NAME_GROUP_ID + "=? AND " + DsignContract.MediaInfo.COLUMN_NAME_MEDIA_ID + "=?";
            String[] whereArgs = {String.valueOf(mediaInfo.getsID()), String.valueOf(mediaInfo.getgID()), String.valueOf(mediaInfo.getmID())};
            ContentValues values = new ContentValues();

            values.put(DsignContract.MediaInfo.COLUMN_NAME_DOWNLOAD_MANAGER_ID, String.valueOf(downloadId));
            values.put(DsignContract.MediaInfo.COLUMN_NAME_DOWNLOAD_STATUS, status.toString());
            db.update(DsignContract.MediaInfo.TABLE_NAME, values, whereClause, whereArgs);


        } catch (Exception e) {
            //throw new RuntimeException(e);
            return false;
        }finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }

        return true;
    }

}
