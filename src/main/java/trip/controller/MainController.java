package trip.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import trip.dto.QnaDTO;
import trip.dto.ResponseDTO;
import trip.dto.TripDTO;
import trip.dto.UserDTO;
import trip.enums.MBTIType;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import trip.oauth.KakaoLogin;
import trip.dto.CourseDTO;
import trip.dto.HotelDTO;
import trip.dto.MbtiDTO;
import trip.service.UserService;

@Controller
public class MainController {
	private UserService userService;
	
	@Autowired	
	KakaoLogin kakaoLogin;
	
	public MainController(UserService userService) {
		this.userService = userService;
	}
		
	@RequestMapping("/")
	public String main(HttpServletRequest req) {
		List<TripDTO> populList = userService.selectPopulTripList();
		req.setAttribute("populList", populList);
		System.out.println(populList);
		HttpSession session = req.getSession();
		String mbti = "";
		UserDTO user = null;
		String[] mbtiTypes = {"ISTJ","ISTP","ISFJ","ISFP","INFJ","INFP","INTJ","INTP","ESTP","ESTJ","ESFP","ESFJ","ENFP","ENFJ","ENTP","ENTJ"};
		if(session.getAttribute("user")!=null) {
			user = (UserDTO)session.getAttribute("user");
			mbti = user.getMbti();
		}else {
			int index =  (int)Math.floor(16 * Math.random());
			mbti = mbtiTypes[index];
			System.out.println(mbti);
		}
		req.setAttribute("mbti", mbti);
		List<TripDTO> mbtiTripList = userService.selectMbtiTripList(mbti);
		req.setAttribute("mbtiTripList", mbtiTripList);
		
		// 이번달 활동왕 top10 
		Map<String, Integer> activeAmount = new HashMap<String, Integer>();
		for(String mb : mbtiTypes) {
			int bAc = userService.amountB(mb);
			int cAc = userService.amountC(mb);
			// 게시글 작성 3점 댓글작성 1점
			activeAmount.put(mb, 3*bAc + cAc);
		}
		Object[] mapValues = activeAmount.values().toArray();
		Arrays.sort(mapValues, Collections.reverseOrder());
		ArrayList<String> mbtiRanking = new ArrayList<String>();
		for(int i=0; i<=9; i++) {
			for(String mb : mbtiTypes) {
				if(activeAmount.get(mb) == mapValues[i]) {
					mbtiRanking.add(mb);
					activeAmount.replace(mb, 0);
					break;
				}
			}
		}
		Map<String, String> mbtiDes = new HashMap<String, String>();
		mbtiDes.put("INTJ", "  -  용의주도한 전략가");
		mbtiDes.put("INTP", "  -  논리적인 사색가");
		mbtiDes.put("ENTJ", "  -  대담한 통솔자");
		mbtiDes.put("ENTP", "  -  뜨거운 논쟁을 즐기는 변론가");
		mbtiDes.put("INFJ", "  -  선의의 옹호자");
		mbtiDes.put("INFP", "  -  열정적인 중재자");
		mbtiDes.put("ENFJ", "  -  정의로운 사회운동가");
		mbtiDes.put("ENFP", "  -  재기발랄한 활동가");
		mbtiDes.put("ISTJ", "  -  청렴결백한 논리주의자");
		mbtiDes.put("ISFJ", "  -  용감한 수호자");
		mbtiDes.put("ESTJ", "  -  엄격한 관리자");
		mbtiDes.put("ESFJ", "  -  사교적인 외교관");
		mbtiDes.put("ISTP", "  -  만능 재주꾼");
		mbtiDes.put("ISFP", "  -  호기심 많은 예술가");
		mbtiDes.put("ESTP", "  -  모험을 즐기는 사업가");
		mbtiDes.put("ESFP", "  -  자유로운 영혼의 연예인");
		for(int i=0; i<mbtiRanking.size(); i++) {
			mbtiRanking.set(i, mbtiRanking.get(i)+" "+mbtiDes.get(mbtiRanking.get(i)));
		}
		req.setAttribute("mbtiRanking", mbtiRanking);
		
		return "TripMain";
	}

	
	@RequestMapping("tripMain.do")
	public String tripMain() {
		return "TripMain";
	}
	
