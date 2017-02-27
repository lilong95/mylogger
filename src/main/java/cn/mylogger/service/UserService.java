package cn.mylogger.service;

import cn.mylogger.po.User;
import cn.mylogger.vo.LoginVo;

public interface UserService extends BaseService<User> {
	User login(LoginVo loginVo);

	int updatePassword(String newLoginpwd,String id);
	
	boolean haveSameLoginName(String loginName);
}
