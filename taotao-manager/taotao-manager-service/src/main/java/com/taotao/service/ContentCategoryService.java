package com.taotao.service;

import java.util.List;

import com.taotao.common.pojo.EasyUITreeNode;

public interface ContentCategoryService {
	
	List<EasyUITreeNode> getContentCat(Long parentId);
}
