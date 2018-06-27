package com.taotao.rest.service;

import com.taotao.common.pojo.TaotaoResult;

public interface ItemCatService {
	String getItemCatList();
	//删除侧边分类缓存
	//返回值：TaotaoResult
	TaotaoResult sideContentCatCacheDelete();
}