	@RequestMapping("/tripView.do")
	public String tripView(HttpServletRequest request, HttpSession session) {
		UserDTO userdto= (UserDTO) session.getAttribute("user");
		
		//String mbti="ENTJ";
		List<TripDTO> list = userService.selectPopulTripList();
		List<TripDTO> areaList = userService.selectAreaList();
		request.setAttribute("list", list);
		request.setAttribute("areaList", areaList);
		return "user/tripView";
	}
	
	@RequestMapping("/tripDetailView.do")
	public String tripDetailView(HttpServletRequest request) {
		String tripNo = request.getParameter("trip_no");
		
		//여행 정보 내용
		TripDTO dto = userService.selectTripInfo(tripNo);
		//여행코스 리스트
		List<CourseDTO> list = userService.selectCourseInfo(tripNo);
		
		double x=0,y=0;
		for(int i=0; i<list.size(); i++) {
			x+=list.get(i).getPlace_x();
			y+=list.get(i).getPlace_y();
		}
		x = x/list.size();
		y = y/list.size();
		JSONArray arr = new JSONArray(list);
		request.setAttribute("tripList", list);
		request.setAttribute("list", arr.toString());
		request.setAttribute("area", dto.getArea_name());
		request.setAttribute("trip_no", tripNo);
		request.setAttribute("dto", dto);
		request.setAttribute("avgX", x);
		request.setAttribute("avgY", y);

		// 좋아요 갯수
		HttpSession session = request.getSession();
		if(session.getAttribute("user") != null) {
			UserDTO user = (UserDTO) session.getAttribute("user");
			int like = userService.getTripLike(tripNo, user.getMbti());
			request.setAttribute("tripLike", like);
		}
		
		return "user/tripDetailView";
	}
	
	@RequestMapping("/hotelView.do")
	public String hotelView(HttpServletRequest request, String area) {
		List<HotelDTO> list = userService.selectHotelInfo(area);
		JSONArray arr = new JSONArray(list);
		double x=0,y=0;
		for(int i=0; i<list.size(); i++) {
			x+=list.get(i).getHotel_x();
			y+=list.get(i).getHotel_y();
		}
		x = x/list.size();
		y = y/list.size();
		
		request.setAttribute("list", arr.toString());
		request.setAttribute("avgX", x);
		request.setAttribute("avgY", y);
		
		return "user/hotelDetailView";
	}
	
	@RequestMapping("/areaView.do")
	public String tripAreaView(String area,HttpServletRequest req) {
		List<TripDTO> list = userService.selectTripByArea(area);
		System.out.println(list);
		req.setAttribute("list", list);
		return "user/areaView";
	}
	
