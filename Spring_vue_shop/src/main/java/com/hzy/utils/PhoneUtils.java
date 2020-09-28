package com.hzy.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhoneUtils {

    public static List<Map<String,String>> getTags(String tag){
        List<Map<String,String>> tags = new ArrayList<>();
        if(tag!=null){
            String[] results = tag.split("&");
            Map<String,String> map;
            for (String result : results) {
                map = new HashMap<>();
                map.put("name",result);
                tags.add(map);
            }
        }
        return tags;
    }
}
