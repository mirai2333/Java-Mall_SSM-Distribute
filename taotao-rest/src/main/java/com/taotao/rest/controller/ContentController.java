package com.taotao.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.rest.service.ContentService;

@Controller
@RequestMapping("/content")
public class ContentController {
	
	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/list/{cid}")
	@ResponseBody
	public TaotaoResult findContentByCatId(@PathVariable Long cid) {
		return TaotaoResult.ok(contentService.findTbContents(cid));
	}
	
	//内容缓存同步服务
	@RequestMapping("/cachesync/{cid}")
	@ResponseBody
	public TaotaoResult contentCacheSync(@PathVariable Long cid) {
		try {
			return contentService.contentCacheDelete(cid);
		} catch (Exception e) {
			return TaotaoResult.build(500, "根据分类id的内容缓存同步失败！");
		}
	}
}
