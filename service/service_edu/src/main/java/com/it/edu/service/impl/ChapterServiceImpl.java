package com.it.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.it.edu.entity.Chapter;
import com.it.edu.entity.Video;
import com.it.edu.entity.chapter.ChapterVo;
import com.it.edu.entity.chapter.VideoVo;
import com.it.edu.mapper.ChapterMapper;
import com.it.edu.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.edu.service.VideoService;
import com.it.servicebase.exceptionHandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author zxb
 * @since 2022-08-28
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Autowired
    private VideoService videoService;  // 注入小节的service
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        // 根绝课程id查询出所有的章节内容
        QueryWrapper<Chapter> wrapper_chapter = new QueryWrapper<>();
        wrapper_chapter.eq("course_id", courseId);
        List<Chapter> list_chapter = baseMapper.selectList(wrapper_chapter);

        // 2 根绝章节id查询出对应的小节内容
        QueryWrapper<Video> wrapper_video = new QueryWrapper<>();
        wrapper_video.eq("course_id", courseId);
        List<Video> list_video = videoService.list(wrapper_video);

        // 3 遍历封装章节内容
        List<ChapterVo> chapterVoList = new ArrayList<>();
        for (int i = 0; i < list_chapter.size(); i++) {
            Chapter chapter = list_chapter.get(i);
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter, chapterVo);

            // 4 遍历封装小节内容
            List<VideoVo> videoVoList = new ArrayList<>();
            for (int j = 0; j < list_video.size(); j++) {
                Video video = list_video.get(j);
                if (chapter.getId().equals(video.getChapterId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video, videoVo);
                    videoVoList.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVoList);
            chapterVoList.add(chapterVo);
        }
        return chapterVoList;
    }

    // 删除章节的方法（有小节就删掉，没有就不删掉）
    @Override
    public boolean deleteChapter(String chapterId) {
        // 根据章节id查询小节表
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("chapter_id",chapterId);
        int count = videoService.count(wrapper); // 根绝条件查询符合要求的数据数目
        if(count>0){
            //  有小节 不能直接删除
            throw new GuliException(20001,"章节下面有小节，不能直接删除");
        }else {
            int result = baseMapper.deleteById(chapterId);
            return result>0;
        }
    }

    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }

}
