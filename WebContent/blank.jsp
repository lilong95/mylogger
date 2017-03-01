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
    </head>
    
    <body>
    	<jsp:include page="/static/include/header.jsp"></jsp:include>
        
        <section id="main">
        	<jsp:include page="/static/include/left_aside.jsp"></jsp:include>
            <section id="content">
                <div class="container">
					<div class="block-header">
                        <h2>Typography</h2>
                    
                        <ul class="actions">
                            <li>
                                <a href="">
                                    <i class="zmdi zmdi-trending-up"></i>
                                </a>
                            </li>
                            <li>
                                <a href="">
                                    <i class="zmdi zmdi-check-all"></i>
                                </a>
                            </li>
                            <li class="dropdown">
                                <a href="" data-toggle="dropdown">
                                    <i class="zmdi zmdi-more-vert"></i>
                                </a>
                    
                                <ul class="dropdown-menu dropdown-menu-right">
                                    <li>
                                        <a href="">Refresh</a>
                                    </li>
                                    <li>
                                        <a href="">Manage Widgets</a>
                                    </li>
                                    <li>
                                        <a href="">Widgets Settings</a>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                    </div>
                    <div class="card-body card-padding">
                    </div>
                </div>
            </section>
        </section>
        
		<jsp:include page="/static/include/footer.jsp"></jsp:include>
		<jsp:include page="/static/include/base_js.jsp"></jsp:include>   
    </body>
</html>