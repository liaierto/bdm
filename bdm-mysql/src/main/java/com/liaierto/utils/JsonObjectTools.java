package com.liaierto.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonObjectTools {
    
    public static JSONObject getJSObj(String pStr){
        
        return JSONObject.fromObject(pStr);
        
    }

    public static JSONArray getJSAObj(String pStr){
        
        return JSONArray.fromObject(pStr);
    }
}
