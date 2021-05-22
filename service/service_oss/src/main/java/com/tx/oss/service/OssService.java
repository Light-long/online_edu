package com.tx.oss.service;

import org.springframework.web.multipart.MultipartFile;

public interface OssService {
    // 头像上传
    String upLoadAvatar(MultipartFile file);
}
