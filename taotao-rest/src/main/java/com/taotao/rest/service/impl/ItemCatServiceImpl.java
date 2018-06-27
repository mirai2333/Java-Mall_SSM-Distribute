package com.taotao.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.rest.component.JedisClient;
import com.taotao.rest.pojo.CatNode;
import com.taotao.rest.pojo.ItemCatResult;
import com.taotao.rest.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper tbItemCatMapper;
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${REDIS_SIDE_CONTENT_CAT_KEY}")
	private String REDIS_SIDE_CONTENT_CAT_KEY;

	@Override
	public String getItemCatList() {
		
		//先插缓存
		try {
			String resultJson = jedisClient.get(REDIS_SIDE_CONTENT_CAT_KEY);
			if(!StringUtils.isBlank(resultJson)) {
				return resultJson;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		//查询数据库
		ItemCatResult result = new ItemCatResult();
		result.setData(getItemCatList(0l));
		String resultJson = JsonUtils.objectToJson(result);
		
		//数据缓存
		try {
			jedisClient.set("REDIS_SIDE_CONTENT_CAT_KEY", resultJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultJson;
	}

	// 递归查询商品类目
	@SuppressWarnings("unchecked")
	private List<CatNode> getItemCatList(Long parentId) {

		// 执行按照parentID查询
		TbItemCatExample example = new TbItemCatExample();
		example.createCriteria().andParentIdEqualTo(parentId);
		List<TbItemCat> list = tbItemCatMapper.selectByExample(example);
		// 根据查询结果创建要返回的列表
		@SuppressWarnings("rawtypes")
		List itemCatResult = new ArrayList<>();
		// 查询不超过14个类目
		int index = 0;
		for (TbItemCat tbItemCat : list) {
			if (index >= 14)
				break;
			if (tbItemCat.getIsParent()) {
				CatNode result = new CatNode();
				result.setUrl("/products/" + tbItemCat.getId() + ".html");
				if (tbItemCat.getParentId() == 0) {
					result.setName(
							"<a href='/products/" + tbItemCat.getId() + ".html'>" + tbItemCat.getName() + "</a>");
					index++;
				} else
					result.setName(tbItemCat.getName());
				result.setItem(getItemCatList(tbItemCat.getId()));
				itemCatResult.add(result);
			} else {
				String item = "/products/" + tbItemCat.getId() + ".html|" + tbItemCat.getName();
				itemCatResult.add(item);
			}
		}
		return itemCatResult;
	}

	@Override
	public TaotaoResult sideContentCatCacheDelete() {
		jedisClient.del(REDIS_SIDE_CONTENT_CAT_KEY);
		return TaotaoResult.ok();
	}

}
