package com.it.edu.service;

import com.it.edu.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.it.edu.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author zxb
 * @since 2022-08-28
 */
public interface ChapterService extends IService<Chapter> {

    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    boolean deleteChapter(String chapterId);

    void removeChapterByCourseId(String courseId);
}
