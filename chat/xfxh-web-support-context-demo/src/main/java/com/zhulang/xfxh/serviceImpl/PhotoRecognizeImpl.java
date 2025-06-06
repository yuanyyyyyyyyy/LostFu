package com.zhulang.xfxh.serviceImpl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.zhulang.xfxh.util.FileUtil;
import com.zhulang.xfxh.util.HttpUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import java.util.UUID;
import static com.zhulang.xfxh.serviceImpl.PhotoRecognizeImpl.ResponseData.generateImageWithText;
@Service
public class PhotoRecognizeImpl {
    // ITR webapi 接口地址
    private static final String WebITR_URL = "https://rest-api.xfyun.cn/v2/itr"; //https url
    // 应用ID（到控制台获取）
    private static final String APPID = "ea1231cb";
    // 接口APIKey（到控制台的拍照速算识别页面获取）
    private static final String API_KEY = "4e16e3fa980ab21fb6001e90fd263fbd";
    // 接口APISercet（到控制台的拍照速算识别页面获取）
    private static final String API_SECRET = "MzdjMzFjZGM2MmZhM2E3ZDVkOWQ0NWQ2";
    // 图片地址
    //private static final String AUDIO_PATH = "src\\main\\resources\\itr\\testitr.jpg";

    public File photoRecognize(File imageFile) throws Exception {
        if (APPID.equals("") || API_KEY.equals("") || API_SECRET.equals("")) {
            throw new IllegalArgumentException("Appid 或APIKey 或APISecret 为空！请打开demo代码，填写相关信息。");
        }
        String body = buildHttpBody(imageFile);
        Map<String, String> header = buildHttpHeader(body);
        Map<String, Object> resultMap = HttpUtil.doPost2(WebITR_URL, header, body);
        if (resultMap != null) {
            String resultStr = resultMap.get("body").toString();
            System.out.println("【ITR WebAPI 接口调用结果】\n" + resultStr);

            Gson json = new Gson();
            ResponseData resultData = json.fromJson(resultStr, ResponseData.class);
//            String outputFilePath = "D:\\testpic\\output.png";
            String outputFilePath=System.getProperty("user.dir")+"\\src\\main\\resources\\static\\images\\"+UUID.randomUUID().toString()+".png";
            generateImageWithText(resultData, outputFilePath);
            if (resultData.getCode() != 0) {
                System.out.println("请前往https://www.xfyun.cn/document/error-code?code=" + resultData.getCode() + "查询解决办法");
            } else {
                printResults(resultData);
            }
            return new File(outputFilePath);
        } else {
            throw new RuntimeException("调用失败！请根据错误信息检查代码，接口文档：https://www.xfyun.cn/doc/words/photo-calculate-recg/API.html");
        }
    }

    //下载图片
    public void downloadImage(String imageUrl, String savePath) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<byte[]> response = restTemplate.getForEntity(imageUrl, byte[].class);

