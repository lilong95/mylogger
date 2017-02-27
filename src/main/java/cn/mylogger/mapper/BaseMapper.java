package cn.mylogger.mapper;

import java.util.List;

public interface BaseMapper<T> {
	T selectById(String id);
	int insert(T t);
	int update(T t);
	int deleteById(String id);
	int batchInsert(List<T> list);
	int batchUpdate(List<T> list);
	int batchDeleteByIds(String[] ids);
	List<T> findPage();
}