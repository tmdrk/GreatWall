package com.test.common.util;

/**
 * 格式化对象工具类异常
 * @ClassName: FormatUtilException 
 * @author zhoujie
 * @date 2017年3月31日 上午11:57:21
 */
public class FormatUtilException extends Exception{

	private static final long serialVersionUID = 8727171815448864035L;

	public FormatUtilException() {
	    super();
	}
	
	public FormatUtilException(String message, Throwable cause) {
		super(message, cause);
	}

	public FormatUtilException(Throwable cause) {
		super(cause);
	}
	
	public FormatUtilException(String msg)  {  
        super(msg);  
    }  
}
