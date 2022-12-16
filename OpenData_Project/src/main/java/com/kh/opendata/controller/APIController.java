package com.kh.opendata.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class APIController {
	
	public static final String SERVICEKEY = "Yr16XkfGx9Vy2lEjt4bQy%2BSYthnLjMGP%2BMz3T%2FfsiD%2FTpHcQewhjnP97drW0gBlWKg%2FXGl6AX%2BXRPVz7UKx8ew%3D%3D";

	@ResponseBody
	// @RequestMapping(value="air.do", produces="application/json; charset=UTF-8") // json 으로 응답데이터를 넘기고싶을때
	@RequestMapping(value="air.do", produces="text/xml; charset=UTF-8") // xml 로 응답데이터를 넘기고싶을때
	public String airPollution(String location) throws IOException {
		
		// System.out.println(location);
		
		String url = "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty";
			   url += "?serviceKey=" + SERVICEKEY;
			   url += "&sidoName=" + URLEncoder.encode(location, "UTF-8");
			   // url += "&returnType=json"; // json 으로 요청 결과를 받고싶을때
			   url += "&returnType=xml"; // xml 로 요청 결과를 받고싶을때
			   url += "&numOfRows=100"; // 결과의 갯수
		
		URL requestUrl = new URL(url);
		
		HttpURLConnection urlConnection = (HttpURLConnection)requestUrl.openConnection();
		
		urlConnection.setRequestMethod("GET");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		
		String responseText = "";
		String line; // null
		
		while((line = br.readLine()) != null) {
			
			responseText += line;
		}
		
		// 응답데이터를 보내주고자 한다면 굳이 ArrayList 로 파싱할 필요가 없음!!
		// String 을 리턴하되, @ResponseBody 추가, produces 설정 추가
		
		br.close();
		urlConnection.disconnect();
		
		return responseText;
	}
	
}









