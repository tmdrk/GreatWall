package com.test.common.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;
import com.test.entity.User;

//import com.api.common.constants.CoreConstants;

public class StringUtil {
    /**
     * 姓名后边的名改为*+
     * @param userName
     * @return
     */
    public static String formatUserName(String userName) {
    	if(StringUtils.isEmpty(userName)){
    		return "";
    	}
    	int length = userName.length();
    	if(length<2){
    		return userName;
    	}else if(length <= 3){
    		return userName.substring(0,1)+userName.substring(1,userName.length()).replaceAll("[\u4E00-\u9FA5]","*");
    	}else{
    		return userName.substring(0,2)+userName.substring(2,userName.length()).replaceAll("[\u4E00-\u9FA5]","*");
    	}
    	
    }
    public static String formatUserName(int userName) {
    	return "测试"+userName;
    }
    
    /**
     * 手机4--7为改为*
     * @param mobile
     * @return
     */
    public static String formatMobile(String mobile) {
    	if(StringUtils.isEmpty(mobile)){
    		return "";
    	}
    	String start = mobile.substring(0, 3);
    	String end = mobile.substring(7, 11);
    	return start+"****"+end;   	
    }
    /**
     * 银行卡号保留后四位，剩余改为*
     * @param bankCard
     * @return
     */
    public static String formatBankCard(String bankCard) {
    	if(StringUtils.isEmpty(bankCard)){
    		return "";
    	}
    	int length = bankCard.length();
    	if(length<16){
    		return "";
    	}else{
    		return "**************"+bankCard.substring(length-4);
    	}
    }
    /**
     * 身份证号码保留前六位，剩余改为*
     * @param identityCode
     * @return
     */
    public static String formatIdentityCode(String identityCode) {
    	if(StringUtils.isEmpty(identityCode)){
    		return "";
    	}
    	int length = identityCode.length();
    	if(length<15){
    		return "";
    	}else{
    		return identityCode.substring(0,6)+"**************";
    	}
    }

	/**
	 * list 转换成 String
	 * @param stringList
	 * @return
     */
	public static String listToString(List<String> stringList){
		if (stringList==null) {
			return null;
		}
		StringBuilder result=new StringBuilder();
		boolean flag=false;
		for (String string : stringList) {
			if (flag) {
				result.append(",");
			}else {
				flag=true;
			}
			result.append(string);
		}
		return result.toString();
	}
	 /**
     * 银行卡隐藏部分内容方法(保留前6后4),如：6227002020930171128---->622700*********1128
     * @param start 前面保留长度
     * @param end 末尾保留长度
     * @param bankCard 银行卡号
     * @return 处理过的银行卡号
     */
    public static String formatBankCard(int start,int end,String bankCard) {
       if(StringUtils.isEmpty(bankCard) || bankCard.length() <= start + end){
    	   return bankCard;
       }
       int startNum = bankCard.length() - start - end;
       StringBuffer sb = new StringBuffer();
       for(int i = 0;i < bankCard.length();i++){
    	   if(i >= start && i < start + startNum){
    		   sb.append("*");
    		   continue;
    	   }
    	   sb.append(bankCard.charAt(i));
       }
       return sb.toString();
    }
    /**
     * @Title: formatMobile
     * @Description: 将手机号码格式化为130****2563
     * @param start 前面保留长度
     * @param end 末尾保留长度
     * @param mobile 手机号
     * @return String
     */
    public static String formatMobile(int start,int end,String mobile) {
        if(StringUtils.isEmpty(mobile) || mobile.length() <= start + end){
     	   return mobile;
        }
        int startNum = mobile.length() - start - end;
        StringBuffer sb = new StringBuffer();
        for(int i = 0;i < mobile.length();i++){
     	   if(i >= start && i < start + startNum){
     		   sb.append("*");
     		   continue;
     	   }
     	   sb.append(mobile.charAt(i));
        }
        return sb.toString();
     }
    /**
     * @Title: randomStr
     * @Description: 获取随机字符，必须为数字和字母的组合区分大小写
     * @return String
     */
    public static String randomStr(int length){//传入的字符串的长度
        StringBuilder builder = new StringBuilder(length);
        for(int i = 0; i < length; i++){
            
            int r = (int) (Math.random()*3);
            int rn1=(int)(48+Math.random()*10);
            int rn2=(int)(65+Math.random()*26);
            int rn3=(int)(97+Math.random()*26);
            
            switch(r){
            case 0:   
                builder.append((char)rn1);
                break;
            case 1:
                builder.append((char)rn2);
                break;
            case 2:
                builder.append((char)rn3);
                break;
            }
        }
        return builder.toString();
    }    
    
