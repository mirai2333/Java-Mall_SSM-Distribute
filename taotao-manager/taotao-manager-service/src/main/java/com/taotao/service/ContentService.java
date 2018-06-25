package com.taotao.service;


import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

public interface ContentService {

	//分页查询
	//参数：page，rows，categoryId
	//返回值：EasyUIDataGridResult
	EasyUIDataGridResult queryContentList(int page,int rows, Long categoryId);
	
	//保存内容信息，Id自动生成
	//参数：TbContent
	//返回值：TaotaoResult
	TaotaoResult saveTbContent(TbContent tbContent);
	
	//升级内容
	//参数：TbContent（已经包含id）
	//返回值：TaotaoResult
	TaotaoResult updateTbContent(TbContent tbContent);
	
	//删除内容
	//参数：id数组
	//返回值：TaotaoResult
	TaotaoResult deleteTbContent(Long[] ids);
}
