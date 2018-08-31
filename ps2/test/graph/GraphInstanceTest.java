/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 * 
 * <p>
 * PS2 instructions: you MUST NOT add constructors, fields, or non-@Test methods
 * to this class, or change the spec of {@link #emptyInstance()}. Your tests
 * MUST only obtain Graph instances by calling emptyInstance(). s * Your tests
 * MUST NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {

	// Testing strategy
	// Fail-fast on null arguments is tested with each graph method;
	// Partition our methods as following:
	// Add: Element e exists; not exists;
	// Set: Illegal argument first/second/third; Legal arguments;
	// Remove: Element e from: empty; containing e; non-containing e;
	// Vertices: Add/remove a set of strings, checking only unique strings count;
	// Source: target illegal; target legal and has 0/1/n sources;
	// Target: source illegal; source legal and has 0/1/n targets;

	/**
	 * Overridden by implementation-specific test classes.
	 * 
	 * @return a new empty graph of the particular implementation being tested
	 */
	public abstract Graph<String> emptyInstance();

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	@Test
	public void testInitialVerticesEmpty() {
		assertEquals("expected new graph to have no vertices", Collections.emptySet(), emptyInstance().vertices());
	}

	@Test
	public void testNullFailFast() {
	}

	@Test
	public void testAddNewOnEmpty() {
		Graph<String> graph = emptyInstance();
		assertEquals("Adding new vertex modifies the graph", true, graph.add("Test"));
	}

	@Test
	public void testAddNew() {
		Graph<String> graph = emptyInstance();
		Set<String> extraStrings = TestDataGenerator.getExtraStrings();
		for (String str : extraStrings) {
			graph.add(str);
		}
		String newString = TestDataGenerator.getUniqueString();
		assertEquals("Adding new vertex modifies the graph", true, graph.add(newString));
	}

	@Test
	public void testAddExisting() {
		Graph<String> graph = emptyInstance();
		graph.add("Test");
		assertEquals("Every vertex is unique", false, graph.add("Test"));
	}

	@Test
	public void testVerticesSize() {
		Graph<String> graph = emptyInstance();
		Set<String> extraStrings = TestDataGenerator.getExtraStrings();
		assertEquals("Zero size in emptyInstance", 0, graph.vertices().size());
		for (String str : extraStrings) {
			graph.add(str);
		}
		assertEquals("One size after adding one vertex", extraStrings.size(), graph.vertices().size());
		graph.add(extraStrings.iterator().next());
		assertEquals("Adding exising vertice doesn't change graph size", extraStrings.size(), graph.vertices().size());
		for (String str : extraStrings) {
			graph.remove(str);
		}
		assertEquals("Zero size after removing all vertices", 0, graph.vertices().size());
	}

	@Test
	public void testVerticesContains() {
		Graph<String> graph = emptyInstance();
		Set<String> extraStrings = TestDataGenerator.getExtraStrings();
		for (String str : extraStrings) {
			graph.add(str);
		}
		String test = extraStrings.iterator().next();
		assertEquals("Vertices contain what was added", true, graph.vertices().contains(test));
		graph.remove(test);
		assertEquals("Vertices do not contain what was removed", false, graph.vertices().contains(test));
	}

	@Test
	public void testRemoveFromEmpty() {
		Graph<String> graph = emptyInstance();
		assertEquals("Removing something from empty graph does no modify it", false, graph.remove("Test"));
	}

	@Test
	public void testRemoveFromContaining() {
		Graph<String> graph = emptyInstance();
		graph.add("Test");
		assertEquals("Removing what exist does modify the graph", true, graph.remove("Test"));
	}

	@Test
	public void testRemoveFromNotContaining() {
		Graph<String> graph = emptyInstance();
		graph.add("Test");
		assertEquals("Removing what does not exist does not modify the graph", false, graph.remove("AnotherTest"));
	}

	@Test
	public void testSet() {
		Graph<String> graph = emptyInstance();
		graph.add("Alpha");
		graph.add("Beta");
		graph.set("Alpha", "Beta", Integer.valueOf(0));
		for (int i = 0; i <= 8; i++) {
			assertEquals("Graph.set returns previous weight", i, graph.set("Alpha", "Beta", Integer.valueOf(i + 1)));
		}
	}

	@Test
	public void testSetNewDest() {
		Graph<String> graph = emptyInstance();
		graph.add("Alpha");
		graph.set("Alpha", "Beta", 1);
		assertEquals("New vertice added", 2, graph.vertices().size());
	}

	
	@Test
	public void testSourceIllegalTarget() {
		Graph<String> graph = emptyInstance();
		graph.add("Alpha");
		assertEquals("Empty map", Collections.EMPTY_MAP, graph.sources("Alpha"));
	}

	@Test
	public void testSourceOne() {
		Map<String, Integer> resMap = new HashMap<>();
		String firstVertex = "Alpha";
		String secondVertex = "Beta";
		int weightFirstToSecond = 1;
		resMap.put(secondVertex, weightFirstToSecond);
		Graph<String> graph = emptyInstance();
		graph.add(firstVertex);
		graph.set(firstVertex, secondVertex, weightFirstToSecond);
		assertEquals("One source", resMap, graph.sources(secondVertex));
	}

	@Test
	public void testSourceN() {
		Map<String, Integer> resMap = TestDataGenerator.getStringIntMap();
		Set<String> sources = resMap.keySet();
		Graph<String> graph = emptyInstance();
		String oneTarget = "Zeta";
		graph.add(oneTarget);
		for(String str : sources) {
			graph.set(str, oneTarget, resMap.get(str));
		}
		assertEquals("Many sources", resMap, graph.sources(oneTarget));
	}
	
	@Test
	public void testTargetIllegalSource() {
		Graph<String> graph = emptyInstance();
		graph.add("Zeta");
		assertEquals("Empty map", Collections.EMPTY_MAP, graph.targets("Zeta"));
	}


	@Test
	public void testTargetOne() {
		Map<String, Integer> resMap = new HashMap<>();
		String firstVertex = "Alpha";
		String secondVertex = "Beta";
		int weightFirstToSecond = 1;
		resMap.put(secondVertex, weightFirstToSecond);
		Graph<String> graph = emptyInstance();
		graph.add(firstVertex);
		graph.set(firstVertex, secondVertex, weightFirstToSecond);
		assertEquals("One target", resMap, graph.targets(firstVertex));
	}

	@Test
	public void testTargetN() {
		Map<String, Integer> resMap = TestDataGenerator.getStringIntMap();
		Set<String> targets = resMap.keySet();
		Graph<String> graph = emptyInstance();
		String oneSource = "Alpha";
		graph.add(oneSource);
		for(String str : targets) {
			graph.set(oneSource, str, resMap.get(str));
		}
		assertEquals("Many targets", resMap, graph.targets(oneSource));
	}

}
