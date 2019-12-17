package top.cyixlq.crouter.interfaces;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.AnimRes;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;

import top.cyixlq.crouter.chain.RouteCallback;
import top.cyixlq.crouter.chain.RouteRequest;

public interface IRoute {
    /**
     * Entrance.
     */
    IRoute build(Uri uri);

    IRoute build(@NonNull RouteRequest request);

    /**
     * Route request callback.
     */
    IRoute callback(RouteCallback callback);

    /**
     * Call <code>startActivityForResult</code>.
     */
    IRoute requestCode(int requestCode);

    /**
     * @see Bundle#putAll(Bundle)
     */
    IRoute with(Bundle bundle);

    /**
     * @see Bundle#putAll(PersistableBundle)
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    IRoute with(PersistableBundle bundle);

    /**
     * bundle.putXXX(String key, XXX value).
     */
    IRoute with(String key, Object value);

    /**
     * @see Intent#addFlags(int)
     */
    IRoute addFlags(int flags);

    /**
     * @see Intent#setData(Uri)
     */
    IRoute setData(Uri data);

    /**
     * @see Intent#setType(String)
     */
    IRoute setType(String type);

    /**
     * @see Intent#setDataAndType(Uri, String)
     */
    IRoute setDataAndType(Uri data, String type);

    /**
     * @see Intent#setAction(String)
     */
    IRoute setAction(String action);

    /**
     * @see android.app.Activity#overridePendingTransition(int, int)
     */
    IRoute anim(@AnimRes int enterAnim, @AnimRes int exitAnim);

    /**
     * {@link ActivityOptions#toBundle()} and {@link ActivityOptionsCompat#toBundle()}.
     */
    IRoute activityOptionsBundle(Bundle activityOptionsBundle);

    /**
     * Skip all the interceptors.
     *//*
    IRoute skipInterceptors();

    *//**
     * Skip the named interceptors.
     *//*
    IRoute skipInterceptors(String... interceptors);

    *//**
     * Add interceptors temporarily for current route request.
     *//*
    IRoute addInterceptors(String... interceptors);*/

    /**
     * Get an intent instance.
     *
     * @param source Activity or Fragment instance.
     * @return {@link Intent} instance.
     */
    Intent getIntent(@NonNull Object source);

    /**
     * Get a fragment instance.
     *
     * @param source Activity or Fragment instance.
     * @return {@link Fragment} instance.
     */
    Fragment getFragment(@NonNull Object source);

    void go(RouteCallback callback);

    void go();

    void go(Context context, RouteCallback callback);

    void go(Context context);

    void go(Fragment fragment, RouteCallback callback);

    void go(Fragment fragment);
}
