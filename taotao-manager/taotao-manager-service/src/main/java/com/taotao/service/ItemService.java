package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;

public interface ItemService {
	TbItem itemsQuery(Long id);
	EasyUIDataGridResult getItemsList(int page, int size);
	TaotaoResult createItem(TbItem item, String desc, String itemParams);
	//根据商品ID查询商品描述
	TaotaoResult getItemDescById(Long id);
	//根据商品ID查询商品的规格参数
	TaotaoResult getItemParamItemById(Long id);
	//根据选中的ID删除商品
	TaotaoResult deleteItems(Long[] ids);
	//根据选中的ID上架/下架
	TaotaoResult updateItemsStatus(Long[] ids, String parameter);
}
