package com.zouzoutingting.utils;

/**
 * 根据经纬度计算距离
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年6月5日
 */
public class DistanceUtil {

	private final static double PI = 3.14159265358979323; // 圆周率
    private final static double R = 6371229; // 地球的半径 (单位M)

    /**
     * @param lon1
     * @param lat1
     * @param lon2
     * @param lat2
     * @return 米
     */
    public static double getDistance(double lon1, double lat1, double lon2, double lat2) {
        double x, y, distance;
        x = (lon2 - lon1) * PI * R * Math.cos(((lat1 + lat2) / 2) * PI / 180) / 180;
        y = (lat2 - lat1) * PI * R / 180;
        distance = Math.hypot(x, y);
        return distance;
    }

}
