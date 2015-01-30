package com.linroid.radio;

import android.app.Application;

import com.linroid.radio.module.AppModule;
import com.linroid.radio.module.Injector;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;
import timber.log.Timber;

/**
 * Created by linroid on 1/14/15.
 */

public class App extends Application
        implements Injector {
    ObjectGraph mObjectGraph;

//
//    private void createAccount()
//    {
//        Account localAccount = new Account(this.person.getNickname(), "com.linroid.alwen.account");
//        if (((AccountManager)getSystemService("account")).addAccountExplicitly(localAccount, null, null))
//        {
//            Timber.i("add account success", new Object[0]);
//            return;
//        }
//        Timber.i("account not added", new Object[0]);
//    }

    public void onCreate() {
        super.onCreate();
        Timber.Tree tree = BuildConfig.DEBUG ? new Timber.DebugTree() : new Timber.HollowTree();
        Timber.plant(tree);
        CrashReport.initCrashReport(this, BuildConfig.BUGLY_APP_ID, BuildConfig.DEBUG);
        mObjectGraph = ObjectGraph.create(getModules().toArray());
        inject(this);

    }

    public List<Object> getModules() {
        return Arrays.<Object>asList(new AppModule(this));
    }

    public void inject(Object target) {
        this.mObjectGraph.inject(target);
    }

    public ObjectGraph plus(Injector injector) {
        return this.mObjectGraph.plus(injector.getModules().toArray());
    }

    public ObjectGraph plus(Object[] modules) {
        return this.mObjectGraph.plus(modules);
    }
}