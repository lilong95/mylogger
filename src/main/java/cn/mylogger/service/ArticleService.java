package cn.mylogger.service;

import cn.mylogger.mybatis.page.Page;
import cn.mylogger.po.Article;
import cn.mylogger.vo.ArticleVo;

public interface ArticleService extends BaseService<Article>{
	Page<ArticleVo> getPageByThemeName(String themeName);
	ArticleVo getVoById(String id);
}
