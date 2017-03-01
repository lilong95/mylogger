<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<header id="header" class="clearfix" data-current-skin="blue">
	<ul class="header-inner">
		<li id="menu-trigger" data-trigger="#sidebar">
			<div class="line-wrap">
				<div class="line top"></div>
				<div class="line center"></div>
				<div class="line bottom"></div>
			</div>
		</li>

		<li class="logo hidden-xs"><a href="index.html">My Logger</a></li>

		<li class="pull-right">
			<ul class="top-menu">
				<li id="toggle-width">
					<div class="toggle-switch">
						<input id="tw-switch" type="checkbox" hidden="hidden"> <label
							for="tw-switch" class="ts-helper"></label>
					</div>
				</li>

				<li id="top-search"><a href=""><i
						class="tm-icon zmdi zmdi-search"></i></a></li>

				<li class="dropdown"><a data-toggle="dropdown" href=""> <i
						class="tm-icon zmdi zmdi-notifications"></i> <i class="tmn-counts">9</i>
				</a>
					<div class="dropdown-menu dropdown-menu-lg pull-right">
						<div class="listview" id="notifications">
							<div class="lv-header">
								Notification

								<ul class="actions">
									<li class="dropdown"><a href="" data-clear="notification">
											<i class="zmdi zmdi-check-all"></i>
									</a></li>
								</ul>
							</div>
							<div class="lv-body">
								<a class="lv-item" href="">
									<div class="media">
										<div class="pull-left">
											<img class="lv-img-sm" src="<%=request.getContextPath()%>/static/assets/img/profile-pics/2.jpg" alt="">
										</div>
										<div class="media-body">
											<div class="lv-title">Jonathan Morris</div>
											<small class="lv-small">Nunc quis diam diamurabitur
												at dolor elementum, dictum turpis vel</small>
										</div>
									</div>
								</a> <a class="lv-item" href="">
									<div class="media">
										<div class="pull-left">
											<img class="lv-img-sm" src="<%=request.getContextPath()%>/static/assets/img/profile-pics/4.jpg" alt="">
										</div>
										<div class="media-body">
											<div class="lv-title">Bill Phillips</div>
											<small class="lv-small">Proin laoreet commodo eros id
												faucibus. Donec ligula quam, imperdiet vel ante placerat</small>
										</div>
									</div>
								</a>
							</div>

							<a class="lv-footer" href="">View Previous</a>
						</div>

					</div></li>

				<li class="dropdown"><a data-toggle="dropdown" href=""><i
						class="tm-icon zmdi zmdi-more-vert"></i></a>
					<ul class="dropdown-menu dm-icon pull-right">
						<li class="skin-switch hidden-xs"><span
							class="ss-skin bgm-lightblue" data-skin="lightblue"></span> <span
							class="ss-skin bgm-bluegray" data-skin="bluegray"></span> <span
							class="ss-skin bgm-cyan" data-skin="cyan"></span> <span
							class="ss-skin bgm-teal" data-skin="teal"></span> <span
							class="ss-skin bgm-orange" data-skin="orange"></span> <span
							class="ss-skin bgm-blue" data-skin="blue"></span></li>
						<li class="divider hidden-xs"></li>
						<li class="hidden-xs"><a data-action="fullscreen" href=""><i
								class="zmdi zmdi-fullscreen"></i> Toggle Fullscreen</a></li>
						<li><a href=""><i class="zmdi zmdi-settings"></i> Other
								Settings</a></li>
					</ul></li>
			</ul>
		</li>
	</ul>

	<!-- Top Search Content -->
	<div id="top-search-wrap">
		<div class="tsw-inner">
			<i id="top-search-close" class="zmdi zmdi-arrow-left"></i> <input
				type="text">
		</div>
	</div>
</header>