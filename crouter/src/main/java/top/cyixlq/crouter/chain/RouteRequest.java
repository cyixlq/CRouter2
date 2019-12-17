package top.cyixlq.crouter.chain;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

public class RouteRequest implements Parcelable {
    private static final int INVALID_CODE = -1;
    private Uri uri;
    private Bundle extras;
    private int flags;
    private Uri data;
    private String type;
    private String action;
    @Nullable
    private RouteCallback routeCallback;
    private int requestCode = INVALID_CODE;
    private int enterAnim = INVALID_CODE;
    private int exitAnim = INVALID_CODE;
    @Nullable
    private Bundle activityOptionsBundle;


    public RouteRequest(Uri uri) {
        this.uri = uri;
    }

    protected RouteRequest(Parcel in) {
        uri = in.readParcelable(Uri.class.getClassLoader());
        extras = in.readBundle();
        flags = in.readInt();
        data = in.readParcelable(Uri.class.getClassLoader());
        type = in.readString();
        action = in.readString();
        requestCode = in.readInt();
        enterAnim = in.readInt();
        exitAnim = in.readInt();
        activityOptionsBundle = in.readBundle();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(uri, flags);
        dest.writeBundle(extras);
        dest.writeInt(flags);
        dest.writeParcelable(data, flags);
        dest.writeString(type);
        dest.writeString(action);
        dest.writeInt(requestCode);
        dest.writeInt(enterAnim);
        dest.writeInt(exitAnim);
        dest.writeBundle(activityOptionsBundle);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RouteRequest> CREATOR = new Creator<RouteRequest>() {
        @Override
        public RouteRequest createFromParcel(Parcel in) {
            return new RouteRequest(in);
        }

        @Override
        public RouteRequest[] newArray(int size) {
            return new RouteRequest[size];
        }
    };

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public Bundle getExtras() {
        return extras;
    }

    public void setExtras(Bundle extras) {
        this.extras = extras;
    }

    public int getFlags() {
        return flags;
    }

    @SuppressWarnings("unused")
    public void setFlags(int flags) {
        this.flags = flags;
    }

    public void addFlags(int flags) {
        this.flags |= flags;
    }

    public Uri getData() {
        return data;
    }

    public void setData(Uri data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Nullable
    public RouteCallback getRouteCallback() {
        return routeCallback;
    }

    public void setRouteCallback(@Nullable RouteCallback routeCallback) {
        this.routeCallback = routeCallback;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        if (requestCode < 0) {
            this.requestCode = INVALID_CODE;
        } else {
            this.requestCode = requestCode;
        }
    }

    public int getEnterAnim() {
        return enterAnim;
    }

    public void setEnterAnim(int enterAnim) {
        if (enterAnim < 0) {
            this.enterAnim = INVALID_CODE;
        } else {
            this.enterAnim = enterAnim;
        }
    }

    public int getExitAnim() {
        return exitAnim;
    }

    public void setExitAnim(int exitAnim) {
        if (exitAnim < 0) {
            this.exitAnim = INVALID_CODE;
        } else {
            this.exitAnim = exitAnim;
        }
    }

    @Nullable
    public Bundle getActivityOptionsBundle() {
        return activityOptionsBundle;
    }

    public void setActivityOptionsBundle(@Nullable Bundle activityOptionsBundle) {
        this.activityOptionsBundle = activityOptionsBundle;
    }
}
