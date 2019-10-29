package io.swagger.model.UserDetails;

/**
 * Simple class Email, that includes private instance variables loginName and domain.
 * */
public class Email {

    private String loginName;
    private String domain;

    public Email(String loginName, String domain){
        this.loginName = loginName;
        this.domain = domain;
    }

    public String getLoginName() {
        return loginName;
    }

    public String getDomain() {
        return domain;
    }
}
