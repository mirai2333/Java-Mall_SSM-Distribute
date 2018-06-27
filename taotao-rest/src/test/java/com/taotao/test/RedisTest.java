package com.taotao.test;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.taotao.rest.component.JedisClient;

public class RedisTest {
	public static void main(String[] args) {
		testItemParamQuery();
	}
	
	//测试规格查询
	private static void testItemParamQuery() {
		AbstractApplicationContext ac = new ClassPathXmlApplicationContext("classpath:/spring/applicationContext-*.xml");
		JedisClient jedisClient = ac.getBean(JedisClient.class);
		//jedisClient.set("test", "mimia");
		System.out.println(jedisClient.get("REDIS_SIDE_CONTENT_CAT_KEY"));
		ac.close();
	}
}
