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
public class ItemController {
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIDataGridResult getItemsList(Integer page, Integer rows) {
		EasyUIDataGridResult items = itemService.getItemsList(page, rows);
		return items;
	}
	
	@RequestMapping("/item/{id}")
	@ResponseBody
	public TbItem getItemById(@PathVariable Long id) {
		TbItem tbItem = itemService.itemsQuery(id);
		return tbItem;
	}
	
	@RequestMapping("/item/save")
	@ResponseBody
	public TaotaoResult createItem(TbItem item, String desc, String itemParams) {
		return itemService.createItem(item, desc, itemParams);
	}
	
	//根据商品ID返回商品描述
	@RequestMapping("/rest/item/query/item/desc/{id}")
	@ResponseBody
	public TaotaoResult getItemDescById(@PathVariable Long id) {
		return itemService.getItemDescById(id);
	}
	
	//根据选中的商品ID删除商品表中的记录
	@RequestMapping("/rest/item/delete")
	@ResponseBody
	public TaotaoResult deleteItemByIds(Long[] ids) {
		return itemService.deleteItems(ids);
	}
	
	//根据URL参数更新商品状态
	@RequestMapping("/rest/item/{status}")
	@ResponseBody
	public TaotaoResult updateItemsStatus(Long[] ids, @PathVariable String status) {
		return itemService.updateItemsStatus(ids, status);
	}
	
	//根据商品ID返回商品规格参数
	@RequestMapping("/rest/item/param/item/query/{id}")
	@ResponseBody
	public TaotaoResult getItemParamItemById(@PathVariable Long id) {
		return itemService.getItemParamItemById(id);
	}
}
