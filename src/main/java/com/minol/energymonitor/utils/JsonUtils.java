package com.minol.energymonitor.utils;

import org.json.JSONObject;

public class JsonUtils {
    public static String fillResultString(Integer status,String msg,Object result){
        JSONObject jsonObject=new JSONObject(){{
            put("status",status);
            put("msg",msg);
            put("result",result);
        }};
        return jsonObject.toString();
    }
}
