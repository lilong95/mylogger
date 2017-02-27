package cn.mylogger.service;

import cn.mylogger.mybatis.page.Page;
import cn.mylogger.po.Revert;
import cn.mylogger.vo.RevertVo;

public interface RevertService extends BaseService<Revert>{
	Page<RevertVo> getPageByArticleId(String articleId);
}
