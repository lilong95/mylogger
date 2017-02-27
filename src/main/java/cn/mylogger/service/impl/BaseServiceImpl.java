package cn.mylogger.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.mylogger.mapper.BaseMapper;
import cn.mylogger.mybatis.page.Page;
import cn.mylogger.mybatis.page.PageContext;
import cn.mylogger.service.BaseService;

public class BaseServiceImpl<T> implements BaseService<T> {
	@Autowired
	protected BaseMapper<T> baseMapper;
	
	@Override
	public T getById(String id) {
		return baseMapper.selectById(id);
	}

	@Override
	public int save(T t) {
		return baseMapper.insert(t);
	}

	@Override
	public int update(T t) {
		return baseMapper.update(t);
	}

	@Override
	public int deleteById(String id) {
		return baseMapper.deleteById(id);
	}

	@Override
	public int batchInsert(List<T> list) {
		return baseMapper.batchInsert(list);
	}

	@Override
	public int batchUpdate(List<T> list) {
		return baseMapper.batchUpdate(list);
	}

	@Override
	public Page<T> findPage() throws Exception {
		List<T> list = baseMapper.findPage();
		return buildPage(list);
	}

	@Override
	public Page<T> buildPage(List<T> list) {
		Page<T> page = new Page<T>();
		page.setData(list);
		page.setPageCode(PageContext.getPageCode());
		page.setPageSize(PageContext.getPageSize());
		page.setTotalPage(PageContext.getTotalPage());
		page.setTotalRecord(PageContext.getTotalRecord());
		return page;
	}

	@Override
	public <V> Page<V> buildVoPage(List<V> list) {
		Page<V> page = new Page<V>();
		page.setData(list);
		page.setPageCode(PageContext.getPageCode());
		page.setPageSize(PageContext.getPageSize());
		page.setTotalPage(PageContext.getTotalPage());
		page.setTotalRecord(PageContext.getTotalRecord());
		return page;
	}

	@Override
	public int batchDeleteByIds(String[] ids) {
		return baseMapper.batchDeleteByIds(ids);
	}

}
