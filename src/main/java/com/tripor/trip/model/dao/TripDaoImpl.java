package com.tripor.trip.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tripor.trip.model.dto.GugunDto;
import com.tripor.trip.model.dto.SidoDto;
import com.tripor.trip.model.dto.TripDto;
import com.tripor.trip.model.dto.TripSearchDto;
import com.tripor.util.DBUtil;

public class TripDaoImpl implements TripDao {
	private DBUtil dbUtil = DBUtil.getInstance();
	static private TripDao instance = new TripDaoImpl();

	private TripDaoImpl() {
	}

	static public TripDao getInstance() {
		return instance;
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
			sb.append("select ai.content_id, ai.content_type_id, ai.title, ai.addr1, ai.first_image,\n");
			sb.append("ai.sido_code, ai.gugun_code, ai.latitude, ai.longitude, ad.overview\n");
			sb.append("from attraction_info ai left join attraction_description ad\n");
			boolean typeFlag = false;
			if(param.getType() == null) {
				sb.append("on ai.content_id = ad.content_id where ai.sido_code=? and ai.gugun_code=?;");
			}else {
				typeFlag = true;
				sb.append("on ai.content_id = ad.content_id where ai.sido_code=? and ai.gugun_code=? and content_type_id=?;");
			}
			
			ps = con.prepareStatement(sb.toString());
			System.out.println(sb.toString());
			ps.setString(1, param.getSido());
			ps.setString(2, param.getGugun());
			if(typeFlag) ps.setString(3, param.getType());
			
			rs = ps.executeQuery();

			while(rs.next()) {
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
				tripDto.setOverview(rs.getString(10));
				list.add(tripDto);
			}
			System.out.println(list.size());
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
			System.out.println("???");
			while (rs.next()) {
				SidoDto sidoDto = new SidoDto();
				sidoDto.setSidoCode(rs.getInt("sido_code"));
				sidoDto.setSidoName(rs.getString("sido_name"));
				System.out.println(sidoDto);
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
}
