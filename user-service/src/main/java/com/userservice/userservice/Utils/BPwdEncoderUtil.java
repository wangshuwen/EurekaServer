package com.userservice.userservice.Utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author wangshuwen
 * @Description:
 * @Date 2019/4/30/16:14
 */
public class BPwdEncoderUtil {
    private  static final BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
    public static String BCryptPassword(String password){
        return encoder.encode(password);
    }

    public static boolean matches (CharSequence  rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}
