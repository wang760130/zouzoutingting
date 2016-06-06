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
 * @date   2016年6月4日
 */

@Entity
@Table(name="t_vccode")
public class VcCode {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private long id;
	
	@Column(name="code")
	private int code;
	
	@Column(name="phone")
	private long phone;
	
	@Column(name="used")
	private short used;
	
	@Column(name="addtime")
	private long addtime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public long getPhone() {
		return phone;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}

	public short getUsed() {
		return used;
	}

	public void setUsed(short used) {
		this.used = used;
	}

	public long getAddtime() {
		return addtime;
	}

	public void setAddtime(long addtime) {
		this.addtime = addtime;
	}


}
