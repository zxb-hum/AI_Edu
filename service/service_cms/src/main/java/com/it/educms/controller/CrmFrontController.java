package com.it.educms.controller;


import com.it.commonutils.R;
import com.it.educms.entity.CrmBanner;
import com.it.educms.service.CrmBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.Cacheable;

import java.util.List;

/**
 * <p>
 * 前端页面
 * </p>
 *
 * @author zxb
 * @since 2022-09-01
 */
@RestController
@RequestMapping("/cms/bannerFront")
@CrossOrigin
@Api(description = "前端Banner列表")
public class CrmFrontController {
    @Autowired
    private CrmBannerService bannerService;


    @ApiOperation(value = "获取首页banner")
    @GetMapping("getAllBanner")
    public R index() {
        List<CrmBanner> list = bannerService.selectAllBanner();
        return R.ok().data("bannerList", list);
    }
}

