package com.hoo.test18_hoo.controller.java.service;


import com.hoo.test18_hoo.controller.common.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Safelist;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class JavaRestServiceImpl implements JavaRestService {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public String encodingSha(String type, String text) {

        System.out.println(type);
        System.out.println(text);

        MessageDigest md = null;

        try {
            md = MessageDigest.getInstance(type);
            md.update(text.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "FAIL";
        }


        return bytesToHex(md.digest());
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    @Override
    public String test() {

        // 토큰 정보 추출
        Map<String, Object> memberMap = new HashMap<>();

        memberMap.put("info", "test");

        System.out.println(jwtTokenProvider.createToken(memberMap));

        return "랄랄라";
    }

    @Override
    public String measureLoadingSpeed(String url) {

        String result = "";

        if (!url.contains("https://") && !url.contains("http://")) {
            url = "http://" + url;
        }

        // 드라이버 경로
        final File driverFile = new File("C:\\Users\\dongwha\\Downloads\\chromedriver_win32\\chromedriver_win.exe");
        final String driverFilePath = driverFile.getAbsolutePath();

        // 시스템 property 설정
        System.setProperty("webdriver.chrome.driver", driverFilePath);


        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("headless");

        WebDriver driver = new ChromeDriver(chromeOptions);


        driver.get(url);

        result = driver.getTitle();
        result += "\n" + driver.getCurrentUrl();

        final JavascriptExecutor js = (JavascriptExecutor) driver;


        // time of the process of navigation and page load
        double loadTime = (Double) js.executeScript("return (window.performance.timing.loadEventEnd - window.performance.timing.navigationStart) / 1000");
//        System.out.print(loadTime + " seconds"); // 5.15 seconds

        result += "\n" + loadTime + " seconds";
        return result;
    }

    @Override
    public String showWebHtml(String url) {

        if (!url.contains("https://") && !url.contains("http://")) {
            url = "http://" + url;
        }

        String result = "";

        try {

            // 자료를 가져올 사이트에 연결하기
            Document doc = Jsoup.connect(url).get();


//            System.out.println(doc.body().html()); // html 코드를 가져온다.
            result = Jsoup.clean(doc.data(), Safelist.basic());

//		/* 크롤링 예시
//		 <div class="box-contents">
//                        <a href="/movies/detail-view/?midx=82986">
//                            <strong class="title">블랙 위도우</strong>
//                        </a>
//
//         <div class="score">
//                            <strong class="percent">예매율<span>23.2%</span></strong>
//                            <!-- 2020.05.07 개봉전 프리에그 노출, 개봉후 골든에그지수 노출변경 (적용 범위1~ 3위)-->
//                            <div class='egg-gage small'>
//		 */
//
//            Elements titles = doc.select("div.box-contents strong.title");
//            // 묶기전 큰 묶음부터 묶어주는게 좋다.
//            Elements percents = doc.select("div.box-contents div.score strong.percent span");
//            for (int i = 0; i < 7; i++) {
//                Element title = titles.get(i);
//                Element percent = percents.get(i);
//                System.out.println(title.text() + " : " + percent.text()); // 보고있는 사이트의 영화 제목을 다 가져온다.
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public String getJWTToken(String text) {
        // 토큰 정보 추출
        Map<String, Object> memberMap = new HashMap<>();

        memberMap.put("info", text);

        System.out.println(jwtTokenProvider.createToken(memberMap));

        return jwtTokenProvider.createToken(memberMap);
    }

    @Override
    public String decodeToken(String text) {



        String[] parts = text.split("\\.");

        Base64.Decoder decoder = Base64.getDecoder();
        String header = "";
        String payload = "";
        String signature = "";

        try {

            header = new String(decoder.decode(parts[0].replace('-', '+').replace('_', '/')), "UTF-8");
            payload = new String(decoder.decode(parts[1].replace('-', '+').replace('_', '/')), "UTF-8");
            signature = new String(decoder.decode(parts[2].replace('-', '+').replace('_', '/')), "UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

        }

        String result = "";

        result = "Header : " + header;
        result += "\nPayload : " + payload;
        result += "\nSignature : " + signature;

        return result;
    }


}
