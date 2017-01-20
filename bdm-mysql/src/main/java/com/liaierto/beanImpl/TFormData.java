//package com.liaierto.beanImpl;
//
//import java.util.ArrayList;
//import java.util.Hashtable;
//import java.util.List;
//import java.util.Map;
//
//
//import com.liaierto.bean.IFormData;
//
//public class TFormData implements IFormData{
//
//    List<Map<String,Object>> mList = new ArrayList<Map<String,Object>>();
//    public List<Map<String,Object>> getList() {
//        return mList;
//    }
//
//
//    public void addItem(Map<String,Object> pItem) {
//        mList.add(pItem);
//    }
//
//    public Map<String,Object> getRowItem(int index) {
//        Map<String,Object> pItem = (Map<String,Object>) mList.get(index);
//        return pItem;
//    }
//
//    public int getRowCount() {
//        return mList.size();
//    }
//
//
//    public static TFormData getInstance(){
//        return new TFormData();
//    }
//
//	public void changeValue(String pKey,String pYes, String pNo) {
//		int pSize = mList.size();
//        for(int i=0;i<pSize;i++){
//            Map<String,Object> pItem = getRowItem(i);
//            String pValue = pItem.get(pKey).toString();
//            if("1".equals(pValue)){
//            	pItem.put(pKey, pYes);
//            }else{
//            	pItem.put(pKey, pNo);
//            }
//        }
//	}
//
//}
