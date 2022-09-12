package com.it.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.it.edu.entity.Subject;
import com.it.edu.entity.excel.SubjectData;
import com.it.edu.entity.subject.OneSubject;
import com.it.edu.entity.subject.TwoSubject;
import com.it.edu.listener.SubjectExcelListener;
import com.it.edu.mapper.SubjectMapper;
import com.it.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author zxb
 * @since 2022-08-27
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Override
    public void saveSubject(MultipartFile file,SubjectService subjectService) {
        try {
            //文件输入流
            InputStream in = file.getInputStream();
            // 调用方法进行读取
            EasyExcel.read(in, SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    // 课程分类代码（树形）
    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        // 1 查询出所有的1级分类
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("parent_id",0);
        List<Subject> oneSubjects = baseMapper.selectList(wrapper);

        // 2 查询所有的二级分类
        QueryWrapper wrapper2=new QueryWrapper();
        wrapper.ne("parent_id",0);
        List<Subject> twoSubjects = baseMapper.selectList(wrapper2);

        // 创建集合，封装最终的数据
        List<OneSubject> findSubjectList = new ArrayList<>();
        // 3 封装一级分类
        for (int i = 0; i < oneSubjects.size(); i++) {
            Subject subject = oneSubjects.get(i);
            OneSubject oneSubject = new OneSubject();
//            oneSubject.setId(subject.getId());
//            oneSubject.setTitle(subject.getTitle());
            BeanUtils.copyProperties(subject, oneSubject);  // 性能等同于上面两行代码


            // 3 封装二级分类
            List<TwoSubject> twoFindSubjectList = new ArrayList<>();
            for (int j = 0; j < twoSubjects.size(); j++) {
                Subject subject1 = twoSubjects.get(j);
                TwoSubject twoSubject = new TwoSubject();
                if (subject.getId().equals(subject1.getParentId())){

                    BeanUtils.copyProperties(subject1,twoSubject);
                    twoFindSubjectList.add(twoSubject);
                }
            }
            oneSubject.setChildren(twoFindSubjectList);
            findSubjectList.add(oneSubject);
        }


        return findSubjectList;
    }
}
