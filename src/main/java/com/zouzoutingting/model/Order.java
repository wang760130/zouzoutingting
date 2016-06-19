package com.zouzoutingting.model;

import javax.persistence.*;

/**
 * Created by zhangyong on 16/6/19.
 */
@Entity
@Table(name = "t_order")
public class Order {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="orderid")
    private long orderid;

    @Column(name="uid")
    private long uid;

    @Column(name = "code")
    private String code;

    @Column(name = "vid")
    private long vid;

    @Column(name = "status")
    private int state;

    @Column(name = "total")
    private double total;

    @Column(name = "needpay")
    private double needpay;

    @Column(name = "comment")
    private String comment;

    public long getOrderid() {
        return orderid;
    }

    public void setOrderid(long orderid) {
        this.orderid = orderid;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getVid() {
        return vid;
    }

    public void setVid(long vid) {
        this.vid = vid;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getNeedpay() {
        return needpay;
    }

    public void setNeedpay(double needpay) {
        this.needpay = needpay;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
