<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<script src="lib/jquery-3.5.1.min .js"></script>
<style media="screen and (min-width:1024px)">
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
footer{
	position: relative;
	bottom:0;
	width:100%;
	background-color: #2f3042;
}
footer ul{
	list-style-type: none;
}
footer a:visited,footer a:link{
	color:white;
	text-decoration: none;
}
footer *{
	color:white;
}
footer p{
	width:15%;
	float:left;
	padding:30px 0;
	text-align: center;
	font-weight:bold;
	font-size: 27px;
	color:#5f6072;
}
footer ul{
	width:85%;
	float:left;
	padding:45px 0;
}
footer li{
	float:left;
	width:110px;
	font-size:14px;
}
footer li:last-child{
	width:130px;
}
footer img{
	width:10px;
}
footer div:first-of-type{
	width:15%;
	padding-bottom:20px;
	text-align: center;
}
footer div:last-of-type{
	padding-left:30px;
	padding-bottom:30px;
	font-size:14px;
}
.hidden{
	display: none;
}
</style>
<style media="screen and (min-width:0) and (max-width:1024px)">
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
footer{
	position: relative;
	bottom:0;
	width:100%;
	background-color: #2f3042;
}
footer ul{
	list-style-type: none;
}
footer a:visited,footer a:link{
	color:white;
	text-decoration: none;
}
footer *{
	color:white;
}
footer p{
	padding:30px 0 0 0;
	width:100%;
	text-align: center;
	font-weight:bold;
	font-size: 27px;
	color:#5f6072;
}
footer ul{
	width:100%;
	padding:20px 0;
}
footer li{
	width:100%;
	padding:10px 0 10px 30px;
	float:left;
	font-size:14px;
}
footer img{
	width:10px;
}
footer div:first-of-type{
	padding-bottom:20px;
	text-align: center;
}
footer div:last-of-type{
	text-align: center;
	padding-bottom:30px;
	font-size:14px;
}
.hidden{
	display: none;
}
</style>
<script>
	$(function(){
		$("#show_corp_info").click(function(e){
			e.preventDefault();
			if($("#corp_info").hasClass("hidden")){
				$("#corp_info").removeClass("hidden");
			}else{
				$("#corp_info").addClass("hidden");
			}
		})
	})
</script>
<body>
	<footer>
		<p>
			UTrip			
		</p>
		<ul>
			<li><a href="#">????????????</a></li>
			<li><a href="#">????????????</a></li>
			<li><a href="#">????????????</a></li>
			<li><a href="#">???????????? ????????????</a></li>
		</ul>
		<div>
			<a href="#" id="show_corp_info">
				<small>?? utrip ????????? ??????</small> <img src="img/dropdown_white.png">
			</a>
		</div>
		<div id="corp_info" class="hidden">
			??????: utrip ???????????? / ??????: ?????????<br>
			??????: ???????????? ?????????<br>
			????????????????????? : 120-11-1526372 <br>
			????????? :  utrip@utrip.net   /       ???????????? : 0000-0000
		</div>
	</footer>
</body>
</html>