package com.taotao.service;

import java.util.List;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;

public interface ContentCategoryService {
	
	List<EasyUITreeNode> getContentCat(Long parentId);
	
	//增加类目：
	//参数：parentId，name
	//返回值TaotaoResult，data为id
	TaotaoResult insertContentCat(Long parentId, String name);
	
	//重命名类目
	//参数：id，name
	//返回值：TaotaoResult
	TaotaoResult updateContentCatName(Long id, String name);
	
	//删除类目
	//参数：id，parentId
	//返回值：TaotaoResult
	TaotaoResult deleteContentCat(Long id);
}
