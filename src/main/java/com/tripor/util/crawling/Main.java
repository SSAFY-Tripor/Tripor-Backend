package com.tripor.util.crawling;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.tripor.util.DBUtil;

/**
 * DB 데이터 없는 경우 초기 세팅용
 */
public class Main {
	private static final int DEFAULT_ROWS = 1000;
	private static DBUtil dbUtil = DBUtil.getInstance();

	public static void main(String[] args) throws IOException {
		sidoAdd();
		StringBuilder urlBuilder = new StringBuilder(
				"https://apis.data.go.kr/B551011/KorService1/areaBasedList1"); /* URL */
		urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8")
				+ "=JHW%2B1hoKL%2B7INEwzeiiNgY%2Flx%2Fowvj1mY%2BMnLWSDbuY9PsYztUv%2BVziTl5zHvAbNTKlXP3MTVs5jK1fOfZ28dQ%3D%3D");
		urlBuilder
				.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /* 페이지번호 */
		urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "="
				+ URLEncoder.encode(String.valueOf(DEFAULT_ROWS), "UTF-8")); /* 한 페이지 결과 수 */
		urlBuilder.append(
				"&" + URLEncoder.encode("MobileOS", "UTF-8") + "=" + URLEncoder.encode("ETC", "UTF-8")); /* OS 구분 */
		urlBuilder.append(
				"&" + URLEncoder.encode("MobileApp", "UTF-8") + "=" + URLEncoder.encode("Tripor", "UTF-8")); /* 서비스명 */
		// urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" +
		// URLEncoder.encode("json", "UTF-8")); /*_type*/
		urlBuilder
				.append("&" + URLEncoder.encode("listYN", "UTF-8") + "=" + URLEncoder.encode("N", "UTF-8")); /* Y=목록 */
		urlBuilder.append(
				"&" + URLEncoder.encode("arrange", "UTF-8") + "=" + URLEncoder.encode("A", "UTF-8")); /* A=제목순 */

		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
//		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
		System.out.println(urlBuilder.toString());

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			// sb.toString으로 얻은 값을 문서(XML)로 만든 것
			Document document = builder.parse(new InputSource(new StringReader(sb.toString())));

			NodeList nodeList = document.getElementsByTagName("totalCnt");

			int totalCnt = Integer.parseInt(nodeList.item(0).getTextContent());
//			int totalCnt = 30;

			for (int pageNo = 1; pageNo <= totalCnt / DEFAULT_ROWS + 1; pageNo++) {
				StringBuilder urlBuilder2 = new StringBuilder(
						"https://apis.data.go.kr/B551011/KorService1/areaBasedList1"); /* URL */
				urlBuilder2.append("?" + URLEncoder.encode("serviceKey", "UTF-8")
						+ "=JHW%2B1hoKL%2B7INEwzeiiNgY%2Flx%2Fowvj1mY%2BMnLWSDbuY9PsYztUv%2BVziTl5zHvAbNTKlXP3MTVs5jK1fOfZ28dQ%3D%3D");
				urlBuilder2.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "="
						+ URLEncoder.encode(String.valueOf(pageNo), "UTF-8")); /* 페이지번호 */
				urlBuilder2.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "="
						+ URLEncoder.encode(String.valueOf(DEFAULT_ROWS), "UTF-8")); /* 한 페이지 결과 수 */
				urlBuilder2.append("&" + URLEncoder.encode("MobileOS", "UTF-8") + "="
						+ URLEncoder.encode("ETC", "UTF-8")); /* OS 구분 */
				urlBuilder2.append("&" + URLEncoder.encode("MobileApp", "UTF-8") + "="
						+ URLEncoder.encode("Tripor", "UTF-8")); /* 서비스명 */
				// urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" +
				// URLEncoder.encode("json", "UTF-8")); /*_type*/
				urlBuilder2.append(
						"&" + URLEncoder.encode("listYN", "UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8")); /* Y=목록 */
				urlBuilder2.append("&" + URLEncoder.encode("arrange", "UTF-8") + "="
						+ URLEncoder.encode("A", "UTF-8")); /* A=제목순 */
				url = new URL(urlBuilder2.toString());
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Content-type", "application/json");
				if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
					rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				} else {
					rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
				}
				sb = new StringBuilder();
				while ((line = rd.readLine()) != null) {
					sb.append(line);
				}
				rd.close();
				conn.disconnect();

				factory = DocumentBuilderFactory.newDefaultInstance();
				builder = factory.newDocumentBuilder();

				document = builder.parse(new InputSource(new StringReader(sb.toString())));

				nodeList = document.getElementsByTagName("item");
				int len = nodeList.getLength();
