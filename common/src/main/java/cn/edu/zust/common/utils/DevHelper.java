package cn.edu.zust.common.utils;

import java.util.UUID;

public class DevHelper {

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-","");
    }

}
