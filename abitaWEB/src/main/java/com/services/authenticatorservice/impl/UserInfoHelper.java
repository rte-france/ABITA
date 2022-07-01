package com.services.authenticatorservice.impl;

import java.util.Map;

import com.dao.user.entity.User;
import com.dto.UserDTO;
import com.dto.UserInfoDTO;

/**
 * A helper to map UserInfoDTO and User.
 * @author
 *
 */
public final class UserInfoHelper {

	/**
	 * Constructor
	 */
	private UserInfoHelper() {
		super();
	}

	/**
	 * Construct  UserInfo with User.
	 * @param user user to map
	 * @return user info.
	 */
	public static UserInfoDTO mapUser2UserInfo(final UserDTO user) {
		final UserInfoDTO userInfo = new UserInfoDTO();
		final Map<String, String> userInfoProperties = userInfo.getProperties();
		userInfoProperties.put(UserInfoDTO.LOGIN_KEY, user.getLogin());
		userInfoProperties.put(UserInfoDTO.FIRST_NAME_KEY, user.getFirstName());
		userInfoProperties.put(UserInfoDTO.LAST_NAME_KEY, user.getLastName());
		userInfoProperties.put(UserInfoDTO.LOCATION_KEY, user.getLocation());
		userInfoProperties.put(UserInfoDTO.EMAIL_KEY, user.getEmail());
		userInfoProperties.put(UserInfoDTO.PHONE_KEY, user.getPhone());
		userInfoProperties.put(UserInfoDTO.FAX_KEY, user.getFax());
		userInfoProperties.put(UserInfoDTO.UID_KEY, user.getLogicalId());
		userInfo.setProperties(userInfoProperties);
		return userInfo;
	}

	/**
	 * Construct User with UserInfo
	 * @param userInfo user info
	 * @return mapped user
	 */
	public static User mapUserInfo2User(final UserInfoDTO userInfo) {
		final User user = new User();
		String login = userInfo.getProperties().get(UserInfoDTO.LOGIN_KEY);
		user.setLogin(login);
		String firstName = userInfo.getProperties().get(UserInfoDTO.FIRST_NAME_KEY);
		user.setFirstName(firstName);
		String lastName = userInfo.getProperties().get(UserInfoDTO.LAST_NAME_KEY);
		user.setLastName(lastName);
		String location = userInfo.getProperties().get(UserInfoDTO.LOCATION_KEY);
		user.setLocation(location);
		String email = userInfo.getProperties().get(UserInfoDTO.EMAIL_KEY);
		user.setEmail(email);
		String phone = userInfo.getProperties().get(UserInfoDTO.PHONE_KEY);
		user.setPhone(phone);
		String fax = userInfo.getProperties().get(UserInfoDTO.FAX_KEY);
		user.setFax(fax);
		return user;
	}
}
