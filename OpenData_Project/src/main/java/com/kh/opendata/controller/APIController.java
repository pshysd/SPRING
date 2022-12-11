package com.kh.opendata.controller;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class APIController {
	public static final String SERVICEKEY = "O4jV5ZsrBr%2Fb1sycvvWPF9189qLChUoSoYWgH5PmWXMFNYXNRPlb9XjB8zrhpQZKvKC2vkIsQemEkYwLwLwUTw%3D%3D";;

	@ResponseBody
//	@RequestMapping(value="air.do", produces="application/json; charset=utf-8") // JSON 타입으로 응답데이터 넘김
	@RequestMapping(value="air.do", produces="text/xml; charset=utf-8") // XML으로 응답데이터 넘김 
	public String airPollution(String location) throws IOException {

//		System.out.println(location);

		String url = "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty";
		url += "?serviceKey=" + SERVICEKEY;
		url += "&sidoName=" + URLEncoder.encode(location, "UTF-8");
//		url += "&returnType=json";
		url += "&returnType=xml";
		url += "&numOfRows=5"; // 결과의 갯수

		URL requestURL = new URL(url);

		HttpURLConnection URLConnection = (HttpURLConnection) requestURL.openConnection();

		URLConnection.setRequestMethod("GET");

		BufferedReader br = new BufferedReader(new InputStreamReader(URLConnection.getInputStream()));

		String responseText = "";
		
		String line;
		
		while((line = br.readLine()) != null) {
			responseText += line;
		}
		
//		응답데이터를 보내주고자 한다면 굳이 ArrayList로 파싱할 필요가 없음 !!
//		String을 리턴하되, @ResponseBody 추가, produces 설정 추가
		
		br.close();
		URLConnection.disconnect();
		
		return responseText;
	}

}