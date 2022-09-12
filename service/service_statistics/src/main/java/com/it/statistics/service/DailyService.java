package com.it.statistics.service;

import com.it.statistics.entity.Daily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author zxb
 * @since 2022-09-08
 */
public interface DailyService extends IService<Daily> {
    // 根据日期创建统计数据
    void createStatisticsByDay(String day);

    // 根据前台选择  后端条件查询数据
    Map<String, Object> showData(String type, String begin, String end);
}
