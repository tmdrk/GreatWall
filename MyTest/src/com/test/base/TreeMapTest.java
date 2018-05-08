package com.test.base;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

public class TreeMapTest {
	public static void main(String[] args) {
		SortedMap<String, Object> p = new TreeMap<String, Object>();  
        p.put("nonce_str", "23234234");  
        p.put("mch_billno", "lfskjd23423");  
        p.put("mch_id", "mch_id");  
        p.put("wxappid", "usd86ddfs76fsfdsf");  
        p.put("re_openid", "sodffkslkdfjslkdfslkdf98fsd");  
        p.put("total_amount", "20");  
        p.put("total_num", "1");  
        p.put("client_ip", "127.0.0.1");  
        p.put("act_name","huhu");  
        p.put("send_name", "huhuaad");  
        p.put("wishing", "sdfsd");  
        p.put("remark","kjsdfhksfjs");
        System.out.println(getUrlParamsByMap(p));
        TreeMap<String, Object> pp = new TreeMap<String, Object>();  
        pp.put("nonce_str", "23234234");  
        pp.put("mch_billno", "lfskjd23423");  
        pp.put("mch_id", "mch_id");  
        pp.put("wxappid", "usd86ddfs76fsfdsf");  
        pp.put("re_openid", "sodffkslkdfjslkdfslkdf98fsd");  
        pp.put("total_amount", "20");  
        pp.put("total_num", "1");  
        pp.put("client_ip", "127.0.0.1");  
        pp.put("act_name","huhu");  
        pp.put("send_name", "huhuaad");  
        pp.put("remark","kjsdfhksfjs");
        pp.put("wishing", "sdfsd");  
        System.out.println(getUrlParamsByMap(pp));
        getMap();
	}
	private static void getMap() {
		Map map = new HashMap();
        StringBuffer sb = new StringBuffer();
        map = new TreeMap<String, Object>(map);
        String s = sb.toString();
        if (s.endsWith("&")) {
            s = StringUtils.substringBeforeLast(s, "&");
        }
	}
    /**
     * 将map转换成url
     * @param map
     * @return
     */
    public static String getUrlParamsByMap(Map<String, Object> map) {
        if (map == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        map = new TreeMap<String, Object>(map);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if(entry.getValue() == null){
                continue;
            }
            sb.append(entry.getKey() + "=" + entry.getValue());
            sb.append("&");
        }
        String s = sb.toString();
        if (s.endsWith("&")) {
            s = StringUtils.substringBeforeLast(s, "&");
        }
        return s;
    }
}
