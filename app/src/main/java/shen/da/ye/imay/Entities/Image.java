package shen.da.ye.imay.Entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cy on 2017/9/4 0004 17:49
 */

public class Image implements Parcelable {
    public String name;
    public String path;
    public long id;

    public Image(long id, String name, String path) {
        this.id = id;
        this.name = name;
        this.path = path;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(path);
    }

    public static final Parcelable.Creator<Image> CREATOR = new Parcelable.Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel source) {
            return new Image(source);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    private Image(Parcel in) {
        id = in.readLong();
        name = in.readString();
        path = in.readString();
    }
}
