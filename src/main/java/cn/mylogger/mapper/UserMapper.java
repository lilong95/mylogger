package cn.mylogger.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.mylogger.po.User;
import cn.mylogger.vo.LoginVo;


@Repository("userMapper")
public interface UserMapper extends BaseMapper<User> {
	User selectByLoginVo(LoginVo loginVo);

	int updatePassword(@Param("newLoginpwd") String newLoginpwd,
			@Param("id") String id);

	int insertUserRole(@Param("uid") String uid, @Param("rid") String rid);
	
	Integer getByLoginName(String loginName);

}