        // 检查响应是否成功
        if (response.getStatusCode().is2xxSuccessful()) {
            byte[] imageBytes = response.getBody();
            saveImage(imageBytes, savePath);
            System.out.println("Image downloaded successfully");
        } else {
            System.err.println("Failed to download image. Status code: " + response.getStatusCode());
        }
    }

    private void saveImage(byte[] imageBytes, String savePath) throws IOException {
        Path path = Paths.get(savePath);
        Files.write(path, imageBytes);
        System.out.println("Image saved to: " + savePath);
    }

    //打印识别的题目
    public static void printResults(ResponseData resultData) {

        //逐层解析
        if (resultData.getData() == null) {
            System.out.println("数据为空");
            return;
        }

        Map<String, Object> data = (Map<String, Object>) resultData.getData();
        if (data == null) {
            System.out.println("data 为空");
            return;
        }

        Map<String, Object> itrResult = (Map<String, Object>) data.get("ITRResult");
        if (itrResult == null) {
            System.out.println("ITRResult 为空");
            return;
        }

        Map<String, Object> multiLineInfo = (Map<String, Object>) itrResult.get("multi_line_info");
        if (multiLineInfo == null) {
            System.out.println("multi_line_info 为空");
            return;
        }

        List<Map<String, Object>> impLineInfo = (List<Map<String, Object>>) multiLineInfo.get("imp_line_info");
        if (impLineInfo == null) {
            System.out.println("imp_line_info 为空");
            return;
        }

        List<Map<String, Object>> recogResult = (List<Map<String, Object>>) itrResult.get("recog_result");
        if (recogResult == null) {
            System.out.println("recog_result 为空");
            return;
        }

        for (int i = 0; i < recogResult.size(); i++) {
            Map<String, Object> result = recogResult.get(i);
            List<Map<String, Object>> lineWordResult = (List<Map<String, Object>>) result.get("line_word_result");

            if (lineWordResult == null) {
                continue;
            }

            for (int j = 0; j < lineWordResult.size(); j++) {
                Map<String, Object> wordResult = lineWordResult.get(j);
                List<String> wordContent = (List<String>) wordResult.get("word_content");

                if (wordContent != null) {
                    boolean isCorrect = ((Number) impLineInfo.get(j).get("total_score")).intValue() == 1;

                    for (String word : wordContent) {
                        if (isCorrect) {
                            System.out.println("正确题目: " + word);
                        } else {
                            System.out.println("错误题目: " + word);
                        }
                    }
                }
            }
        }
    }


    //构造http请求头
    public static Map<String, String> buildHttpHeader(String body) throws Exception {
        Map<String, String> header = new HashMap<>();
        URL url = new URL(WebITR_URL);

        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        String date = format.format(new Date());

        String digestBase64 = "SHA-256=" + signBody(body);

        StringBuilder builder = new StringBuilder("host: ").append(url.getHost()).append("\n")
                .append("date: ").append(date).append("\n")
                .append("POST ").append(url.getPath()).append(" HTTP/1.1").append("\n")
                .append("digest: ").append(digestBase64);

        String sha = hmacsign(builder.toString(), API_SECRET);

        String authorization = String.format("api_key=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"",
                API_KEY, "hmac-sha256", "host date request-line digest", sha);

        header.put("Authorization", authorization);
        header.put("Content-Type", "application/json");
        header.put("Accept", "application/json,version=1.0");
        header.put("Host", url.getHost());
        header.put("Date", date);
        header.put("Digest", digestBase64);
        return header;
    }
//生成请求体，不传参形式
//    public static String buildHttpBody() throws Exception {
//        JsonObject body = new JsonObject();
//        JsonObject business = new JsonObject();
//        JsonObject common = new JsonObject();
//        JsonObject data = new JsonObject();
//
//        common.addProperty("app_id", APPID);
//        business.addProperty("ent", "math-arith");
//        business.addProperty("aue", "raw");
//
//        byte[] imageByteArray = FileUtil.read(AUDIO_PATH);
//        String imageBase64 = Base64.getEncoder().encodeToString(imageByteArray);
//        data.addProperty("image", imageBase64);
//
//        body.add("common", common);
//        body.add("business", business);
//        body.add("data", data);
//
//        return body.toString();
//    }

    //构造请求体传参形式
