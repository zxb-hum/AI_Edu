package com.it.order.client;

import com.it.commonutils.vo.CourseWebVoOrder;
import org.springframework.stereotype.Component;

/*
 *@author       :zxb
 *@data         :7/9/2022 15:13
 *@description  :
 */
@Component
public class EduDegradeClient implements EduClient {
    @Override
    public CourseWebVoOrder getCourseInfoOrder(String courseId) {
        return null;
    }
}
