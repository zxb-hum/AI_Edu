package com.it.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.it.commonutils.JwtUtils;
import com.it.commonutils.MD5;
import com.it.servicebase.exceptionHandler.GuliException;
import com.it.ucenter.entity.Member;
import com.it.ucenter.entity.vo.LoginVo;
import com.it.ucenter.entity.vo.RegisterVo;
import com.it.ucenter.mapper.MemberMapper;
import com.it.ucenter.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author zxb
 * @since 2022-09-03
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {
//使用MD5进行数据加密<只支持加密，不支持解密>
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public String login(LoginVo loginVo) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();

        //校验参数
        if(StringUtils.isEmpty(mobile) ||
                StringUtils.isEmpty(password)) {
            throw new GuliException(20001,"error");
        }

        //获取会员
        Member member = baseMapper.selectOne(new QueryWrapper<Member>().eq("mobile", mobile));
        if(null == member) {
            throw new GuliException(20001,"该用户不存在，请注册！");
        }

        //校验密码
        if(!MD5.MD5(password).equals(member.getPassword())) {
            throw new GuliException(20001,"密码错误，请重试！");
        }

        //校验是否被禁用
        if(member.getIsDisabled()) {
            throw new GuliException(20001,"当前用户已被禁用！");
        }

        //使用JWT生成token字符串
        String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());
        return token;
    }

    @Override
    public void register(RegisterVo registerVo) {
        //获取注册信息，进行校验
        String nickname = registerVo.getNickname();
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();

        //校验参数
        if(StringUtils.isEmpty(mobile) ||
                StringUtils.isEmpty(mobile) ||
                StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(code)) {
            throw new GuliException(20001,"error");
        }

        //校验校验验证码
        //从redis获取发送的验证码
        String mobleCode = redisTemplate.opsForValue().get(mobile);
        if(!code.equals(mobleCode)) {
            throw new GuliException(20001,"验证码错误或过期，请重试！");
        }

        //查询数据库中是否存在相同的手机号码
        Integer count = baseMapper.selectCount(new QueryWrapper<Member>().eq("mobile", mobile));
        if(count.intValue() > 0) {
            throw new GuliException(20001,"当前手机号已经注册，请更换手机号重试！");
        }

        //添加注册信息到数据库
        Member member = new Member();
        member.setNickname(nickname);
        member.setMobile(registerVo.getMobile());
        member.setPassword(MD5.MD5(password));
        member.setIsDisabled(false);
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        baseMapper.insert(member);
    }

    @Override
    public Member getMember(String memberId) {
        Member member = baseMapper.selectById(memberId);
//        LoginVo loginVo = new LoginVo();
//        BeanUtils.copyProperties(member, loginVo);
        return member;
    }

    @Override
    public Member getByOpenid(String openid) {
        Member members = baseMapper.selectOne(new QueryWrapper<Member>().eq("openid", openid));
        return members;
    }



    /******************************为statistics模块调用的接口****************************/
    @Override
    public Integer countregister(String day) {
        return baseMapper.countregister(day);
    }
    /******************************为statistics模块调用的接口****************************/
}
