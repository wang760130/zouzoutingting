package com.zouzoutingting.enums;

/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年7月24日
 */
public enum SpotEnum {

	EXPLAIN((short)0),	// 讲解点
	TOILET((short)1),	// 厕所
	POINT((short)2),	// 拐点(推荐路线)
	LINE((short)3),		// 路线
	PICTURE((short)4);	// 图片，仅当景点类型为图片是才有

	private short type;
	
	private SpotEnum(short type) {
		this.type = type;
	}

	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
	}
	
}
