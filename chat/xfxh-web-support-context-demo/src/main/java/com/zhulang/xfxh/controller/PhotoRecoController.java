package com.zhulang.xfxh.controller;

import com.zhulang.xfxh.serviceImpl.PhotoRecognizeImpl;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
@RestController
public class PhotoRecoController {
    @Value("${photo}")
   private String photo;
    private String uploadDir=System.getProperty("user.dir")+"\\src\\main\\resources\\static\\images\\down.jpg";

    @Resource
    private PhotoRecognizeImpl PhotoRecognizeImpl;
//    @GetMapping("/photoReco")
//    public ResponseEntity<String> recognizePhoto(@RequestParam("file") MultipartFile file) {
//        if (file.isEmpty()) {
//            return new ResponseEntity<>("请提供一个有效的图片文件。", HttpStatus.BAD_REQUEST);
//        }
//
//        try {
//            // 保存上传的文件到指定目录
//            File uploadFile = new File(uploadDir + file.getOriginalFilename());
//            file.transferTo(uploadFile);
//
//            // 这里可以调用你的图片识别逻辑
//            PhotoRecognizeImpl.photoRecognize(uploadFile);
//
//            // 返回图片的网络访问路径
//            //String fileUrl = "/images/" + file.getOriginalFilename();
//            String fileUrl = "http://localhost:8091/images/output.png";
//            System.out.println(fileUrl);
//            return new ResponseEntity<>(fileUrl, HttpStatus.OK);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return new ResponseEntity<>("图片识别失败：" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
@GetMapping("/photoReco")
public String recognizePhoto(@RequestParam("url") String url) {

    try {
        // 保存上传的文件到指定目录
        PhotoRecognizeImpl.downloadImage(url, uploadDir);
        // 这里可以调用你的图片识别逻辑
        File file =PhotoRecognizeImpl.photoRecognize(new File(uploadDir));

        // 返回图片的网络访问路径
        //String fileUrl = "/images/" + file.getOriginalFilename();
        String fileUrl =photo+file.getName();

        System.out.println(fileUrl);
        return fileUrl;
    } catch (IOException e) {
        e.printStackTrace();
        return "图片识别失败";
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}
}
