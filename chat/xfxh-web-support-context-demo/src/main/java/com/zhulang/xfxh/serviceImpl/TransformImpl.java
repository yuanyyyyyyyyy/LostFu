package com.zhulang.xfxh.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.zhulang.xfxh.mapper.ConvertMapper;
import com.zhulang.xfxh.pojo.form;
import com.zhulang.xfxh.service.TransformService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Service
public class TransformImpl extends ServiceImpl<ConvertMapper, form> implements TransformService {

    private static final String OUTPUT_FOLDER = System.getProperty("user.dir") + "/src/main/resources/Conversion/";
    private static final String ORIGINAL_FOLDER = System.getProperty("user.dir") + "/src/main/resources/Original/";
    private void saveToOriginal(File file) throws IOException {
        String originalImagePath = ORIGINAL_FOLDER + file.getName();

        Files.copy(file.toPath(), new File(originalImagePath).toPath(), StandardCopyOption.REPLACE_EXISTING);
        System.out.println("文件已保存至: " + originalImagePath);
    }
    public String convertImageToPdf(File imageFile) throws IOException, DocumentException {
        saveToOriginal(imageFile);
        String outputPdfPath = OUTPUT_FOLDER + imageFile.getName().replace(".jpg", ".pdf");
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(outputPdfPath));
        document.open();
        Image image = Image.getInstance(imageFile.getAbsolutePath());
        image.scaleAbsolute(400, 400);
        document.add(image);
        document.close();
        System.out.println("Image 转 PDF 成功：" + outputPdfPath);
        return "/src/main/resources/Conversion/"+imageFile.getName().replace(".jpg", ".pdf");
    }

    public String convertImageToWord(File imageFile) {
        try (XWPFDocument document = new XWPFDocument();

             FileOutputStream outputStream = new FileOutputStream(OUTPUT_FOLDER + imageFile.getName().replace(".jpg", ".docx"))) {
                saveToOriginal(imageFile);
            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run = paragraph.createRun();

            String fileName = imageFile.getName();
            try (InputStream imageStream = new FileInputStream(imageFile)) {
                run.addPicture(imageStream, XWPFDocument.PICTURE_TYPE_JPEG, fileName, Units.toEMU(400), Units.toEMU(400));
            } catch (InvalidFormatException e) {
                e.printStackTrace();
            }
            document.write(outputStream);
            System.out.println("Image 转 Word 成功!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "/src/main/resources/Conversion/"+imageFile.getName().replace(".jpg", ".docx");
    }

    public String convertPdfToWord(File inputFile) {

        String outputFileName = inputFile.getName().replace(".pdf", ".docx");
        File outputFile = new File(OUTPUT_FOLDER + outputFileName);

        if (!inputFile.exists() || !inputFile.canRead()) {
            System.out.println("输入文件不存在或不可读：" + inputFile.getAbsolutePath());
            return"";
        }

        try (PDDocument pdfDocument = PDDocument.load(inputFile)) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(pdfDocument);
            saveToOriginal(inputFile);
            XWPFDocument wordDocument = new XWPFDocument();
            String[] paragraphs = text.split("\\r?\\n");

            for (String paragraphText : paragraphs) {
                XWPFParagraph paragraph = wordDocument.createParagraph();
                paragraph.createRun().setText(paragraphText);
            }

            try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                wordDocument.write(fos);
            }

            System.out.println("PDF 转 Word 成功！输出文件路径为：" + outputFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "/src/main/resources/Conversion/"+inputFile.getName().replace(".pdf", ".docx");
    }

    public String convertWordToPdf(File inputFile) {
        String outputFileName = inputFile.getName().replace(".docx", ".pdf");
        File outputFile = new File(OUTPUT_FOLDER + outputFileName);

        if (!inputFile.exists() || !inputFile.canRead()) {
            System.out.println("输入文件不存在或不可读：" + inputFile.getAbsolutePath());
            return "";
        }

        XWPFDocument document = null;
        Document pdfDocument = null;
        PdfWriter writer = null;

        try {
            FileInputStream fis = new FileInputStream(inputFile);
            document = new XWPFDocument(fis);

            FileOutputStream fos = new FileOutputStream(outputFile);

            pdfDocument = new Document();
            writer = PdfWriter.getInstance(pdfDocument, fos);
            pdfDocument.open();

            String fontPath = "C:/Windows/Fonts/simsun.ttc";
            BaseFont bfChinese = BaseFont.createFont(fontPath + ",0", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font fontChinese = new Font(bfChinese, 12, Font.NORMAL);

            for (XWPFParagraph paragraph : document.getParagraphs()) {
                for (XWPFRun run : paragraph.getRuns()) {
                    String text = run.getText(0);
                    if (text != null) {
                        Paragraph pdfParagraph = new Paragraph(text, fontChinese);
                        if (run.isBold()) {
                            pdfParagraph.getFont().setStyle(Font.BOLD);
                        }
                        if (run.isItalic()) {
                            pdfParagraph.getFont().setStyle(Font.ITALIC);
                        }
                        pdfDocument.add(pdfParagraph);
                    }
                }
            }
            saveToOriginal(inputFile);

            System.out.println("Word 转 PDF 成功！输出文件路径为：" + outputFile.getAbsolutePath());
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        } finally {
            try {
                if (document != null) {
                    document.close();
                }
                if (pdfDocument != null) {
                    pdfDocument.close();
                }
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "/src/main/resources/Conversion/"+inputFile.getName().replace(".docx", ".pdf");
    }

    public void insertConvert(form form) throws IllegalArgumentException, DataAccessException {
        // 参数校验
        if (form == null) {

            throw new IllegalArgumentException("转换对象不能为空");
        }
        // 这里可以添加更多的字段校验逻辑
        System.out.println(form);
        // 数据库操作在一个事务中执行，保证数据一致性
        // 假设开启、提交和回滚事务的逻辑已经被正确配置和处理
        baseMapper.insert(form);
    }
    public List<form> selectForm(long uid) {
        List<form> list=baseMapper.selectList(new QueryWrapper<form>().eq("uid",uid));
        System.out.println(list);
        return list;
    }


}