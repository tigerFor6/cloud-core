package com.wisdge.cloud.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.codec.Hex;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.MessageDigest;

@Slf4j
public class Helper {
    /**
     * 通过SHA1对密码进行编码
     *
     * @param password 密码
     * @param salt 盐值
     * @return
     */
    public static String encodeToken(String password, String salt) {
        String encodedPassword;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            if (salt != null) {
                digest.reset();
                digest.update(salt.getBytes());
            }
            byte[] hashed = digest.digest(password.getBytes());
            encodedPassword = new String(Hex.encode(hashed));
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            return null;
        }
        return encodedPassword;
    }

    /**
     * 获取完整的异常栈信息
     * @param t
     * @return
     */
    public static String getTrace(Throwable t) {
        StringWriter stringWriter= new StringWriter();
        PrintWriter writer= new PrintWriter(stringWriter);
        t.printStackTrace(writer);
        StringBuffer buffer= stringWriter.getBuffer();
        return buffer.toString();
    }
}
