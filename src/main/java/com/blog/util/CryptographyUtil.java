package com.blog.util;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.tomcat.util.security.MD5Encoder;
import sun.misc.BASE64Encoder;
import sun.security.provider.MD5;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Jennyism
 * @date 20/4/2020 下午2:16
 */
public class CryptographyUtil {

    public static String md5(String input,String salt) {
        return new Md5Hash(input,salt).toString();
    }

    public static void main(String[] args) {
        String java1234 = md5("1234", "java1234");
        System.out.println(java1234);
    }
}
