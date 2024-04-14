package com.tripor.trip.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.tripor.trip.model.dto.GugunDto;
import com.tripor.trip.model.dto.SidoDto;
import com.tripor.trip.model.dto.TripDto;
import com.tripor.trip.model.dto.TripPlanDto;
import com.tripor.trip.model.dto.TripSearchDto;
import com.tripor.util.DBUtil;
import com.tripor.util.crawling.PublicAPI;

public class TripDaoImpl implements TripDao {
	private DBUtil dbUtil = DBUtil.getInstance();
	static private TripDao instance = new TripDaoImpl();

	private TripDaoImpl() {
	}

	static public TripDao getInstance() {
		return instance;
	}

	@Override
	public List<TripDto> searchAll(String keyword) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<TripDto> list = new ArrayList<>();
		try {
			con = dbUtil.getConnection();
			StringBuilder sb = new StringBuilder();
			sb.append("select content_id, content_type_id, title, addr, first_image,\n");
			sb.append("sido_code, gugun_code, latitude, longitude, tel, description \n");
			sb.append("from attraction_info \n");
			sb.append("where title like concat('%', ?, '%')");

			ps = con.prepareStatement(sb.toString());
			ps.setString(1, keyword);

			rs = ps.executeQuery();

			while (rs.next()) {
				TripDto tripDto = new TripDto();
				tripDto.setContentId(rs.getString(1));
				tripDto.setContentTypeId(rs.getInt(2));
				tripDto.setTitle(rs.getString(3));
				tripDto.setAddr(rs.getString(4));
				tripDto.setFirstImage(rs.getString(5));
				tripDto.setSidoCode(rs.getInt(6));
				tripDto.setGugunCode(rs.getInt(7));
				tripDto.setLatitude(rs.getString(8));
				tripDto.setLongitude(rs.getString(9));
				tripDto.setTel(rs.getString(10));
				tripDto.setOverview(rs.getString(11));
				list.add(tripDto);
			}
			return list;
		} catch (SQLException e) {
			throw e;
		} finally {
			dbUtil.close(rs, ps, con);
		}
	}

	@Override
	public List<TripDto> searchAll(TripSearchDto param) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<TripDto> list = new ArrayList<>();
		try {
			con = dbUtil.getConnection();
			StringBuilder sb = new StringBuilder();
			sb.append("select content_id, content_type_id, title, addr, first_image,\n");
			sb.append("sido_code, gugun_code, latitude, longitude, tel, description \n");
			sb.append("from attraction_info \n");
			boolean typeFlag = false;
			if (param.getType() == null) {
				sb.append("where sido_code=? and gugun_code=?;");
			} else {
				typeFlag = true;
				sb.append("where sido_code=? and gugun_code=? and content_type_id=?;");
			}

			ps = con.prepareStatement(sb.toString());
			ps.setString(1, param.getSido());
			ps.setString(2, param.getGugun());
			if (typeFlag)
				ps.setString(3, param.getType());

			rs = ps.executeQuery();

			while (rs.next()) {
				TripDto tripDto = new TripDto();
				tripDto.setContentId(rs.getString(1));
				tripDto.setContentTypeId(rs.getInt(2));
				tripDto.setTitle(rs.getString(3));
				tripDto.setAddr(rs.getString(4));
				tripDto.setFirstImage(rs.getString(5));
				tripDto.setSidoCode(rs.getInt(6));
				tripDto.setGugunCode(rs.getInt(7));
				tripDto.setLatitude(rs.getString(8));
				tripDto.setLongitude(rs.getString(9));
				tripDto.setTel(rs.getString(10));
				tripDto.setOverview(rs.getString(11));
				list.add(tripDto);
			}
			return list;
		} catch (SQLException e) {
			throw e;
		} finally {
			dbUtil.close(rs, ps, con);
		}

	}

	@Override
	public List<SidoDto> searchAllSido() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<SidoDto> list = new ArrayList<SidoDto>();
		try {
			con = dbUtil.getConnection();
			String sql = "select * from sido;";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				SidoDto sidoDto = new SidoDto();
				sidoDto.setSidoCode(rs.getInt("sido_code"));
				sidoDto.setSidoName(rs.getString("sido_name"));
				list.add(sidoDto);
			}
			return list;
		} catch (SQLException e) {
			throw e;
		} finally {
			dbUtil.close(rs, ps, con);
		}
	}

	@Override
	public List<GugunDto> searchGugunBySido(int sidoCode) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<GugunDto> list = new ArrayList<GugunDto>();
		try {
			con = dbUtil.getConnection();
			String sql = "select gugun_code, gugun_name from gugun where sido_code=?;";
			ps = con.prepareStatement(sql);
			ps.setInt(1, sidoCode);
			rs = ps.executeQuery();
			while (rs.next()) {
				GugunDto gugunDto = new GugunDto();
				gugunDto.setGugunCode(rs.getInt("gugun_code"));
				gugunDto.setGugunName(rs.getString("gugun_name"));
				list.add(gugunDto);
			}
			return list;
		} catch (SQLException e) {
			throw e;
		} finally {
			dbUtil.close(rs, ps, con);
		}
	}

	@Override
	public int insertTripPlan(List<String> tripList, String userId, String planName) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "";
		try {
			con = dbUtil.getConnection();
			sql = "insert into trip_plan (plan_name, member_id) values (?, ?)";
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, planName);
			ps.setString(2, userId);
			ps.executeUpdate();

			// 생성된 AUTO_INCREMENT 값을 얻기
			ResultSet generatedKeys = ps.getGeneratedKeys();

			if (generatedKeys.next()) {
				int planId = generatedKeys.getInt(1); // 생성된 AUTO_INCREMENT 값
				for (String contentId : tripList) {
					sql = "insert into plan_content_relation (plan_id, content_id) values (?, ?)";
					ps = con.prepareStatement(sql);
					ps.setInt(1, planId);
					ps.setInt(2, Integer.parseInt(contentId));
					ps.executeUpdate();
				}
			} else {
				throw new SQLException("Failed to get the generated plan_id value.");
			}
			return 1;
		} catch (SQLException e) {
			throw e;
		} finally {
			dbUtil.close(ps, con);
		}
	}

	@Override
	public List<TripDto> searchByPlanId(int planId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<TripDto> list = new ArrayList<TripDto>();
		try {
			con = dbUtil.getConnection();
			String sql = "select content_id from plan_content_relation where plan_id=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, planId);
			rs = ps.executeQuery();
			while (rs.next()) {
				int contentId = rs.getInt(1);
				list.add(searchByContentId(contentId));
			}
			return list;
		} catch (SQLException e) {
			throw e;
		} finally {
			dbUtil.close(rs, ps, con);
		}
	}

	@Override
	public List<TripPlanDto> searchPlansByUserId(String userId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<TripPlanDto> list = new ArrayList<TripPlanDto>();
		try {
			con = dbUtil.getConnection();
			String sql = "select * from trip_plan where member_id=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, userId);
			rs = ps.executeQuery();
			while (rs.next()) {
				TripPlanDto tripPlanDto = new TripPlanDto();
				tripPlanDto.setPlanId(rs.getInt(1));
				tripPlanDto.setPlanName(rs.getString(2));
				tripPlanDto.setPlanUserId(rs.getString(3));
				tripPlanDto.setPlanRegisterDate(rs.getString(4));
				tripPlanDto.setTripList(searchByPlanId(tripPlanDto.getPlanId()));
				list.add(tripPlanDto);
			}
			return list;
		} catch (SQLException e) {
			throw e;
		} finally {
			dbUtil.close(rs, ps, con);
		}
	}

	@Override
	public TripDto searchByContentId(int contentId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = dbUtil.getConnection();
			StringBuilder sb = new StringBuilder();
			sb.append("select content_id, content_type_id, title, addr, first_image,\n");
			sb.append("sido_code, gugun_code, latitude, longitude, tel, description\n");
			sb.append("from attraction_info\n");
			sb.append("where content_id=?");
			ps = con.prepareStatement(sb.toString());
			ps.setInt(1, contentId);
			rs = ps.executeQuery();
			TripDto tripDto = new TripDto();
			if (rs.next()) {
				tripDto.setContentId(rs.getString(1));
				tripDto.setContentTypeId(rs.getInt(2));
				tripDto.setTitle(rs.getString(3));
				tripDto.setAddr(rs.getString(4));
				tripDto.setFirstImage(rs.getString(5));
				tripDto.setSidoCode(rs.getInt(6));
				tripDto.setGugunCode(rs.getInt(7));
				tripDto.setLatitude(rs.getString(8));
				tripDto.setLongitude(rs.getString(9));
				tripDto.setTel(rs.getString(10));
				tripDto.setOverview(rs.getString(11));
				
			}
			return tripDto;
		} catch (SQLException e) {
			throw e;
		} finally {
			dbUtil.close(rs, ps, con);
		}
	}

	@Override
	public TripPlanDto searchPlanByPlanId(int planId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = dbUtil.getConnection();
			String sql = "select * from trip_plan where plan_id=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, planId);
			rs = ps.executeQuery();
			TripPlanDto tripPlanDto = new TripPlanDto();
			if (rs.next()) {
				tripPlanDto.setPlanId(rs.getInt(1));
				tripPlanDto.setPlanName(rs.getString(2));
				tripPlanDto.setPlanUserId(rs.getString(3));
				tripPlanDto.setPlanRegisterDate(rs.getString(4));
				tripPlanDto.setTripList(searchByPlanId(planId));
			}
			return tripPlanDto;
		} catch (SQLException e) {
			throw e;
		} finally {
			dbUtil.close(rs, ps, con);
		}
	}

	@Override
	public int deleteTripPlan(int planId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = dbUtil.getConnection();

			// 외래키 제약 조건을 통해 삭제도 가능하지만, 일단 임시로 삭제 방식 추가
			String deleteRelationSql = "delete from plan_content_relation where plan_id=?";
			ps = con.prepareStatement(deleteRelationSql);
			ps.setInt(1, planId);
			ps.executeUpdate();

			String sql = "delete from trip_plan where plan_id=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, planId);
			return ps.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			dbUtil.close(ps, con);
		}
	}

	public List<TripDto> planJsonToTripList(String json) throws Exception {
		Gson gson = new Gson();
		List<String> tripList = gson.fromJson(json, List.class);
		return searchByContentIds(tripList);
	}

	public List<TripDto> searchByContentIds(List<String> list) throws Exception {
		List<TripDto> returnList = new ArrayList<>();
		for (String contentId : list) {
			returnList.add(searchByContentId(Integer.parseInt(contentId)));
		}
		return returnList;
	}

}
