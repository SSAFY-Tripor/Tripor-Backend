package com.tripor.util.crawling;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

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

public class Test {
	private static final int DEFAULT_ROWS = 10;
	private DBUtil dbUtil = DBUtil.getInstance();

	public static void main(String[] args) throws IOException {
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
		System.out.println("Response code: " + conn.getResponseCode());
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

			// sb.toString으로 얻은 값을 문서(XML)로 만든 것
			Document document = builder.parse(new InputSource(new StringReader(sb.toString())));

			NodeList nodeList = document.getElementsByTagName("totalCnt");

//			int totalCnt = Integer.parseInt(nodeList.item(0).getTextContent());
			int totalCnt = 30;
			
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
				System.out.println("만듬");
				System.out.println(urlBuilder2.toString());
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
				System.out.println(len);
				for (int i = 0; i < len; i++) {
					Node node = nodeList.item(i);
					if(node.getNodeType() == Node.ELEMENT_NODE) {
						Element element = (Element) node;
						System.out.println("----");
						// contentId는 AutoIncrement
						String contentTypeId = element.getElementsByTagName("contenttypeid").item(0).getTextContent();
						if(contentTypeId != null) {
							System.out.println("콘텐트 타입 : " + contentTypeId);
						}
						String title = element.getElementsByTagName("title").item(0).getTextContent();
						if(title != null) {
							System.out.println("이름 : " + title);
						}
						String addr = element.getElementsByTagName("addr1").item(0).getTextContent();
						if(addr != null) {
							System.out.println("주소 : " + addr);
						}
						String tel =  element.getElementsByTagName("tel").item(0).getTextContent();
						if(tel != null) {
							System.out.println("전화번호 : " + tel);
						}
						String firstImage = element.getElementsByTagName("firstimage").item(0).getTextContent();
						if(firstImage!=null) {
							System.out.println("이미지 : " + firstImage);
						}
						String sidoCode = element.getElementsByTagName("areacode").item(0).getTextContent();
						if(sidoCode!=null) {
							System.out.println("시도코드 : " + sidoCode);
						}
						String gugunCode = element.getElementsByTagName("sigungucode").item(0).getTextContent();
						if(gugunCode != null) {
							System.out.println("구군코드 : " + gugunCode);
						}
						String latitude =  element.getElementsByTagName("mapx").item(0).getTextContent();
						if(latitude != null) {
							System.out.println("위도 : " + latitude);
						}
						String longitude = element.getElementsByTagName("mapy").item(0).getTextContent();
						if(longitude != null) {
							System.out.println("경도 : " + longitude);
						}
						String mLevel = element.getElementsByTagName("mlevel").item(0).getTextContent();
						if(mLevel != null) {
							System.out.println("맵레벨 : " + mLevel);
						}
						String cat1 = element.getElementsByTagName("cat1").item(0).getTextContent();
						if(cat1 != null) {
							System.out.println("cat1 : " + cat1);
						}
						String cat2 = element.getElementsByTagName("cat2").item(0).getTextContent();
						if(cat2 != null) {
							System.out.println("cat2 : " + cat2);
						}
						String cat3 = element.getElementsByTagName("cat3").item(0).getTextContent();
						if(cat3 != null) {
							System.out.println("cat3 : " + cat3);
						}
						// description은 5번 공통정보조회에 있음.
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
