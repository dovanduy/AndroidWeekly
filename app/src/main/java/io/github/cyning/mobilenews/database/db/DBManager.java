package io.github.cyning.mobilenews.database.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import io.github.cyning.droidcore.utils.StringUtils;
import io.github.cyning.greendao.DaoMaster;
import io.github.cyning.greendao.DaoSession;

/**
 * @author Cyning
 * @since 2016.03.12
 * Time    1:10 AM
 * Desc    <p>类/接口描述</p>
 */

public class DBManager {


    public static final String DEFAULT_NAME = "default.db";

    private static DBManager mInstance = null;

    SQLiteDatabase db;


    public static DBManager getInstance(Context context,String dbName) {
        if (mInstance == null) {
            synchronized (DBManager.class) {
                if (mInstance == null) {
                    mInstance = new DBManager(context.getApplicationContext(),dbName);
                }
            }
        }
        return mInstance;
    }
    DaoSession daoSession =null;
    public DBManager(Context context,String dbName) {
        String mDBName = dbName;
        if (StringUtils.hasText(dbName)){
            if (!dbName.contains(".db")){
                mDBName = dbName +".db";
            }
        }else {
            mDBName = DEFAULT_NAME;
        }
        DBOpenHelper dbOpenHelper = new DBOpenHelper(context, mDBName, null, DaoMaster.SCHEMA_VERSION);
         db = dbOpenHelper.getWritableDatabase();
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }
}
