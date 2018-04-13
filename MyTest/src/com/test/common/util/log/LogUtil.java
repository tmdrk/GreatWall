package com.test.common.util.log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;

/**
 * 
 * @ClassName: LogUtil  
 * @Description: TODO 
 * @author zhoujie  
 * @date 2018年4月11日
 */
public class LogUtil{
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, InstantiationException, InvocationTargetException {
		SettlementVo oldSV = new SettlementVo();
		oldSV.setId(1);
		oldSV.setName("张三");
		oldSV.setNickName("皮鞋");
		oldSV.setCurrent("喂马");
		oldSV.setStartTime(new Date());
		oldSV.setMoney(new BigDecimal(34));
		SettlementVo newSV = new SettlementVo();
		newSV.setId(2);
		newSV.setName("李四");
		newSV.setCurrent("");
		newSV.setStartTime(new Date(10000));
		newSV.setMoney(new BigDecimal(34.000));
		SettlementLog slog = new SettlementLog();
		slog.setId(12);
		slog.setSettlementId("TICAMERO2343545232");
		slog.setOrderId(2018030434);
		
		SettlementVo oldSV2 = new SettlementVo();
		oldSV2.setId(101);
		oldSV2.setName("王明");
		oldSV2.setNickName("夜叉");
		oldSV2.setCurrent("姑姑");
		oldSV2.setStartTime(new Date());
		oldSV2.setMoney(new BigDecimal(222));
		SettlementVo newSV2 = new SettlementVo();
		newSV2.setId(33);
		newSV2.setName("李四");
		newSV2.setCurrent("");
		newSV2.setStartTime(new Date(10000000));
		newSV2.setMoney(new BigDecimal(554.000));
		SettlementLog slog2 = new SettlementLog();
		slog2.setId(12);
		slog2.setSettlementId("TICAMERO4983759834");
		slog2.setOrderId(2019031234);
		
		List<SettlementLog> list = compareAndAssembleLog(oldSV,newSV,slog);
		for(SettlementLog sl:list){
			System.out.println(sl.toString());
		}
		
		System.out.println("-----------------------------------");
		
		List<SettlementVo> oList = new ArrayList<SettlementVo>();
		oList.add(oldSV);
		oList.add(oldSV2);
		List<SettlementVo> nList = new ArrayList<SettlementVo>();
		nList.add(newSV);
		nList.add(newSV2);
		List<SettlementLog> tList = new ArrayList<SettlementLog>();
		tList.add(slog);
		tList.add(slog2);
		
		List<SettlementLog> list2 = compareAndAssembleLog(oList,nList,slog);
		for(SettlementLog sl:list2){
			System.out.println(sl.toString());
		}
	}
	
	/**
	 * 比较和组装不一致字段
	 * @Title: compareAndAssembleLog 
	 * @Description: TODO 
	 * @param oList 修改前对象list集
	 * @param nList 修改后对象list集
	 * @param tList 封装对象list集（需要继承 LogVO类）
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws InvocationTargetException List<T>
	 * @author zhoujie
	 * @date 2018年4月12日上午9:52:03
	 */
	public static <E,T extends LogVo> List<T> compareAndAssembleLog(List<E> oList,List<E> nList,List<T> tList) throws IllegalArgumentException, IllegalAccessException, InstantiationException, InvocationTargetException{
		if(CollectionUtils.isEmpty(oList)||CollectionUtils.isEmpty(nList)||CollectionUtils.isEmpty(tList)){
			throw new RuntimeException("比较对象不能为空!");
		}
		if(oList.size()!=nList.size()||oList.size()!=tList.size()){
			throw new RuntimeException("比较对象大小不一致，无法比较!");
		}
		List<T> retList = new ArrayList<T>();
		for(int i=0;i<oList.size();i++){
			List<T> list = compareAndAssembleLog(oList.get(i),nList.get(i),tList.get(i));
			retList.addAll(list);
		}
		return retList;
	}
	
	public static <E,T extends LogVo> List<T> compareAndAssembleLog(List<E> oList,List<E> nList,T t) throws IllegalArgumentException, IllegalAccessException, InstantiationException, InvocationTargetException{
		if(CollectionUtils.isEmpty(oList)||CollectionUtils.isEmpty(nList)){
			throw new RuntimeException("比较对象不能为空!");
		}
		if(oList.size()!=nList.size()){
			throw new RuntimeException("比较对象大小不一致，无法比较!");
		}
		List<T> retList = new ArrayList<T>();
		for(int i=0;i<oList.size();i++){
			List<T> list = compareAndAssembleLog(oList.get(i),nList.get(i),t);
			retList.addAll(list);
		}
		return retList;
	}
	
	/**
	 * 比较和组装不一致字段
	 * @Title: compareAndAssembleLog 
	 * @Description: TODO 
	 * @param o 修改前对象
	 * @param n 修改后对象
	 * @param t 封装对象（需要继承 LogVO类）
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws InvocationTargetException List<T>
	 * @author zhoujie
	 * @date 2018年4月11日下午7:11:17
	 */
	@SuppressWarnings({ "unchecked" })
	public static <E,T extends LogVo> List<T> compareAndAssembleLog(E o,E n,T t) throws IllegalArgumentException, IllegalAccessException, InstantiationException, InvocationTargetException{
		if(!o.getClass().equals(n.getClass())){
			throw new RuntimeException("传入比较对象的类型不匹配 !");
		}
		List<T> list = new ArrayList<T>();
		// 获取实体类的所有属性，返回Field数组  
        Field[] oldFields = o.getClass().getDeclaredFields();
        Field[] newFields = n.getClass().getDeclaredFields();
        for(int i=0;i<oldFields.length;i++){
        	//设置私有字段可访问
        	oldFields[i].setAccessible(true);
        	newFields[i].setAccessible(true);
        	String oldName = oldFields[i].getName();//old字段名
        	String newName = newFields[i].getName();//old字段名
        	String oldValue = oldFields[i].get(o)==null?null:oldFields[i].get(o).toString();//old字段值
        	String newValue = newFields[i].get(n)==null?null:newFields[i].get(n).toString();//new字段值
        	if(oldName.equals(newName)&&!compareValue(oldValue,newValue)){
        		//创建封装类
        		LogVo log = t.getClass().newInstance();
        		//封装对象
        		BeanUtils.copyProperties(log, t);
        		log.setFieldName(oldName);
        		log.setOldValue(oldValue);
        		log.setNewValue(newValue);
        		list.add((T)log);
        	}
        }
        return list;
	}
	/**
	 * 比较两个字符串
	 * @Title: compareValue 
	 * @Description: TODO 
	 * @param ov
	 * @param nv
	 * @return boolean
	 * @author zhoujie
	 * @date 2018年4月11日下午7:10:54
	 */
	public static boolean compareValue(String ov,String nv){
		if(ov==nv){
			return true;
		}
		if((ov!=null&&nv!=null)&&(ov.equals(nv))){
			return true;
		}
		return false;
	}
}
