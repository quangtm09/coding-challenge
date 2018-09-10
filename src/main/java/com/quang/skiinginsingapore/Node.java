package com.quang.skiinginsingapore;

/**
 * The Class Node.
 */
public class Node {

	/** The node id. */
	private int nodeId;

	/** The node value. */
	private int nodeValue;

	/**
	 * Instantiates a new node.
	 *
	 * @param nodeId
	 *            the node id
	 * @param nodeValue
	 *            the node value
	 */
	public Node(final int nodeId, final int nodeValue) {
		this.nodeId = nodeId;
		this.nodeValue = nodeValue;
	}

	/**
	 * Gets the node id.
	 *
	 * @return the node id
	 */
	public int getNodeId() {
		return nodeId;
	}

	/**
	 * Sets the node id.
	 *
	 * @param nodeId
	 *            the new node id
	 */
	public void setNodeId(final int nodeId) {
		this.nodeId = nodeId;
	}

	/**
	 * Gets the node value.
	 *
	 * @return the node value
	 */
	public int getNodeValue() {
		return nodeValue;
	}

	/**
	 * Sets the node value.
	 *
	 * @param nodeValue
	 *            the new node value
	 */
	public void setNodeValue(final int nodeValue) {
		this.nodeValue = nodeValue;
	}

}