package com.zouzoutingting.utils;


/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年8月19日
 */
public class ClientVersion {
	
	private String version;
	
	public ClientVersion(String version) {
		this.version = version;
	}
	
	public String getVersion() {
		return this.version;
	}
	
	@Override
	public String toString() {
		return "ClientVersion [version=" + version + "]";
	}

	public boolean isNotLowerThan(ClientVersion anotherVersion) {
		String[] versionParts = version.split("\\.");
		String[] anotherVersionParts = anotherVersion.getVersion().split("\\.");
		for (int index = 0; index < versionParts.length && index < anotherVersionParts.length; index++) {
			if (Integer.parseInt(versionParts[index]) < Integer.parseInt(anotherVersionParts[index])) {
				return false;
			} else if (Integer.parseInt(versionParts[index]) > Integer.parseInt(anotherVersionParts[index])) {
				return true;
			}
		}
		if (versionParts.length < anotherVersionParts.length) {
			return false;
		}
		return true;
	}
	
	public static void main(String[] args) {
		ClientVersion a = new ClientVersion("3.2.3");
		System.out.println(a.isNotLowerThan(new ClientVersion("2.2.3.1")));
	}
}
