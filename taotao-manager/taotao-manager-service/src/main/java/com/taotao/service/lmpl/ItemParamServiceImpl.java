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
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.mapper.custom.ItemParamMapper;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamExample;
import com.taotao.pojo.custom.ItemParamCustom;
import com.taotao.service.ItemParamService;

@Service
public class ItemParamServiceImpl implements ItemParamService {

	@Autowired
	private ItemParamMapper itemParamMapper;
	@Autowired
	private TbItemParamMapper tbItemParamMapper;
	
	@Override
	public EasyUIDataGridResult queryItemParam(int page,int size) {
		
		PageHelper.startPage(page, size);
		List<ItemParamCustom> itemParamList = itemParamMapper.queryItemParam();
		PageInfo<ItemParamCustom> pageInfo = new PageInfo<>(itemParamList);
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setTotal((int) pageInfo.getTotal());
		result.setRows(itemParamList);
		
		return result;
	}

	@Override
	public TaotaoResult queryHaveItemParamByCid(Long cid) {
		//执行查询
		TbItemParamExample example = new TbItemParamExample();
		example.createCriteria().andItemCatIdEqualTo(cid);
		List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(example);
		//返回值判断
		if(list != null && list.size()>0) {
			Object paramData = list.get(0).getParamData();
			return TaotaoResult.ok(paramData);
		}
			
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult insertItemParam(Long cid, String paramData) {
		TbItemParam tbItemParam = new TbItemParam();
		tbItemParam.setItemCatId(cid);
		tbItemParam.setParamData(paramData);
		tbItemParam.setCreated(new Date());
		tbItemParam.setUpdated(new Date());
		tbItemParamMapper.insert(tbItemParam);
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult deleteItemParamById(Long[] ids) {
		//将数组转换为列表
		ArrayList<Long> arrayList = new ArrayList<>(Arrays.asList(ids));
		//执行删除操作
		TbItemParamExample example = new TbItemParamExample();
		example.createCriteria().andIdIn(arrayList);
		tbItemParamMapper.deleteByExample(example);
		return TaotaoResult.ok();
	}

}
