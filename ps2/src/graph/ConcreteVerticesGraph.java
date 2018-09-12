/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import java.util.Set;
import static org.junit.Assert.*;

/**
 * An implementation of Graph. Vertices know about edges
 * 
 */
public class ConcreteVerticesGraph implements Graph<String> {

	private final List<Vertex> vertices = new ArrayList<>();

	// Abstraction function:
	// TODO
	// Representation invariant:
	//     vertices.count < ArrayList max;
	//     all vertices in vertices are non-empty;
	// Safety from rep exposure:
	// TODO

	// TODO constructor

	// TODO checkRep
	private void checkRep() {
		for (Vertex vt : vertices) {
			assertTrue(vt.initialized());
		}
	}

	/**
	 * Adds a graph with this name
	 */
	@Override
	public boolean add(String name) {
		Vertex vertex = getVertexMaybe(name);
		if (vertex.initialized())
			return false;
		vertex.initializeWithName(name);
		if (vertices.add(vertex))
			return true;
		else
			throw new RuntimeException("Rep full");
	}

	/**
	 * Creates a new vertex if no vertices with the name specified exist
	 * 
	 * @param name
	 *            vertex's name
	 * @return graph's vertex named name, if it exists; else returns empty vertex.
	 */
	private Vertex getVertexMaybe(String name) {
		for (Vertex vt : vertices) {
			if (vt.getName() == name)
				return vt;
		}
		return new Vertex();
	}

	/**
	 * @param name
	 * @return true if name is a vertex in this graph
	 */
	private boolean exists(String name) {
		return getVertexMaybe(name).initialized();
	}
	
	/**
	 * 
	 * @param name
	 * @return vertex with the name specified only if it exists
	 */
	private Vertex getVertexOnlyIfExists(String name) {
		if (!exists(name))
			throw new IllegalArgumentException("Vertex " + name + " not created");
		return getVertexMaybe(name);
	}

	/**
	 * @param name
	 * @return vertex with the name specified 
	 */
	private Vertex getVertexAnyway(String name) {
		if (!exists(name)) {
			add(name);
		}
		return getVertexMaybe(name);
	}

	

	/**
	 * Sets an edge between the vertices
	 */
	public int set(String source, String target, int weight) {
		checkRep();
		Vertex sourceToGet = getVertexAnyway(source);
		Vertex targetToGet = getVertexAnyway(target);
		return sourceToGet.set(targetToGet, weight);
	}

	@Override
	public boolean remove(String name) {
		Vertex vertex = getVertexMaybe(name);
		if (vertex.initialized()) 
			return vertices.remove(vertex);
		else return false;
	}

	@Override
	public Set<String> vertices() {
		Set<String> res = new HashSet<>();
		for (Vertex vt : vertices) {
			res.add(vt.getName());
		}
		return res;
	}

	@Override
	public Map<String, Integer> sources(String target) {
		Map<String, Integer> res = new HashMap<>();
		for (Vertex vt : vertices) {
			int weightToTarget = vt.weight(target);
			if (weightToTarget != 0)
				res.put(vt.getName(), weightToTarget);
		}
		return res;
	}

	@Override
	public Map<String, Integer> targets(String source) {
		Vertex vt = getVertexOnlyIfExists(source);
		Map<String, Integer> res = new HashMap<>();
		for (Entry<Vertex, Integer> e : vt.getEdgesOut().entrySet()) {
			res.put(e.getKey().getName(), e.getValue());
		}
		return res;
	}

	// TODO toString()

}

/**
 * TODO specification Mutable. This class is internal to the rep of
 * ConcreteVerticesGraph.
 * 
 */
class Vertex {

	private String name;
	private Map<Vertex, Integer> edgesOut;
	boolean isEmpty;
	
	// Abstraction function:
	//     isEmpty -> vertex exists or no;
	//     name, edgesOut -> edges directed from this vertex to others
	// Representation invariant:
	//     If isEmpty, name is null, and vice versa; edgesOut not null;
	// Safety from rep exposure:
	//     isEmpty is not exposed; name, edgesOut are mutable; edgesOut is hidden with Map interface

	/**
	 * Empty constructor creates empty vertex;
	 */
	Vertex() {
		setEdgesOut(new HashMap<Vertex, Integer>());
		isEmpty = true;
	}

	/**
	 * Constructor that creates initialized vertex
	 * @param name
	 */
	Vertex(String name) {
		this();
		initializeWithName(name);
	}
	
	void initializeWithName(String name) {
		this.setName(name);
		isEmpty = false;	
	}
	
	boolean initialized() {
		return !isEmpty;
	}

	boolean checkRep() {
		if (isEmpty && name!=null) return false;
		if (!isEmpty && name == null) return false;
		if (edgesOut  == null) return false;
		return true;
	}
	
	String getName() {
		return name;
	}

	void setName(String name) {
		this.name = name;
	}
	
	Map<Vertex, Integer> getEdgesOut() {
		return edgesOut;
	}

	void setEdgesOut(Map<Vertex, Integer> edgesOut) {
		this.edgesOut = edgesOut;
	}

	/**
	 * 
	 * @param target
	 *            name of vertex
	 * @return weight from this to that vertex
	 */
	int weight(String target) {
		for (Vertex vt : getEdgesOut().keySet()) {
			if (vt.getName() == target) {
				return getEdgesOut().get(vt);
			}
		}
		return 0;
	}

	/**
	 * @param target
	 * @param weight
	 * @return
	 */
	int set(Vertex target, Integer weight) {
		if (!getEdgesOut().keySet().contains(target)) {
			getEdgesOut().put(target, weight.intValue());
			return 0;
		}
		return getEdgesOut().put(target, weight.intValue());
	}

	public String toString() {
		return "Vertex " + getName() + " has " + getEdgesOut().size() + " Edges";
	}
}
