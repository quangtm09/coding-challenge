package com.quang.skiinginsingapore;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;

public class GraphTest {

	private ClassLoader classLoader;

	private InputStream inputStream;

	private Graph graph;

	public GraphTest() {
	}

	@Before
	public void setUp() throws Exception {
		classLoader = this.getClass().getClassLoader();
		inputStream = classLoader.getResourceAsStream("test.txt");
		graph = new Graph();
	}

	@Test
	public void givenOneLineOfNumber_whenImplementGraphNetwork_ExpectOneRowNodeIsInserted() {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
			// 7 6 5 4 1
			final String lineOfNumbers = br.lines().skip(1).limit(1).collect(Collectors.toList()).get(0);

			graph.implementGraphNetwork(lineOfNumbers.split(Util._DELIMETER), 1);
		} catch (final Exception e) {
			e.printStackTrace();
		}

		final List<List<Node>> graphNodeList = graph.getGraphNodeList();
		assertNotNull(graphNodeList);
		assertEquals(graphNodeList.size(), 1);

		final List<Node> rowNode = graphNodeList.get(0);
		assertNotNull(rowNode);
		assertEquals(rowNode.size(), 5);
		assertEquals(rowNode.get(0).getNodeValue(), 7);
		assertEquals(rowNode.get(1).getNodeValue(), 6);
		assertEquals(rowNode.get(2).getNodeValue(), 5);
		assertEquals(rowNode.get(3).getNodeValue(), 4);
		assertEquals(rowNode.get(4).getNodeValue(), 1);

