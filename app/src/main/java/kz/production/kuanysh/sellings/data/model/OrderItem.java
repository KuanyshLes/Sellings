package kz.production.kuanysh.sellings.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by User on 10.06.2018.
 */

public class OrderItem implements Parcelable{
    private String number;
    private String owner;
    private String time;
    private String status;

    public OrderItem(String number, String owner, String time, String status) {
        this.number = number;
        this.owner = owner;
        this.time = time;
        this.status = status;
    }

    protected OrderItem(Parcel in) {
        number = in.readString();
        owner= in.readString();
        time= in.readString();
        status= in.readString();
    }

    public static final Creator<OrderItem> CREATOR = new Creator<OrderItem>() {
        @Override
        public OrderItem createFromParcel(Parcel in) {
            return new OrderItem(in);
        }

        @Override
        public OrderItem[] newArray(int size) {
            return new OrderItem[size];
        }
    };


    public String getNumber() {
        return number;
    }

    public String getOwner() {
        return owner;
    }

    public String getTime() {
        return time;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(number);
        dest.writeString(owner);
        dest.writeString(time);
        dest.writeString(status);
    }
}
