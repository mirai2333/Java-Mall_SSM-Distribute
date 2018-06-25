package com.taotao.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbContent;
import com.taotao.portal.pojo.CarouselAD;

@Service
public class ContentServiceImpl implements com.taotao.portal.service.ContentService {
	
	//服务层基础URL
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	//广告按类别查询URL
	@Value("${REST_CONTENT_LIST}")
	private String REST_CONTENT_LIST;
	
	//轮播广告ID
	@Value("${REST_CAROUSEL_ID}")
	private String REST_CAROUSEL_ID;

	@Override
	public String getCarouselADs() {
		//从服务层获取资源
		String response = HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_LIST + REST_CAROUSEL_ID);
		TaotaoResult formatData = TaotaoResult.formatToList(response, TbContent.class);
		List<TbContent> ADlist = (List<TbContent>) formatData.getData();
		//格式化资源为pojo类
		List<CarouselAD> list = new ArrayList<>();
		for (TbContent tbContent : ADlist) {
			CarouselAD carouselAD = new CarouselAD();
			carouselAD.setWidth(670);
			carouselAD.setHeight(240);
			carouselAD.setWidthB(550);
			carouselAD.setHeightB(240);
			carouselAD.setSrc(tbContent.getPic());
			carouselAD.setSrcB(tbContent.getPic2());
			carouselAD.setAlt(tbContent.getSubTitle());
			carouselAD.setHref(tbContent.getUrl());
			list.add(carouselAD);
		}
		String json = JsonUtils.objectToJson(list);
		return json;
	}

}
