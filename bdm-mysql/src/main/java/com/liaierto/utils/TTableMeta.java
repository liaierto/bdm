package com.liaierto.utils;

import java.util.HashMap;
import java.util.Map;


public class TTableMeta {
    private static  TTableMeta pTableMeta = null;
    private static  Map  mTableMeta = null;
    public TTableMeta(){
        mTableMeta = new HashMap();
    }
    public void setMeta(String pKey,Map pVal){
        mTableMeta.put(pKey, pVal);
    }

    public String getMeta(String pKey,String pFieldKey){
        Map pVal   = (Map) mTableMeta.get(pKey);
        String pFV = (String) pVal.get(pFieldKey);
        return pFV;
    }
    
    public boolean isEmpty(String pKey){
        return mTableMeta.containsKey(pKey);
    }
    
    
    public static TTableMeta getInstance(){
    	if(pTableMeta == null){
           return new TTableMeta();
    	}
    	return pTableMeta;
    }
}
