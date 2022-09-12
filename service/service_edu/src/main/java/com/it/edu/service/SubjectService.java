package com.it.edu.service;

import com.it.edu.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.it.edu.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author zxb
 * @since 2022-08-27
 */
public interface SubjectService extends IService<Subject> {



    void saveSubject(MultipartFile file, SubjectService subjectService);

    List<OneSubject> getAllOneTwoSubject();
}
