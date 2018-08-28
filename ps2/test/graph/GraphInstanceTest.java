/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.Set;

import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 * 
 * <p>PS2 instructions: you MUST NOT add constructors, fields, or non-@Test
 * methods to this class, or change the spec of {@link #emptyInstance()}.
 * Your tests MUST only obtain Graph instances by calling emptyInstance().
s * Your tests MUST NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {
       
	// Testing strategy
    //   Fail-fast on null arguments is tested with each graph method;
	//   Partition our methods as following:
	//   Add: Element e exists; not exists;
    //   Set: Illegal argument first/second/third; Legal arguments; 
	//   Remove: Element e from: empty; containing e; non-containing e;
	//   Vertices: Add/remove a set of strings, checking only unique strings count;
    //   Source: target illegal; target legal and has 0/1/n sources;
    //   Target: source illegal; source legal and has 0/1/n targets;
    
    /**
     * Overridden by implementation-specific test classes.
     * 
     * @return a new empty graph of the particular implementation being tested
     */
    public abstract Graph<String> emptyInstance();
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testInitialVerticesEmpty() {
        // TODO you may use, change, or remove this test
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), emptyInstance().vertices());
    }
    
    @Test
    public void testNullFailFast(){}
    
    @Test
    public void testAddNewOnEmpty(){
    	Graph<String> graph = emptyInstance();
    	assertEquals("Adding new vertex modifies the graph",
                true, graph.add("Test"));
    }
    
    @Test
    public void testAddNew(){
    	Graph<String> graph = emptyInstance();
    	Set<String> extraStrings = VerticeSetGenerator.getExtraStrings();
    	for(String str : extraStrings) {
    		graph.add(str);
    	}
    	String newString = VerticeSetGenerator.getUniqueString();
    	assertEquals("Adding new vertex modifies the graph",
                true, graph.add(newString));
    }
    
    @Test
    public void testAddExisting(){
    	Graph<String> graph = emptyInstance();
    	graph.add("Test");
    	assertEquals("Every vertex is unique",
                false, graph.add("Test"));
    }
    
    @Test 
    public void testVerticesSize() {
    	Graph<String> graph = emptyInstance();
    	Set<String> extraStrings = VerticeSetGenerator.getExtraStrings();
    	assertEquals("Zero size in emptyInstance",
                0, graph.vertices().size());
    	for(String str : extraStrings) {
    		graph.add(str);
    	}
    	assertEquals("One size after adding one vertex",
    			extraStrings.size(), graph.vertices().size());
    	graph.add(extraStrings.iterator().next());
    	assertEquals("Adding exising vertice doesn't change graph size",
    			extraStrings.size(), graph.vertices().size());
    	for(String str : extraStrings) {
    		graph.remove(str);
    	}
    	assertEquals("Zero size after removing all vertices",
                0, graph.vertices().size());
    }

    @Test 
    public void testVerticesContains() {
    	Graph<String> graph = emptyInstance();
    	Set<String> extraStrings = VerticeSetGenerator.getExtraStrings();
    	for(String str : extraStrings) {
    		graph.add(str);
    	}
    	String test = extraStrings.iterator().next();
    	assertEquals("Vertices contain what was added",
                true, graph.vertices().contains(test));
    	graph.remove(test);
    	assertEquals("Vertices do not contain what was removed",
                false, graph.vertices().contains(test));
    }
    
    @Test
    public void testRemoveFromEmpty(){
    	Graph<String> graph = emptyInstance();
    	assertEquals("Removing something from empty graph does no modify it",
                false, graph.remove("Test"));
    }
    
    @Test
    public void testRemoveFromContaining(){
    	Graph<String> graph = emptyInstance();
    	graph.add("Test");
    	assertEquals("Removing what exist does modify the graph",
                true, graph.remove("Test"));
    }

    @Test
    public void testRemoveFromNotContaining(){
    	Graph<String> graph = emptyInstance();
    	graph.add("Test");
    	assertEquals("Removing what does not exist does not modify the graph",
                false, graph.remove("AnotherTest"));
    }
    
    @Test
    public void testSetIllegalSource(){}
    
    @Test
    public void testSetIllegalDest(){}

    @Test
    public void testSetIllegalWeight(){}
    
    @Test
    public void testSet(){}
    
    @Test
    public void testSourceIllegalTarget(){}
    
    @Test
    public void testSourceZero(){}

    @Test
    public void testSourceOne(){}
    
    @Test
    public void testSourceN(){}
    
    @Test
    public void testTargetIllegalSource(){}
    
    @Test
    public void testTargetZero(){}

    @Test
    public void testTargetOne(){}
    
    @Test
    public void testTargetN(){}
    
}
