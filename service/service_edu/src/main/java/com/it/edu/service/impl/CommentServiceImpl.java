package com.it.edu.service.impl;

import com.it.edu.entity.Comment;
import com.it.edu.mapper.CommentMapper;
import com.it.edu.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author zxb
 * @since 2022-09-05
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
