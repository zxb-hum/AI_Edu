package com.it.order.client;

import com.it.commonutils.vo.UcenterMember;
import org.springframework.stereotype.Component;

/*
 *@author       :zxb
 *@data         :7/9/2022 15:15
 *@description  :
 */
@Component
public class UcenterDegradeClient implements UcenterClient {
    @Override
    public UcenterMember getUcenterInfo(String memberId) {
        return null;
    }
}
