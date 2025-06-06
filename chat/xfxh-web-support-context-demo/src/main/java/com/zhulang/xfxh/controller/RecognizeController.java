package com.zhulang.xfxh.controller;

import com.zhulang.xfxh.serviceImpl.PhotoRecognizeImpl;
import com.zhulang.xfxh.serviceImpl.RecognizeImpl;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
@RestController
public class RecognizeController {
    private String uploadDir=System.getProperty("user.dir")+"\\src\\main\\resources\\static\\images\\down.jpg";

    @Resource
    private com.zhulang.xfxh.serviceImpl.PhotoRecognizeImpl PhotoRecognizeImpl;

    @Resource
    RecognizeImpl Recognize;
//    @GetMapping("/recognize")
//    public ResponseEntity<String> recognizePhoto(@RequestParam("file") MultipartFile file) {
//        if (file.isEmpty()) {
//            return new ResponseEntity<>("请提供一个有效的图片文件。", HttpStatus.BAD_REQUEST);
//        }
//
//        try {
//            // 将上传的 MultipartFile 转换为 File
//            Path tempDir = Files.createTempDirectory("");
//            File imageFile = new File(tempDir.toFile(), file.getOriginalFilename());
//            file.transferTo(imageFile);
//
//            // 调用 OCR 服务进行识别
//            String result = Recognize.recognize(imageFile);
//
//            // 删除临时文件
//            Files.delete(imageFile.toPath());
//            return new ResponseEntity<>(result, HttpStatus.OK);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>("图片识别失败：" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
    @GetMapping("/recognize")
    public String recognizePhoto(@RequestParam("url") String url) {
        try {
            PhotoRecognizeImpl.downloadImage(url, uploadDir);
            // 调用 OCR 服务进行识别

            File imageFile = new File(uploadDir);
            try {
                BufferedImage image = ImageIO.read(imageFile);
                if (image == null) {
                    System.out.println("文件不是有效的 PNG 图片");
                    throw new IOException("文件不是有效的 PNG 图片: " + imageFile.getAbsolutePath());
                }
            } catch (IOException e) {
                System.out.println("读取 PNG 文件失败");
                e.printStackTrace();
                throw new IOException("读取 PNG 文件失败: " + imageFile.getAbsolutePath(), e);
            }
            String result = Recognize.recognize(uploadDir);

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "图片识别失败";
        }
    }
}
