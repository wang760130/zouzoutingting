package com.zouzoutingting.model;

import javax.persistence.*;

/**
 * Created by zhangyong on 16/4/8.
 * 景点讲解点数据实体
 */
@Entity
@Table(name = "t_spot")
public class Spot {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    private long id;

    /**
     * 景点id
     */
    @Column(name = "vid")
    private int vid;

    /**
     * 讲解顺序
     */
    @Column(name = "sequence")
    private int sequence;

    /**
     * 讲解名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 图片地址
     */
    @Column(name = "pic")
    private String pic;

    /**
     * 音频地址
     */
    @Column(name = "audio")
    private String audio;

    /**
     * 经度
     */
    @Column(name = "longitude")
    private Double longitude;

    /**
     * 纬度
     */
    @Column(name = "latitude")
    private Double latitude;

    /**
     * 讲解半径
     */
    @Column(name = "radius")
    private Double radius;

    /**
     * 是否付费
     */
    @Column(name = "isfree")
    private int isfree;

    /**
     * 状态
     */
    @Column(name = "state")
    private int state;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getVid() {
        return vid;
    }

    public void setVid(int vid) {
        this.vid = vid;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getRadius() {
        return radius;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
    }

    public int getIsfree() {
        return isfree;
    }

    public void setIsfree(int isfree) {
        this.isfree = isfree;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