//				System.out.println(len);

				Connection con = null;
				PreparedStatement ps = null;

				for (int i = 0; i < len; i++) {
					Node node = nodeList.item(i);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						Element element = (Element) node;
//						System.out.println("==========================================");
						String contentId = element.getElementsByTagName("contentid").item(0).getTextContent();
						if (contentId == null || contentId.length() == 0) {
							continue;
						}
//						System.out.println("콘텐트 아이디 : " + contentId);
						String contentTypeId = element.getElementsByTagName("contenttypeid").item(0).getTextContent();
						if (contentTypeId == null || contentTypeId.length() == 0) {
							continue;
						}
//						System.out.println("콘텐트 타입 : " + contentTypeId);
						String title = element.getElementsByTagName("title").item(0).getTextContent();
						if (title == null || title.length() == 0) {
							continue;
						}
//						System.out.println("이름 : " + title);
						String addr = element.getElementsByTagName("addr1").item(0).getTextContent();
						if (addr == null || addr.length() == 0) {
							continue;
						}
//						System.out.println("주소 : " + addr);
						String tel = element.getElementsByTagName("tel").item(0).getTextContent();
						if (tel != null) {
//							System.out.println("전화번호 : " + tel);
							// 없어도 됨.
						}
						String firstImage = element.getElementsByTagName("firstimage").item(0).getTextContent();
						if (firstImage != null) {
//							System.out.println("이미지 : " + firstImage);
							// 없어도 됨.
						}
						String sidoCode = element.getElementsByTagName("areacode").item(0).getTextContent();
						if (sidoCode == null || sidoCode.length() == 0) {
							continue;
						}
//						System.out.println("시도코드 : " + sidoCode);
						String gugunCode = element.getElementsByTagName("sigungucode").item(0).getTextContent();
						if (gugunCode == null || gugunCode.length() == 0) {
							continue;
						}
//						System.out.println("구군코드 : " + gugunCode);
						String mapx = element.getElementsByTagName("mapx").item(0).getTextContent();
						if (mapx == null || mapx.length() == 0) {
							continue;
						}
//						System.out.println("위도 : " + latitude);
						String mapy = element.getElementsByTagName("mapy").item(0).getTextContent();
						if (mapy == null || mapy.length() == 0) {
							continue;
						}
						double longitude = Double.parseDouble(mapx);
						if(longitude < 124 || longitude > 132) continue;
						double latitude = Double.parseDouble(mapy);
						if(latitude < 33 || latitude > 43) continue;
//						System.out.println("경도 : " + longitude);
						String mLevel = element.getElementsByTagName("mlevel").item(0).getTextContent();
						if (mLevel != null) {
//							System.out.println("맵레벨 : " + mLevel);
							mLevel = "0";
							// 없어도 됨.
						}
						String cat1 = element.getElementsByTagName("cat1").item(0).getTextContent();
						if (cat1 != null) {
//							System.out.println("cat1 : " + cat1);
							// 없어도 됨.
						}
						String cat2 = element.getElementsByTagName("cat2").item(0).getTextContent();
						if (cat2 != null) {
//							System.out.println("cat2 : " + cat2);
							// 없어도 됨.
						}
						String cat3 = element.getElementsByTagName("cat3").item(0).getTextContent();
						if (cat3 != null) {
//							System.out.println("cat3 : " + cat3);
							// 없어도 됨.
						}
//						System.out.println("여까지 옴");
						// description은 5번 공통정보조회에 있음.
						try {
							con = dbUtil.getConnection();
							StringBuilder sql = new StringBuilder();
							sql.append(
									"insert into attraction_info (content_id, content_type_id, title, addr, tel, first_image, sido_code, gugun_code, longitude, latitude, mlevel, cat1, cat2, cat3) \n");
							sql.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
							ps = con.prepareStatement(sql.toString());
							ps.setInt(1, Integer.parseInt(contentId));
							ps.setInt(2, Integer.parseInt(contentTypeId));
							ps.setString(3, title);
							ps.setString(4, addr);
							ps.setString(5, tel);
							ps.setString(6, firstImage);
							ps.setInt(7, Integer.parseInt(sidoCode));
							ps.setInt(8, Integer.parseInt(gugunCode));
							ps.setDouble(9, longitude);
							ps.setDouble(10, latitude);
							ps.setInt(11, Integer.parseInt(mLevel));
							ps.setString(12, cat1);
							ps.setString(13, cat2);
							ps.setString(14, cat3);
							ps.execute();
						} catch (Exception e) {
							System.out.println(
									contentId + " " + sidoCode + " " + gugunCode + " " + longitude + " " + latitude);
							e.printStackTrace();
						} finally {
							dbUtil.close(ps, con);
						}
					}
				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}

	private static void sidoAdd() throws IOException {
		StringBuilder urlBuilder = new StringBuilder("https://apis.data.go.kr/B551011/KorService1/areaCode1"); /* URL */
		urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8")
				+ "=JHW%2B1hoKL%2B7INEwzeiiNgY%2Flx%2Fowvj1mY%2BMnLWSDbuY9PsYztUv%2BVziTl5zHvAbNTKlXP3MTVs5jK1fOfZ28dQ%3D%3D");
		urlBuilder
				.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /* 페이지번호 */
		urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "="
				+ URLEncoder.encode(String.valueOf(100), "UTF-8")); /* 한 페이지 결과 수 */
		urlBuilder.append(
				"&" + URLEncoder.encode("MobileOS", "UTF-8") + "=" + URLEncoder.encode("ETC", "UTF-8")); /* OS 구분 */
		urlBuilder.append(
				"&" + URLEncoder.encode("MobileApp", "UTF-8") + "=" + URLEncoder.encode("Tripor", "UTF-8")); /* 서비스명 */
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			Document document = builder.parse(new InputSource(new StringReader(sb.toString())));

			NodeList nodeList = document.getElementsByTagName("totalCnt");

			factory = DocumentBuilderFactory.newDefaultInstance();
			builder = factory.newDocumentBuilder();

			document = builder.parse(new InputSource(new StringReader(sb.toString())));

			nodeList = document.getElementsByTagName("item");
			int len = nodeList.getLength();
			System.out.println(len);

			Connection con = null;
			PreparedStatement ps = null;

			for (int i = 0; i < len; i++) {
				Node node = nodeList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					String code = element.getElementsByTagName("code").item(0).getTextContent();
					String name = element.getElementsByTagName("name").item(0).getTextContent();
					try {
						con = dbUtil.getConnection();
						StringBuilder sql = new StringBuilder();
						sql.append("insert into sido (sido_code, sido_name) \n");
						sql.append("values (?, ?);");
						ps = con.prepareStatement(sql.toString());
						ps.setInt(1, Integer.parseInt(code));
						ps.setString(2, name);
						ps.executeUpdate();
						gugunAdd(Integer.parseInt(code));
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						dbUtil.close(ps, con);
					}
				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}

	static void gugunAdd(int sido) throws IOException {
		StringBuilder urlBuilder = new StringBuilder("https://apis.data.go.kr/B551011/KorService1/areaCode1"); /* URL */
		urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8")
				+ "=JHW%2B1hoKL%2B7INEwzeiiNgY%2Flx%2Fowvj1mY%2BMnLWSDbuY9PsYztUv%2BVziTl5zHvAbNTKlXP3MTVs5jK1fOfZ28dQ%3D%3D");
		urlBuilder
				.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /* 페이지번호 */
		urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "="
				+ URLEncoder.encode(String.valueOf(1000), "UTF-8")); /* 한 페이지 결과 수 */
		urlBuilder.append(
				"&" + URLEncoder.encode("MobileOS", "UTF-8") + "=" + URLEncoder.encode("ETC", "UTF-8")); /* OS 구분 */
		urlBuilder.append(
				"&" + URLEncoder.encode("MobileApp", "UTF-8") + "=" + URLEncoder.encode("Tripor", "UTF-8")); /* 서비스명 */
		urlBuilder.append("&" + URLEncoder.encode("areaCode", "UTF-8") + "="
				+ URLEncoder.encode(String.valueOf(sido), "UTF-8")); /* 시도 코드 */
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			Document document = builder.parse(new InputSource(new StringReader(sb.toString())));

			NodeList nodeList = document.getElementsByTagName("totalCnt");

			factory = DocumentBuilderFactory.newDefaultInstance();
			builder = factory.newDocumentBuilder();

			document = builder.parse(new InputSource(new StringReader(sb.toString())));

			nodeList = document.getElementsByTagName("item");
			int len = nodeList.getLength();

			Connection con = null;
			PreparedStatement ps = null;

			for (int i = 0; i < len; i++) {
				Node node = nodeList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					String code = element.getElementsByTagName("code").item(0).getTextContent();
					String name = element.getElementsByTagName("name").item(0).getTextContent();
					try {
						con = dbUtil.getConnection();
						StringBuilder sql = new StringBuilder();
						sql.append("insert into gugun (gugun_code, gugun_name, sido_code) \n");
						sql.append("values (?, ?, ?);");
						ps = con.prepareStatement(sql.toString());
						ps.setInt(1, Integer.parseInt(code));
						ps.setString(2, name);
						ps.setInt(3, sido);
						ps.executeUpdate();
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						dbUtil.close(ps, con);
					}
				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}
}
