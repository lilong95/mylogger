package cn.mylogger.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mylogger.mapper.RevertMapper;
import cn.mylogger.mybatis.page.Page;
import cn.mylogger.po.Revert;
import cn.mylogger.service.RevertService;
import cn.mylogger.vo.RevertVo;

@Service("revertService")
public class RevertServiceImpl extends BaseServiceImpl<Revert> implements RevertService {

	@Autowired
	private RevertMapper revertMapper;

	@Override
	public Page<RevertVo> getPageByArticleId(String articleId) {
		List<RevertVo> list = revertMapper.getPageByArticleId(articleId);
		return buildVoPage(list);
	}

}
