package com.example.asus.marimakan.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ASUS on 11/13/2017.
 */

public class Dish implements Parcelable {

    String name;
    long price;
    String description;
    int itemId;
    int imageId;
    int qty;

    protected Dish(Parcel in) {
        name = in.readString();
        price = in.readLong();
        description = in.readString();
        itemId = in.readInt();
        imageId = in.readInt();
        qty = in.readInt();
        selected = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeLong(price);
        dest.writeString(description);
        dest.writeInt(itemId);
        dest.writeInt(imageId);
        dest.writeInt(qty);
        dest.writeByte((byte) (selected ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Dish> CREATOR = new Creator<Dish>() {
        @Override
        public Dish createFromParcel(Parcel in) {
            return new Dish(in);
        }

        @Override
        public Dish[] newArray(int size) {
            return new Dish[size];
        }
    };

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    boolean selected;

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getName() {
        return name;
    }

    public Long getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public int getItemId() {
        return itemId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Dish(){
    }

    public Dish(String name, Long price, String description, int itemId, int imageId, int qty){
        this.name = name;
        this.price = price;
        this.description = description;
        this.itemId = itemId;
        this.imageId = imageId;
        this.qty = qty;
    }

}