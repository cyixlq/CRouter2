package top.cyixlq.crouter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.util.Log;
import android.util.SparseArray;

import androidx.annotation.AnimRes;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import top.cyixlq.crouter.chain.RealInterceptorChain;
import top.cyixlq.crouter.chain.RouteCallback;
import top.cyixlq.crouter.chain.RouteInterceptor;
import top.cyixlq.crouter.chain.RouteRequest;
import top.cyixlq.crouter.chain.RouteResponse;
import top.cyixlq.crouter.chain.RouteStatus;
import top.cyixlq.crouter.interfaces.IRoute;

public class _CRouter implements IRoute {

    private Map<String, Class<?>> classMap;
    private RouteRequest mRouteRequest;

    private _CRouter() {
        classMap = new HashMap<>();
    }

    private static final class SingleHolder {
        private static final _CRouter instance = new _CRouter();
    }

    static _CRouter get() {
        return SingleHolder.instance;
    }

    void addPath(String path, Class<?> clazz) {
        if (classMap.containsKey(path)) {
            throw new IllegalArgumentException(path + "：该路径名已经被注入过了，请重新取名");
        }
        classMap.put(path, clazz);
    }


    @Override
    public IRoute build(Uri uri) {
        mRouteRequest = new RouteRequest(uri);
        Bundle bundle = new Bundle();
        bundle.putString(CRouter.RAW_URI, uri == null ? null : uri.toString());
        mRouteRequest.setExtras(bundle);
        return this;
    }

    @Override
    public IRoute build(@NonNull RouteRequest request) {
        mRouteRequest = request;
        Bundle bundle = mRouteRequest.getExtras();
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putString(CRouter.RAW_URI, request.getUri().toString());
        mRouteRequest.setExtras(bundle);
        return this;
    }

    @Override
    public IRoute callback(RouteCallback callback) {
        mRouteRequest.setRouteCallback(callback);
        return this;
    }

    @Override
    public IRoute requestCode(int requestCode) {
        mRouteRequest.setRequestCode(requestCode);
        return this;
    }

