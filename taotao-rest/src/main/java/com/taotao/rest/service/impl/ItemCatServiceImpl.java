package com.taotao.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.rest.pojo.CatNode;
import com.taotao.rest.pojo.ItemCatResult;
import com.taotao.rest.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper tbItemCatMapper;

	@Override
	public ItemCatResult getItemCatList() {
		ItemCatResult result = new ItemCatResult();
		result.setData(getItemCatList(0l));
		return result;
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

}