		assertNoChangesOnMaxLengthAndSteep();
	}

	@Test
	public void test_adjacentNodeMap_given2LinesOfNumbers() {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
			// 7 6 5 4 1
			// 2 5 9 3 1
			final List<String> linesOfNumbers = br.lines().skip(1).limit(2).collect(Collectors.toList());

			IntStream.range(0, linesOfNumbers.size()).forEach(index -> {
				graph.implementGraphNetwork(linesOfNumbers.get(index).split(Util._DELIMETER), index + 1);
			});
		} catch (final Exception e) {
			e.printStackTrace();
		}

		final List<List<Node>> graphNodeList = graph.getGraphNodeList();
		assertNotNull(graphNodeList);
		assertEquals(graphNodeList.size(), 2);

		final List<Node> firstLine = graphNodeList.get(0);
		assertNotNull(firstLine);
		assertEquals(firstLine.size(), 5);
		assertEquals(firstLine.get(0).getNodeValue(), 7);
		assertEquals(firstLine.get(1).getNodeValue(), 6);
		assertEquals(firstLine.get(2).getNodeValue(), 5);
		assertEquals(firstLine.get(3).getNodeValue(), 4);
		assertEquals(firstLine.get(4).getNodeValue(), 1);

		final List<Node> secondLine = graphNodeList.get(1);
		assertNotNull(secondLine);
		assertEquals(secondLine.size(), 5);
		assertEquals(secondLine.get(0).getNodeValue(), 2);
		assertEquals(secondLine.get(1).getNodeValue(), 5);
		assertEquals(secondLine.get(2).getNodeValue(), 9);
		assertEquals(secondLine.get(3).getNodeValue(), 3);
		assertEquals(secondLine.get(4).getNodeValue(), 1);

		// First row nodes: 7 -> 2 | 7 -> 6, 6 -> 5 (horizontally) | 6 -> 5
		// (vertically)
		// , 5 -> 4, 4 -> 1 | 4 -> 3
		// ==> 7 holds 2 adjacent nodes (2 & 6), 6 holds 2 (5 & 5), 5 holds 1 (4), 4
		// holds 2 (1 & 3)
		// Second row nodes: 5 -> 2, 9 -> 5 (horizontally) | 9 -> 5 (vertically) | 9
		// ->
		// 3, 3 -> 1
		// ==> 5 holds 1 (2), 9 holds 3 (5 & 5 & 3), 3 holds 1 (1)
		// ==> Total 7 nodes hold its adjacent nodes
		final Map<Integer, List<Node>> adjacentMap = graph.getAdjacentNodeMap();
		assertThat(adjacentMap.size(), is(7));

		// First row nodes adjacent map test
		// Node 7
		final int node7Id = firstLine.get(0).getNodeId();
		assertTrue(adjacentMap.containsKey(node7Id));
		final List<Node> node7Adjacent = adjacentMap.get(node7Id);
		assertEquals(node7Adjacent.size(), 2);
		node7Adjacent.forEach(adjacentNode -> {
			assertThat(adjacentNode.getNodeValue(), anyOf(is(2), is(6)));
		});

		// Node 6
		final int node6Id = firstLine.get(1).getNodeId();
		assertTrue(adjacentMap.containsKey(node6Id));
		final List<Node> node6Adjacent = adjacentMap.get(node6Id);
		assertEquals(node6Adjacent.size(), 2);
		node6Adjacent.forEach(adjacentNode -> {
			assertThat(adjacentNode.getNodeValue(), is(5));
		});

		// Node 5
		final int node5Id = firstLine.get(2).getNodeId();
		assertTrue(adjacentMap.containsKey(node5Id));
		final List<Node> node5Adjacent = adjacentMap.get(node5Id);
		assertEquals(node5Adjacent.size(), 1);
		assertThat(node5Adjacent.get(0).getNodeValue(), is(4));

		// Node 4
		final int node4Id = firstLine.get(3).getNodeId();
		assertTrue(adjacentMap.containsKey(node4Id));
		final List<Node> node4Adjacent = adjacentMap.get(node4Id);
		assertEquals(node4Adjacent.size(), 2);
		node4Adjacent.forEach(adjacentNode -> {
			assertThat(adjacentNode.getNodeValue(), anyOf(is(1), is(3)));
		});

		// Second row nodes adjacent map test
		// Node 5
		final int secondRowNode5Id = secondLine.get(1).getNodeId();
		assertTrue(adjacentMap.containsKey(secondRowNode5Id));
		final List<Node> secondRowNode5Adjacent = adjacentMap.get(secondRowNode5Id);
		assertEquals(secondRowNode5Adjacent.size(), 1);
		assertThat(secondRowNode5Adjacent.get(0).getNodeValue(), is(2));

		// Node 9
		final int node9Id = secondLine.get(2).getNodeId();
		assertTrue(adjacentMap.containsKey(node9Id));
		final List<Node> node9Adjacent = adjacentMap.get(node9Id);
		assertEquals(node9Adjacent.size(), 3);
		node9Adjacent.forEach(adjacentNode -> {
			assertThat(adjacentNode.getNodeValue(), anyOf(is(5), is(3)));
		});

		// Node 3
		final int node3Id = secondLine.get(3).getNodeId();
		assertTrue(adjacentMap.containsKey(node3Id));
		final List<Node> node3Adjacent = adjacentMap.get(node3Id);
		assertEquals(node3Adjacent.size(), 1);
		assertThat(node3Adjacent.get(0).getNodeValue(), is(1));

		assertNoChangesOnMaxLengthAndSteep();
	}

	@Test
	public void test_graphTraverse() {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
			// 7 6 5 4 1
			// 2 5 9 3 1
			// 6 4 1 2 1
			// 4 3 2 0 1
			final List<String> linesOfNumbers = br.lines().skip(1).collect(Collectors.toList());

			IntStream.range(0, linesOfNumbers.size()).forEach(index -> {
				graph.implementGraphNetwork(linesOfNumbers.get(index).split(Util._DELIMETER), index + 1);
			});
		} catch (final Exception e) {
			e.printStackTrace();
		}

		graph.dfsTraverse();

		// 7-6-5-4-3-2-0
		assertThat(graph.getMaxLength(), is(7));
		assertThat(graph.getMaxSteep(), is(7));
	}

	private void assertNoChangesOnMaxLengthAndSteep() {
		// Assert that max length & steep is still 0
		assertEquals(graph.getMaxLength(), 0);
		assertEquals(graph.getMaxSteep(), 0);
	}
}
