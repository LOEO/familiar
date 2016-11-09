package cn.tidylink.familiar.util;

import cn.tidylink.familiar.model.BDPoint;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LocationUtils {
    static double DEF_PI = 3.14159265359; // PI
    static double DEF_2PI= 6.28318530712; // 2*PI
    static double DEF_PI180= 0.01745329252; // PI/180.0
    static double DEF_R =6370693.5; // radius of earth
/*    public static double getAngle(BDPoint A,BDPoint B,BDPoint C){
        double a = GetShortDistance(Double.parseDouble(B.getLon()),Double.parseDouble(B.getLat()),Double.parseDouble(C.getLon()),Double.parseDouble(C.getLat()));
        double b = GetShortDistance(Double.parseDouble(A.getLon()),Double.parseDouble(A.getLat()),Double.parseDouble(C.getLon()),Double.parseDouble(C.getLat()));
        double c = GetShortDistance(Double.parseDouble(A.getLon()),Double.parseDouble(A.getLat()),Double.parseDouble(B.getLon()),Double.parseDouble(B.getLat()));
        double radians = (c*c + a*a - b*b)/(2*a*c);
        double angle = Math.abs(Math.acos(radians)*57.3);
        return angle;
    }*/
    public static double GetShortDistance(BDPoint p1,BDPoint p2)
    {
        return GetShortDistance(Double.parseDouble(p1.getLon()), Double.parseDouble(p1.getLat()), Double.parseDouble(p2.getLon()), Double.parseDouble(p2.getLat()));
    }

    public static double GetShortDistance(double lon1, double lat1, double lon2, double lat2)
    {
        double ew1, ns1, ew2, ns2;
        double dx, dy, dew;
        double distance;
        // �Ƕ�ת��Ϊ����
        ew1 = lon1 * DEF_PI180;
        ns1 = lat1 * DEF_PI180;
        ew2 = lon2 * DEF_PI180;
        ns2 = lat2 * DEF_PI180;
        // ���Ȳ�
        dew = ew1 - ew2;
        // ���綫��������180 �ȣ����е���
        if (dew > DEF_PI)
            dew = DEF_2PI - dew;
        else if (dew < -DEF_PI)
            dew = DEF_2PI + dew;
        dx = DEF_R * Math.cos(ns1) * dew; // �������򳤶�(��γ��Ȧ�ϵ�ͶӰ����)
        dy = DEF_R * (ns1 - ns2); // �ϱ����򳤶�(�ھ���Ȧ�ϵ�ͶӰ����)
        // ���ɶ�����б�߳�
        distance = Math.sqrt(dx * dx + dy * dy);
        return distance;
    }
    public static double GetLongDistance(double lon1, double lat1, double lon2, double lat2)
    {
        double ew1, ns1, ew2, ns2;
        double distance;
        // �Ƕ�ת��Ϊ����
        ew1 = lon1 * DEF_PI180;
        ns1 = lat1 * DEF_PI180;
        ew2 = lon2 * DEF_PI180;
        ns2 = lat2 * DEF_PI180;
        // ���Բ�ӻ����������еĽ�(����)
        distance = Math.sin(ns1) * Math.sin(ns2) + Math.cos(ns1) * Math.cos(ns2) * Math.cos(ew1 - ew2);
        // ������[-1..1]��Χ�ڣ��������
        if (distance > 1.0)
            distance = 1.0;
        else if (distance < -1.0)
            distance = -1.0;
        // ���Բ�ӻ�����
        distance = DEF_R * Math.acos(distance);
        return distance;
    }
    /**
     * MD5 ����
     */
    public static String getMD5Str(String str) {
        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance("MD5");

            messageDigest.reset();

            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException caught!");
            System.exit(-1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        byte[] byteArray = messageDigest.digest();

        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }

        return md5StrBuff.toString();
    }
}

