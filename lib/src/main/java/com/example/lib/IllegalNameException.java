package com.example.lib;

public class IllegalNameException extends Exception {

    String name;

    public IllegalNameException(String s, String name) {
        super(s);
        this.name = name;
    }


    @Override
    public String toString() {
        return "MyException{" +
                "list=" + name +
                '}';
    }
}
