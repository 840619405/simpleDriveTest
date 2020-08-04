package com.tqc.hnkj.drivingtest.entity;

import android.widget.TextView;

public class GvItemEntity {
    private int iv;
    private String text;
    public GvItemEntity(int iv, String text) {
        this.iv = iv;
        this.text = text;
    }
    public int getIv() {
        return iv;
    }

    public void setIv(int iv) {
        this.iv = iv;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
