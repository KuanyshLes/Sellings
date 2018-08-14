package kz.production.kuanysh.sellings.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by User on 12.06.2018.
 */

public class OwnerItem implements Parcelable{
    private String name;
    private String time;
    private String address;
    private String status;
    private int image;
    private String consignment;
    private String cash;

    public static final String CASH_YES="Наличный расчет";
    public static final String CASH_NO="Безналичный расчет";
    public static final String CONSIGNMENT_YES="yes";
    public static final String CONSIGNMENT_NO="no";
    public static final String STATUS_WAITING="Ожидает";
    public static final String STATUS_CONFIRMED="Принят";
    public static final String STATUS_CANCELLED="Отказано";

    public OwnerItem(String name, String time, String address,String status,String consignment,String cash) {
        this.name = name;
        this.time = time;
        this.address = address;
        this.status=status;
        this.consignment=consignment;
        this.cash=cash;
    }

    protected OwnerItem(Parcel in) {
        name = in.readString();
        time= in.readString();
        address= in.readString();
        status= in.readString();
        consignment= in.readString();
        cash= in.readString();
    }

    public static final Creator<OwnerItem> CREATOR = new Creator<OwnerItem>() {
        @Override
        public OwnerItem createFromParcel(Parcel in) {
            return new OwnerItem(in);
        }

        @Override
        public OwnerItem[] newArray(int size) {
            return new OwnerItem[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getAddress() {
        return address;
    }

    public int getImage() {
        return image;
    }

    public String getStatus() {
        return status;
    }

    public String getConsignment() {
        return consignment;
    }

    public String getCash() {
        return cash;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(time);
        dest.writeString(status);
        dest.writeString(address);
        dest.writeString(consignment);
        dest.writeString(cash);
    }
}
