package com.daniel.security.web.controller;

import com.daniel.security.web.dto.FileInfo;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;

/**
 * on 5/25/2018.
 */
@RestController
@RequestMapping("/file")
public class FileController {
    /**
     * 文件上传都是POST请求
     *
     * @param file 参数名要和请求参数名称相同 类似form表单的name=file
     * @return
     */
    @PostMapping
    public FileInfo upload(HttpServletRequest request, MultipartFile file) throws IOException {
        System.out.println(file.getName());
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getSize());

        String folder = "E:\\workspace\\Learning\\springsecurity\\security-demo\\src\\main\\resources\\upload";
        File folderDir = new File(folder);
        if (!folderDir.exists())
            folderDir.mkdirs();

        File localFile = new File(folder, new Date().getTime() + "-" + file.getOriginalFilename());
        file.transferTo(localFile);
        return new FileInfo(localFile.getAbsolutePath());
    }

    @GetMapping("/{id}")
    public void download(HttpServletResponse response, @PathVariable String id) throws IOException {
        String folder = "E:\\workspace\\Learning\\springsecurity\\security-demo\\src\\main\\resources\\upload";

        try (
                FileInputStream inputStream = new FileInputStream(new File(folder, id + ".txt"));
                OutputStream outputStream = response.getOutputStream();
        ) {
            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition", "attachment;filename = " + id + ".txt");

            //把输入流写到输出流中
            IOUtils.copy(inputStream, outputStream);

            //刷新到响应 即下载
            outputStream.flush();
        }
    }

}
