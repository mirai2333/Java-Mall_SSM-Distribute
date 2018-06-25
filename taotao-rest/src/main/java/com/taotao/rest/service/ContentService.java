package com.taotao.rest.service;

import java.util.List;

import com.taotao.pojo.TbContent;

public interface ContentService {

	//根据内容分类id查询内容
	//参数：分类id
	//返回值：id对应的内容列表
	List<TbContent> findTbContents(Long id);
}
