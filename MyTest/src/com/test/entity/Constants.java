package com.test.entity;

import java.lang.reflect.Method;

import com.test.common.util.ReflectionUtils;
import com.test.common.util.StringUtil;

/**
 * 状态
 */
public class Constants {
    
    /** 团队人员状态-离职 */
    public static final String team_user_status_0 = "0";
    /** 团队人员状态-在职*/
    public static final String team_user_status_1 = "1";
    
    /** 结算状态*/
    public enum IsBalanceStatus{
        IS_BALANCE_0("0","未结算"),//未结算
        IS_BALANCE_1("1","已结算");//已结算

        private String key;
        private String value;
        
        IsBalanceStatus(String key,String value){
            this.key = key;
            this.value = value;
        }
        public String getKey(){
            return this.key;
        }
        public String getValue(){
            return this.value;
        }

        public static IsBalanceStatus getValue(String key){
            for(IsBalanceStatus isBalanceStatus:IsBalanceStatus.values()){
                if(isBalanceStatus.getKey().equals(key)){
                    return isBalanceStatus;
                }
            }
            return null;
        }
    }
    public static void main(String[] args) throws Exception{
    	Object isBalanceStatus = Constants.IsBalanceStatus.getValue("1");
    	Class clazz = isBalanceStatus.getClass();
    	System.out.println(clazz);
    	int ENUM = 0x00004000;
    	if((clazz.getModifiers() & ENUM) != 0){
    		System.out.println("是枚举");
//    		isBalanceStatus.getValue();
    	}
    	System.out.println(clazz.getName());
    	IsBalanceStatus isBS = Constants.IsBalanceStatus.getValue("1");
    	Method method = ReflectionUtils.findMethod(clazz, "getValue",new Class[]{});
    	System.out.println(method.invoke(isBalanceStatus,new Object[0]));
//    	System.out.println(isBalanceStatus.getValue());
	}
}
