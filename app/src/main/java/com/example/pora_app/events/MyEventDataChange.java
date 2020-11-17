package com.example.pora_app.events;

public class MyEventDataChange {
    private String data;
    private MyEnumType myDataType;

    public enum MyEnumType {newdata, delete}

    public MyEventDataChange(String data, MyEnumType myDataType) {
        this.data = data;
        this.myDataType = myDataType;
    }

    public String getData() {
        return data;
    }

    public MyEnumType getMyDataType() {
        return myDataType;
    }

    @Override
    public String toString() {
        return "MyEventDataChange{" +
                "data='" + data + '\'' +
                ", myDataType=" + myDataType +
                '}';
    }
}
