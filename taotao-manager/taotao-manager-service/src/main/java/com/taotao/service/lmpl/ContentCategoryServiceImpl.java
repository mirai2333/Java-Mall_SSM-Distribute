package com.taotao.service.lmpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.service.ContentCategoryService;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper tbContentCategoryMapper;
	
	@Override
	public List<EasyUITreeNode> getContentCat(Long parentId) {
		//查询类目
		TbContentCategoryExample example = new TbContentCategoryExample();
		example.createCriteria().andParentIdEqualTo(parentId);
		List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
		//创建返回列表
		List<EasyUITreeNode> treeList = new ArrayList<>();
		for (TbContentCategory tbContentCategory : list) {
			EasyUITreeNode treeNode = new EasyUITreeNode();
			treeNode.setId(tbContentCategory.getId());
			treeNode.setText(tbContentCategory.getName());
			treeNode.setState(tbContentCategory.getIsParent()?"closed":"open");
			treeList.add(treeNode);
		}
		return treeList;
	}

	@Override
	public TaotaoResult insertContentCat(Long parentId, String name) {
		//补全要插入的对象
		TbContentCategory tbContentCategory = new TbContentCategory();
		tbContentCategory.setIsParent(false);
		tbContentCategory.setName(name);
		tbContentCategory.setParentId(parentId);
		tbContentCategory.setSortOrder(1);
		tbContentCategory.setStatus(1);							//状态。可选值:1(正常),2(删除)
		tbContentCategory.setCreated(new Date());
		tbContentCategory.setUpdated(new Date());
		//插入,并修正父节点的isParent
		tbContentCategoryMapper.insert(tbContentCategory);
		TbContentCategory parentCat = new TbContentCategory();
		parentCat.setId(parentId);
		parentCat.setIsParent(true);
		tbContentCategoryMapper.updateByPrimaryKeySelective(parentCat);
		//返回对象
		Long id = tbContentCategory.getId();
		return TaotaoResult.ok(id);
	}

	@Override
	public TaotaoResult updateContentCatName(Long id, String name) {
		//创建模板类
		TbContentCategory tbContentCategory = new TbContentCategory();
		tbContentCategory.setId(id);
		tbContentCategory.setName(name);
		tbContentCategoryMapper.updateByPrimaryKeySelective(tbContentCategory);
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult deleteContentCat(Long id) {
		//递归获取要删除的id列表
		List<Long> idList = new ArrayList<>();
		idList.add(id);
		getDeleteIds(id,idList);
		//删除
		TbContentCategoryExample example = new TbContentCategoryExample();
		example.createCriteria().andIdIn(idList);
		tbContentCategoryMapper.deleteByExample(example);
		return TaotaoResult.ok();
	}
	
	//查询需要删除的子类目的id
	private void getDeleteIds(Long parentId, List<Long> idList) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		example.createCriteria().andParentIdEqualTo(parentId);
		List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
		for (TbContentCategory tbContentCategory : list) {
			idList.add(tbContentCategory.getId());
			if(tbContentCategory.getIsParent()) {
				getDeleteIds(tbContentCategory.getId(),idList);
			}
		}
	}

}
