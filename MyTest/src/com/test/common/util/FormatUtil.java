package com.test.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.test.entity.Argument;
import com.test.entity.User;
import com.test.entity.UserVO;
import com.test.service.impl.UserServiceImpl;

/**
 * 格式化对象工具类
 * @ClassName: FormatUtil 
 * @author zhoujie
 * @date 2017年3月23日 下午4:47:07
 */
public class FormatUtil {
	/**
	 * 默认值
	 */
	private static String DEFAULT_VALUE = "--"; 
	
	/**
	 * 是否格式化key为驼峰（使用范围，当key值为USER_NAME格式时使用）
	 */
	private static Boolean KEY_FORMAT = false;
	
	/**
	 * @Title: formatElements 
	 * @Description: 格式化列表信息
	 * @param list 数据
	 * @return List<?>
	 * @author zhoujie
	 * @date 2017年3月23日 下午4:45:54
	 */
	public static List<Map<String,Object>> formatElements(List<?> list) throws Exception{
		return formatElements(list,null,null,KEY_FORMAT,null);
	}
	
	/**
	 * @Title: formatElements 
	 * @Description: 格式化列表信息
	 * @param list 数据
	 * @param paraMap 参数设置
	 * @return List<?>
	 * @author zhoujie
	 * @date 2017年3月23日 下午4:45:54
	 */
	public static List<Map<String,Object>> formatElements(List<?> list,Map<String,Object> paraMap) throws Exception{
		return formatElements(list,paraMap,null,KEY_FORMAT,null);
	}
	
	/**
	 * 格式化列表信息
	 * @param list 数据
	 * @param paraMap 参数设置
	 * @param keyReplaceMap key值替换map 如 map.put("oldKey","newKey"); id --> userId
	 * @return
	 * @throws Exception
	 * @author zhoujie
	 * @date 2017年3月27日 下午5:55:09
	 */
	public static List<Map<String,Object>> formatElements(List<?> list,Map<String,Object> paraMap,Map<String,String> keyReplaceMap) throws Exception{
		return formatElements(list,paraMap,keyReplaceMap,KEY_FORMAT,null);
	}
	
	/**
	 * @Title: formatElements 
	 * @Description: 格式化列表信息
	 * @param list 数据
	 * @param paraMap 参数设置
	 * @param keyReplaceMap key值替换map 如 map.put("oldKey","newKey"); id --> userId
	 * @param b 是否将Map类型Key值格式化成驼峰形式 例如:USER_ID 可转为 userId
	 * @param defaultValue 当值为空时默认值
	 * @return List<?>
	 * @author zhoujie
	 * @date 2017年3月23日 下午4:45:54
	 */
	public static List<Map<String,Object>> formatElements(List<?> list,Map<String,Object> paraMap,Map<String,String> keyReplaceMap,boolean b,String defaultValue) throws Exception{
		if(null==defaultValue){
			defaultValue = DEFAULT_VALUE;
		}
		List<Map<String, Object>> resList = new ArrayList<Map<String,Object>>();
		if(null==list||list.size()==0)
			return resList;
		Map<String, Object> tempMap = null;
		//循环处理
		for (Object object :list) {
			tempMap = new HashMap<String, Object>();
			//判断对象类型
			if(object instanceof Map){
				//为map时
				resList.add(handleMap(object,paraMap,keyReplaceMap,b,tempMap,defaultValue));
			}else{
				//为实体类时
				resList.add(handleEntity(object,paraMap,keyReplaceMap,tempMap,defaultValue));
			}
		}
		return resList;
	}
	
