package com.zouzoutingting.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年5月2日
 */
@Entity
@Table(name="t_appversion")
public class AppVersion {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private long id;
	
	@Column(name="os")
	private String os;
	
	@Column(name="version")
	private int version;
	
	@Column(name="minversion")
	private int minVersion;
	
	@Column(name="versionname")
	private String versionName;
	
	@Column(name="publicdate")
	private Date publicDate;
	
	@Column(name="updatelog")
	private String updateLog;
	
	@Column(name="updateurl")
	private String updateUrl;

	@Transient
	private boolean isUpdate;
	
	@Transient
	private boolean isForceUpdate;
	
	@Transient
	private String publicDateStr;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getMinVersion() {
		return minVersion;
	}

	public void setMinVersion(int minVersion) {
		this.minVersion = minVersion;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public Date getPublicDate() {
		return publicDate;
	}

	public void setPublicDate(Date publicDate) {
		this.publicDate = publicDate;
	}

	public String getUpdateLog() {
		return updateLog;
	}

	public void setUpdateLog(String updateLog) {
		this.updateLog = updateLog;
	}

	public String getUpdateUrl() {
		return updateUrl;
	}

	public void setUpdateUrl(String updateUrl) {
		this.updateUrl = updateUrl;
	}

	public boolean isUpdate() {
		return isUpdate;
	}

	public void setUpdate(boolean isUpdate) {
		this.isUpdate = isUpdate;
	}

	public boolean isForceUpdate() {
		return isForceUpdate;
	}

	public void setForceUpdate(boolean isForceUpdate) {
		this.isForceUpdate = isForceUpdate;
	}

	public String getPublicDateStr() {
		return publicDateStr;
	}

	public void setPublicDateStr(String publicDateStr) {
		this.publicDateStr = publicDateStr;
	}

}