public static String buildHttpBody(File imageFile) throws Exception {
    JsonObject body = new JsonObject();
    JsonObject business = new JsonObject();
    JsonObject common = new JsonObject();
    JsonObject data = new JsonObject();

    common.addProperty("app_id", APPID);
    business.addProperty("ent", "math-arith");
    business.addProperty("aue", "raw");

    byte[] imageByteArray = FileUtil.read(imageFile.getPath());
    String imageBase64 = Base64.getEncoder().encodeToString(imageByteArray);
    data.addProperty("image", imageBase64);

    body.add("common", common);
    body.add("business", business);
    body.add("data", data);

    return body.toString();
}

    private static String signBody(String body) throws Exception {
        MessageDigest messageDigest;
        String encodestr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(body.getBytes("UTF-8"));
            encodestr = Base64.getEncoder().encodeToString(messageDigest.digest());
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodestr;
    }

    private static String hmacsign(String signature, String apiSecret) throws Exception {
        Charset charset = Charset.forName("UTF-8");
        Mac mac = Mac.getInstance("hmacsha256");
        SecretKeySpec spec = new SecretKeySpec(apiSecret.getBytes(charset), "hmacsha256");
        mac.init(spec);
        byte[] hexDigits = mac.doFinal(signature.getBytes(charset));
        return Base64.getEncoder().encodeToString(hexDigits);
    }

    public static class ResponseData {
        private int code;
        private String message;
        private String sid;
        private Object data;

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        public String getSid() {
            return sid;
        }

        public Object getData() {
            return data;
        }

        public static void generateImageWithText(ResponseData resultData, String outputPath) throws IOException {
            // 创建一个空白的图片
            int width = 800;
            int height = 1000;
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = image.createGraphics();

            // 设置背景色
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, width, height);

            // 设置字体和颜色
            Font font = new Font("SimSun", Font.PLAIN, 24); // 使用支持中文的字体
            g2d.setFont(font);

            if (resultData.getData() == null) {
                g2d.setColor(Color.RED);
                g2d.drawString("数据为空", 50, 50);
                g2d.dispose();
                ImageIO.write(image, "png", new File(outputPath));
                return;
            }

            Map<String, Object> data = (Map<String, Object>) resultData.getData();
            if (data == null) {
                g2d.setColor(Color.RED);
                g2d.drawString("data 为空", 50, 50);
                g2d.dispose();
                ImageIO.write(image, "png", new File(outputPath));
                return;
            }

            Map<String, Object> itrResult = (Map<String, Object>) data.get("ITRResult");
            if (itrResult == null) {
                g2d.setColor(Color.RED);
                g2d.drawString("ITRResult 为空", 50, 50);
                g2d.dispose();
                ImageIO.write(image, "png", new File(outputPath));
                return;
            }

            Map<String, Object> multiLineInfo = (Map<String, Object>) itrResult.get("multi_line_info");
            if (multiLineInfo == null) {
                g2d.setColor(Color.RED);
                g2d.drawString("multi_line_info 为空", 50, 50);
                g2d.dispose();
                ImageIO.write(image, "png", new File(outputPath));
                return;
            }

            List<Map<String, Object>> impLineInfo = (List<Map<String, Object>>) multiLineInfo.get("imp_line_info");
            if (impLineInfo == null) {
                g2d.setColor(Color.RED);
                g2d.drawString("imp_line_info 为空", 50, 50);
                g2d.dispose();
                ImageIO.write(image, "png", new File(outputPath));
                return;
            }

            List<Map<String, Object>> recogResult = (List<Map<String, Object>>) itrResult.get("recog_result");
            if (recogResult == null) {
                g2d.setColor(Color.RED);
                g2d.drawString("recog_result 为空", 50, 50);
                g2d.dispose();
                ImageIO.write(image, "png", new File(outputPath));
                return;
            }

            int y = 50;
            for (int i = 0; i < recogResult.size(); i++) {
                Map<String, Object> result = recogResult.get(i);
                List<Map<String, Object>> lineWordResult = (List<Map<String, Object>>) result.get("line_word_result");

                if (lineWordResult == null) {
                    continue;
                }

                for (int j = 0; j < lineWordResult.size(); j++) {
                    Map<String, Object> wordResult = lineWordResult.get(j);
                    List<String> wordContent = (List<String>) wordResult.get("word_content");

                    if (wordContent != null) {
                        boolean isCorrect = ((Number) impLineInfo.get(j).get("total_score")).intValue() == 1;

                        for (String word : wordContent) {
                            if (isCorrect) {
                                g2d.setColor(Color.BLACK);
                                g2d.drawString("正确: " + word, 50, y);
                            } else {
                                g2d.setColor(Color.RED);
                                g2d.drawString("错误: " + word, 50, y);
                            }
                            y += 30;
                        }
                    }
                }
            }

            g2d.dispose();
            ImageIO.write(image, "png", new File(outputPath));
        }
    }

}
