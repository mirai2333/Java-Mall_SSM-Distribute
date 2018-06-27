package com.taotao.rest.service;

import java.util.List;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

public interface ContentService {

	//根据内容分类id查询内容
	//参数：分类id
	//返回值：id对应的内容列表
	List<TbContent> findTbContents(Long cid);
	
	//根据内容分类id删除缓存，（hset）
	//参数：分类id
	//返回值：TaotaoResult
	TaotaoResult contentCacheDelete(Long cid);
}
