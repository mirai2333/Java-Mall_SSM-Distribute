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
import com.taotao.common.utils.IDUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemDescExample;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper tbItemMapper;
	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	@Autowired
	private TbItemParamItemMapper tbItemParamItemMapper;

	/**
	 * 根据商品ID查询商品
	 */
	@Override
	public TbItem itemsQuery(Long id) {
		TbItem tbItem = tbItemMapper.selectByPrimaryKey(id);
		return tbItem;
	}

	/**
	 * 分页查询商品列表
	 */
	@Override
	public EasyUIDataGridResult getItemsList(int page, int size) {
		TbItemExample example = new TbItemExample();
		PageHelper.startPage(page, size);
		List<TbItem> list = tbItemMapper.selectByExample(example);

		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		EasyUIDataGridResult easyUIDataGridResult = new EasyUIDataGridResult();
		easyUIDataGridResult.setTotal((int) pageInfo.getTotal());
		easyUIDataGridResult.setRows(list);
		return easyUIDataGridResult;
	}

	/**
	 * 添加商品
	 */
	@Override
	public TaotaoResult createItem(TbItem item, String desc, String itemParams) {
		// 补全商品信息，并插入商品表
		Long itemId = IDUtils.genItemId();
		item.setId(itemId);
		// 商品状态：1-正常、2-下架
		item.setStatus((byte) 1);
		// 添加创建日期和修改日期
		Date date = new Date();
		item.setCreated(date);
		item.setUpdated(date);
		tbItemMapper.insert(item);

		// 补全商品描述信息，并插入商品描述表
		TbItemDesc tbItemDesc = new TbItemDesc();
		tbItemDesc.setItemId(itemId);
		tbItemDesc.setItemDesc(desc);
		tbItemDesc.setCreated(date);
		tbItemDesc.setUpdated(date);
		tbItemDescMapper.insert(tbItemDesc);

		// 补全商品规格参数，并插入商品规格参数表
		TbItemParamItem itemParamItem = new TbItemParamItem();
		itemParamItem.setItemId(itemId);
		itemParamItem.setParamData(itemParams);
		itemParamItem.setCreated(date);
		itemParamItem.setUpdated(date);
		tbItemParamItemMapper.insert(itemParamItem);
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult getItemDescById(Long id) {
		TbItemDescExample example = new TbItemDescExample();
		example.createCriteria().andItemIdEqualTo(id);
		List<TbItemDesc> list = tbItemDescMapper.selectByExampleWithBLOBs(example);
		if (list != null && list.size() > 0) {
			TbItemDesc tbItemDesc = list.get(0);
			return TaotaoResult.ok(tbItemDesc.getItemDesc());
		}
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult deleteItems(Long[] ids) {
		// ID数组转换为列表
		ArrayList<Long> idList = new ArrayList<>(Arrays.asList(ids));
		// 在商品表中删除
		TbItemExample tbItemExample = new TbItemExample();
		tbItemExample.createCriteria().andIdIn(idList);
		tbItemMapper.deleteByExample(tbItemExample);
		// 在商品描述表中删除（暂时省略）
		// TbItemDescExample tbItemDescExample = new TbItemDescExample();
		// tbItemDescExample.createCriteria().andItemIdIn(idList);
		// tbItemDescMapper.deleteByExample(tbItemDescExample);
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult updateItemsStatus(Long[] ids, String parameter) {
		// ID数组转换为列表
		ArrayList<Long> idList = new ArrayList<>(Arrays.asList(ids));
		//根据参数确定状态码,1-正常、2-下架
		Byte status = (byte) ("instock".equals(parameter)?2:1);
		//更新商品状态
		TbItemExample example = new TbItemExample();
		example.createCriteria().andIdIn(idList);
		TbItem record = new TbItem();
		record.setStatus(status);
		tbItemMapper.updateByExampleSelective(record, example);
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult getItemParamItemById(Long id) {
		TbItemParamItemExample example = new TbItemParamItemExample();
		example.createCriteria().andItemIdEqualTo(id);
		List<TbItemParamItem> list = tbItemParamItemMapper.selectByExampleWithBLOBs(example);
		if(list != null && list.size()>0) 
			return TaotaoResult.ok(list.get(0));
		return TaotaoResult.ok();
	}

}
