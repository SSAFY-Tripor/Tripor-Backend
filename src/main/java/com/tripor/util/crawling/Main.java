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

/**
 * DB 데이터 없는 경우 초기 세팅용
 */
public class Main {
	private static final int DEFAULT_ROWS = 1000; 
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
				"&" + URLEncoder.encode("MobileApp", "UTF-8") + "=" + URLEncoder.encode("AppTest", "UTF-8")); /* 서비스명 */
		// urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" +
		// URLEncoder.encode("json", "UTF-8")); /*_type*/
		urlBuilder
				.append("&" + URLEncoder.encode("listYN", "UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8")); /* Y=목록 */
		urlBuilder.append(
				"&" + URLEncoder.encode("arrange", "UTF-8") + "=" + URLEncoder.encode("A", "UTF-8")); /* A=제목순 */
		urlBuilder.append("&" + URLEncoder.encode("keyword", "UTF-8") + "="
				+ URLEncoder.encode("강원", "UTF-8")); /* 검색요청할키워드 : (국문=인코딩필요) 샘플 - 강원 */
		urlBuilder.append(
				"&" + URLEncoder.encode("contentTypeId", "UTF-8") + "=" + URLEncoder.encode("12", "UTF-8")); /* 광광타입 */

		System.out.println(urlBuilder.toString());

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
		System.out.println(sb.toString());

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			// sb.toString으로 얻은 값을 문서(XML)로 만든 것
			Document document = builder.parse(new InputSource(new StringReader(sb.toString())));

			NodeList nodeList = document.getElementsByTagName("item");
			System.out.println("여행지 수 : " + nodeList.getLength());

			int len = nodeList.getLength();
			for (int i = 0; i < len; i++) {
				Node node = nodeList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
//					System.out.println("-----------------------------");
//					System.out.println("주소 : " + element.getElementsByTagName("addr1").item(0).getTextContent());
//					System.out.println("이미지 : " + element.getElementsByTagName("firstimage").item(0).getTextContent());
//					System.out.println("이름 : " + element.getElementsByTagName("title").item(0).getTextContent());
//					System.out.println("위치x : " + element.getElementsByTagName("mapx").item(0).getTextContent());
//					System.out.println("위치y : " + element.getElementsByTagName("mapy").item(0).getTextContent());
				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}
}
