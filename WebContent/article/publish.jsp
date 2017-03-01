<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--[if IE 9 ]><html class="ie9"><![endif]-->
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>这里是标题</title>
<jsp:include page="/static/include/base_css.jsp"></jsp:include>
<link
	href="<%=request.getContextPath()%>/static/assets/vendors/summernote/dist/summernote.css"
	rel="stylesheet">
</head>

<body>
	<jsp:include page="/static/include/header.jsp"></jsp:include>

	<section id="main">
		<jsp:include page="/static/include/left_aside.jsp"></jsp:include>
		<section id="content">
			<div class="container">
				<div class="block-header">
					<h2><i class="zmdi zmdi-border-color zmdi-hc-fw"></i> <span>发布文章</span></h2>
					<ul class="actions">
						<li><a href=""> <i class="zmdi zmdi-trending-up"></i>
						</a></li>
						<li><a href=""> <i class="zmdi zmdi-check-all"></i>
						</a></li>
						<li class="dropdown"><a href="" data-toggle="dropdown"
							aria-expanded="false"> <i class="zmdi zmdi-more-vert"></i>
						</a>
							<ul class="dropdown-menu dropdown-menu-right">
								<li><a href="">Refresh</a></li>
								<li><a href="">Manage Widgets</a></li>
								<li><a href="">Widgets Settings</a></li>
							</ul></li>
					</ul>
				</div>
				<div class="card">
					<div class="card-header">
						<div class="form-group fg-float">
							<div class="fg-line">
								<input type="text" class="input-lg form-control fg-input">
								<label class="fg-label f-18 f-800">标题</label>
							</div>
						</div>
					</div>
					<div class="card-body card-padding">
						<div id="article_editor">
						</div>
					</div>
				</div>

			</div>
		</section>
	</section>
	<jsp:include page="/static/include/footer.jsp"></jsp:include>
	<jsp:include page="/static/include/base_js.jsp"></jsp:include>
	<script
		src="<%=request.getContextPath()%>/static/assets/vendors/summernote/dist/summernote.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/static/assets/vendors/summernote/dist/lang/summernote-zh-CN.min.js"></script>
	<script type="text/javascript">
		$(function() {
			var flag=true;
			$('#article_editor').summernote({
				height : 300,
				lang : 'zh-CN',
				callbacks: {
					onImageUpload : function(files) {
						sendFile(files);
					},
					onInit: function() {
						showHint();
				    },
					onFocus: function() {
						if(flag) {
							$(this).summernote('code', "");
							flag=false;
						}
				    },
				    onBlur: function() {
				    	var $self = $(this);
				        setTimeout(function() {
				          if ($self.summernote('isEmpty') && !$self.summernote('codeview.isActivated')) {
				        	  showHint();
				        	  flag=true;
				          }
				        }, 300);
				    }
				}
			});
			showHint();
			function sendFile(files) {
				data = new FormData();
				data.append("files", files[0]);
				$.ajax({
					data : data,
					type : "POST",
					url : "../upload.do",
					cache : false,
					contentType : false,
					processData : false,
					success : function(result) {
						$('#article_editor').summernote('insertImage',
								result.urls[0]);
					}
				});
			}
			function showHint() {
				$('#article_editor').summernote('code', "<h2><font color='#9c9c94'>正文内容</font></h2>");
			}
		});
	</script>
</body>
</html>