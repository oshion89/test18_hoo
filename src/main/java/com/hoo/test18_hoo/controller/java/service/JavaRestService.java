package com.hoo.test18_hoo.controller.java.service;

public interface JavaRestService {

    String encodingSha(String type, String text);

    String test();

    String measureLoadingSpeed(String url);

    String showWebHtml(String url);

    String getJWTToken(String text);

    String decodeToken(String text);
}
