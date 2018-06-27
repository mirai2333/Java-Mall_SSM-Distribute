package com.taotao.rest.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.rest.service.ItemCatService;

@Controller
@RequestMapping("/item/cat")
public class ItemCatController {
	
	@Autowired
	private ItemCatService itemCatService;
	
	@RequestMapping(value="list",produces=MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
	@ResponseBody
	public String getItemCatList(String callback) {
		String json = itemCatService.getItemCatList();
		if(StringUtils.isBlank(callback)) {
			return json;
		}
		return callback+"("+json+");";
	}
	
	//侧边分类缓存同步
	@RequestMapping("/sideconcent/cachesync")
	@ResponseBody
	public TaotaoResult sideContentCatCacheSync() {
		try {
			return itemCatService.sideContentCatCacheDelete();
		} catch (Exception e) {
			return TaotaoResult.build(500, "侧边分类缓存同步错误！");
		}
	}
}
