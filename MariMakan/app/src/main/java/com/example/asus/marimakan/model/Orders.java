package com.example.asus.marimakan.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ASUS on 11/13/2017.
 */

public class Orders implements Parcelable {

    String itemIds;
    String date;
    String names;
    long totalOrderAmount;

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public static Creator<Orders> getCREATOR() {
        return CREATOR;
    }

    public Orders(){
    }

    protected Orders(Parcel in) {
        itemIds = in.readString();
        date = in.readString();
        totalOrderAmount = in.readLong();
    }

    public static final Creator<Orders> CREATOR = new Creator<Orders>() {
        @Override
        public Orders createFromParcel(Parcel in) {
            return new Orders(in);
        }

        @Override
        public Orders[] newArray(int size) {
            return new Orders[size];
        }
    };

    public String getItemIds() {
        return itemIds;
    }

    public void setItemIds(String itemIds) {
        this.itemIds = itemIds;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getTotalOrderAmount() {
        return totalOrderAmount;
    }

    public void setTotalOrderAmount(long totalOrderAmount) {
        this.totalOrderAmount = totalOrderAmount;
    }

    public Orders(String itemIds, String names, String date, long totalOrderAmount){
        this.itemIds = itemIds;
        this.names = names;
        this.date = date;
        this.totalOrderAmount = totalOrderAmount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(itemIds);
        dest.writeString(date);
        dest.writeString(names);
        dest.writeLong(totalOrderAmount);
    }
}
