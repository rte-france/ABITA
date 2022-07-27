/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.dto.batch;

/**
 * Information level types.
 * @author
 */
public enum BatchMessageSeverityLevel {
	/** Just for information */
	INFO,
	/** Something strange happened */
	WARN,
	/** Error raised */
	ERROR;
}
