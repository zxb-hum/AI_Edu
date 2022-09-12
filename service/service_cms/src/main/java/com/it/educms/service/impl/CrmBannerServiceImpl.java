package com.it.educms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.it.educms.entity.CrmBanner;
import com.it.educms.mapper.CrmBannerMapper;
import com.it.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author zxb
 * @since 2022-09-01
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    // 查询所有的banner
    @Cacheable(value = "banner", key="'selectIndexList'")
    // key 表示在缓存中存储的key值  生成的数据在redis中存储的键名为：value::key
    // 即名称为  banner::selectIndexList

    @Override
    public List<CrmBanner> selectAllBanner() {
        // 根据id进行降序排列，现实排列之后前两条数据
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        // last语句，拼接sql
        wrapper.last("limit 2");

        List<CrmBanner> bannerList = baseMapper.selectList(wrapper);
        return bannerList;
    }

    // 根据id查询banner
    @Override
    public CrmBanner getBannerById(String id) {
        CrmBanner crmBanner = baseMapper.selectById(id);
        return crmBanner;
    }

    //新增banner
    @Override
    public void saveBanner(CrmBanner banner) {
        baseMapper.insert(banner);
    }

    @Override
    public void updateBannerById(CrmBanner banner) {
        baseMapper.updateById(banner);
    }

    @Override
    public void removeBannerById(String id) {
        baseMapper.deleteById(id);
    }
}
