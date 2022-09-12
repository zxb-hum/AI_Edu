package com.it.edu.client;

import com.it.commonutils.vo.UcenterMember;
import org.springframework.stereotype.Component;

/*
 *@author       :zxb
 *@data         :5/9/2022 22:22
 *@description  :
 */
@Component
public class UcenterFileDegradeFeignClient implements UcenterClient {
    @Override
    public UcenterMember getUcenterInfo(String memberId) {
        return null;
    }
}
