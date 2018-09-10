package com.quang.skiinginsingapore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The Class Graph.
 */
public class Graph {

	/** The adjacent node map. */
	private Map<Integer, List<Node>> adjacentNodeMap;

	/** The graph node list. */
	private List<List<Node>> graphNodeList;

	/** The max length. */
	private int maxLength;

	/** The max steep. */
	private int maxSteep;

	/**
	 * Instantiates a new graph.
	 */
	public Graph() {
		adjacentNodeMap = new HashMap<>();
		graphNodeList = new ArrayList<>();
		maxLength = 0;
		maxSteep = 0;
	}

	/**
	 * Gets the adjacent node map.
	 *
	 * @return the adjacent node map
	 */
	public Map<Integer, List<Node>> getAdjacentNodeMap() {
		return adjacentNodeMap;
	}

	/**
	 * Sets the adjacent node map.
	 *
	 * @param adjacentNodeMap
	 *            the adjacent node map
	 */
	public void setAdjacentNodeMap(final Map<Integer, List<Node>> adjacentNodeMap) {
		this.adjacentNodeMap = adjacentNodeMap;
	}

	/**
	 * Gets the graph node list.
	 *
	 * @return the graph node list
	 */
	public List<List<Node>> getGraphNodeList() {
		return graphNodeList;
	}

	/**
	 * Sets the graph node list.
	 *
	 * @param graphNodeList
	 *            the new graph node list
	 */
	public void setGraphNodeList(final List<List<Node>> graphNodeList) {
		this.graphNodeList = graphNodeList;
	}

	/**
	 * Gets the max length.
	 *
	 * @return the max length
	 */
	public int getMaxLength() {
		return maxLength;
	}

	/**
	 * Sets the max length.
	 *
	 * @param maxLength
	 *            the new max length
	 */
	public void setMaxLength(final int maxLength) {
		this.maxLength = maxLength;
	}

	/**
	 * Gets the max steep.
	 *
	 * @return the max steep
	 */
	public int getMaxSteep() {
		return maxSteep;
	}

	/**
	 * Sets the max steep.
	 *
	 * @param maxSteep
	 *            the new max steep
	 */
	public void setMaxSteep(final int maxSteep) {
		this.maxSteep = maxSteep;
	}

	/**
	 * Adds the adjacent node.
	 *
	 * @param previousNode
	 *            the previous node
	 * @param currentNode
	 *            the current node
	 */
	public void addAdjacentNode(final Node previousNode, final Node currentNode) {
		if (previousNode.getNodeValue() > currentNode.getNodeValue()) {
			this.addAdjacentNode(previousNode.getNodeId(), currentNode);
		} else if (previousNode.getNodeValue() < currentNode.getNodeValue()) {
			this.addAdjacentNode(currentNode.getNodeId(), previousNode);
		}
	}

	/**
	 * Gets the adjacent nodes.
	 *
	 * @param node
	 *            the node
	 * @return the adjacent nodes
	 */
	public List<Node> getAdjacentNodes(final Node node) {
		final List<Node> adjacent = Optional.ofNullable(adjacentNodeMap.get(node.getNodeId()))
				.orElseGet(() -> new ArrayList<>());
		return adjacent;
	}

	/**
	 * Depth-first-search traverse all nodes of the graph.
	 */
	public void dfsTraverse() {
		graphNodeList.forEach(rowNodes -> rowNodes.forEach(node -> {
			final List<Node> visited = new ArrayList<>();
			visited.add(node);
			findMaxLengthAndSteep(visited, node);
		}));
	}

	/**
	 * Find max length and steep by recursively calling this method.
	 *
	 * @param visited
	 *            the visited
	 * @param node
	 *            the node
	 */
	private void findMaxLengthAndSteep(final List<Node> visited, final Node node) {
		final List<Node> adjacentNodes = getAdjacentNodes(node);
		final int currentSteep = calculateCurrentSteep(visited);

		if (adjacentNodes.isEmpty()) {
			if (visited.size() > getMaxLength()) {
				setMaxLength(visited.size());
				setMaxSteep(currentSteep);
			} else if (visited.size() == getMaxLength() && currentSteep > getMaxSteep()) {
				setMaxSteep(currentSteep);
			}
			return;
		}

		adjacentNodes.forEach(adjacentNode -> {
			final List<Node> tempVisited = new ArrayList<>();
			tempVisited.addAll(visited);
			tempVisited.add(adjacentNode);
			findMaxLengthAndSteep(tempVisited, adjacentNode);
		});
	}

	/**
	 * Calculate current steep.
	 *
	 * @param visited
	 *            the visited
	 * @return the int
	 */
	private int calculateCurrentSteep(final List<Node> visited) {
		final Node firstNode = visited.get(0);
		final Node lastNode = visited.get(visited.size() - 1);
		return firstNode.getNodeValue() - lastNode.getNodeValue();
	}

	/**
	 * Adds the adjacent node.
	 *
	 * @param nodeId
	 *            the node id
	 * @param adjacentNode
	 *            the adjacent node
	 */
	private void addAdjacentNode(final int nodeId, final Node adjacentNode) {
		final List<Node> adjacent = Optional.ofNullable(adjacentNodeMap.get(nodeId)).orElseGet(() -> new ArrayList<>());
		adjacentNodeMap.put(nodeId, adjacent);
		adjacent.add(adjacentNode);
	}

	/**
	 * Implement graph network based on input text string.
	 *
	 * @param inputIntegerArray
	 *            the input integer array
	 * @param rowIndex
	 *            the row index
	 */
	protected void implementGraphNetwork(final String[] inputIntegerArray, final int rowIndex) {
		final List<Node> rowNodes = new ArrayList<>();

		// Create new nodes
		for (int column = 1; column <= inputIntegerArray.length; column++) {
			final String inputInteger = inputIntegerArray[column - 1];
			final int nodeValue = Integer.parseInt(inputInteger);
			final Node currentNode = new Node(Util.createId(), nodeValue);

			if (column != 1) {
				final Node previousNode = rowNodes.get(column - 2);

				// if these nodes are different in values, edge is created between these which
				// it is pointed from node that has higher value
				this.addAdjacentNode(previousNode, currentNode);
			}

			if (rowIndex != 1) {
				// Get above node
				final Node aboveNode = getGraphNodeList().get(rowIndex - 2).get(column - 1);

				// if these nodes are different in values, edge is created between these which
				// it is pointed from node that has higher value
				this.addAdjacentNode(aboveNode, currentNode);
			}

			// Add a node to a row
			rowNodes.add(currentNode);
		}

		// Add a row to the graph
		getGraphNodeList().add(rowNodes);
	}
}
