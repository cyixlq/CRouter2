package top.cyixlq.crouter;

import android.content.Context;
import android.net.Uri;

import top.cyixlq.crouter.chain.RouteRequest;
import top.cyixlq.crouter.interfaces.IRoute;

public class CRouter {

    static final String TAG = "CRoute";
    static final String RAW_URI = "raw_uri";
    static final String BUNDLE_TAG = "bundle_tag";

    void addPath(final String path, Class<?> clazz) {
        _CRouter.get().addPath(path, clazz);
    }

    public static IRoute build(String path) {
        return build(path == null ? null : Uri.parse(path));
    }

    public static IRoute build(Uri uri) {
        return _CRouter.get().build(uri);
    }

    public static IRoute build(RouteRequest request) {
        return _CRouter.get().build(request);
    }

    public static void go(final String path, final Context context) {
        _CRouter.get().go(path, context);
    }

}
