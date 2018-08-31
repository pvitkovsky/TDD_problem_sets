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
import static org.junit.Assert.*;
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
    //	 all vertices in vertices are non-empty;
    // Safety from rep exposure:
    //   TODO
    
    // TODO constructor
    
    // TODO checkRep
    
    @Override public boolean add(String name) {
    	Vertex vertex =  getVertex(name);
    	if (vertex.exists()) return false;
    	vertex.setName(name);
    	if (vertices.add(vertex)) return true;
    	else throw new RuntimeException("Rep full");
     }
    
    private Vertex getVertex(String name) {
    	for (Vertex vt : vertices){
    		if (vt.name == name) return vt;
    	}
    	Vertex res = new Vertex(); 
    	return res;
    }
    
    ///rewrite, NPE, unclear
    public int set(String source, String target, int weight) {
    	Vertex sourceToGet = getVertex(source);
    	Vertex targetToGet = getVertex(target);
    	if (sourceToGet.exists() && targetToGet.exists()) return sourceToGet.set(targetToGet, weight);
    	if (sourceToGet.exists() && !targetToGet.exists()){
    		add(target);
    		targetToGet = getVertex(target);
    		assertTrue(sourceToGet.exists());
    		assertTrue(targetToGet.exists());
    		return sourceToGet.set(targetToGet, weight);
    	} else {
    		throw new IllegalArgumentException("Source "+source+" Not exists");
    	}
    }

    /**
     * 
     * @param source
     * @param thisVertex
     * @return
     */
	boolean vertexCreated(String source, Vertex thisVertex) {
		return  (thisVertex.name == source) ? true : false;
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
    	this();
    	setName(name);
    }
    
    public void setName(String name2) {
    	this.name = name2;
    	isEmpty = false;
	}
    
    /**
     * Empty constructor creates empty vertex;
     */
	Vertex(){
		edgesOut = new HashMap<Vertex, Integer>();
    	isEmpty = true;
    }
    
    boolean exists(){
    	return !isEmpty;
    }
    
    // TODO checkRep
    
    // TODO methods
    int set(Vertex target, Integer weight){
    	if (edgesOut.isEmpty()) {
    		edgesOut.put(target, weight.intValue());
    		return 0;
    	}
    	return edgesOut.put(target, weight.intValue());
    }
    
    public String toString(){
    	return "Vertex "+name+" has "+edgesOut.size()+" Edges";
    }
}
