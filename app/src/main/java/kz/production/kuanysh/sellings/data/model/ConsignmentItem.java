package kz.production.kuanysh.sellings.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by User on 12.06.2018.
 */

public class ConsignmentItem implements Parcelable {
    private String name;
    private String time;
    private String price;
    private int image;

    public ConsignmentItem(String name, String time, String price) {
        this.name = name;
        this.time = time;
        this.price = price;
    }
    protected ConsignmentItem(Parcel in) {
        name = in.readString();
        time= in.readString();
        price= in.readString();
        image= in.readInt();
    }

    public static final Creator<ConsignmentItem> CREATOR = new Creator<ConsignmentItem>() {
        @Override
        public ConsignmentItem createFromParcel(Parcel in) {
            return new ConsignmentItem(in);
        }

        @Override
        public ConsignmentItem[] newArray(int size) {
            return new ConsignmentItem[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getPrice() {
        return price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(time);
        dest.writeString(price);
        dest.writeInt(image);
    }
}
