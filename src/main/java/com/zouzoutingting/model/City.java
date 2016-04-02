package com.zouzoutingting.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年3月28日
 */
@Entity
@Table(name="t_city")
public class City {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="ename")
	private String ename;
	
	@Column(name="synopsis")
	private String synopsis;
	
	@Column(name="pic")
	private String pic;
	
	@Column(name="state")
	private short state;

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

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public short getState() {
		return state;
	}

	public void setState(short state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "City [id=" + id + ", name=" + name + ", ename=" + ename
				+ ", synopsis=" + synopsis + ", pic=" + pic + ", state="
				+ state + "]";
	}
	
}
