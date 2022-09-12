package com.it.ucenter.service;

import com.it.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.it.ucenter.entity.vo.LoginVo;
import com.it.ucenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author zxb
 * @since 2022-09-03
 */
public interface MemberService extends IService<Member> {

    String login(LoginVo loginVo);

    void register(RegisterVo registerVo);

//    Member getLoginVo(String memberId);

    Member getMember(String memberId);

    Member getByOpenid(String openid);


    Integer countregister(String day);
}
