package cn.mylogger.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.mylogger.po.Revert;
import cn.mylogger.vo.RevertVo;


@Repository("revertMapper")
public interface RevertMapper extends BaseMapper<Revert> {
	List<RevertVo> getPageByArticleId(String articleId);
}
