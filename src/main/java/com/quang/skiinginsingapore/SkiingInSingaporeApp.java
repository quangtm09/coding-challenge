package com.quang.skiinginsingapore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * The Class SkiingInSingaporeApp.
 */
public class SkiingInSingaporeApp {

	/** The Constant _1000X1000_MAP. */
	private static final String _1000X1000_MAP = "1000x1000.txt";

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void main(final String[] args) throws IOException {
		final long start = System.currentTimeMillis();
		final ClassLoader classLoader = SkiingInSingaporeApp.class.getClassLoader();
		final InputStream inputStream = classLoader.getResourceAsStream(_1000X1000_MAP);

		try {
			// Initializing the graph
			final Graph graph = new Graph();
			final List<String> result = readFromInputStream(inputStream);
			IntStream.range(0, result.size()).forEach(index -> {
				graph.implementGraphNetwork(result.get(index).split(Util._DELIMETER), index + 1);
			});

			// Traverse all nodes of the graph
			graph.dfsTraverse();
			final long end = System.currentTimeMillis();

			System.out.println("App finished running in " + (end - start) + " ms");
			System.out.println(
					"Send your CV and code to: " + graph.getMaxLength() + graph.getMaxSteep() + "@redmart.com");
		} catch (final IOException e) {
			e.printStackTrace();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Read from input stream.
	 *
	 * @param inputStream
	 *            the input stream
	 * @return the list
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private static List<String> readFromInputStream(final InputStream inputStream) throws IOException {
		List<String> result;
		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
			result = br.lines().skip(1).collect(Collectors.toList());
		}
		return result;
	}
}
