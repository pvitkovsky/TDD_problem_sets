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

import javax.xml.transform.Source;

import org.junit.runner.manipulation.Sortable;

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
	// vertices.count < ArrayList max;
	// all vertices in vertices are non-empty;
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
			if (vt.name == name)
				return vt;
		}
		return new Vertex();
	}

	/**
	 * @param name
	 * @return vertex with the name specified only if it exists
	 */
	private Vertex getVertexOnlyIfExists(String name) {
		if (notExists(name))
			throw new IllegalArgumentException("Vertex " + name + " not created");
		return getVertexMaybe(name);
	}

	/**
	 * @param name
	 * @return vertex with the name specified 
	 */
	private Vertex getVertexAnyway(String name) {
		if (notExists(name)) {
			add(name);
		}
		return getVertexMaybe(name);
	}

	/**
	 * @param name
	 * @return true if name is not a vertex in this graph
	 */
	private boolean notExists(String name) {
		return !getVertexMaybe(name).initialized();
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
	public boolean remove(String vertex) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public Set<String> vertices() {
		Set<String> res = new HashSet<>();
		for (Vertex vt : vertices) {
			res.add(vt.name);
		}
		return res;
	}

	@Override
	public Map<String, Integer> sources(String target) {
		Map<String, Integer> res = new HashMap<>();
		for (Vertex vt : vertices) {
			int weightToTarget = vt.weight(target);
			if (weightToTarget != 0)
				res.put(vt.name, weightToTarget);
		}
		return res;
	}

	@Override
	public Map<String, Integer> targets(String source) {
		Vertex vt = getVertexOnlyIfExists(source);
		Map<String, Integer> res = new HashMap<>();
		for (Entry<Vertex, Integer> e : vt.edgesOut.entrySet()) {
			res.put(e.getKey().name, e.getValue());
		}
		return res;
	}

	// TODO toString()

}

/**
 * TODO specification Mutable. This class is internal to the rep of
 * ConcreteVerticesGraph.
 * 
 * <p>
 * PS2 instructions: the specification and implementation of this class is up to
 * you.
 */
class Vertex {

	// TODO fields
	String name;
	boolean isEmpty;
	Map<Vertex, Integer> edgesOut;

	// Abstraction function:
	// edgesOut -> edges directed from this vertex to others
	// Representation invariant:
	// TODO
	// Safety from rep exposure:
	// TODO //is it ok to expose rep of inner class to the host class?

	/**
	 * Empty constructor creates empty vertex;
	 */
	Vertex() {
		edgesOut = new HashMap<Vertex, Integer>();
		isEmpty = true;
	}

	void initializeWithName(String name) {
		this.name = name;
		isEmpty = false;
	}

	Vertex(String name) {
		this();
		initializeWithName(name);
	}

	boolean initialized() {
		return !isEmpty;
	}

	// TODO checkRep

	/**
	 * 
	 * @param target
	 *            name of vertex
	 * @return weight from this to that vertex
	 */
	int weight(String target) {
		for (Vertex vt : edgesOut.keySet()) {
			if (vt.name == target) {
				return edgesOut.get(vt);
			}
		}
		return 0;
	}

	int set(Vertex target, Integer weight) {
		if (!edgesOut.keySet().contains(target)) {
			edgesOut.put(target, weight.intValue());
			return 0;
		}
		return edgesOut.put(target, weight.intValue());
	}

	public String toString() {
		return "Vertex " + name + " has " + edgesOut.size() + " Edges";
	}
}
