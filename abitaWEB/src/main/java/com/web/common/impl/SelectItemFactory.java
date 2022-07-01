package com.web.common.impl;

import com.services.common.constants.Constants;
import com.services.common.util.MessageSupport;

import javax.faces.model.SelectItem;

/**
 * Util Factory for common list item.
 * @author
 *
 */
public abstract class SelectItemFactory {

	/**
	 * Private constructor
	 */
	private SelectItemFactory() {
		super();
	}

	/**
	 * ALL messages Key
	 */
	private static final String ALL_KEY = "all";

	/**
	 * NONE messages Key
	 */
	private static final String NONE_KEY = "none";

	/**
	 * SelectItem value for ALL
	 */
	private static final String ALL_VALUE = "";

	/**
	 * SelectItem value for NONE
	 */
	private static final String NONE_VALUE = " ";

	/**
	 * SelectItem for ALL
	 */
	private static SelectItem allSelectItem = null;

	/**
	 * SelectItem for NONE
	 */
	private static SelectItem noneSelectItem = null;

	/**
	 * internationalized All select item
	 * @return selectItem all
	 */
	public static SelectItem getAll() {
		if (allSelectItem == null) {
			String labelAll = MessageSupport.getMessage(Constants.COMMON_BUNDLE, ALL_KEY);
			allSelectItem = new SelectItem(ALL_VALUE, labelAll);
		}
		return allSelectItem;
	}

	/**
	 * internationalized none select item
	 * @return selectItem none
	 */
	public static SelectItem getNone() {
		if (noneSelectItem == null) {
			String labelNone = MessageSupport.getMessage(Constants.COMMON_BUNDLE, NONE_KEY);
			noneSelectItem = new SelectItem(NONE_VALUE, labelNone);
		}
		return noneSelectItem;
	}

}
