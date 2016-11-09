package cn.tidylink.familiar.util;

import java.util.regex.Pattern;

/**
 * Created by LT on 2015-05-10.
 */
public class StringUtil {
    public static boolean isIp(String ip){
        Pattern pattern = Pattern.compile("((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)");
        if(pattern.matcher(ip).matches()){
            return true;
        }
        return false;
    }

    public static boolean isNum(String str){
        Pattern pattern = Pattern.compile("^\\+?[1-9][0-9]*$");
        if( !pattern.matcher(str).matches() ){
            return false;
        }
        return true;
    }

    //首字母转小写
    public static String toLowerCaseFirstOne(String s)
    {
        if(Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }
    //首字母转大写
    public static String toUpperCaseFirstOne(String s)
    {
        if(Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }
}
