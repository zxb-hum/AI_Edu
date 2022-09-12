package com.it.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.it.edu.client.VideoClient;
import com.it.edu.entity.Video;
import com.it.edu.mapper.VideoMapper;
import com.it.edu.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import feign.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author zxb
 * @since 2022-08-28
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {
    @Autowired
    private VideoClient videoClient;

    @Override
    public void removeVideoByCourseId(String courseId) {
        //根据课程id查出所有的视频id
        QueryWrapper wrapperVideo = new QueryWrapper();
        wrapperVideo.eq("course_id",courseId);
        wrapperVideo.select("video_source_id");  // 设置查询的字段
        List<Video> list_videos = baseMapper.selectList(wrapperVideo);

        // 将list<Video> 转变为 list<String>
        List<String> videoIds = new ArrayList<>();
        for (int i = 0; i < list_videos.size(); i++) {
            Video video = list_videos.get(i);
            String videoSourceId = video.getVideoSourceId();
            if (videoSourceId!=null){
                videoIds.add(videoSourceId);
            }
        }
        if(videoIds.size()>0){
            videoClient.deleteBatch(videoIds);
        }

        QueryWrapper wrapper =  new QueryWrapper();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
