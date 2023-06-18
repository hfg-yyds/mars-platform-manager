package com.hacker.mars.application.api.common;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.hacker.mars.common.core.domain.AjaxResult;
import com.hacker.mars.common.utils.StringUtils;
import com.hacker.mars.common.utils.file.FileUtils;
import com.hacker.mars.domain.common.CommonService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 通用请求处理
 *
 * @author ruoyi
 */
@Slf4j
@RestController
@RequestMapping("/common")
public class CommonApi {

    @Autowired
    private CommonService commonService;

    /**
     * 通用下载请求
     *
     * @param fileName 文件名称
     * @param delete   是否删除
     */
    @ApiOperation(value = "通用下载请求")
    @GetMapping("/download")
    public void fileDownload(String fileName, Boolean delete, HttpServletResponse response) {
        if (!FileUtils.checkAllowDownload(fileName)) {
            throw new RuntimeException(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
        }
        commonService.fileDownload(fileName, delete, response);
    }

    /**
     * 通用上传请求（单个）
     */
    @ApiOperation(value = "通用上传请求")
    @PostMapping("/upload")
    public AjaxResult uploadFile(MultipartFile file) {
        return commonService.uploadFile(file);
    }

    /**
     * 通用上传请求（多个）
     */
    @ApiOperation(value = "通用上传请求-多个")
    @PostMapping("/uploads")
    public AjaxResult uploadFiles(List<MultipartFile> files) {
        return commonService.uploadFiles(files);
    }

    /**
     * 本地资源通用下载
     */
    @ApiOperation(value = "本地资源通用下载")
    @GetMapping("/download/resource")
    public void resourceDownload(String resource, HttpServletResponse response) {
        if (!FileUtils.checkAllowDownload(resource)) {
            throw new RuntimeException(StringUtils.format("资源文件({})非法，不允许下载。 ", resource));
        }
        commonService.resourceDownload(resource, response);
    }

}
