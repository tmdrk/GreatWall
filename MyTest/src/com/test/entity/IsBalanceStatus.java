package com.test.entity;
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
    
    public static void main(String[] args) {
		System.out.println(IsBalanceStatus.IS_BALANCE_0);
		System.out.println(IsBalanceStatus.IS_BALANCE_0.getKey());
		System.out.println(IsBalanceStatus.IS_BALANCE_0.getValue());
		System.out.println(IsBalanceStatus.getValue("1").getValue());
	}
 }