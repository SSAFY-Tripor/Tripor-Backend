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

public class PublicAPI {
	public static PublicAPI instance = new PublicAPI();

	public static PublicAPI getInstance() {
		return instance;
	}

	private PublicAPI() {
	}

	/*
	 * &_type=XML &contentId=126508 &contentTypeId=12 &defaultYN=N &firstImageYN=N
	 * &areacodeYN=N &catcodeYN=N &addrinfoYN=N &mapinfoYN=N &overviewYN=Y
	 */

	public String getDescriptionInfo(String contentId, int contentTypeId) {
		try {
			StringBuilder urlBuilder = new StringBuilder(
					"https://apis.data.go.kr/B551011/KorService1/detailCommon1"); /* URL */
			urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8")
					+ "=JHW%2B1hoKL%2B7INEwzeiiNgY%2Flx%2Fowvj1mY%2BMnLWSDbuY9PsYztUv%2BVziTl5zHvAbNTKlXP3MTVs5jK1fOfZ28dQ%3D%3D");
			urlBuilder.append(
					"&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /* 페이지번호 */
			urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "="
					+ URLEncoder.encode("10", "UTF-8")); /* 한 페이지 결과 수 */
			urlBuilder.append(
					"&" + URLEncoder.encode("MobileOS", "UTF-8") + "=" + URLEncoder.encode("ETC", "UTF-8")); /* OS 구분 */
			urlBuilder.append("&" + URLEncoder.encode("MobileApp", "UTF-8") + "="
					+ URLEncoder.encode("Tripor", "UTF-8")); /* 서비스명 */
			urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("xml", "UTF-8"));
			urlBuilder.append("&" + URLEncoder.encode("contentId", "UTF-8") + "="
					+ URLEncoder.encode(contentId, "UTF-8"));
			urlBuilder.append("&" + URLEncoder.encode("contentTypeId", "UTF-8") + "="
					+ URLEncoder.encode(String.valueOf(contentTypeId), "UTF-8"));
			urlBuilder.append("&" + URLEncoder.encode("defaultYN", "UTF-8") + "=" + URLEncoder.encode("N", "UTF-8"));
			urlBuilder.append("&" + URLEncoder.encode("firstImageYN", "UTF-8") + "=" + URLEncoder.encode("N", "UTF-8"));
			urlBuilder.append("&" + URLEncoder.encode("areacodeYN", "UTF-8") + "=" + URLEncoder.encode("N", "UTF-8"));
			urlBuilder.append("&" + URLEncoder.encode("catcodeYN", "UTF-8") + "=" + URLEncoder.encode("N", "UTF-8"));
			urlBuilder.append("&" + URLEncoder.encode("addrinfoYN", "UTF-8") + "=" + URLEncoder.encode("N", "UTF-8"));
			urlBuilder.append("&" + URLEncoder.encode("mapinfoYN", "UTF-8") + "=" + URLEncoder.encode("N", "UTF-8"));
			urlBuilder.append("&" + URLEncoder.encode("overviewYN", "UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8"));

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
			DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			Document document = builder.parse(new InputSource(new StringReader(sb.toString())));

			NodeList nodeList = document.getElementsByTagName("totalCnt");

			factory = DocumentBuilderFactory.newDefaultInstance();
			builder = factory.newDocumentBuilder();

			document = builder.parse(new InputSource(new StringReader(sb.toString())));

			nodeList = document.getElementsByTagName("item");
			int len = nodeList.getLength();

			for (int i = 0; i < len; i++) {
				Node node = nodeList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					return element.getElementsByTagName("overview").item(0).getTextContent();
				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
