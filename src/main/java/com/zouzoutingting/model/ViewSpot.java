package com.zouzoutingting.model;

import javax.persistence.*;

/**
 * Created by zhangyong on 16/4/6.
 * 景点实体
 */
@Entity
@Table(name = "t_viewspot")
public class ViewSpot {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    private long id;

    @Column(name = "cityid")
    private int cityid;

    @Column(name = "name")
    private String name;

    @Column(name = "explaincount")
    private int explaincount;//讲解景点数

    @Column(name = "ecplainsize")
    private int ecplainsize;//讲解包大小

    @Column(name = "synopsis")
    private String synopsis;//简介

    @Column(name = "introduce")
    private String introduce;//详细介绍

    @Column(name = "pic")
    private String pic;//图片地址

    @Column(name = "price")
    private double price;

    @Column(name = "state")
    private int state;//状态

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCityid() {
        return cityid;
    }

    public void setCityid(int cityid) {
        this.cityid = cityid;
    }

    public int getExplaincount() {
        return explaincount;
    }

    public void setExplaincount(int explaincount) {
        this.explaincount = explaincount;
    }

    public int getEcplainsize() {
        return ecplainsize;
    }

    public void setEcplainsize(int ecplainsize) {
        this.ecplainsize = ecplainsize;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

}
