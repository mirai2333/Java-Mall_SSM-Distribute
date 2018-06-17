package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/list")
	@ResponseBody
	public EasyUIDataGridResult getItemsList(Integer page, Integer rows) {
		EasyUIDataGridResult items = itemService.getItemsList(page, rows);
		return items;
	}
	
	@RequestMapping("/{id}")
	@ResponseBody
	public TbItem getItemById(@PathVariable Long id) {
		TbItem tbItem = itemService.itemsQuery(id);
		return tbItem;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public TaotaoResult createItem(TbItem item, String desc) {
		return itemService.createItem(item, desc);
	}
}
