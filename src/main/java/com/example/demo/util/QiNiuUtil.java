package com.example.demo.util;


import com.qiniu.storage.Region;

/**
 * @author Crazyxu
 * @Create 2020-56-2020/9/16-19:56
 */
public class QiNiuUtil {
    private static final String HUADONG = "huadong";

    private static final String HUABEI = "huabei";

    private static final String HUANAN = "huanan";

    private static final String BEIMEI = "beimei";

    private static final String DONGNANYA = "dongnanya";
    /**
     * 得到机房的对应关系
     * @param zone 机房名称
     * @return Region
     */
    public static Region getRegion(String zone){
        zone = zone.toLowerCase();
        if(HUADONG.equals(zone)){
            return Region.region0();
        } else if(HUABEI.equals(zone)){
            return Region.region1();
        } else if(HUANAN.equals(zone)){
            return Region.region2();
        }  else {
            try {
                throw new Exception("没有配置存储区域,或者存储区域配置不对");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
