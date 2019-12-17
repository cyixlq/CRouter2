package top.cyixlq.crouter.chain;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public interface RouteInterceptor {
    @NonNull
    RouteResponse intercept(Chain chain);

    /**
     * Interceptor chain processor.
     */
    interface Chain {
        /**
         * Get current RouteRequest object.
         */
        @NonNull
        RouteRequest getRequest();

        /**
         * Get source object, activity or fragment instance.
         */
        @NonNull
        Object getSource();

        @NonNull
        Context getContext();

        @Nullable
        Fragment getFragment();

        /**
         * Continue to process this route request.
         */
        @NonNull
        RouteResponse process();

        /**
         * Intercept this route request.
         */
        @NonNull
        RouteResponse intercept();
    }
}
