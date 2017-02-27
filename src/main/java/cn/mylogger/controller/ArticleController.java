package cn.mylogger.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mylogger.baseInfo.BaseInfo;
import cn.mylogger.mybatis.page.Page;
import cn.mylogger.po.Article;
import cn.mylogger.service.ArticleService;
import cn.mylogger.utils.CommonUtils;
import cn.mylogger.utils.ResultMap;
import cn.mylogger.vo.ArticleVo;


@Controller
@RequestMapping("article")
public class ArticleController {

	@Autowired
	private ArticleService articleService;

	@ResponseBody
	@RequestMapping("getPageByThemeName")
	public ResultMap getPageByThemeName(String themeName) {
		ResultMap rm = new ResultMap();
		try {
			Page<ArticleVo> page = articleService.getPageByThemeName(themeName);
			rm.info("获取成功").page(page);
		} catch (Exception e) {
			e.printStackTrace();
			rm.fail().info("获取失败");
		}
		return rm;
	}

	@ResponseBody
	@RequestMapping("add")
	public ResultMap add(Article article, HttpServletRequest request) {
		ResultMap rm = new ResultMap();
		try {
			if (BaseInfo.getUser(request) == null) {
				return rm.fail().info("请您先登录");
			}
			if (article != null && !CommonUtils.isEmpty(article.getContent())
					&& !CommonUtils.isEmpty(article.getTitle())) {
				article.setCreateDt(new Date());
				article.setLastRevertDt(new Date());
				article.setUserId(BaseInfo.getUser(request).getId());
				articleService.save(article);
				rm.info("发布成功");
			} else {
				rm.fail().info("发布失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rm.fail().info("发布失败");
		}
		return rm;
	}
	
	@ResponseBody
	@RequestMapping("getVoById")
	public ResultMap getVoById(String articleId, HttpServletRequest request) {
		ResultMap rm = new ResultMap();
		try {
			ArticleVo vo = articleService.getVoById(articleId);
			rm.info("获取成功").data(vo);
		} catch (Exception e) {
			e.printStackTrace();
			rm.fail().info("获取失败");
		}
		return rm;
	}
}
