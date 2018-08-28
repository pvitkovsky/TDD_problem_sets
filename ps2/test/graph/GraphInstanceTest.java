/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 * 
 * <p>PS2 instructions: you MUST NOT add constructors, fields, or non-@Test
 * methods to this class, or change the spec of {@link #emptyInstance()}.
 * Your tests MUST only obtain Graph instances by calling emptyInstance().
 * Your tests MUST NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {
    
    // Testing strategy
    //   Fail-fast on null arguments is tested with each graph method;
	//   Partition our methods as following:
	//   Add: Element e exists; not exists;
	//   Remove: Element e from: empty; containing e; non-containing e;
	//   Set: Illegal argument first/second/third; Legal arguments; 
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
    public void testAddNew(){}
    
    @Test
    public void testAddExisting(){}
    
    @Test
    public void testRemoveFromEmpty(){}
    
    @Test
    public void testRemoveFromContaining(){}
    
    @Test
    public void testRemoveFromNotContaining(){}
    
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
