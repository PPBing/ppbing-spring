package com.ppbing.test;

import com.ppbing.spring.PpbingApplicationContext;

public class Main {
    public static void main(String[] args) {

        PpbingApplicationContext ppbingApplicationContext = new PpbingApplicationContext(AppConfig.class);

        UserService userService = (UserService) ppbingApplicationContext.getBean("userService");
        System.out.println(userService);
    }
}
