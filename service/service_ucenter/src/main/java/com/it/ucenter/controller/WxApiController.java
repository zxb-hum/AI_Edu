package com.it.ucenter.controller;

import com.google.gson.Gson;
import com.it.commonutils.JwtUtils;
import com.it.servicebase.exceptionHandler.GuliException;
import com.it.ucenter.entity.Member;
import com.it.ucenter.service.MemberService;
import com.it.ucenter.utils.ConstantWxUtils;
import com.it.ucenter.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/*
 *@author       :zxb
 *@data         :4/9/2022 10:24
 *@description  :
 */
@RequestMapping("/ucenter/wx")
//@RestController
@Controller
@CrossOrigin
public class WxApiController {

    @Autowired
    private MemberService memberService;
    @GetMapping("callback")
    public String callback(String code , String state){

        //1 得到授权临时票据code
//        System.out.println(code);
//        System.out.println(state);

        //从redis中将state获取出来，和当前传入的state作比较
        //如果一致则放行，如果不一致则抛出异常：非法访问

        //2 向认证服务器发送请求换取  access_token  和  openid
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";

        String accessTokenUrl = String.format(baseAccessTokenUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                ConstantWxUtils.WX_OPEN_APP_SECRET,
                code);

        String result = null;
        try {
            // 请求拼接好的地址，返回两个access_token 和 openId
            // 使用httpClient 发送请求，得到返回结果
            result = HttpClientUtils.get(accessTokenUrl);
            System.out.println("accessToken=============" + result);
        } catch (Exception e) {
            throw new GuliException(20001, "获取access_token失败");
        }

        //解析json字符串
        Gson gson = new Gson();
        HashMap map = gson.fromJson(result, HashMap.class);
        String accessToken = (String)map.get("access_token");
        String openid = (String)map.get("openid");

        //查询数据库当前用用户是否曾经使用过微信登录  openid:是用户名
        Member member = memberService.getByOpenid(openid);
        if(member == null){
            System.out.println("新用户注册");

            //3  访问微信的资源服务器，获取用户信息
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";
            String userInfoUrl = String.format(baseUserInfoUrl, accessToken, openid);
            String resultUserInfo = null;
            try {
                resultUserInfo = HttpClientUtils.get(userInfoUrl);
                System.out.println("resultUserInfo==========" + resultUserInfo);
            } catch (Exception e) {
                throw new GuliException(20001, "获取用户信息失败");
            }

            //解析json
            HashMap<String, Object> mapUserInfo = gson.fromJson(resultUserInfo, HashMap.class);
            String nickname = (String)mapUserInfo.get("nickname");
            String headimgurl = (String)mapUserInfo.get("headimgurl");

            //向数据库中插入一条记录
            member = new Member();
            member.setNickname(nickname);
            member.setOpenid(openid);
            member.setAvatar(headimgurl);
            memberService.save(member);
        }

        //使用jwt根据 member对象生成token字符串
        String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());

        return "redirect:http://localhost:3000?token="+token;
    }
    // 生成微信扫描二维码
    @GetMapping("login")
    public String getWxCode(){
        // 固定地址拼接参数
//        String url = "https://open.weixin.qq.com/content/qrconnect?appid="+ ConstantWxUtils.WX_OPEN_APP_ID+"&response_type=code";

        // 微信开放平台授权baseUrl
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        // 对redirect_url 进行URLEncoder编码
        String redirectUrl = ConstantWxUtils.WX_OPEN_REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(redirectUrl,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //生成Url
        String url = String.format(
                baseUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                redirectUrl,
                "atguigu");


        // 重定向到请求微信地址里面
        return "redirect:"+url;
    }
}
