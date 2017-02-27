package cn.mylogger.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mylogger.mapper.ArticleMapper;
import cn.mylogger.mybatis.page.Page;
import cn.mylogger.po.Article;
import cn.mylogger.service.ArticleService;
import cn.mylogger.vo.ArticleVo;

@Service("articleService")
public class ArticleServiceImpl extends BaseServiceImpl<Article> implements ArticleService {

	@Autowired
	private ArticleMapper articleMapper;

	@Override
	public Page<ArticleVo> getPageByThemeName(String themeName) {
		List<ArticleVo> list = articleMapper.getPageByThemeName(themeName);
		return this.buildVoPage(list);
	}

	@Override
	public ArticleVo getVoById(String id) {
		return articleMapper.getVoById(id);
	}

}
