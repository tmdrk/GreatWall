package com.test.common.util;

import java.util.Date;
import java.util.Locale;

public class StringFormat {
	public static void main(String[] args) {
		String expire_seconds = "123434545";
		String action_name = null;
		String scene_id = null;
		String strJson = String.format("{"+(expire_seconds==null?"%1$s":"\"expire_seconds\": %1$s, ")+"\"action_name\": \"%2$s\", \"action_info\": {\"scene\": {\"scene_id\": %3$d}}}",expire_seconds==null?"":expire_seconds,action_name,scene_id);
		System.out.println(strJson);
		
		String str=null;  
	    str=String.format("Hi,%s", "王力");  
	    System.out.println(str);  
	    str=String.format("Hi,%s:%s.%s", "王南","王力","王张");            
	    System.out.println(str);                           
	    System.out.printf("字母a的大写是：%c %n", 'A');  
	    System.out.printf("3>7的结果是：%b %n", 3>7);  
	    System.out.printf("100的一半是：%d %n", 100/2);  
	    System.out.printf("100的16进制数是：%x %n", 100);  
	    System.out.printf("100的8进制数是：%o %n", 100);  
	    System.out.printf("50元的书打8.5折扣是：%f 元%n", 50*0.85);  
	    System.out.printf("上面价格的16进制数是：%a %n", 50*0.85);  
	    System.out.printf("上面价格的指数表示：%e %n", 50*0.85);  
	    System.out.printf("上面价格的指数和浮点数结果的长度较短的是：%g %n", 50*0.85);  
	    System.out.printf("上面的折扣是%d%% %n", 85);  
	    System.out.printf("字母A的散列码是：%h %n", 'A'); 
	    Date date=new Date(); 
	    String strDate=String.format(Locale.US,"英文月份简称：%tb",date);       
	    System.out.println(strDate);                                                                              
	    System.out.printf("本地月份简称：%tb%n",date);  
	    //B的使用，月份全称  
	    str=String.format(Locale.US,"英文月份全称：%tB",date);  
	    System.out.println(str);  
	    System.out.printf("本地月份全称：%tB%n",date);  
	    //a的使用，星期简称  
	    str=String.format(Locale.US,"英文星期的简称：%ta",date);  
	    System.out.println(str);  
	    //A的使用，星期全称  
	    System.out.printf("本地星期的简称：%tA%n",date);  
	    //C的使用，年前两位  
	    System.out.printf("年的前两位数字（不足两位前面补0）：%tC%n",date);  
	    //y的使用，年后两位  
	    System.out.printf("年的后两位数字（不足两位前面补0）：%ty%n",date);  
	    //j的使用，一年的天数  
	    System.out.printf("一年中的天数（即年的第几天）：%tj%n",date);  
	    //m的使用，月份  
	    System.out.printf("两位数字的月份（不足两位前面补0）：%tm%n",date);  
	    //d的使用，日（二位，不够补零）  
	    System.out.printf("两位数字的日（不足两位前面补0）：%td%n",date);  
	    //e的使用，日（一位不补零）  
	    System.out.printf("月份的日（前面不补0）：%te",date);  
	    
	    
	    //H的使用  
	    System.out.printf("2位数字24时制的小时（不足2位前面补0）:%tH%n", date);  
	    //I的使用  
	    System.out.printf("2位数字12时制的小时（不足2位前面补0）:%tI%n", date);  
	    //k的使用  
	    System.out.printf("2位数字24时制的小时（前面不补0）:%tk%n", date);  
	    //l的使用  
	    System.out.printf("2位数字12时制的小时（前面不补0）:%tl%n", date);  
	    //M的使用  
	    System.out.printf("2位数字的分钟（不足2位前面补0）:%tM%n", date);  
	    //S的使用  
	    System.out.printf("2位数字的秒（不足2位前面补0）:%tS%n", date);  
	    //L的使用  
	    System.out.printf("3位数字的毫秒（不足3位前面补0）:%tL%n", date);  
	    //N的使用  
	    System.out.printf("9位数字的毫秒数（不足9位前面补0）:%tN%n", date);  
	    //p的使用  
	    str = String.format(Locale.US, "小写字母的上午或下午标记(英)：%tp", date);  
	    System.out.println(str);   
	    System.out.printf("小写字母的上午或下午标记（中）：%tp%n", date);  
	    //z的使用  
	    System.out.printf("相对于GMT的RFC822时区的偏移量:%tz%n", date);  
	    //Z的使用  
	    System.out.printf("时区缩写字符串:%tZ%n", date);  
	    //s的使用  
	    System.out.printf("1970-1-1 00:00:00 到现在所经过的秒数：%ts%n", date);  
	    //Q的使用  
	    System.out.printf("1970-1-1 00:00:00 到现在所经过的毫秒数：%tQ%n", date);  
	}
}
