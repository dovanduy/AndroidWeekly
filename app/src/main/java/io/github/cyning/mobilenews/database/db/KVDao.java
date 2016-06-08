package io.github.cyning.mobilenews.database.db;

import android.content.Context;
import de.greenrobot.dao.query.QueryBuilder;
import io.github.cyning.greendao.DaoSession;
import io.github.cyning.greendao.Key_Value;
import io.github.cyning.greendao.Key_ValueDao;
import io.github.cyning.mobilenews.common.CollectionsUtils;

/**
 * @author Cyning
 * @since 2016.03.12
 * Time    11:46 AM
 * Desc    <p>Key- Value的存放数据库</p>
 */

public class KVDao {

    private static KVDao mInstance = null;
    private DBManager manager;
    private DaoSession daoSession = null;

    private KVDao(Context ctx) {
        manager = DBManager.getInstance(ctx, null);
    }

    public static KVDao getInstance(Context ctx) {
        if (mInstance == null) {
            synchronized (KVDao.class) {
                if (mInstance == null) {
                    mInstance = new KVDao(ctx);
                }
            }
        }
        return mInstance;
    }

    public Key_ValueDao getTableDao(){
        if (daoSession == null){
            daoSession = manager.getDaoSession();
        }
       return daoSession.getKey_ValueDao();
    }

    public void insertOrUpdate(String key,String value){
        getTableDao().insertOrReplaceInTx(new Key_Value(key,value));
    }
    public String  getValue(String key,String DefaultValue){
        QueryBuilder<Key_Value> query =getTableDao().queryBuilder().where(Key_ValueDao.Properties.Key.eq(key));
        if (CollectionsUtils.hasEle(query.list())){
           return query.list().get(0).getValue();
        }else {
            return  DefaultValue;
        }

    }
}
