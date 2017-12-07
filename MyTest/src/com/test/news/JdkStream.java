package com.test.news;

import java.time.Clock;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.test.common.util.ListUtil;
import com.test.common.util.StringUtil;

public class JdkStream {
	public static void main(String[] args) {
		List<String> nums = (List<String>) ListUtil.newArrayList();
		System.out.println(nums);
		List<String> stringCollection = new ArrayList<>();
		stringCollection.add("ddd2");
		stringCollection.add("aaa2");
		stringCollection.add("bbb1");
		stringCollection.add("aaa1");
		stringCollection.add("bbb3");
		stringCollection.add("ccc");
		stringCollection.add("bbb2");
		stringCollection.add("ddd1");
		stringCollection.stream().filter((s)->s.startsWith("b"));
		System.out.println(stringCollection.stream().filter((s)->s.startsWith("b")).collect(Collectors.toList()));
		Clock clock = Clock.systemDefaultZone();
		System.out.println(clock.millis());
		System.out.println(System.currentTimeMillis());
		
		stringCollection
	    .stream()
	    .map(StringUtil::firstUpperCase)
	    .sorted((a, b) -> b.compareTo(a))
	    .forEach(System.out::println);
	}
}
