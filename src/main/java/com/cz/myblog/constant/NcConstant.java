package com.cz.myblog.constant;

import java.util.Arrays;
import java.util.List;

public class NcConstant {

    public static final List<String> ALLOWED_IMG_TYPES = Arrays.asList("image/jpeg","application/octet-stream","image/bmp", "image/png"); //允许的图片类型

    public static final Long maxFileSize = 500 * 1024L; //阿里云OSS文件大小限制

}