    /**
     * @Title: randomStr
     * @Description: 获取随机字符，必须为数字和字母的组合不区分大小写
     * @return String
     */
    public static String randomString(int length){//传入的字符串的长度
        StringBuilder builder = new StringBuilder(length);
        for(int i = 0; i < length; i++){
            
            int r = (int) (Math.random()*2);
            int rn1=(int)(48+Math.random()*10);
            int rn2=(int)(65+Math.random()*26);
            
            switch(r){
            case 0:   
                builder.append((char)rn1);
                break;
            case 1:
                builder.append((char)rn2);
                break;
            }
        }
        return builder.toString();
    }    
    
    public static String randomNum(int length){//传入的字符串的长度
        StringBuilder builder = new StringBuilder(length);
        for(int i = 0; i < length; i++){
            int rn1=(int)(48+Math.random()*10);
            builder.append((char)rn1);
        }
        return builder.toString();
    }  
    
    /**
     * @Title: formatMoneyTwo
     * @Description: 将钱精确到小数点后两位不够补零
     * @return String
     */
    public static String formatMoneyTwo(BigDecimal big) {
		double yuan = big.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		DecimalFormat df = new DecimalFormat("0.00");
		String s = df.format(yuan);
		return s;
	}
    /**
     * @Title: formatMoneyFour
     * @Description: 将钱精确到小数点后四位不够补零
     * @return String
     */
    public static String formatMoneyFour(BigDecimal big) {
		double yuan = big.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
		DecimalFormat df = new DecimalFormat("0.0000");
		String s = df.format(yuan);
		return s;
	}
    
	/**
	 * 首字符转换为大写
	 * 
	 * @param str
	 * @return
	 */
	public static String firstUpperCase(String str) {
		char[] cs=str.toCharArray();
		if(cs[0]>= 'a' && cs[0] <= 'z'){
			cs[0]-=32;
		}
		return String.valueOf(cs);
	}
	
	/**
	 * @Title: formatString 
	 * @Description: 格式化字符串
	 * @param  str 待格式化字符
	 * @param  type 字符类型
	 * @return String
	 */
	public static String formatString(String str,String type){
		switch(type){
			case "realName":return isCompoundSurname(str)?formatStringByRange(str,2,0):formatStringByRange(str,1,0);
			case "mobileNumber":return formatStringByRange(str,3,4);
			case "idCardNumber":return formatStringByRange(str,6,4);
			case "bankCardNumber":return formatStringByRange(str,6,4);
			case "amount":return AmountUtil.AmountFormatThousand(str);
			case "date":return DateUtil.getDateFormat(str);
			case "time":return DateUtil.getTimeFormat(str);
			case "userType":return userTypeAddOne(str);
		}
		return str;
	}
	
	/**
	 * 字符串隐藏部分内容方法
	 * @param str 待处理字符串
	 * @param start 前面保留长度
	 * @param end 末尾保留长度
	 * @return 处理过的字符串
	 */
    public static String formatStringByRange(String str, int start,int end) {
	    if(StringUtils.isEmpty(str) || str.length() <= start + end){
		    return str;
	    }
	    int startNum = str.length() - start - end;
	    StringBuffer sb = new StringBuffer();
	    for(int i = 0;i < str.length();i++){
		    if(i >= start && i < start + startNum){
			    sb.append("*");
			    continue;
		    }
		    sb.append(str.charAt(i));
	    }
	    return sb.toString();
    }
    
