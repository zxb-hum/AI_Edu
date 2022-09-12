package com.it.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.it.statistics.client.UcenterClient;
import com.it.statistics.client.UcenterClient;
import com.it.statistics.entity.Daily;
import com.it.statistics.mapper.DailyMapper;
import com.it.statistics.service.DailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author zxb
 * @since 2022-09-08
 */
@Service
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements DailyService {

    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public void createStatisticsByDay(String day) {
        //删除已存在的统计对象
        QueryWrapper<Daily> dayQueryWrapper = new QueryWrapper<>();
        dayQueryWrapper.eq("date_calculated", day);
        baseMapper.delete(dayQueryWrapper);


        //获取统计信息
        Integer registerNum = (Integer) ucenterClient.countregister(day).getData().get("countregister");
        Integer loginNum = RandomUtils.nextInt(100, 200);//TODO
        Integer videoViewNum = RandomUtils.nextInt(100, 200);//TODO
        Integer courseNum = RandomUtils.nextInt(100, 200);//TODO

        //创建统计对象
        Daily daily = new Daily();
        daily.setRegisterNum(registerNum);
        daily.setLoginNum(loginNum);
        daily.setVideoViewNum(videoViewNum);
        daily.setCourseNum(courseNum);
        daily.setDateCalculated(day);

        baseMapper.insert(daily);
    }

    @Override
    public Map<String, Object> showData(String type, String begin, String end) {
        QueryWrapper<Daily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated",begin,end);
        wrapper.select("date_calculated",type);  // type对应数据表中的数据信息，最终的查询结果以data_calculated 作为横坐标，type类型的数据作为纵坐标
        List<Daily> dailies = baseMapper.selectList(wrapper);

        // 返回的有两部分的数据：日期和日期对应的数量
        // 前端要求数据为json结构，对应后端java代码的是list结构
        // 传感两个list集合，一个日期list，一个integer list
        List<String> data_calculatedList =new ArrayList<>();
        List<Integer> numDataList = new ArrayList<>();
        
        //  遍历所欲的数据list集合，取出对应的数据 并封装到结果集合中
        for (Daily daily : dailies) {
            data_calculatedList.add(daily.getDateCalculated());
            switch (type){
                case "register_num":
                    numDataList.add(daily.getRegisterNum());
                    break;
                case "login_num":
                    numDataList.add(daily.getLoginNum());
                    break;
                case "video_view_num":
                    numDataList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    numDataList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }

        }

        Map<String,Object> map = new HashMap<>();
        map.put("data_x",data_calculatedList);
        map.put("data_y",numDataList);
        return map;
    }


//    private UcenterClient ucenterClient;


    // 根据日期创建统计数据

}
