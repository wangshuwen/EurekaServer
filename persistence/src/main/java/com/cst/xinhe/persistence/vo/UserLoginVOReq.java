package com.cst.xinhe.persistence.vo;

/**
 * @ClassName UserLoginVOReq
 * @Description
 * @Auther lifeng
 * @DATE 2018/9/4 9:05
 * @Vserion v0.0.1
 */
public class UserLoginVOReq {
    private String account;

    private String passWord;

    private boolean remenberMe;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public boolean isRemenberMe() {
        return remenberMe;
    }

    public void setRemenberMe(boolean remenberMe) {
        this.remenberMe = remenberMe;
    }

    @Override
    public String toString() {
        return "SysUserVo{" +
                "account='" + account + '\'' +
                ", passWord='" + passWord + '\'' +
                ", remenberMe=" + remenberMe +
                '}';
    }
}