    /**
     * @Title: isCompoundSurname 
     * @Description: 判断姓名是否为复姓
     * @param name 姓名全称
     * @return
     */
    public static boolean isCompoundSurname(String name){
//    	if(StringUtils.isBlank(name)||name.length()<=2)
//    		return false;
//    	name = name.substring(0,2);
//    	String[] surnames = CoreConstants.COMPOUND_SURNAME.split("\\|");
//    	for(String surname:surnames){
//    		if(surname.equals(name))
//    			return true;
//    	}
    	return false;
    }
    
    public static String userTypeAddOne(String userType){
    	int type = Integer.parseInt(userType)+1;
    	return String.valueOf(type);
    }
    
    /**
     * 判断对象是否为空
     * @param obj
     * @return
     * @author zhoujie
     * @date 2017年3月27日 下午2:13:29
     */
    public static Boolean isBlank(Object obj){
    	if(null==obj||StringUtils.isBlank(obj.toString())){
    		return true;
    	}
    	return false;
    }
    /**
     * 判断对象是否不为空
     * @param obj
     * @return
     * @author zhoujie
     * @date 2017年3月27日 下午2:13:33
     */
    public static Boolean isNotBlank(Object obj){
    	if(isBlank(obj)){
    		return false;
    	}
    	return true;
    }
    
    /**
     * 对象toString
     * @param obj 适用map类和javaBean对象
     * @return
     * @author zhoujie
     * @date 2017年6月14日 下午8:55:51
     */
    public static String toString(Object obj){
    	return toString(obj,false);
    }
    
