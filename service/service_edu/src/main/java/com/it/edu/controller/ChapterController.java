package com.it.edu.controller;


import com.it.commonutils.R;
import com.it.edu.entity.Chapter;
import com.it.edu.entity.chapter.ChapterVo;
import com.it.edu.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author zxb
 * @since 2022-08-28
 */
@RestController
@RequestMapping("/edu/chapter")
@CrossOrigin
public class ChapterController {
    @Autowired
    private ChapterService chapterService;

    // 1 根据课程iD进行查询
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId){
        List<ChapterVo> list = chapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("list",list);
    }

    // 2 添加章节
    @PostMapping("addChapter")
    public R addChapter(@RequestBody Chapter chapter){
        chapterService.save(chapter);
        return R.ok();
    }

    // 3 根绝章节id查询
    @GetMapping("getChapterInfo/{chapterId}")
    public  R getChapterInfo(@PathVariable String chapterId){
//        System.out.println(chapterId);
        Chapter chapter = chapterService.getById(chapterId);
        return R.ok().data("chapter",chapter);
    }

    // 4 修改章节
    @PostMapping("updateChapter")
    public  R updateChapter(@RequestBody Chapter chapter){
        chapterService.updateById(chapter);
        return R.ok();
    }

    // 5 删除章节
    @DeleteMapping("{chapterId}")
    public R deleteChapter(@PathVariable String chapterId){
        boolean flag = chapterService.deleteChapter(chapterId);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }

    }
}

