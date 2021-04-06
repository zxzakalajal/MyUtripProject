package trip.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import trip.dto.CourseDTO;
import trip.dto.FileDTO;
import trip.dto.HotelDTO;
import trip.dto.HotelRequestDTO;
import trip.dto.MbtiDTO;
import trip.dto.NoticeCommentDTO;
import trip.dto.NoticeDTO;
import trip.dto.QnaDTO;
import trip.dto.TripDTO;
import trip.dto.UserDTO;

@Mapper
public interface UserMapper {
	public int register(UserDTO user);
	public UserDTO selectById(String id);
	public int kakaoRegister(UserDTO user);
	public int update(UserDTO user);
	List<TripDTO> selectPopulTripList();
	TripDTO selectTripInfo(String tripNo);
	List<CourseDTO> selectCourseInfo(String tripNo);
	public List<TripDTO> selectAreaList();
	public List<HotelDTO> selectHotelInfo(String area_name);
	public List<TripDTO> selectTripByArea(String area);
	public int getTripLike(Map<String, Object> map);
	public int tripLike(Map<String, Object> map);
	public void insertTripLike(String tripNo);
	public int tripLikeCheck(Map<String, Object> map);
	public void insertTripLikeUser(Map<String, Object> map);
	public void withdraw(String id);
	public List<NoticeDTO> selectNoticeList(int page);
	public int selectnoticeCount();
	public NoticeDTO selectNotice(int notice_no);
	public List<FileDTO> selectFileList(int notice_no);
	public int insertNoticeComment(NoticeCommentDTO dto);
	public void addnoticeCount(int notice_no);
	public List<NoticeCommentDTO> selectNoticeComment(int notice_no);
	public int updatenoticeCommentLike(int comment_no);
	public int updatenoticeCommentHate(int comment_no);
	public void addNoticeLikeHate(HashMap<String, Object> map);
	public int assessment(Map<String, Object> map);
	public void insertTripRating(String trip_no);
	public double getRating(String trip_no);
	public void updateTripRating(Map<String, Object> map);
	public int tripRatingCheck(Map<String, Object> map);
	public void insertTripRatingUser(Map<String, Object> map);
	public int nextnotice(int notice_no);
	public List<MbtiDTO> getMbtiList();
	public List<String> getMbtiType();
	public int getCount();
	public List<TripDTO> getMbtiTripList(String mbti);
	public List<TripDTO> selectMbtiTripList(String mbti);
	public List<TripDTO> selectPopulByLike();
	public int amountB(String mb);
	public int amountC(String mb);
	public List<String> getEmailList(String mbti);
}
