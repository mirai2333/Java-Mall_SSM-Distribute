package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;

@Controller
@RequestMapping("/content")
public class ContentController {
	
	@Autowired
	private ContentService contentService;
	
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_CONTENT_SYNC_PATH}")
	private String REST_CONTENT_SYNC_PATH;
	
	@RequestMapping("/list")
	@ResponseBody
	public EasyUIDataGridResult queryContentList(Integer page, Integer rows, Long categoryId) {
		return contentService.queryContentList(page, rows, categoryId);
	}
	
	//存储内容
	@RequestMapping("/save")
	@ResponseBody
	public TaotaoResult saveTbContent(TbContent tbContent) {
		TaotaoResult taotaoResult = contentService.saveTbContent(tbContent);
		HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_SYNC_PATH + tbContent.getCategoryId());
		return taotaoResult;
	}
	
	//编辑内容
	@RequestMapping("/edit")
	@ResponseBody
	public TaotaoResult updateTbContent(TbContent tbContent) {
		TaotaoResult taotaoResult = contentService.updateTbContent(tbContent);
		HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_SYNC_PATH + tbContent.getCategoryId());
		return taotaoResult;
	}
	
	//删除内容
	@RequestMapping("/delete")
	@ResponseBody
	public TaotaoResult deleteTbContent(Long[] ids) {
		return contentService.deleteTbContent(ids);
	}
}
