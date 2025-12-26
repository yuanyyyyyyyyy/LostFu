package com.demo.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AlipayConfig {
    // 支付宝沙箱APPID（替换为你的沙箱APPID）
    @Value("9021000158622522")
    private String appId;

    // 商户私钥（替换为你的沙箱商户私钥）
    @Value("MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCN5qPVVYoLDLgavcDMTx+sC+uKcBKyZaHHAJ6BDdZ5KquBx3hmegjJGSKIfOlWiNmKQVnmQ1jLXS0U3aXY4UVQjVzBzHoPbPZ6ocAWLTYbuGygQLvgqKOeXjFSm4hbpunugHllm+DW19K0Ey9ELzrUwyMgE8xfVLOkfv66a5956voCs4nvtpbZCM182V3hj22W7Szd9gr0EcsFvShdBSerysOdh+/TY38pN/RHGx80comhcJRG4ahiRdKEc38BICy9bWlDIqThad/z+qNxr75ybC5cungraswL2stsCgquuu1zkTYV/+fJzjr4mLnZULoOsD8DnJOQkGdum0gkusxxAgMBAAECggEAdFsaHnWMwzDDyhGshRugYehIVPU/L4+Mz2xfjH03T5XAmqNgw87vY0679WVPxIhksSyTNfAlYGRf8gsp0iQuPTYdSECOGhYEKlx18PlTs/lPATzzke1WdSbRlOY6MsU0b0BjywC9pg3FFanhiSydNhdotwXlAIqo34rO/zf+xe24C1kbNShWIhL5IWkuTHEdOaaVD9J/xr4JBu6sIoLwJ9SB8iS/vaCA7n9vFKPvqqRE9N+rd7NFIc92sT51x6HGZpgbdIdGVw1buUvKOLvdZMJTD5S1vcUjhfFqiwLg0tDbOGM3PpOhyvoEa9u3wrlq/9/U9HyxSOZaIEQsnbP6AQKBgQD2GZK6gRXsnMk9IZmbuMfcciDz5tevQ3SCN1xnp1OJ5Yn+mBTBa2Yl3omjqPdSVFVPXC/grv4fQsf4Gzs3FHyA/ONwXJcH7QZRUVuovMcEOt6IyEmcDPt6cKXv29dVBRCXXsY7aHLPLDHNoJdXGPZEQMY9CORlU3ejXs2t14rykQKBgQCTm/zf4oqtxxLNdZ1xrCmJD41hWvgd1z1qKr54Us1twvQM3EwV6fVS7p1ZL/kRXvHpoPnfS1nDeVfJzRiimPJlkNd6fuujD2uBEw7HFYIhLF1UKuQlbRqvL2+j5Yn34qQ707LfHaNtSyCZxXAfDSigs18GkyvRPgvk4FmO1ylr4QKBgQCLMP2fROFsmWTskXN68vD/iwF+UcMN2t4rxDocWjRSmyQIfmXG3ALDSnGYwjlYNGCn6SEfOHp8nd3SQzyjs4+TuPS+mW/GyNW46oYoEtWRW+Z/gKC5hVI69zmzRyksahfvNWhzM2k0Ke/lOH5AgdrP0b8NyFi9wo65NSapu25YMQKBgEpebgJyxgIV6YBAwMlzvx2CC25Agf+u4Q9rZ7mBpmSls4YDPAZScLqO5BH6XJeG6YUXCK2a7ifRLAzNW5sYShAn8JiT966Y50YJzwk46e5CxubQEVBV7vrSgIHn9SJCOxK1b0qASUWYc11mHz5Vx+FnXfoXDh2HEEEJU7Xm68ShAoGATk/MEqVAqhAKKCa5bI9BBMJWPRJrZrRUDmofe8f0L6Bst739BzoCJDuP+rF0Pgy11NMTxnwoyMQPsd+wBgYXGfgoaH5G4UymkCURRGZFU95HZ62t5y2WgVxg4CGf25yE/2Cc3ioBUX6BFle84WGMgtEPfit/oefqMnmAEgGgrmc=")
    private String privateKey;

    // 支付宝公钥（替换为你的沙箱支付宝公钥）
    @Value("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhtgaWvGWOLqCaHNjKKWvzv7r6LYuEuEWsvkYfBpVbUrIvoJAR/1mNXyVmxrAYZTJ/EW1LhBXKywGTm+H8dSg148P5vyNCMiqtlnTlb1FQL9Lb74Y+utZonAY7xXuM/Pkyy/9I/jP6yNAw+7FRIRSWhfjdoeeDdmh7+uB64vKirKsPpUZpOnWBJ50P6oll+XtLy5J0PCFgzUGv7s+iV3Rias0Gz2eESzL6RkWLTFfKabBvz6dUdOOZhlXUB0Ir9RYGEOgbz3U+OZeoGzYmzBsFvjVXRuKnLbrfFW+aFiK9ZhMpWTAmVMoWqsFGMbB2znR4sAXIc7FtoYoHjZN3+05VwIDAQAB")
    private String publicKey;

    // 沙箱网关
    private static final String GATEWAY_URL = "https://openapi.alipaydev.com/gateway.do";

    // 格式
    private static final String FORMAT = "json";

    // 编码
    private static final String CHARSET = "UTF-8";

    // 签名方式
    private static final String SIGN_TYPE = "RSA2";

    // getter方法
    public String getAppId() { return appId; }
    public String getPrivateKey() { return privateKey; }
    public String getPublicKey() { return publicKey; }
    public String getGatewayUrl() { return GATEWAY_URL; }
    public String getFormat() { return FORMAT; }
    public String getCharset() { return CHARSET; }
    public String getSignType() { return SIGN_TYPE; }
}