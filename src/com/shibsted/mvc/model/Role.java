package com.shibsted.mvc.model;

public enum Role {
    PAGE_1,
    PAGE_2,
    PAGE_3;

    @Override
    public String toString() {
        switch(this) {
            case PAGE_1:
                return "Page 1";
            case PAGE_2:
                return "Page 2";
            case PAGE_3:
                return "Page 3";
            default:
                return null;
        }
    }
}
