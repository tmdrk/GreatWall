package com.test.entity;

import java.lang.reflect.Method;

/**
 * 参数类
 * @ClassName: Argument 
 * @author zhoujie
 * @date 2017年3月27日 下午6:39:25
 */
public class Argument {
	/**
	 * 目的方法
	 */
	private Method mothod;
	/**
	 * 额外参数对象(map/实体类)
	 */
	private Object object;
	/**
	 * 额外参数对象中，待赋值对象名称(为空时默认使用原类（key/属性）值)
	 */
	private String evaluation;
	/**
	 * 返回类型为map/实体对象时，指定获取某个特定字段值（若返回值是单一对象，可忽略该字段）
	 */
	private String target;
	
	public Method getMothod() {
		return mothod;
	}
	public void setMothod(Method mothod) {
		this.mothod = mothod;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	public String getEvaluation() {
		return evaluation;
	}
	public void setEvaluation(String evaluation) {
		this.evaluation = evaluation;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
}
