package com.asifadam93.gestionnewsforum.application;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);

        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(config);

    }
}
