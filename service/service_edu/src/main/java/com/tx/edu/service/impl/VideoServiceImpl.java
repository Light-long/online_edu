package com.tx.edu.service.impl;

import com.tx.edu.entity.Video;
import com.tx.edu.mapper.VideoMapper;
import com.tx.edu.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author lishilong
 * @since 2021-05-23
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

}
