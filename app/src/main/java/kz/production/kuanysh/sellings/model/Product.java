package kz.production.kuanysh.sellings.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by User on 10.06.2018.
 */

public class Product implements Parcelable{
    private String name;
    private int count;
    private int price;

    public Product(String name, int count, int price) {
        this.name = name;
        this.count = count;
        this.price = price;
    }

    protected Product(Parcel in) {
        name = in.readString();
        count= in.readInt();
        price= in.readInt();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubCategory)) return false;

        SubCategory artist = (SubCategory) o;

        return getName() != null ? getName().equals(artist.getTitle()) : artist.getTitle() == null;

    }


    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };


    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(count);
        dest.writeInt(price);
    }
}
