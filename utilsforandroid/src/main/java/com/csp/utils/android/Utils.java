package com.csp.utils.android;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.csp.utils.android.log.LogCat;

import java.lang.reflect.InvocationTargetException;

/**
 * utils about initialization
 * Created by Blankj on 2016/12/08.
 * Modified by csp on 2018/03/02 just delete something
 *
 * @version 1.0.0
 */
public final class Utils {

    @SuppressLint("StaticFieldLeak")
    private static Application sApplication;

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Init utils.
     * <p>Init it in the class of Application.</p>
     *
     * @param context context
     */
    public static void init(@NonNull final Context context) {
        init((Application) context.getApplicationContext());
    }

    /**
     * Init utils.
     * <p>Init it in the class of Application.</p>
     *
     * @param app application
     */
    public static void init(@NonNull final Application app) {
        if (sApplication == null) {
            Utils.sApplication = app;
        }
    }

    /**
     * Return the context of Application object.
     *
     * @return the context of Application object
     */
    public static Application getApp() {
        if (sApplication != null)
            return sApplication;

        try {
            @SuppressLint("PrivateApi")
            Class<?> activityThread = Class.forName("android.app.ActivityThread");
            Object at = activityThread.getMethod("currentActivityThread").invoke(null);
            Object app = activityThread.getMethod("getApplication").invoke(at);
            if (app == null) {
                throw new NullPointerException("u should init first");
            }
            init((Application) app);
            return (Application) app;
        } catch (NoSuchMethodException e) {
            LogCat.printStackTrace(e);
        } catch (IllegalAccessException e) {
            LogCat.printStackTrace(e);
        } catch (InvocationTargetException e) {
            LogCat.printStackTrace(e);
        } catch (ClassNotFoundException e) {
            LogCat.printStackTrace(e);
        }
        throw new NullPointerException("u should init first");
    }
}
