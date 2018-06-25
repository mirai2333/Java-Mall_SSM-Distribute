package com.taotao.portal.service;

public interface ContentService {
	
	//从web服务获得轮播图资源
	//参数：属性文件设置
	//返回值：ADNode的json数据
	String getCarouselADs();
}