    @Override
    public IRoute with(Bundle bundle) {
        if (bundle != null && !bundle.isEmpty()) {
            Bundle extras = mRouteRequest.getExtras();
            if (extras == null) {
                extras = new Bundle();
            }
            extras.putAll(bundle);
            mRouteRequest.setExtras(extras);
        }
        return this;
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public IRoute with(PersistableBundle bundle) {
        if (bundle != null && !bundle.isEmpty()) {
            Bundle extras = mRouteRequest.getExtras();
            if (extras == null) {
                extras = new Bundle();
            }
            extras.putAll(bundle);
            mRouteRequest.setExtras(extras);
        }
        return this;
    }

    @SuppressLint("ObsoleteSdkInt")
    @SuppressWarnings("unchecked")
    @Override
    public IRoute with(String key, Object value) {
        if (value == null) {
            Log.w(CRouter.TAG, "已被忽略: extra为null");
            return this;
        }
        Bundle bundle = mRouteRequest.getExtras();
        if (bundle == null) {
            bundle = new Bundle();
        }
        if (value instanceof Bundle) {
            bundle.putBundle(key, (Bundle) value);
        } else if (value instanceof Byte) {
            bundle.putByte(key, (byte) value);
        } else if (value instanceof Short) {
            bundle.putShort(key, (short) value);
        } else if (value instanceof Integer) {
            bundle.putInt(key, (int) value);
        } else if (value instanceof Long) {
            bundle.putLong(key, (long) value);
        } else if (value instanceof Character) {
            bundle.putChar(key, (char) value);
        } else if (value instanceof Boolean) {
            bundle.putBoolean(key, (boolean) value);
        } else if (value instanceof Float) {
            bundle.putFloat(key, (float) value);
        } else if (value instanceof Double) {
            bundle.putDouble(key, (double) value);
        } else if (value instanceof String) {
            bundle.putString(key, (String) value);
        } else if (value instanceof CharSequence) {
            bundle.putCharSequence(key, (CharSequence) value);
        } else if (value instanceof byte[]) {
            bundle.putByteArray(key, (byte[]) value);
        } else if (value instanceof short[]) {
            bundle.putShortArray(key, (short[]) value);
        } else if (value instanceof int[]) {
            bundle.putIntArray(key, (int[]) value);
        } else if (value instanceof long[]) {
            bundle.putLongArray(key, (long[]) value);
        } else if (value instanceof char[]) {
            bundle.putCharArray(key, (char[]) value);
        } else if (value instanceof boolean[]) {
            bundle.putBooleanArray(key, (boolean[]) value);
        } else if (value instanceof float[]) {
            bundle.putFloatArray(key, (float[]) value);
        } else if (value instanceof double[]) {
            bundle.putDoubleArray(key, (double[]) value);
        } else if (value instanceof String[]) {
            bundle.putStringArray(key, (String[]) value);
        } else if (value instanceof CharSequence[]) {
            bundle.putCharSequenceArray(key, (CharSequence[]) value);
        } else if (value instanceof IBinder) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                bundle.putBinder(key, (IBinder) value);
            } else {
                Log.e(CRouter.TAG, "API必须大于18");
            }
        } else if (value instanceof SparseArray) {
            bundle.putSparseParcelableArray(key, (SparseArray<? extends Parcelable>) value);
        } else if (value instanceof Parcelable) {
            bundle.putParcelable(key, (Parcelable) value);
        } else if (value instanceof Parcelable[]) {
            bundle.putParcelableArray(key, (Parcelable[]) value);
        } else if (value instanceof Serializable) {
            bundle.putSerializable(key, (Serializable) value);
        } else {
            Log.w(CRouter.TAG, "未知类型: " + value.getClass().getName());
        }
        mRouteRequest.setExtras(bundle);
        return this;
    }

    @Override
    public IRoute addFlags(int flags) {
        mRouteRequest.addFlags(flags);
        return this;
    }

    @Override
    public IRoute setData(Uri data) {
        mRouteRequest.setData(data);
        return this;
    }

    @Override
    public IRoute setType(String type) {
        mRouteRequest.setType(type);
        return this;
    }

    @Override
    public IRoute setDataAndType(Uri data, String type) {
        mRouteRequest.setData(data);
        mRouteRequest.setType(type);
        return this;
    }

    @Override
    public IRoute setAction(String action) {
        mRouteRequest.setAction(action);
        return this;
    }

    @Override
    public IRoute anim(@AnimRes int enterAnim, @AnimRes int exitAnim) {
        mRouteRequest.setEnterAnim(enterAnim);
        mRouteRequest.setExitAnim(exitAnim);
        return this;
    }

    @Override
    public IRoute activityOptionsBundle(Bundle activityOptionsBundle) {
        mRouteRequest.setActivityOptionsBundle(activityOptionsBundle);
        return this;
    }

    @Override
    public Intent getIntent(@NonNull Object source) {
        List<RouteInterceptor> interceptors = new LinkedList<>();
        RealInterceptorChain chain = new RealInterceptorChain(source, mRouteRequest, interceptors);
        RouteResponse response = chain.process();
        callback(response);
        return (Intent) response.getResult();
    }

    private void callback(RouteResponse response) {
        if (response.getStatus() != RouteStatus.SUCCEED) {
            Log.w(CRouter.TAG, response.getMessage());
        }
        if (mRouteRequest.getRouteCallback() != null) {
            mRouteRequest.getRouteCallback().callback(
                    response.getStatus(), mRouteRequest.getUri(), response.getMessage());
        }
    }

    @Override
    public Fragment getFragment(@NonNull Object source) {
        return null;
    }

    @Override
    public void go(RouteCallback callback) {
        go(ApplicationContextProvider.context, callback);
    }

    @Override
    public void go() {
        go(ApplicationContextProvider.context);
    }


    @Override
    public void go(Context context, RouteCallback callback) {
        mRouteRequest.setRouteCallback(callback);
        go(context);
    }

    public void go(final String path, final Context context) {
        final Class<?> aClass = classMap.get(path);
        final Intent intent = new Intent(context, aClass);
        if (context instanceof Application) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    @Override
    public void go(Context context) {
        Intent intent = getIntent(context);
        if (intent != null) {
            Bundle options = mRouteRequest.getActivityOptionsBundle();
            if (context instanceof Activity) {
                ((Activity) context).startActivityForResult(intent, mRouteRequest.getRequestCode(), options);
                if (mRouteRequest.getEnterAnim() >= 0 && mRouteRequest.getExitAnim() >= 0) {
                    ((Activity) context).overridePendingTransition(
                            mRouteRequest.getEnterAnim(), mRouteRequest.getExitAnim());
                }
            } else {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent, options);
            }
        }
    }

    @Override
    public void go(Fragment fragment, RouteCallback callback) {
        mRouteRequest.setRouteCallback(callback);
        go(fragment);
    }

    @Override
    public void go(Fragment fragment) {
        Intent intent = getIntent(fragment);
        if (intent != null) {
            Bundle options = mRouteRequest.getActivityOptionsBundle();
            if (mRouteRequest.getRequestCode() < 0) {
                fragment.startActivity(intent, options);
            } else {
                fragment.startActivityForResult(intent, mRouteRequest.getRequestCode(), options);
            }
            if (mRouteRequest.getEnterAnim() >= 0 && mRouteRequest.getExitAnim() >= 0
                    && fragment.getActivity() != null) {
                // 添加跳转过度动画
                fragment.getActivity().overridePendingTransition(
                        mRouteRequest.getEnterAnim(), mRouteRequest.getExitAnim());
            }
        }
    }
}
