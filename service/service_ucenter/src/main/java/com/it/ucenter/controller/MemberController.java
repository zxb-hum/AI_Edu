package com.it.ucenter.controller;


import com.it.commonutils.JwtUtils;
import com.it.commonutils.R;
import com.it.commonutils.vo.UcenterMember;
import com.it.servicebase.exceptionHandler.GuliException;
import com.it.ucenter.entity.Member;
import com.it.ucenter.entity.vo.LoginVo;
import com.it.ucenter.entity.vo.RegisterVo;
import com.it.ucenter.service.MemberService;
import io.swagger.annotations.ApiOperation;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author zxb
 * @since 2022-09-03
 */
@RestController
@RequestMapping("/ucenter")
@CrossOrigin
public class MemberController {

    @Autowired
    private MemberService memberService;

    @ApiOperation(value = "会员登录")
    @PostMapping("login")
    public R login(@RequestBody LoginVo loginVo) {
        String token = memberService.login(loginVo);
        return R.ok().data("token", token);
    }

    @ApiOperation(value = "会员注册")
    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return R.ok();
    }
    @ApiOperation(value = "根据token获取登录信息")
    @GetMapping("getLoginInfo")
    public R getLoginInfo(HttpServletRequest request){
        try {
            // 调用Jwt工具类方法，根据request对象获取头信息，返回用户id
            String memberId = JwtUtils.getMemberIdByJwtToken(request);
            Member member= memberService.getMember(memberId);
            return R.ok().data("item", member);
        }catch (Exception e){
            e.printStackTrace();
            throw new GuliException(20001,"error");
        }
    }

    @ApiOperation(value = "测试路由")
    @GetMapping("test_router")
    public R test_router(){
        return R.ok().data("测试路由","success!");
    }

    @ApiOperation(value = "根据id获取登录用户信息")
    @GetMapping("getUcenterInfo/{memberId}")
    public UcenterMember getUcenterInfo(@PathVariable String memberId){
        Member member = memberService.getById(memberId);
        UcenterMember ucenterMember = new UcenterMember();
        BeanUtils.copyProperties(member, ucenterMember);
        return ucenterMember;
    }

    /******************************为statistics模块调用的接口****************************/
    @GetMapping("countregister/{day}")
    public R countregister(@PathVariable String day){
        Integer count = memberService.countregister(day);
        return R.ok().data("countregister",count);
    }
    /******************************为statistics模块调用的接口****************************/
}

