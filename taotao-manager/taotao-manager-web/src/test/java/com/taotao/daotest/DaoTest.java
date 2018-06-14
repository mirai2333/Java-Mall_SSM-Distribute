package com.taotao.daotest;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;

public class DaoTest {

	@Test
	public void test() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:spring/applicationContext-*.xml");
		TbItemMapper mapper = applicationContext.getBean(TbItemMapper.class);
		TbItem tbItem = mapper.selectByPrimaryKey((long) 816448);
		System.out.println(tbItem);
	}
}
