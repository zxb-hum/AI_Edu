package com.it.edu.controller;


import com.it.commonutils.R;
import com.it.edu.client.VideoClient;
import com.it.edu.entity.Chapter;
import com.it.edu.entity.Video;
import com.it.edu.service.VideoService;
import com.it.servicebase.exceptionHandler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author zxb
 * @since 2022-08-28
 */
@RestController
@RequestMapping("/edu/video")
@CrossOrigin
public class VideoController {
    @Autowired
    private VideoService videoService;

    @Autowired
    private VideoClient videoClient;
    // 1 添加小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody Video video){
        videoService.save(video);
        return R.ok();
    }

    // 2 删除小节
//      删除小节的时候，同时把阿里云里面的视频也删除
    @DeleteMapping("{videoId}")
    public R deleteVideo(@PathVariable String videoId){
        // 根据小节id得到视频id
        Video video = videoService.getById(videoId);
        // 得到视频id
        String videoSourceId = video.getVideoSourceId();
        // 远程调用
        if(!StringUtils.isEmpty(videoSourceId)){
            R result = videoClient.removeAliyunVideo(videoSourceId);
            if (result.getCode()==20001){
                throw new GuliException(20001,"删除视频失败，熔断器执行....");
            }
        }
        videoService.removeById(videoId);
        return R.ok();
    }
    // 3 修改小节
    @PostMapping("updateVideo")
    public  R updateVideo(@RequestBody Video video){
        videoService.updateById(video);
        return R.ok();
    }

    // 4 根据小节id查询
    @GetMapping("getVideoInfo/{videoId}")
    public  R getVideoInfo(@PathVariable String videoId){
        Video video = videoService.getById(videoId);
        return R.ok().data("video",video);
    }
}

