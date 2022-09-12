package com.it.edu.client;

import org.springframework.stereotype.Component;

/*
 *@author       :zxb
 *@data         :7/9/2022 21:54
 *@description  :
 */
@Component
public class OrderDegradeClient implements OrderClient {
    @Override
    public boolean isBUyCourse(String courseId, String memberId) {
        return false;
    }
}
