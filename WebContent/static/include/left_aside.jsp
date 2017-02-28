<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<aside id="sidebar" class="sidebar c-overflow">
	<div class="profile-menu">
		<a href="">
			<div class="profile-pic">
				<img src="<%=request.getContextPath()%>/static/assets/img/profile-pics/1.jpg" alt="">
			</div>

			<div class="profile-info">
				Malinda Hollaway <i class="zmdi zmdi-caret-down"></i>
			</div>
		</a>

		<ul class="main-menu">
			<li><a href="profile-about.html"><i
					class="zmdi zmdi-account"></i> 个人信息</a></li>
			<li><a href=""><i class="zmdi zmdi-settings"></i> 设置</a></li>
			<li><a href=""><i class="zmdi zmdi-time-restore"></i> 注销</a></li>
		</ul>
	</div>

	<ul class="main-menu">
		<li><a href="index.html"><i class="zmdi zmdi-home"></i> 主页</a></li>
		<li class="sub-menu"><a href=""><i
				class="zmdi zmdi-view-compact"></i> Headers</a>

			<ul>
				<li><a href="textual-menu.html">Textual menu</a></li>
				<li><a href="image-logo.html">Image logo</a></li>
				<li><a href="top-mainmenu.html">Mainmenu on top</a></li>
			</ul></li>
		<li class="active"><a href="typography.html"><i
				class="zmdi zmdi-format-underlined"></i> Typography</a></li>

		<li class="sub-menu"><a href="form-examples.html"><i
				class="zmdi zmdi-menu"></i> 3 Level Menu</a>

			<ul>
				<li><a href="form-elements.html">Level 2 link</a></li>
				<li><a href="form-components.html">Another level 2 Link</a></li>
				<li class="sub-menu"><a href="form-examples.html">I have
						children too</a>
					<ul>
						<li><a href="">Level 3 link</a></li>
						<li><a href="">Another Level 3 link</a></li>
						<li><a href="">Third one</a></li>
					</ul></li>
				<li><a href="form-validations.html">One more 2</a></li>
			</ul></li>
	</ul>
</aside>