package com.example.springboot.Controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.thread.ThreadUtil;
import com.example.springboot.common.AuthAccess;
import com.example.springboot.common.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

@RestController
public class FileController {

    @Value("${ip:localhost}")
    String ip;

    @Value("${server.port}")
    String port;

    private static final String ROOT_PATH = System.getProperty("user.dir") + File.separator + "files";  // G:\CQPUT2025-Graduation\files

    @PostMapping("/file/upload")
    public Result upload1(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();  // 文件名+后缀名
        String mainName = FileUtil.mainName(originalFilename);  // 文件名
        String extName = FileUtil.extName(originalFilename);    // 后缀名
        if (!FileUtil.exist(ROOT_PATH)) {
            FileUtil.mkdir(ROOT_PATH);  // 如果当前文件的父级目录不存在，就创建
        }
        if (FileUtil.exist(ROOT_PATH + File.separator + originalFilename)) {  // 如果当前上传的文件已经存在了，那么这个时候我就要重名一个文件名称
            originalFilename = System.currentTimeMillis() + "_" + mainName + "." + extName;
        }
        File saveFile = new File(ROOT_PATH + File.separator + originalFilename);
        file.transferTo(saveFile);  // 存储文件到本地的磁盘里面去
        String url = "http://" + ip + ":" + port + "/file/download/" + originalFilename;
        return Result.success(url);  //返回文件的链接，这个链接就是文件的下载地址，这个下载地址就是我的后台提供出来的
    }

    @PostMapping("/files/upload")
    public Result upload2(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();  // 文件名+后缀名
        String mainName = FileUtil.mainName(originalFilename);  // 文件名
        String extName = FileUtil.extName(originalFilename);    // 后缀名
        if (!FileUtil.exist(ROOT_PATH)) {
            FileUtil.mkdir(ROOT_PATH);  // 如果当前文件的父级目录不存在，就创建
        }
        if (FileUtil.exist(ROOT_PATH + File.separator + originalFilename)) {  // 如果当前上传的文件已经存在了，那么这个时候我就要重名一个文件名称
            originalFilename = System.currentTimeMillis() + "_" + mainName + "." + extName;
        }
        File saveFile = new File(ROOT_PATH + File.separator + originalFilename);
        file.transferTo(saveFile);  // 存储文件到本地的磁盘里面去
        String url = "http://" + ip + ":" + port + "/file/download/" + originalFilename;
        return Result.success(url);  //返回文件的链接，这个链接就是文件的下载地址，这个下载地址就是我的后台提供出来的
    }

    @AuthAccess
    @GetMapping("/file/download/{fileName}")
    public void download(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        response.addHeader("Content-Disposition", "inline;filename=" + URLEncoder.encode(fileName, "UTF-8")); // 预览
        String filePath = ROOT_PATH  + File.separator + fileName;
        if (!FileUtil.exist(filePath)) {
            return;
        }
        byte[] bytes = FileUtil.readBytes(filePath);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes);  // 数组是一个字节数组，也就是文件的字节流数组
        outputStream.flush();
        outputStream.close();
    }

    /**
     *  富文本文件上传
     */
    @PostMapping("/files/editor/upload")
    public Dict editorUpload(MultipartFile file) {
        String flag;
        synchronized (FileController.class) {
            flag = System.currentTimeMillis() + "";
            ThreadUtil.sleep(1L);
        }
        String originalFilename = file.getOriginalFilename();
        try {
            if (!FileUtil.exist(ROOT_PATH)) {
                FileUtil.mkdir(ROOT_PATH);
            }
            String newFileName = flag + "-" + originalFilename;
            FileUtil.writeBytes(file.getBytes(), ROOT_PATH + File.separator + newFileName);

            String url = "http://" + ip + ":" + port + "/file/download/" + newFileName;

            if (originalFilename != null && originalFilename.toLowerCase().endsWith(".mp4")) {
                return Dict.create().set("errno", 0).set("data", Dict.create().set("url", url));
            } else {
                return Dict.create().set("errno", 0).set("data", CollUtil.newArrayList(Dict.create().set("url", url)));
            }
        } catch (Exception e) {
            System.err.println(originalFilename + "--文件上传失败");
            return Dict.create().set("errno", 500);
        }
    }
}
