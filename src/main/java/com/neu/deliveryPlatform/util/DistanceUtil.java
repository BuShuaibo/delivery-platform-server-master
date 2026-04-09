package com.neu.deliveryPlatform.util;

import com.neu.deliveryPlatform.entity.Location;

import static java.lang.Math.sqrt;

/**
 * @Author asiawu
 * @Date 2023-04-21 22:09
 * @Description:
 */
public class DistanceUtil {

    public static Double distance(Location x,Location y){
        return sqrt(
                    (x.getLatitude()-y.getLatitude())*(x.getLatitude()-y.getLatitude())
                    +(x.getLongitude()-y.getLongitude())*(x.getLongitude()-y.getLongitude())
        );
    }
}
