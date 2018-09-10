package com.quang.skiinginsingapore;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * The Class Util.
 */
public final class Util {

	/** The Constant ID_COUNTER. */
	private static final AtomicInteger NODEID_COUNTER = new AtomicInteger(0);

	/** The Constant _DELIMETER. */
	public static final String _DELIMETER = " ";

	private Util() {
		// Util class cannot be instantiated
	}

	/**
	 * Creates the id.
	 *
	 * @return the int
	 */
	public static int createId() {
		return NODEID_COUNTER.getAndIncrement();
	}
}
