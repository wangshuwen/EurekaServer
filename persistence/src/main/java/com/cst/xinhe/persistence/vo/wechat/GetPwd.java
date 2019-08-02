package com.cst.xinhe.persistence.vo.wechat;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-07-04 17:06
 **/
public class GetPwd {

    private String account;

    private String idCard;

    private String newPassword;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
