package com.zouzoutingting.model;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

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
    private double ecplainsize;//讲解包大小
    
    @Column(name = "centercoord")
    private String centercoord; // 中心点坐标
    
    @Column(name = "scaling")
    private double scaling;  // 缩放比例
    
    @Column(name = "synopsis")
    private String synopsis;//简介

    @Column(name = "introduce")
    private String introduce;//详细介绍

    @Column(name = "defualtPic")
    private String defualtPic;//列表页图片
    
    @Column(name = "listPic")
    private String listPic;//详情页轮播图片地址

	@Transient
    @Column(name = "offlinepackage")
    private String offlinepackage; // 离线下载包地址
 
    @Column(name = "price")
    private double price;
    
    @Column(name = "type")
    private short type; 	// 景点类型
    
    @Column(name = "state")
    private short state;//状态
    
    @Transient
    private String[] pic;//详情页轮播图片地址数组
    
    @Transient
    private double distance; // 距离

	@Transient
	private boolean ispayed = true;//是否支付

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getCityid() {
		return cityid;
	}

	public void setCityid(int cityid) {
		this.cityid = cityid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getExplaincount() {
		return explaincount;
	}

	public void setExplaincount(int explaincount) {
		this.explaincount = explaincount;
	}

	public double getEcplainsize() {
		return ecplainsize;
	}

	public void setEcplainsize(double ecplainsize) {
		this.ecplainsize = ecplainsize;
	}

	public String getCentercoord() {
		return centercoord;
	}

	public void setCentercoord(String centercoord) {
		this.centercoord = centercoord;
	}

	public double getScaling() {
		return scaling;
	}

	public void setScaling(double scaling) {
		this.scaling = scaling;
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

	public String getDefualtPic() {
		return defualtPic;
	}

	public void setDefualtPic(String defualtPic) {
		this.defualtPic = defualtPic;
	}

	public String getListPic() {
		return listPic;
	}

	public void setListPic(String listPic) {
		this.listPic = listPic;
	}

	public String getOfflinepackage() {
		return offlinepackage;
	}

	public void setOfflinepackage(String offlinepackage) {
		this.offlinepackage = offlinepackage;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
	}

	public short getState() {
		return state;
	}

	public void setState(short state) {
		this.state = state;
	}

	public String[] getPic() {
		return pic;
	}

	public void setPic(String[] pic) {
		this.pic = pic;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public boolean ispayed() {
		return ispayed;
	}

	public void setIspayed(boolean ispayed) {
		this.ispayed = ispayed;
	}

	@Override
	public String toString() {
		return "ViewSpot [id=" + id + ", cityid=" + cityid + ", name=" + name
				+ ", explaincount=" + explaincount + ", ecplainsize="
				+ ecplainsize + ", centercoord=" + centercoord + ", scaling="
				+ scaling + ", synopsis=" + synopsis + ", introduce="
				+ introduce + ", defualtPic=" + defualtPic + ", listPic="
				+ listPic + ", offlinepackage=" + offlinepackage + ", price="
				+ price + ", state=" + state + ", pic=" + Arrays.toString(pic)
				+ ", distance=" + distance + ", ispayed=" + ispayed +"]";
	}

}
