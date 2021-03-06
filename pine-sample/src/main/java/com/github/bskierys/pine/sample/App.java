/*
* author: Bartlomiej Kierys
* date: 2016-11-09
* email: bskierys@gmail.com
*/
package com.github.bskierys.pine.sample;

import android.app.Application;

import com.github.bskierys.pine.LogInfo;
import com.github.bskierys.pine.MessageInfo;
import com.github.bskierys.pine.Pine;

import java.util.Locale;

import timber.log.Timber;

public class App extends Application {
    @Override public void onCreate() {
        super.onCreate();

        // you can use default implementation (package name as tag)
        Pine defaultPine = Pine.growDefault();

        // you can replace your package (as well as others) with short word
        Pine pineWithReplace = new Pine.Builder()
                .addPackageReplacePattern(getPackageName(), "PINE")
                .addPackageReplacePattern("com.github.simonpercic.oklog", "OKLOG")
                .grow();

        // or you can grow your own custom pine
        Pine customPine = new Pine.Builder()
                .addPackageReplacePattern(getPackageName(), "PINE")
                .setTagFormatter(LogInfo::packageName)
                .setMessageFormatter(this::formatMessage)
                .grow();

        // plant a pine at the beginning of application lifecycle
        if (BuildConfig.DEBUG) {
            Timber.plant(pineWithReplace);
        }
    }

    private String formatMessage(MessageInfo info) {
        return String.format(Locale.getDefault(), "%s#%s, %d ----> %s", info.className(),
                             info.methodName(), info.lineNumber(), info.message());
    }
}
