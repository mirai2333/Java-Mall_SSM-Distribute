package com.taotao.rest.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.rest.component.JedisClient;
import com.taotao.rest.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper tbContentMapper;
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${REDIS_CONTENT_KEY}")
	private String REDIS_CONTENT_KEY;
	
	@Override
	public List<TbContent> findTbContents(Long id) {
		
		//查询缓存
		try {
			String json = jedisClient.hget(REDIS_CONTENT_KEY, id+"");
			if(!StringUtils.isBlank(json)) {
				List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
				return list;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		//查询数据库
		TbContentExample example = new TbContentExample();
		example.createCriteria().andCategoryIdEqualTo(id);
		List<TbContent> list = tbContentMapper.selectByExampleWithBLOBs(example);
		
		//查完存在缓存里
		try {
			jedisClient.hset(REDIS_CONTENT_KEY, id+"", JsonUtils.objectToJson(list));
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public TaotaoResult contentCacheDelete(Long cid) {
		jedisClient.hdel(REDIS_CONTENT_KEY, cid+"");
		return TaotaoResult.ok();
	}
	
	

}
