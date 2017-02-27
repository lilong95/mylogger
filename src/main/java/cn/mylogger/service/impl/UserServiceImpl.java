package cn.mylogger.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mylogger.mapper.UserMapper;
import cn.mylogger.po.User;
import cn.mylogger.service.UserService;
import cn.mylogger.vo.LoginVo;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public User login(LoginVo loginVo) {
		return userMapper.selectByLoginVo(loginVo);
	}

	@Override
	public int updatePassword(String newLoginpwd, String id) {
		return userMapper.updatePassword(newLoginpwd, id);
	}

	@Override
	public boolean haveSameLoginName(String loginName) {
		Integer count = userMapper.getByLoginName(loginName);
		if(count!=null && count>0) {
			return true;
		}
		return false;
	}

}