	/**
	 * @Title: formatElements 
	 * @Description: 格式化单一对象信息
	 * @param object 待处理对象
	 * @return Object 
	 * @author zhoujie
	 * @date 2017年3月23日 下午4:45:54
	 */
	public static Map<String, Object> formatElements(Object object) throws Exception{
		return formatElements(object,null,null,KEY_FORMAT,null);
	}
	/**
	 * @Title: formatElements 
	 * @Description: 格式化单一对象信息
	 * @param object
	 * @param paraMap 参数设置
	 * @return Object 
	 * @author zhoujie
	 * @date 2017年3月23日 下午4:45:54
	 */
	public static Map<String, Object> formatElements(Object object,Map<String,Object> paraMap) throws Exception{
		return formatElements(object,paraMap,null,KEY_FORMAT,null);
	}
	/**
	 * @Title: formatElements 
	 * @Description: 格式化单一对象信息
	 * @param object
	 * @param paraMap 待处理字段设置
	 * @param keyReplaceMap key值替换map 如 map.put("oldKey","newKey"); id --> userId
	 * @return Object
	 * @author zhoujie
	 * @date 2017年3月23日 下午4:45:54
	 */
	public static Map<String, Object> formatElements(Object object,Map<String,Object> paraMap,Map<String,String> keyReplaceMap) throws Exception{
		return formatElements(object,paraMap,keyReplaceMap,KEY_FORMAT,null);
	}
	/**
	 * @Title: formatElements 
	 * @Description: 格式化单一对象信息
	 * @param object
	 * @param keyReplaceMap key值替换map 如 map.put("oldKey","newKey"); id --> userId
	 * @param b 是否将Map类型Key值格式化成驼峰形式 例如:USER_ID 转为 userId
	 * @param defaultValue 当值为空时默认值
	 * @return Object
	 * @author zhoujie
	 * @date 2017年3月23日 下午4:45:54
	 */
	public static Map<String, Object> formatElements(Object object,Map<String,Object> paraMap,Map<String,String> keyReplaceMap,boolean b,String defaultValue) throws Exception{
		if(null==defaultValue){
			defaultValue = DEFAULT_VALUE;
		}
		Map<String, Object> resMap = new HashMap<String,Object>();
		//判断对象类型
		if(object instanceof Map){
			//为map时
			resMap = handleMap(object,paraMap,keyReplaceMap,b,resMap,defaultValue);
		}else{
			//为实体类时
			resMap = handleEntity(object,paraMap,keyReplaceMap,resMap,defaultValue);
		}
		return resMap;
	}
	
	/**
	 * 处理map
	 * @param object
	 * @param paraMap
	 * @param keyReplaceMap
	 * @param b
	 * @param tempMap
	 * @param defaultValue
	 * @return
	 * @throws Exception
	 * @author zhoujie
	 * @date 2017年3月31日 上午10:42:02
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,Object> handleMap(Object object,Map<String,Object> paraMap,Map<String,String> keyReplaceMap,boolean b,Map<String, Object> tempMap,String defaultValue)throws Exception{
		Map<String, Object> map = (Map<String, Object>)object;
		for (Entry<String, Object> e : map.entrySet()) {
			if(null==keyReplaceMap){
				tempMap.put(b?keyFormat(e.getKey()):e.getKey(),StringUtil.isBlank(e.getValue())?defaultValue:e.getValue());
				if(null!=paraMap&&paraMap.containsKey(e.getKey())&&StringUtil.isNotBlank(e.getValue())){
					tempMap.put(b?keyFormat(e.getKey()):e.getKey(),formatObject(e.getKey(),e.getValue(),paraMap.get(e.getKey())));
				}
			}else{
				if(keyReplaceMap.containsKey(e.getKey())){
					tempMap.put(keyReplaceMap.get(e.getKey()),StringUtil.isBlank(e.getValue())?defaultValue:e.getValue());
					if(null!=paraMap&&paraMap.containsKey(e.getKey())&&StringUtil.isNotBlank(e.getValue())){
						tempMap.put(keyReplaceMap.get(e.getKey()),formatObject(e.getKey(),e.getValue(),paraMap.get(e.getKey())));
					}
				}
			}
		}
		return tempMap;
	}
	
	/**
	 * 处理实体类
	 * @param object
	 * @param paraMap
	 * @param keyReplaceMap
	 * @param tempMap
	 * @param defaultValue
	 * @return
	 * @throws Exception
	 * @author zhoujie
	 * @date 2017年3月31日 上午10:41:52
	 */
	public static Map<String,Object> handleEntity(Object object,Map<String,Object> paraMap,Map<String,String> keyReplaceMap,Map<String, Object> tempMap,String defaultValue)throws Exception{
		Class<?> cla = object.getClass();
		Field[] fields = cla.getDeclaredFields();
		for(Field field:fields){
			if(!field.getName().equals("serialVersionUID")){
				Object obj = ReflectionUtils.findMethod(cla,findGetMethod(field.getName())).invoke(object,new Object[]{});
				if(null==keyReplaceMap){//key不替换
					if(null==obj){
						tempMap.put(field.getName(),defaultValue);
						continue;
					}
					if(null!=paraMap&&paraMap.containsKey(field.getName())){
						Object value = formatObject(field.getName(),obj,paraMap.get(field.getName()));
						tempMap.put(field.getName(),StringUtil.isBlank(value)?defaultValue:value);
					}else{
						tempMap.put(field.getName(), StringUtil.isBlank(obj)?defaultValue:obj);
					}
				}else{//key替换
					if(keyReplaceMap.containsKey(field.getName())){
						if(null==obj){
							tempMap.put(keyReplaceMap.get(field.getName()),defaultValue);
							continue;
						}
						if(null!=paraMap&&paraMap.containsKey(field.getName())){
							Object value = formatObject(field.getName(),obj,paraMap.get(field.getName()));
							tempMap.put(keyReplaceMap.get(field.getName()),StringUtil.isBlank(value)?defaultValue:value);
						}else{
							tempMap.put(keyReplaceMap.get(field.getName()), StringUtil.isBlank(obj)?defaultValue:obj);
						}
					}
				}
			}
		}
		return tempMap;
	}
	