	// tripDetailView에서 따봉 클릭시 
	@RequestMapping("/tripLike.do")
	public String tripLike(String tripNo,String mbti,String userId, HttpServletResponse response) {
		ResponseDTO<String> resp = new ResponseDTO<String>();
		// 이미 해당 trip 에 따봉을 누른 유저인지 체크
		int check = userService.tripLikeCheck(tripNo,userId);
		// 이미 해당 trip 에 따봉을 누른 유저일때
		if(check > 0) {
			response.setContentType("html/text;charset=utf-8");
			resp.setResponseCode(201);
			resp.setResponseMessage("좋아요는 한번만 가능 합니다");
			JSONObject json = new JSONObject(resp);
			try {
				response.getWriter().write(json.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		// 해당 trip 에 처음 따봉을 누른 유저일때
		int count = userService.tripLike(tripNo,mbti,userId);
		if(count==1) {
			resp.setResponseCode(200);
		}else {
			resp.setResponseCode(500);
		}
		int tripLike = userService.getTripLike(tripNo,mbti);
		resp.setResponseMessage(""+tripLike);
		response.setContentType("html/text;charset=utf-8");
		JSONObject json = new JSONObject(resp);
		try {
			response.getWriter().write(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// tripDetailView 에서 별점 매겼을때 실행되는 메서드
	@RequestMapping("/assess.do")
	public String assess(int score,String trip_no,String user_id,HttpServletResponse res) {
		ResponseDTO<Double> resp = new ResponseDTO<>();
		// 해당 trip 에 별점을 이미 매긴 유저인지 체크
		int count = userService.tripRatingCheck(user_id,trip_no);
		if(count >= 1) {
			ResponseDTO<String> resp2 = new ResponseDTO<>();
			resp2.setResponseCode(201);
			resp2.setResponseMessage("평가는 한번만 하실수 있습니다");
			res.setContentType("html/text;charset=utf-8");
			try {
				res.getWriter().write((new JSONObject(resp2)).toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		double rating = 0.0;
		rating = userService.assessment(score,trip_no,user_id);
		resp.setResponseCode(200);
		resp.setResponseMessage(rating);
		res.setContentType("html/text;charset=utf-8");
		try {
			res.getWriter().write((new JSONObject(resp)).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	@RequestMapping("mbtiTestView.do")
	public String mbtiTestView(HttpServletRequest request) {
		List<MbtiDTO> list = userService.getMbtiList();
		request.setAttribute("list", list);
		return "user/mbtiView";
	}
	
	@RequestMapping("mbtiTest.do")
	public String mbtiTest(HttpServletRequest request) {
		System.out.println("A");
		List<String> list = userService.getMbtiType();
		int count = userService.getCount();
		int sum=0;
		String mbti="";
		ArrayList<MbtiDTO> mList = new ArrayList<MbtiDTO>();
		for(int i=1; i<=count/9; i++) {
			for(int j=1; j<=9; j++) {
			sum+=Integer.parseInt(request.getParameter(""+(j+(i-1)*9)));
			}
			mList.add(new MbtiDTO(list.get(i-1), sum));
			sum=0;
		}
		
		System.out.println("a"+mList.toString());
		for(int i=0; i<mList.size(); i+=2) {
			if(mList.get(i).getCount()>mList.get(i+1).getCount()) {
				mbti+=mList.get(i).getMbti_type();
			}else if(mList.get(i).getCount()<mList.get(i+1).getCount()){
				mbti+=mList.get(i+1).getMbti_type();
			}else {
				mbti+=mList.get(i).getMbti_type();
			}
			
		}
		System.out.println(mbti);
		request.setAttribute("mbti", mbti);
		return "user/mbtiResultView";
	}
	
	@RequestMapping("mbtiTripView.do")
	public String mbtiTripView(HttpSession session, HttpServletRequest request) {
		UserDTO userdto= (UserDTO) session.getAttribute("user");
		List<TripDTO> list = userService.getMbtiTripList(userdto.getMbti());
		request.setAttribute("list", list);
		return "user/mbtiTripView";
	}
	
	@RequestMapping("/selectPopulByLike.do")
	public String selectPopulByLike(HttpServletResponse res) {
		List<TripDTO> list = userService.selectPopulByLike();
		JSONObject result = new JSONObject();
		JSONArray ja = new JSONArray(list);
		result.put("list", ja);
		try {
			res.setContentType("json/html;charset=utf-8");
			res.getWriter().write(result.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping("/selectPopulByRating.do")
	public String selectPopulByRating(HttpServletResponse res) {
		List<TripDTO> list = userService.selectPopulTripList();
		JSONObject result = new JSONObject();
		JSONArray ja = new JSONArray(list);
		result.put("list", ja);
		try {
			res.setContentType("json/html;charset=utf-8");
			res.getWriter().write(result.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/getEmail.do")
	public String getEmail(String mbti, HttpServletResponse res) {
		List<String> emailList = userService.getEmailList(mbti);
		System.out.println(emailList);
		res.setContentType("html/text;charset=utf-8");
		try {
			res.getWriter().write(emailList.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
