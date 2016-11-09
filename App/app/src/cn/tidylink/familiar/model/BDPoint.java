package cn.tidylink.familiar.model;

import cn.tidylink.familiar.util.EvilTransform;

/**
 * Created by TSQ on 2014/6/9.
 */
public class BDPoint{
    private String lon = "";//����:lon
    private String lat = "";//γ��:lat
    private String time = "";//ʱ��:time
    private String error = "99.99";//��λ���� ԽСԽ��//�����ʶ��0ΪGPS��λ����Ϊ�޴���1Ϊ���綨λ����Ϊλ�ò�׼ȷ���д���
    private String distance = "";//����һ��ľ���,distance
    private String speed = "";//�ٶ�:speed
    private String battery = "?";//����
    private String bearing = "";//����
    private String altitude = "";//�߶�
    //��������
    private String lon_m = "";
    private String lat_m = "";
    //�ٶ�����
    private String lon_b = "";
    private String lat_b = "";

    public BDPoint(String lon,String lat,String time,String error,String speed,String bearing,String altitude){
        this.lon = lon;
        this.lat = lat;
        this.time = time;
        this.error = error;
        this.speed = speed;
        this.bearing = bearing;
        this.altitude = altitude;
        setLonLat_mb();
    }

    public BDPoint(String lon,String lat,String time,String error){
        this.lon = lon;
        this.lat = lat;
        this.time = time;
        this.error = error;
        setLonLat_mb();
    }

    public BDPoint(String lon,String lat){
        this.lon = lon;
        this.lat = lat;
        setLonLat_mb();
    }

    public BDPoint(){}

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getBattery() {
        return battery;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public String getBearing() {
        return bearing;
    }

    public void setBearing(String bearing) {
        this.bearing = bearing;
    }

    public String getLon_m() {
        return lon_m;
    }

    public String getLat_m() {
        return lat_m;
    }

    public String getLon_b() {
        return lon_b;
    }

    public String getLat_b() {
        return lat_b;
    }

    private void setLonLat_mb()
    {
        double flon = Double.parseDouble(lon);
        double flat = Double.parseDouble(lat);
        double[] ff = EvilTransform.wg2mg(flon, flat);
        lon_m = String.format("%.6f", ff[0]);
        lat_m = String.format("%.6f", ff[1]);
        ff = EvilTransform.mg2bd(ff[0],ff[1]);
        lon_b = String.format("%.6f", ff[0]);
        lat_b = String.format("%.6f", ff[1]);
    }
}