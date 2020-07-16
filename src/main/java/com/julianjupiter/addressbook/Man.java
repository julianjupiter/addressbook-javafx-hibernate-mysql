package com.julianjupiter.addressbook;

import java.util.List;

public class Man {
    public static void main(String[] args) {
        var x = new Man().hi();
    }

    List<String> hi() {
        List<String> x;

        try {
            String y = null;
            int i = y.length();
            x = List.of("y");
        } finally {

        }
        return x;
    }

}