	/**
	 *  反射调用方法处理对象
	 * @param key 待处理字段key
	 * @param value 待处理字符
	 * @param object 处理方式对象
	 * @return
	 * @author zhoujie
	 * @date 2017年3月27日 下午2:01:54
	 */
	public static Object formatObject(String key,Object value,Object object){
		Object obj = null;
		try {
			if(null==object){
				return value;
			}
			if(object instanceof Method){
				Method method = (Method)object;
				obj = ReflectionUtils.invokeMethod(method,value);
			}else if(object instanceof Argument){
				Argument argument = (Argument)object;
				if(argument.getObject() instanceof Map){
					obj = invokeParamaterMap(argument,key,value);
				}else{
					obj = invokeParamaterEntity(argument,key,value);
				}
			}else{
				throw new FormatUtilException("参数类型异常，参数不符合规则！只支持Method或Argument对象,Object=["+object.getClass().getName()+"]");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null==obj?value:obj;
	}
	
	/**
	 * 调用参数为map的方法
	 * @param argument
	 * @param key
	 * @param value
	 * @return
	 * @throws Exception
	 * @author zhoujie
	 * @date 2017年3月31日 上午10:42:14
	 */
	@SuppressWarnings("unchecked")
	public static Object invokeParamaterMap(Argument argument,String key,Object value) throws Exception{
		Method method = argument.getMothod();
		Object o = argument.getObject();
		Map<String,Object> map = (Map<String,Object>)o;
		map.put(StringUtil.isNotBlank(argument.getEvaluation())?argument.getEvaluation():key, value);
		Object obj = ReflectionUtils.invokeMethod(method,map);
		if(StringUtil.isNotBlank(argument.getTarget())){
			obj = getInnerElement(argument,obj);
		}
		return obj;
	}
	
	/**
	 * 调用参数为实体类的方法
	 * @param argument
	 * @param key
	 * @param value
	 * @return
	 * @throws Exception
	 * @author zhoujie
	 * @date 2017年3月31日 上午10:42:23
	 */
	public static Object invokeParamaterEntity(Argument argument,String key,Object value) throws Exception{
		Method method = argument.getMothod();
		Object o = argument.getObject();
		Class<? extends Object> clazz = o.getClass();
		try {
			Field field = clazz.getDeclaredField(StringUtil.isNotBlank(argument.getEvaluation())?argument.getEvaluation():key);
			//将属性设置为可见
			field.setAccessible(true);
			field.set(o, value);
		} catch (Exception e) {
			String str = StringUtil.isNotBlank(argument.getEvaluation())?argument.getEvaluation():key;
			throw new FormatUtilException("获取类字段异常，class=["+clazz+"]中不存在["+str+"]字段");
		}
		Object obj = ReflectionUtils.invokeMethod(method,o);
		/*******返回类型为map或实体类时，获取其内部指定元素********/
		if(StringUtil.isNotBlank(argument.getTarget())){
			obj = getInnerElement(argument,obj);
		}
		return obj;
	}
	
	/**
	 * 获取内部元素
	 * @param argument
	 * @param obj
	 * @return
	 * @throws Exception
	 * @author zhoujie
	 * @date 2017年3月31日 上午11:05:01
	 */
	@SuppressWarnings("unchecked")
	public static Object getInnerElement(Argument argument,Object obj) throws Exception{ 
		if(obj instanceof Map){
			Map<String,Object> ret = (Map<String,Object>)obj;
			obj = ret.get(argument.getTarget());
		}else{
			Class<? extends Object> cla = obj.getClass();
			Field field = null;
			try {
				field = cla.getDeclaredField(argument.getTarget());
				field.setAccessible(true);
			} catch (Exception e) {
				throw new FormatUtilException("获取类字段异常，class=["+cla+"]中不存在argument.getTarget()=["+argument.getTarget()+"]字段");
			}
			obj = field.get(obj);
		}
		return obj;
	}
	
	/**
	 * key 格式化  例如:USER_ID 转为 userId
	 * @param key
	 * @return
	 * @author zhoujie
	 * @date 2017年3月31日 上午10:42:34
	 */
	public static String keyFormat(String key){
		StringBuffer sb = new StringBuffer() ;
		String[] array = key.toLowerCase().split("_");
		if(array.length<=1)
			return key.toLowerCase();
		for (int i = 0; i < array.length; i++) {
			if (i==0) 
				sb.append(array[i]);
			else
				sb.append(StringUtil.firstUpperCase(array[i]));
		}
		return sb.toString();
	}
	
	/**
	 * @Title: findGetMethod 
	 * @Description: 获取参数get方法名称
	 * @param paraName 参数名称
	 * @return String 返回值
	 */
	public static String findGetMethod(String paraName){
        return "get"+StringUtil.firstUpperCase(paraName);
	}
	
	/**
	 * @Title: findSetMethod 
	 * @Description: 获取参数set方法名称
	 * @param paraName 参数名称
	 * @return String 返回值
	 */
	public static String findSetMethod(String paraName){
        return "set"+StringUtil.firstUpperCase(paraName);
	}
	
	/**
	 * 测试方法
	 * @param args
	 * @throws Exception
	 * @author zhoujie
	 * @date 2017年3月23日 下午4:48:58
	 */
	public static void main(String[] args) throws Exception {
		List<Object> list = new ArrayList<Object>();
		Map<String,Object> pMap = null;
//		for(int i=0;i<5;i++){
//			pMap = new HashMap<String, Object>();
//			pMap.put("userId", 1000+i);
//			pMap.put("userType", "1"+i);
//			pMap.put("USER_NAME", "司丰"+i);
//			pMap.put("mobileNumber", "1308398245"+i);
//			pMap.put("idCardNumber", "3425325532"+i);
//			pMap.put("bankCardNumber", "622256325452365332"+i);
//			pMap.put("SEX_NAME", null);
//			pMap.put("regTime", 1467129599);
//			list.add(pMap);
//		}
		User user = null;
		for(int i=0;i<10;i++){
			user = new User();
			user.setUserId(101L+i);
			user.setUserName("欧阳丰"+i);
			user.setMobileNumber("1302365245"+i);
			user.setUserType("1"+i);
			list.add(user);
		}
//		
//		User user = new User();
//		user.setUserId(101L);
//		user.setUserName("张三丰");
//		user.setMobileNumber("1302365245");
//		user.setUserType("1");
//		list.add(user);
		
//		Map<String,Object> pMap = new HashMap<String, Object>();
//		pMap.put("User_Id", 1000);
//		pMap.put("userName", "张三丰");
//		pMap.put("mobileNumber", "1308398245");
//		pMap.put("idCardNumber", "3425322225532");
//		pMap.put("bankCardNumber", "622256325452365332");
//		pMap.put("SEX_NAME", null);
		
		Map<String,Object> map = new HashMap<String, Object>();
//		map.put("userName", "realName");
//		map.put("mobileNumber", "mobileNumber");
//		map.put("idCardNumber", "idCardNumber");
//		map.put("bankCardNumber", "bankCardNumber");
//		map.put("regTime", "date");
		map.put("userName", ReflectionUtils.findMethod(StringUtil.class, "formatUserName",new Class[]{String.class}));
		Method method = ReflectionUtils.findMethod(UserServiceImpl.class, "findNameSex",new Class[]{UserVO.class});
//		Map<String,Object> param = new HashMap<String, Object>();
//		param.put("sex", 1);
		UserVO vo = new UserVO();
		vo.setSex("1");
		Argument argument = new Argument();
		argument.setMothod(method);
		argument.setObject(vo);
		argument.setEvaluation("userType1");
		argument.setTarget("userType");
//		argument.setTarget("userType1");
		map.put("userType", argument);
		map.put("userType1", argument);
		System.out.println(list);
		Map<String,String> keyReplaceMap = new HashMap<String, String>();
		keyReplaceMap.put("userName", "realName");
		keyReplaceMap.put("mobileNumber", "mobile");
		keyReplaceMap.put("userType", "type");
//		System.out.println(FormatUtil.formatElements(list,map,true,null));
		System.out.println(FormatUtil.formatElements(list,map,keyReplaceMap,true,null));
	}
}
