<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="lib/jquery-3.5.1.min .js"></script>
<style>
 @font-face { 
	font-family: 'Noto Sans KR'; 
	font-weight:normal;
	font-style:normal;
	src: 'url(/font/NotoSansKR-Bold.otf) format('truetype'); 
} 
*{
	font-family: 'Noto Sans KR';
	padding:0;
	margin:0;
	color:#121212;
}
header a:link,a:visited{
	text-decoration: none;
	color:black;
	transition: all 0.1s;
}
/*****************************/
header{
	font-weight:bold;
	text-align: center;
	height:55px;
	transition:all 0.3s;
	position:relative;
	width:100%;
	z-index:3;
	border-bottom: 1px solid #e9e9e9;
	background-color: white;
}
header div:first-child{
	width:10%;
	float:left;
	font-size: 18px;
	padding:15px 0;
}
/****************************/
header>ul{
	display:block;
	width:45%;
	min-width:503px;
	float:left;
	list-style-type: none;
	box-sizing: border-box;
}
header>ul a:hover{
	color:#816bff;
}
header li{
	width:100px;
	float:left;
	padding:15px 0;
}
header li a{
	font-size: 14px;
}
/****************************/
#info{
	float:right;
	padding:15px 25px;
	box-sizing:border-box;
	text-align: right;
}
#info ul{
	list-style-type: none;
	float:right;
	background-color: #f1f1f1;
	border-radius: 5px;
	width:200px;
}
header #info li{
	border-top: 1px solid #e8e8e8;
	border-bottom: 1px solid #e8e8e8;
	width:100%;
	text-align: left;
	padding:5px;
	box-sizing: border-box;
}
#info img{
	width:14px;
}
.hidden{
	display: none;
}
</style>
<!-- tablit -->
<style media="screen and (min-width:480px) and (max-width:1024px)">
	header{
		width:100%;
	}
	header>ul{
		display:none;
		width:100%;
		height:270px;
		background-color: white;
		border-radius: 0 0 10px 10px;
		min-width:0;
	}
	header>ul li{
		width:100%;
		min-width:0;
		text-align: center;
	}
	#info{
		display: none;
		float:left;
		text-align: left;
		background-color: white;
		width:100%;
	}
	#info ul{
		width:100%;
	}
	header>div:first-of-type{
		width:100%;
		text-align: center;
	}
	#menu{
		display: block;
		float:right;
		position:absolute;
		top:13px;
		right:10px;
	}
	#btn_user{
		display: block;
		float:left;
		position:absolute;
		top:16px;
		left:10px;
	}
</style>
<!-- mobile -->
<style media="screen and (min-width:0) and (max-width:480px)">
	header{
		width:100%;
	}
	header>ul{
		display:none;
		width:100%;
		height:270px;
		background-color: white;
		border-radius: 0 0 10px 10px;
		min-width:0;
	}
	header>ul li{
		width:100%;
		min-width:0;
		text-align: center;
	}
	#info{
		display: none;
		float:left;
		text-align: left;
		background-color: white;
		width:100%;
	}
	#info ul{
		width:100%;
	}
	header>div:first-of-type{
		width:100%;
		text-align: center;
	}
	#menu{
		display: block;
		float:right;
		position:absolute;
		top:13px;
		right:10px;
	}
	#btn_user{
		display: block;
		float:left;
		position:absolute;
		top:18px;
		left:10px;
	}
</style>
<body>
	<header>
		<div>
		<a href="#"  class="hidden" id="btn_user">
			<img alt="????????? ??????" src="img/user.png">
		</a>
			<a href="/">UTrip</a>
		</div>
		<a href="#" id="menu" class="hidden">
			<img alt="??????" src="img/menu.png">
		</a>
		<ul>
			<li><a href="notice.do">????????????</a></li> <!-- ?????? -->
			<li><a href="qnaView.do">QnA</a></li>
			<li><a href="tripView.do">????????????</a></li>
			<li><a href="board.do">????????????</a></li>
			<li><a href="hotelRequestView.do">????????????</a></li>
		</ul>
		
		<!-- ????????? ??? ????????????  -->
		<div id="info">
			<p>
				<c:if test="${!empty sessionScope.user}">
					<a href="#" id="user">??????????????? ${sessionScope.user.name }???!<img src="img/dropdown.png"></a>
				</c:if>
			<!-- ????????? ?????? ????????????  -->
				<c:if test="${empty sessionScope.user}">
					<a href="loginView.do">?????????/????????????</a>
				</c:if>
			</p>
			<ul id="user_dropdown" class="hidden">
				<li><a href="infoUpdateView.do">????????????</a></li>
				<li><a href="logout.do">????????????</a></li>
				<li><a href="#">??? ?????????</a></li>
			</ul>
		</div>
	</header>
	<script>
	$(function(){
		$("#user").click(function(){
			$("#user_dropdown").toggleClass("hidden");
		})
		
		$("#menu").click(function(e){
			e.preventDefault();
			$("#info").slideUp(400)
			$("header>ul").slideToggle(400)
		})
		$("#btn_user").click(function(e){
			e.preventDefault();
			$("header>ul").slideUp(400)
			$("#info").slideToggle(400)
		})
		
		$(window).resize(function(){
			if($(window).width() > 1026){
				$("header>ul").css({"display":"block"});
				$("#info").css({"display":"block"});
			}else{
				$("header>ul").css({"display":"none"});
				$("#info").css({"display":"none"});
			}
		})
	})
	</script>
</body>
