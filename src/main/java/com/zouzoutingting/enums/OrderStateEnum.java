package com.zouzoutingting.enums;

/**
 * Created by zhangyong on 16/6/19.
 */
public enum OrderStateEnum {

    Jinx(0,"进行中"),
    TobePayed(1,"待支付"),
    Finish(2, "已完成");

    private OrderStateEnum(int state, String desc){
        this.state = state;
        this.desc = desc;
    }

    public int getState() {
        return state;
    }

    public String getDesc() {
        return desc;
    }

    private int state;
    private String desc;
}
