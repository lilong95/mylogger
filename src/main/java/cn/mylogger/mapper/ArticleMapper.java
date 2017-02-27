package cn.mylogger.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.mylogger.po.Article;
import cn.mylogger.vo.ArticleVo;


@Repository("articleMapper")
public interface ArticleMapper extends BaseMapper<Article> {
	List<ArticleVo> getPageByThemeName(@Param("themeName") String themeName);
	ArticleVo getVoById(String id);
}