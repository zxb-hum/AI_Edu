package com.it.ucenter.mapper;

import com.it.ucenter.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author zxb
 * @since 2022-09-03
 */
public interface MemberMapper extends BaseMapper<Member> {

    Integer countregister(String day);
}
