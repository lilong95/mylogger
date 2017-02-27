package cn.mylogger.service;

import java.util.List;

import cn.mylogger.mybatis.page.Page;


public interface BaseService<T> {
	T getById(String id);
	int save(T t);
	int update(T t);
	int deleteById(String id);
	int batchInsert(List<T> list);
	int batchUpdate(List<T> list);
	int batchDeleteByIds(String[] ids);
	Page<T> findPage() throws Exception;
	Page<T> buildPage(List<T> list);
	<V> Page<V> buildVoPage(List<V> list);
}
