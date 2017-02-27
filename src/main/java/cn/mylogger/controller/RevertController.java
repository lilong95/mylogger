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
import cn.mylogger.po.Revert;
import cn.mylogger.service.ArticleService;
import cn.mylogger.service.RevertService;
import cn.mylogger.utils.ResultMap;
import cn.mylogger.vo.RevertVo;

@Controller
@RequestMapping("revert")
public class RevertController {

	@Autowired
	private RevertService revertService;
	
	@Autowired
	private ArticleService articleService;

	@ResponseBody
	@RequestMapping("getPageByArticleId")
	public ResultMap getPageByArticleId(String articleId) {
		ResultMap rm = new ResultMap();
		try {
			Page<RevertVo> page = revertService.getPageByArticleId(articleId);
			rm.info("获取成功").page(page);
		} catch (Exception e) {
			e.printStackTrace();
			rm.fail().info("获取失败");
		}
		return rm;
	}
	
	@ResponseBody
	@RequestMapping("add")
	public ResultMap add(Revert revert,HttpServletRequest request) {
		ResultMap rm = new ResultMap();
		try {
			revert.setCreateDt(new Date());
			revert.setUserId(BaseInfo.getUser(request).getId());
			Article article = new Article();
			article.setId(revert.getArticleId());
			article.setLastRevertDt(new Date());
			articleService.update(article);
			revertService.save(revert);
			rm.info("回复成功");
		} catch (Exception e) {
			e.printStackTrace();
			rm.fail().info("回复失败");
		}
		return rm;
	}
}