    /**
     * 对象toString
	 * @param obj 适用map类和javaBean对象
     * @param ignoreNull 是否忽略null显示 true:忽略 false:不忽略
     * @return
     * @author zhoujie
     * @date 2017年6月14日 下午8:53:37
     */
    public static String toString(Object obj,boolean ignoreNull){
    	if(null==obj){
    		return "";
    	}
    	StringBuffer sb = new StringBuffer();

    	if(checkBaseType(obj)){
    		return obj.toString();
    	}
    	
    	//如果是map
    	if(obj instanceof Map){
    		Map<String, Object> map = (Map<String, Object>)obj;
    		for (Entry<String, Object> e : map.entrySet()) {
    			if(!ignoreNull){
    				sb.append(e.getKey()+":"+toString(e.getValue())+";");
    			}else{
    				if(null!=e.getValue()){
    					sb.append(e.getKey()+":"+toString(e.getValue())+";");
    				}
    			}
    		}
    		return obj.getClass().getSimpleName()+"=["+sb.toString()+"]";
    	}
    	//如果是实体
    	Class<?> cla = obj.getClass();
		Field[] fields = cla.getDeclaredFields();
		for(Field field:fields){
			if(!field.getName().equals("serialVersionUID")){
				try {
					field.setAccessible(true);
					if(!ignoreNull){
						sb.append(field.getName()+":"+toString(field.get(obj))+";");
					}else{
	    				if(null!=field.get(obj)){
	    					sb.append(field.getName()+":"+toString(field.get(obj))+";");
	        			}
	    			}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
    	return obj.getClass().getSimpleName()+"=["+sb.toString()+"]";
    }
    
    /**
     * 检查对象是否属于基本/常见简单类型
     * @param obj
     * @return
     * @author zhoujie
     * @date 2017年6月29日 下午8:37:58
     */
	private static boolean checkBaseType(Object obj) {
		if(obj instanceof Integer){
    		System.out.println("Integer类型");
    		return true;
    	}else if(obj instanceof String){
    		System.out.println("String类型");
    		return true;
    	}else if(obj instanceof Long){
    		System.out.println("Long类型");
    		return true;
    	}else if(obj instanceof Short){
    		System.out.println("Short类型");
    		return true;
    	}else if(obj instanceof Byte){
    		System.out.println("Byte类型");
    		return true;
    	}else if(obj instanceof Character){
    		System.out.println("Character类型");
    		return true;
    	}else if(obj instanceof Double){
    		System.out.println("Double类型");
    		return true;
    	}else if(obj instanceof Float){
    		System.out.println("Float类型");
    		return true;
    	}else if(obj instanceof Boolean){
    		System.out.println("Boolean类型");
    		return true;
    	}else if(obj instanceof BigInteger){
    		System.out.println("BigInteger类型");
    		return true;
    	}else if(obj instanceof BigDecimal){
    		System.out.println("BigDecimal类型");
    		return true;
    	}else if(obj instanceof Date){
    		System.out.println("Date类型");
    		obj = ((Date) obj).getTime()/1000;
    		return true;
    	}
		return false;
	}
    
    public static String toStringIgnoreNull(Object obj){
    	if(null==obj){
    		return "";
    	}
    	StringBuffer sb = new StringBuffer();
    	//如果是map
    	if(obj instanceof Map){
    		Map<String, Object> map = (Map<String, Object>)obj;
    		for (Entry<String, Object> e : map.entrySet()) {
    			if(null!=e.getValue()){
    				sb.append(e.getKey()+":"+e.getValue()+";");
    			}
    		}
    		return obj.getClass().getSimpleName()+"=["+toString(sb)+"]";
    	}
    	//如果是实体
    	Class<?> cla = obj.getClass();
		Field[] fields = cla.getDeclaredFields();
		for(Field field:fields){
			if(!field.getName().equals("serialVersionUID")){
					try {
						field.setAccessible(true);
						if(null!=field.get(obj)){
							sb.append(field.getName()+":"+toString(field.get(obj))+";");
						}
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
			}
		}
    	return obj.getClass().getSimpleName()+"=["+sb.toString()+"]";
    }
    
    public static void main(String[] args) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	
    	map.put("id", 12);
    	map.put("userName", "zhoujie");
    	map.put("sex", null);
    	map.put("status", "");
    	map.put("createtTime", new Date());
    	map.put("chars", new String[]{"kwrk","skdfh","sdfh"});
    	User user = new User();
    	user.setUserName("王五");
//		System.out.println(toString(map));
		System.out.println(toString(new Date()));
//		System.out.println(toStringIgnoreNull(map));
//		System.out.println(toString(user));
//		System.out.println(toStringIgnoreNull(user));
//		System.out.println(toString("sdf"));
//		System.out.println(toString(11));
//		System.out.println(toString(11L));
//		System.out.println(toString('a'));
//		System.out.println(toString(true));
//		System.out.println(toString(new BigDecimal("4")));
//		System.out.println(toString(new Date()));
//		System.out.println(int.class.isPrimitive());
//		System.out.println(byte.class.isPrimitive());
//		System.out.println(char.class.isPrimitive());
//		System.out.println(boolean.class.isPrimitive());
//		
//		System.out.println(short.class.isPrimitive());
//		System.out.println(float.class.isPrimitive());
//		System.out.println(double.class.isPrimitive());
//		System.out.println(long.class.isPrimitive());
//		
//		System.out.println(String.class.isPrimitive());
//		System.out.println(String[].class.isPrimitive());
//		System.out.println(Map.class.isPrimitive());
//		System.out.println(Integer.class.isPrimitive());
//		
//		System.out.println(int.class.isPrimitive());
//		System.out.println(byte.class.isPrimitive());
//		System.out.println(char.class.isPrimitive());
//		System.out.println(boolean.class.isPrimitive());
//		
//		System.out.println(short.class.isPrimitive());
//		System.out.println(float.class.isPrimitive());
//		System.out.println(double.class.isPrimitive());
//		System.out.println(long.class.isPrimitive());
//		
//		System.out.println(String.class.isPrimitive());
//		System.out.println(String[].class.isPrimitive());
//		System.out.println(Map.class.isPrimitive());
//		System.out.println(Integer.class.isPrimitive());
		
//		System.out.println(isWrapClass(long.class));
//		System.out.println(isWrapClass(String.class));
		
		User[] users = new User[3];
		for(int i=0;i<users.length;i++){
			User u = new User();
			u.setUserId(i);
			users[i] = u;
		}
		for(User u:users){
			System.out.println(u.getUserId());
		}
	}
    
    public static boolean isWrapClass(Class clz) { 
        try { 
           return ((Class) clz.getField("TYPE").get(null)).isPrimitive(); 
        } catch (Exception e) { 
            return false; 
        } 
    } 
} 
