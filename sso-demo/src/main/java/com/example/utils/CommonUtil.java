package com.example.utils;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/10/16.
 */
public class CommonUtil {

    private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

    private static final int[] INDEX = {1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192};

    private static  Random r = new Random();


    /**
     * 获取距离某个值最近的2的n次幂, 比如当number=15时,返回16, 当number=33时,返回32
     *
     * @return
     */
    public static int getNearestPowerOfTwo(int number) {

        int index = (int) Math.round(Math.log(number) / Math.log(2));
        int result = INDEX[index];

        return result;
    }

    /**
     * @param args
     * @return
     * @title: checkParamBlank
     * @description: 判断不存在为null的参数，不存在为true，存在为false，String类型包括""、" "
     */
    public static boolean checkParamBlank(Object... args) {
        for (Object arg : args) {
            if (arg == null || (arg.getClass().equals(String.class) && StringUtils.isEmpty((String) arg))) {
                return false;
            }
        }
        return true;
    }


    /**
     * 获取固定长度的数值字符串
     *
     * @param charCount
     * @return
     */
    public static String getRandNum(int charCount) {
        String charValue = "";
        for (int i = 0; i < charCount; i++) {
            char c = (char) (randomInt(0, 10) + '0');
            charValue += String.valueOf(c);
        }
        return charValue;

    }

    /**
     * 生成某个范围内的单个随机数字
     *
     * @param from
     * @param to
     * @return
     */
    public static int randomInt(int from, int to) {
        return from + r.nextInt(to - from);
    }


    /**
     * 求一个整数的各位数字的和
     *
     * @param num
     * @return
     */
    public static long sumOfNum(Long num) {
        long result = 0;
        Long numTemp = new Long(num);
        if (numTemp < 0) {
            numTemp = 0 - numTemp;
        }
        String numStr = "" + numTemp;
        for (int i = 0; i < numStr.length(); i++) {
            result += Long.valueOf(numStr.substring(i, i + 1));
        }
        return result;
    }


    /**
     * 验证IP地址
     *
     * @param str 待验证的字符串
     * @return 如果是符合格式的字符串, 返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean isIP(String str) {
        String num = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
        String regex = "^" + num + "\\." + num + "\\." + num + "\\." + num + "$";
        return match(regex, str);
    }

    /**
     * 验证IP地址
     *
     * @param str 待验证的字符串
     * @return 如果是符合格式的字符串, 返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean isIpPrefix(String str) {
        String num = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
        String regex = "^" + num + "\\." + num + "\\." + num + "\\." + num;
        return match(regex, str);
    }

    /**
     * @param regex 正则表达式字符串
     * @param str   要匹配的字符串
     * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
     */
    private static boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

}
