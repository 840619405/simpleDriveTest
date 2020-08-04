package com.tqc.hnkj.drivingtest.entity;

public class ErrEntity {
    String type;
    String num;
    public ErrEntity(String type, String num) {
        this.type = type;
        this.num = num;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
