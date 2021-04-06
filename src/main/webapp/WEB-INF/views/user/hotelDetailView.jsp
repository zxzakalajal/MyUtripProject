<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>지도 범위 재설정 하기</title>
<link rel="stylesheet" href="css/hotel_detail_view.css">
<style>

</style>
</head>
<body>
<c:choose>
		<c:when test="${sessionScope.user.role == 'ADMIN' }">
			<%@include file="../template/header_admin.jsp"%>
		</c:when>
		<c:otherwise>
			<%@include file="../template/header.jsp"%>
		</c:otherwise>
	</c:choose>
	<div id="container">
	<div id="content">
	<h2>호텔정보</h2>
	<div id="map" ></div>
	<p class="back_btn">
		<button onclick="setBounds()" id="btn_zoom" class="button">재설정 하기</button>
		<span id="go_back">
					<a href="#" id="back_btn" class="button" onclick="history.back()">뒤로가기</a>
		</span>
	</p>
	</div>
	</div>

	<script type="text/javascript"
		src="//dapi.kakao.com/v2/maps/sdk.js?appkey=16b17515f471d69c146a2979295c4faf"></script>
	<script>
		var list = ${requestScope.list}
		
		var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
		mapOption = {
			center : new kakao.maps.LatLng(
	<%=request.getAttribute("avgX")%>
		,
	<%=request.getAttribute("avgY")%>
		), // 지도의 중심좌표
			level : 10
		// 지도의 확대 레벨
		};
		var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

		// 버튼을 클릭하면 아래 배열의 좌표들이 모두 보이게 지도 범위를 재설정합니다 
		var points = new Array();
		var content = new Array();
		
		
		for (i = 0; i < list.length; i++) {
			points[i] = new kakao.maps.LatLng(list[i].hotel_x, list[i].hotel_y);
			content[i]= '<div class="customoverlay">'
				+ '  <a href="'+list[i].hotel_url+'" target="_blank">'
				+ '    <span class="title">'+list[i].hotel_name+'</span>' + '  </a>' + '</div>';
		}

		// 지도를 재설정할 범위정보를 가지고 있을 LatLngBounds 객체를 생성합니다
		var bounds = new kakao.maps.LatLngBounds();

		var i, marker, overlay;
		for (i = 0; i < points.length; i++) {
			// 배열의 좌표들이 잘 보이게 마커를 지도에 추가합니다
			marker = new kakao.maps.Marker({
				position : points[i]
			});
			marker.setMap(map);
			overlay = new kakao.maps.CustomOverlay({
				content : content[i],
				map : map,
				position : marker.getPosition()
			});

			// LatLngBounds 객체에 좌표를 추가합니다
			bounds.extend(points[i]);
		}

		function setBounds() {
			// LatLngBounds 객체에 추가된 좌표들을 기준으로 지도의 범위를 재설정합니다
			// 이때 지도의 중심좌표와 레벨이 변경될 수 있습니다
			map.setBounds(bounds);
		}

		// 마커를 클릭했을 때 커스텀 오버레이를 표시합니다
		kakao.maps.event.addListener(marker, 'click', function() {
			overlay.setMap(map);
		});

		// 커스텀 오버레이를 닫기 위해 호출되는 함수입니다 
		function closeOverlay() {
			overlay.setMap(null);
		}
		setBounds();
	</script>
	<%@include file="../template/footer.jsp"%>
</body>
</html>