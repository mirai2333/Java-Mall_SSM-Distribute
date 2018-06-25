package com.taotao.service.lmpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper tbContentMapper;

	@Override
	public EasyUIDataGridResult queryContentList(int page, int rows, Long categoryId) {
		// 分页查询
		TbContentExample example = new TbContentExample();
		PageHelper.startPage(page, rows);
		if (categoryId != 0)
			example.createCriteria().andCategoryIdEqualTo(categoryId);
		List<TbContent> list = tbContentMapper.selectByExample(example);
		// 创建返回值
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		PageInfo<TbContent> contentInfo = new PageInfo<>(list);
		result.setTotal((int) contentInfo.getTotal());
		result.setRows(list);
		return result;
	}

	@Override
	public TaotaoResult saveTbContent(TbContent tbContent) {
		//补全信息
		tbContent.setCreated(new Date());
		tbContent.setUpdated(new Date());
		//存储
		tbContentMapper.insert(tbContent);
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult updateTbContent(TbContent tbContent) {
		//补全编辑时间
		tbContent.setUpdated(new Date());
		tbContentMapper.updateByPrimaryKeySelective(tbContent);
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult deleteTbContent(Long[] ids) {
		ArrayList<Long> deleteList = new ArrayList<>(Arrays.asList(ids));
		TbContentExample example = new TbContentExample();
		example.createCriteria().andIdIn(deleteList);
		tbContentMapper.deleteByExample(example);
		return TaotaoResult.ok();
	}

}
