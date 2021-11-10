package com.sso.server.util;

import org.springframework.util.DigestUtils;

/**
 * MD5加密工具类
 * @author wangteng05
 * @description: MD5Util类
 * @date 2021/11/1 16:56
 */
public class MD5Util {
    private static final byte[] PASS_WORD_SALT_BYTES = "sso-server-pwd".getBytes();


    /**
     * 明文密码加密为密文密码
     * @param passWord
     * @return
     */
    public static String getEncryptedPwd(String passWord) {
        String pwdMd5HexStr = DigestUtils.md5DigestAsHex(passWord.getBytes());
        String saltMd5HexStr = DigestUtils.md5DigestAsHex(PASS_WORD_SALT_BYTES);

        return DigestUtils.md5DigestAsHex((pwdMd5HexStr + saltMd5HexStr).getBytes());
    }


    /**
     * 密码校验
     * @param sourcePassWord  输入的原密码(明文)
     * @param storedPassWord  存储到DB的密码（密文）
     * @return
     */
    public static boolean validPassword(String sourcePassWord, String storedPassWord) {
        String finalPwdStr = getEncryptedPwd(sourcePassWord);
        if (finalPwdStr.equals(storedPassWord)){
            return true;
        }
        return false;
    }


    public static void main(String[] args) {
        String s = getEncryptedPwd("123456");
        System.out.println(s);
    }
}
