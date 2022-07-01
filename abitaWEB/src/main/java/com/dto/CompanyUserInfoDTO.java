package com.dto;

import com.dto.UserInfoDTO;

import java.io.Serializable;

/**
 * User: P.Rahmoune Date: 11 janv. 2005 Time: 11:57:12
 */
public class CompanyUserInfoDTO extends UserInfoDTO implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = -4031369399660701532L;

    /**
     * User have a Role Agent.
     */
    public static final String AGENT_KEY = "isAgent";
    /**
     * password.
     */
    public static final String PWD_KEY = "password";

}
