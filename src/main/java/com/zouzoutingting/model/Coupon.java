package com.zouzoutingting.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by zhangyong on 16/6/19.
 * 代金券
 */
@Entity
@Table(name = "t_coupon")
public class Coupon {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="couponid")
    private long couponid;

    @Column(name="code")
    private String code;

    @Column(name="amount")
    private Double amount;

    @Column(name = "state")
    private int state;

    @Column(name = "starttime")
    private Date starttime;

    @Column(name = "endtime")
    private Date endtime;

    public long getCouponid() {
        return couponid;
    }

    public void setCouponid(long couponid) {
        this.couponid = couponid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }
}
