package top.cyixlq.crouter.chain;

import android.net.Uri;

import java.io.Serializable;

public interface RouteCallback extends Serializable {
    /**
     * Callback
     *
     * @param status  {@link RouteStatus}
     * @param uri     uri
     * @param message notice msg
     */
    void callback(RouteStatus status, Uri uri, String message);
}
