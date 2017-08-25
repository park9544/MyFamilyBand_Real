package com.usepjh92.user.myfamilyband.item;



public class Item {

    public String title;
    public String desc;
    public String imgUrl;

    public Item() {
    }

    public Item(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }

    public Item(String title, String desc, String imgUrl) {
        this.title = title;
        this.desc = desc;
        this.imgUrl = imgUrl;
    }
}
