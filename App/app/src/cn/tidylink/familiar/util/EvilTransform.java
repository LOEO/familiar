package cn.tidylink.familiar.util;

/**
 * Created by TSQ on 2014/9/9.
 */
public class EvilTransform {

    final static double pi = 3.14159265358979324;

    //
    //
    // a = 6378245.0, 1/f = 298.3
    // b = a * (1 - f)
    // ee = (a^2 - b^2) / a^2;
    final static double a = 6378245.0;
    final static double ee = 0.00669342162296594323;


    //
    // World Geodetic System ==> Mars Geodetic System
    public static double[] transform(double wgLat, double wgLon)
    {
        double mgLat=0;
        double mgLon=0;
        if (outOfChina(wgLat, wgLon))
        {
            mgLat = wgLat;
            mgLon = wgLon;

        }else{
            double dLat = transformLat(wgLon - 105.0, wgLat - 35.0);
            double dLon = transformLon(wgLon - 105.0, wgLat - 35.0);
            double radLat = wgLat / 180.0 * pi;
            double magic = Math.sin(radLat);
            magic = 1 - ee * magic * magic;
            double sqrtMagic = Math.sqrt(magic);
            dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
            dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
            mgLat = wgLat + dLat;
            mgLon = wgLon + dLon;
        }
        double[] point={mgLon,mgLat};
        return point;
    }

    private static boolean outOfChina(double lat, double lon)
    {
        if (lon < 72.004 || lon > 137.8347)
            return true;
        if (lat < 0.8293 || lat > 55.8271)
            return true;
        return false;
    }

    private static double transformLat(double x, double y)
    {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * pi) + 40.0 * Math.sin(y / 3.0 * pi)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * pi) + 320 * Math.sin(y * pi / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    private static double transformLon(double x, double y)
    {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * pi) + 40.0 * Math.sin(x / 3.0 * pi)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * pi) + 300.0 * Math.sin(x / 30.0 * pi)) * 2.0 / 3.0;
        return ret;
    }

    final static double x_pi = 3.14159265358979324 * 3000.0 / 180.0;

    private static double[] bd_encrypt(double gg_lat, double gg_lon)
    {
        double x = gg_lon, y = gg_lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
        double bd_lon = z* Math.cos(theta) + 0.0065;//0.0065;  再加上一点更接近百度的转换结果，2014年9月9日 13:38:52
        double bd_lat = z* Math.sin(theta) + 0.006; //0.006;
        double[] point={Math.round(bd_lon * 1000000.0)/1000000.0, Math.round(bd_lat * 1000000.0)/1000000.0};
        return point;
    }

    private static double[] bd_decrypt(double bd_lat, double bd_lon)
    {
        double x = bd_lon - 0.0065, y = bd_lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
        double gg_lon = z * Math.cos(theta);
        double gg_lat = z * Math.sin(theta);
        double[] point={gg_lon,gg_lat};
        return point;
    }

    //世界坐标系→百度坐标系，经度、纬度
    public static double[] wg2bd(double wgLon, double wgLat)
    {
        double[] mgLonLat = transform(wgLat,wgLon);
        double[] bdLonLat = bd_encrypt(mgLonLat[1], mgLonLat[0]);
        return  bdLonLat;
    }

    //世界坐标系→火星坐标系，经度、纬度
    public static double[] wg2mg(double wgLon, double wgLat)
    {
        double[] mgLonLat = transform(wgLat,wgLon);
        return  mgLonLat;
    }

    //火星坐标系→百度坐标系，经度、纬度
    public static double[] mg2bd(double mgLon, double mgLat)
    {
        double[] bdLonLat = bd_encrypt(mgLat, mgLon);
        return  bdLonLat;
    }

    //百度坐标系→火星坐标系，经度、纬度
    public static double[] bd2mg(double bdLon, double bdLat)
    {
        double[] mgLonLat = bd_decrypt(bdLat, bdLon);
        return  mgLonLat;
    }
}