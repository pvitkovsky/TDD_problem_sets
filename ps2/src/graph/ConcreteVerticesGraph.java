/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Graph.
 * Vertices know about edges
 * 
 */
public class ConcreteVerticesGraph implements Graph<String> {
    
    private final List<Vertex> vertices = new ArrayList<>();
    
    // Abstraction function:
    //   TODO
    // Representation invariant:
    //   vertices.count < ArrayList max;
    // Safety from rep exposure:
    //   TODO
    
    // TODO constructor
    
    // TODO checkRep
    
    @Override public boolean add(String vertex) {
    	return getVertex(vertex).exists();
    }
    
    private Vertex getVertex(String vertex) {
    	for (Vertex vt : vertices){
    		if (vt.name == vertex) return vt;
    	}
    	Vertex res = new Vertex(vertex); 
    	if(vertices.add(res)) return res;
    	else return new Vertex(); 
    }
    
    public int set(String source, String target, int weight) {
    	Vertex vtSource = new Vertex();
    	Vertex vtTarget = new Vertex();
    	boolean sourceCreated = false;
    	boolean targetCreated = false;
    	for (Vertex thisVertex : vertices){
    		sourceCreated = sourceCreated || vertexCreated(source, thisVertex, vtSource);
    		targetCreated = targetCreated || vertexCreated(target, thisVertex, vtTarget);
    	}
    	if (sourceCreated && targetCreated) return vtSource.set(vtTarget, weight);
    	if (sourceCreated){
    		vtTarget = getVertex(target);
    		return vtSource.set(vtTarget, weight);
    	} else {
    		throw new IllegalArgumentException("Source "+source+" Not exists");
    	}
    }

	boolean vertexCreated(String source, Vertex vt, Vertex create) {
		if (vt.name == source){
			create = vt;
			return true;
		}
		return false;
	}
    
    @Override public boolean remove(String vertex) {
        throw new RuntimeException("not implemented");
    }
    
    @Override public Set<String> vertices() {
    	Set<String> res = new HashSet<>();
        for(Vertex vt : vertices){
        	res.add(vt.name);
        }
        return res;
    }
    
    @Override public Map<String, Integer> sources(String target) {
        throw new RuntimeException("not implemented");
    }
    
    @Override public Map<String, Integer> targets(String source) {
        throw new RuntimeException("not implemented");
    }
    
    // TODO toString()
    
}

/**
 * TODO specification
 * Mutable.
 * This class is internal to the rep of ConcreteVerticesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Vertex {
    
    // TODO fields
	String name;
	boolean isEmpty;
    Map<Vertex, Integer> edgesOut;
	// Abstraction function:
    //   edgesOut -> edges directed from this vertex to others
    // Representation invariant:
    //   TODO
    // Safety from rep exposure:
    //   TODO
    
    // TODO constructor
    Vertex(String name){
    	this.name = name;
    	edgesOut = new HashMap<Vertex, Integer>();
    	isEmpty = false;
    }
    
    Vertex(){
    	isEmpty = true;
    }
    
    boolean exists(){
    	return !isEmpty;
    }
    
    // TODO checkRep
    
    // TODO methods
    int set(Vertex target, Integer weight){
    	return edgesOut.put(target, weight);
    }
    
    // TODO toString()
}
