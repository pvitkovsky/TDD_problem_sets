/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Graph.
 * Edges know Vertices and the route of moving;
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph implements Graph<String> {
    
    private final Set<String> vertices = new HashSet<>();
    private final List<Edge> edges = new ArrayList<>();
    
    // Abstraction function:
    //   TODO
    // Representation invariant:
    //   TODO
    // Safety from rep exposure:
    //   TODO
    
    // TODO constructor
    
    // TODO checkRep
    
    @Override public boolean add(String name) {
    	if (exists(name)) return false;
    	return vertices.add(name);
    }
    
    private boolean exists(String name) {
    	return vertices.contains(name);
    }
    
    @Override public int set(String source, String target, int weight) {
    	if (!exists(source)) throw new IllegalArgumentException("Source not found");
    	if (!exists(target)) throw new IllegalArgumentException("Target not found");
    	// if can't find edge with this source and target;
    		// if weigth is 0; 
    		// else create edge; 
    		// return 0;
    	// else remember edge's oldweight
    		// if weight is 0 set edge's weigth to 0 // or kill edge??
    		// else set edge's weight to weight
    		// return oldweight;
    	if(weight != 0) {
       		//edges.add(new Edge(source, target, weight)); 
    	} else {
    		//edges.remove(find this edge)
    	}
  
    	throw new RuntimeException("not implemented");
    }
    
    @Override public boolean remove(String name) {
        return vertices.remove(name);
    }
    
    @Override public Set<String> vertices() {
        throw new RuntimeException("not implemented");
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
 * Immutable.
 * This class is internal to the rep of ConcreteEdgesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Edge {
    
    // TODO fields
	final int weight;
	final String source;
	final String target;
    
    // Abstraction function:
    //   TODO
    // Representation invariant:
    //   TODO
    // Safety from rep exposure:
    //   TODO
    
    public String getSource() {
		return source;
	}

	public String getTarget() {
		return target;
	}

	// TODO constructor
	Edge(String source, String target,int weight){
		this.source = source;
		this.target = target;
		this.weight = weight;
		
	}

	int getWeight() {
		return weight;
	}
    
    // TODO checkRep
    
    // TODO methods
    
    // TODO toString()
    
}
