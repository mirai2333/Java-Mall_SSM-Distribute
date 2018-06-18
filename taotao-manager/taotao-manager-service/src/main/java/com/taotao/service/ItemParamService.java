package com.taotao.service;

import java.util.List;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;

public interface ItemParamService {
	
	//分页查询规格模板列表
	EasyUIDataGridResult queryItemParam(int page,int size);
	//根据cid查询是否已有规格模板
	TaotaoResult queryHaveItemParamByCid(Long cid);
	//添加商品规格模板
	TaotaoResult insertItemParam(Long cid, String paramData);
	//根据ID删除商品规格模板
	TaotaoResult deleteItemParamById(Long[] ids);
}
