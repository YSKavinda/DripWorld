package com.codevibe.web.dripworld.constants;

public enum FilterFormats {
    A_Z("A-Z"),
    Z_A("Z-A"),
    H_L("Price High to Low"),
    L_H("Price Low to High"),
    LATEST("Latest");

    private final String name;

     FilterFormats(String name){this.name = name;}

}
