package com.sqisland.friendspell.util;

import android.os.Bundle;
import android.support.test.runner.AndroidJUnitRunner;
import android.util.Log;

import com.sqisland.friendspell.BuildConfig;

import java.lang.reflect.Method;

import timber.log.Timber;

// http://stackoverflow.com/questions/30337375/empty-jacoco-report-for-android-espresso/31600193#31600193
public class AndroidJacocoTestRunner extends AndroidJUnitRunner {
  static {
    System.setProperty("jacoco-agent.destfile",
        "/data/data/" + BuildConfig.APPLICATION_ID + "/coverage.ec");
  }

  @Override
  public void finish(int resultCode, Bundle results) {
    try {
      Class rt = Class.forName("org.jacoco.agent.rt.RT");
      Method getAgent = rt.getMethod("getAgent");
      Method dump = getAgent.getReturnType().getMethod("dump", boolean.class);
      Object agent = getAgent.invoke(null);
      dump.invoke(agent, false);
    } catch (Throwable e) {
      Timber.e(Log.getStackTraceString(e));
    }
    super.finish(resultCode, results);
  }
}