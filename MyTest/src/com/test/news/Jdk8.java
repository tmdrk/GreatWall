package com.test.news;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;

public class Jdk8 {
	public static void main(String[] args) {
//		Converter<String, Integer> converter = Integer::valueOf;
//		Integer converted = converter.convert("123");
//		System.out.println(converted);   // 123
//		System.out.println(converter.sqrt(32));
//		
//		Converter<String, Integer> converter1 = Integer::parseInt;
//		Integer converted1 = converter1.convert("12");
//		System.out.println(converted1);   // 123
		String[] str = new String[]{"6","232"};
		System.out.println(String.join("-", str));
		System.out.println("a039".codePointBefore(2));
		
//		Converter<String, StringUtil> converter2 = StringUtil::formatUserName;
//		String converted2 = converter2.convert("张小凡");
//		System.out.println(converted2);   // 123
		
		final int num = 1;
		Converter<Integer, String> stringConverter =
		        (from) -> String.valueOf(from + num);
		System.out.println(stringConverter.convert(354));
		
		Predicate<String> predicate = (s) -> s.length()>0;
		System.out.println(predicate.test("4544"));
		
		Map<String,Object> map = new ConcurrentHashMap<String, Object>();
		map.put("name", "张华");
		map.put("id", 123);
		map.put("sex", "男");
		for(Entry<String, Object> it:map.entrySet()){
			System.out.println(it.getValue());
			if("name".equals(it.getKey())){
				map.remove("name");
			}
		}
		List<String> list = new ArrayList<String>();
		list.add("234");
		list.add("45");
		list.add("23354");
//		for(String val:list){
//			System.out.println(val);
//			if("234".equals(val)){
//				list.remove(val);
//			}
//		}
		for(String val:list){
			System.out.println(val);
		}
		
	}
}
@FunctionalInterface
interface Converter<F, T> {
    T convert(F from);
    default double sqrt(int a) {
        return Math.sqrt(a);
    }
}
