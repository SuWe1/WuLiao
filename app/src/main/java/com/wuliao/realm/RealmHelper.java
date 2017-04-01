package com.wuliao.realm;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Swy on 2017/3/31.
 */

public class RealmHelper  {
    public static final String DATABASE_NAME="wuliao";

    public static Realm newRealmInstance(){
        return Realm.getInstance(new RealmConfiguration.Builder()
        .deleteRealmIfMigrationNeeded()
        .name(DATABASE_NAME)
        .build());
    }
}
