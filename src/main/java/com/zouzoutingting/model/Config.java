package com.zouzoutingting.model;

import javax.persistence.*;

/**
 * Created by zhangyong on 17/2/4.
 * 配置
 */
@Entity
@Table(name="t_config")
public class Config {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    private long id;

    @Column(name="kname")
    private String key;

    @Column(name = "val")
    private String val;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
