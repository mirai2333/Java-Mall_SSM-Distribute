package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;

public interface ItemService {
	TbItem itemsQuery(Long id);
	EasyUIDataGridResult getItemsList(int page, int size);
	TaotaoResult createItem(TbItem item, String desc, String itemParams);
}
