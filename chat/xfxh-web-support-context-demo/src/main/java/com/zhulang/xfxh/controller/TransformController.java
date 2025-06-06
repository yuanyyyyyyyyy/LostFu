package com.zhulang.xfxh.controller;

import com.itextpdf.text.DocumentException;
import com.zhulang.xfxh.pojo.form;
import com.zhulang.xfxh.serviceImpl.TransformImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class TransformController {

@Autowired
private TransformImpl transformService;

//    @PostMapping("/image-to-pdf")
//    public ResponseEntity<String> convertImageToPdf(@RequestParam("file") MultipartFile file) {
//        form form = new form();
//        String res;
//        try {
//            File imageFile = convertMultipartFileToFile(file);
//            form.setId(null);
//            form.setOriginal("src/main/resources/Original"+file.getOriginalFilename());
//            form.setConversion(res=transformService.convertImageToPdf(imageFile));
//            form.setUid(12l);
//            form.setDate( LocalDateTime.now().toString()); // 使用当前时间
//            transformService.insertConvert(form);
//            return ResponseEntity.ok("图片 to PDF 成功！.  Output path: "+res);
//        } catch (IOException | DocumentException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error converting image to PDF.");
//        }
//    }
//
//
//
//    @PostMapping("/image-to-word")
//    public ResponseEntity<String> convertImageToWord(@RequestParam("file") MultipartFile file) {
//        form form=new form();
//        String res;
//        try {
//
//            File imageFile = convertMultipartFileToFile(file);
//            form.setId(null);
//            form.setOriginal("src/main/resources/Original"+file.getOriginalFilename());
//            form.setConversion(res=transformService.convertImageToWord(imageFile));
//            form.setUid(12l);
//            form.setDate( LocalDateTime.now().toString()); // 使用当前时间
//            transformService.insertConvert(form);
//
//            return ResponseEntity.ok("图片 to Word 成功！. Output path: "+res);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error converting image to Word.");
//        }
//    }
//
//    @PostMapping("/pdf-to-word")
//    public ResponseEntity<String> convertPdfToWord(@RequestParam("file") MultipartFile file) {
//        form form=new form();
//        String res;
//        try {
//            File inputFile = convertMultipartFileToFile(file);
//
//
//            form.setId(null);
//            form.setOriginal("src/main/resources/Original"+file.getOriginalFilename());
//            form.setConversion(res=transformService.convertPdfToWord(inputFile));
//            form.setUid(12l);
//            form.setDate( LocalDateTime.now().toString()); // 使用当前时间
//            transformService.insertConvert(form);
//            return ResponseEntity.ok("PDF to Word 成功！.  Output path: "+res);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error converting PDF to Word.");
//        }
//    }
//
//    @PostMapping("/word-to-pdf")
//    public ResponseEntity<String> convertWordToPdf(@RequestParam("file") MultipartFile file) {
//        form form=new form();
//        String res;
//        try {
//            File inputFile = convertMultipartFileToFile(file);
//            form.setId(null);
//            form.setOriginal("src/main/resources/Original"+file.getOriginalFilename());
//            form.setConversion( res=transformService.convertWordToPdf(inputFile));
//            form.setUid(12l);
//            form.setDate( LocalDateTime.now().toString()); // 使用当前时间
//            transformService.insertConvert(form);
//
//            return ResponseEntity.ok("Word to PDF 成功！  Output path: "+res);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error converting Word to PDF.");
//        }
//    }
//
//    private File convertMultipartFileToFile(MultipartFile file) throws IOException {
//        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
//        file.transferTo(convFile);
//        return convFile;
//    }
@GetMapping("/image-to-pdf")
public String convertImageToPdf(@RequestParam("file") String file ,@RequestParam("uid") Long uid) {
    form form = new form();
    String res;
    try {
        File imageFile = new File(file);
        form.setId(null);
        form.setOriginal("src/main/resources/Original/"+imageFile.getName());
        form.setConversion(res=transformService.convertImageToPdf(imageFile));
        form.setUid(uid);
        form.setDate( LocalDateTime.now().toString()); // 使用当前时间
        transformService.insertConvert(form);
        return "转换成功";
    } catch (IOException | DocumentException e) {
        e.printStackTrace();
        return "Error";
    }
}
    @GetMapping("/image-to-word")
    public String convertImageToWord(@RequestParam("file") String file,@RequestParam("uid") Long uid) {
        form form=new form();
        String res;
        try {

            File imageFile = new File(file);
            form.setId(null);
            form.setOriginal("src/main/resources/Original/"+imageFile.getName());
            form.setConversion(res=transformService.convertImageToWord(imageFile));
            form.setUid(uid);
            form.setDate( LocalDateTime.now().toString()); // 使用当前时间
            transformService.insertConvert(form);

            return "转换成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error" ;
        }
    }

    @GetMapping("/pdf-to-word")
    public String convertPdfToWord(@RequestParam("file") String file,@RequestParam("uid") Long uid) {
        form form=new form();
        String res;
        try {
            File inputFile = new File(file);
            form.setId(null);
            form.setOriginal("src/main/resources/Original/"+inputFile.getName());
            form.setConversion(res=transformService.convertPdfToWord(inputFile));
            form.setUid(uid);
            form.setDate( LocalDateTime.now().toString()); // 使用当前时间
            transformService.insertConvert(form);
            return "转换成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }

    @GetMapping("/word-to-pdf")
    public String convertWordToPdf(@RequestParam("file") String file,@RequestParam("uid") Long uid) {
        form form=new form();
        String res;
        try {
            System.out.println("开始转换");
            File inputFile = new File(file);
            form.setId(null);
            form.setOriginal("src/main/resources/Original/"+inputFile.getName());
            form.setConversion( res=transformService.convertWordToPdf(inputFile));
            form.setUid(uid);
            form.setDate( LocalDateTime.now().toString()); // 使用当前时间
            transformService.insertConvert(form);
            return "转换成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }

    private File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
        file.transferTo(convFile);
        return convFile;
    }
    @GetMapping("/getformnames")
    public List<String> getFormNamesByUserId(@RequestParam("uid") Long uid) {
        List<form> formList = transformService.selectForm(uid);
        // 提取每个form的original属性中的文件名
        return   formList.stream()
                .map(form::getOriginal)
                .map(original -> original.substring(original.lastIndexOf("/") + 1))
                .collect(Collectors.toList());

    }


}
