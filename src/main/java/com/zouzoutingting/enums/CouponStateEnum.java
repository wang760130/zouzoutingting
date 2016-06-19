package com.zouzoutingting.enums;

/**
 * Created by zhangyong on 16/6/19.
 * 代金券状态值枚举
 */
public enum CouponStateEnum {
    Available(0,"可用"),
    Frozen(1,"冻结"),
    Used(2, "已使用");

    private CouponStateEnum(int state, String desc){
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
