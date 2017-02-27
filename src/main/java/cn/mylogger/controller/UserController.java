package cn.mylogger.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mylogger.baseInfo.WebConst;
import cn.mylogger.po.User;
import cn.mylogger.service.UserService;
import cn.mylogger.utils.CommonUtils;
import cn.mylogger.utils.ResultMap;
import cn.mylogger.vo.LoginVo;
import cn.mylogger.vo.RegisterVo;

@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserService userService;

	@ResponseBody
	@RequestMapping("login")
	public ResultMap login(LoginVo loginVo, HttpServletRequest request) {
		ResultMap rm = new ResultMap();
		try {
			User user = userService.login(loginVo);
			if (user != null) {
				request.getSession().setAttribute(WebConst.SESSION_USER, user);
			} else {
				rm.fail().info("登录失败，请输入正确的账号密码");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rm.fail().info("登录失败");
		}
		return rm;
	}

	@ResponseBody
	@RequestMapping("register")
	public ResultMap register(RegisterVo registerVo, HttpServletRequest request) {
		ResultMap rm = new ResultMap();
		try {
			if (CommonUtils.isEmpty(registerVo.getNickname()) || CommonUtils.isEmpty(registerVo.getPassword())
					|| CommonUtils.isEmpty(registerVo.getRePassword())
					|| CommonUtils.isEmpty(registerVo.getLoginName())) {
				return rm.fail().info("注册失败，账号或密码或昵称为空");
			} else if (!registerVo.getRePassword().equals(registerVo.getPassword())) {
				return rm.fail().info("注册失败，两次密码不一致");
			} else if (userService.haveSameLoginName(registerVo.getLoginName())) {
				return rm.fail().info("抱歉，该账号已经注册");
			} else {
				User user = new User();
				user.setLoginName(registerVo.getLoginName());
				user.setNickname(registerVo.getNickname());
				user.setPassword(registerVo.getPassword());
				user.setSex(registerVo.getSex());
				userService.save(user);
				rm.info("恭喜您注册成功，请登录！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rm.fail().info("注册失败");
		}
		return rm;
	}

	@ResponseBody
	@RequestMapping("logout")
	public ResultMap logout(LoginVo loginVo, HttpServletRequest request) {
		ResultMap rm = new ResultMap();
		try {
			request.getSession().invalidate();
			rm.info("注销成功");
		} catch (Exception e) {
			e.printStackTrace();
			rm.fail().info("注销失败");
		}
		return rm;
	}

}
