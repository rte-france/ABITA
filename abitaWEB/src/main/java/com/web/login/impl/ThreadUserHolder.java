package com.web.login.impl;

import com.dto.UserDTO;

/**
 * thread context.
 * @author
 *
 */
public final class ThreadUserHolder {

	/**
	 * Private constructor
	 */
	private ThreadUserHolder() {
		super();
	}

	/** thread local user */
	private static ThreadLocal<UserDTO> threadLocal = new ThreadLocal<UserDTO>();

	/**
	 * return the thread user.
	 * @return user
	 */
	public static UserDTO get() {
		return threadLocal.get();
	}

	/**
	 * set the thread user.
	 * @param user user
	 */
	public static void set(UserDTO user) {
		threadLocal.set(user);
	}
}
