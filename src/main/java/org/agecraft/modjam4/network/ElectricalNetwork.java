package org.agecraft.modjam4.network;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.agecraft.modjam4.util.Tuple;
import org.lwjgl.util.vector.Vector3f;

public class ElectricalNetwork implements Serializable, Iterable<Vector3f> {

	private static final long serialVersionUID = 1L;
	
	public long id;
	public HashMap<Vector3f, List<Vector3f>> nodes = new HashMap<Vector3f, List<Vector3f>>();
	public ArrayList<Tuple<Vector3f, Vector3f>> edges = new ArrayList<Tuple<Vector3f, Vector3f>>();
	
	public void addNode(Vector3f node) {
		if(!nodes.containsKey(node)) {
			nodes.put(node, new ArrayList<Vector3f>());
		}
	}

	public void removeNode(Vector3f node) {
		nodes.remove(node);
		ArrayList<Tuple<Vector3f, Vector3f>> removeEdges = new ArrayList<Tuple<Vector3f,Vector3f>>();
		for(Tuple<Vector3f, Vector3f> edge : edges) {
			if(edge.value1 == node || edge.value2 == node) {
				removeEdges.add(edge);
			}
		}
		edges.removeAll(removeEdges);
	}
	
	public boolean containsNode(Vector3f node) {
		return nodes.containsKey(node);
	}

	public void addEdge(Vector3f from, Vector3f to) {
		if(nodes.containsKey(from) && nodes.containsKey(to)) {
			nodes.get(from).add(to);
			nodes.get(to).add(from);
			edges.add(new Tuple<Vector3f, Vector3f>(from, to));
			edges.add(new Tuple<Vector3f, Vector3f>(to, from));
		}
	}
	
	public void removeEdge(Vector3f from, Vector3f to) {
		if(nodes.containsKey(from)) {
			nodes.get(from).remove(to);
		}
		if(nodes.containsKey(to)) {
			nodes.get(to).remove(from);
		}
		ArrayList<Tuple<Vector3f, Vector3f>> removeEdges = new ArrayList<Tuple<Vector3f, Vector3f>>();
		for(Tuple<Vector3f, Vector3f> edge : edges) {
			if((edge.value1 == from && edge.value2 == to) || (edge.value1 == to && edge.value2 == from)) {
				removeEdges.add(edge);
			}
		}
		edges.removeAll(removeEdges);
	}
	
	public boolean hasEdge(Vector3f from, Vector3f to) {
		return nodes.containsKey(from) && nodes.get(from).contains(to);
	}
	
	public List<Vector3f> edgesFrom(Vector3f... from) {
		if(from.length == 1) {
			return nodes.get(from[0]);
		} else {
			ArrayList<Vector3f> list = new ArrayList<Vector3f>();
			for(int i = 0; i < from.length; i++) {
				list.addAll(edgesFrom(from[i]));
			}
			return list;
		}
	}
	
	public List<Vector3f> edgesTo(Vector3f... to) {
		if(to.length == 1) {
			ArrayList<Vector3f> list = new ArrayList<Vector3f>();
			for(Vector3f from : nodes.keySet()) {
				if(nodes.get(from).contains(to)) {
					list.add(from);
				}
			}
			return list;
		} else {
			ArrayList<Vector3f> list = new ArrayList<Vector3f>();
			for(int i = 0; i < to.length; i++) {
				list.addAll(edgesTo(to[i]));
			}
			return list;
		}
	}
	
	public List<Vector3f> getPath(Vector3f from, Vector3f to) {
		ArrayList<Vector3f> list = new ArrayList<Vector3f>();
		list.add(from);
		return findPath(from, to, from, list);
	}
	
	private List<Vector3f> findPath(Vector3f from, Vector3f to, Vector3f current, List<Vector3f> path) {
		if(current == to) {
			return path;
		} else {
			List<Vector3f> edges = edgesFrom(current);
			for(Vector3f node : edges) {
				path.add(node);
				List<Vector3f> list = findPath(from, to, node, path);
				if(list != null) {
					return list;
				}
				path.remove(node);
			}
			return null;
		}
	}
	
	@Override
	public Iterator<Vector3f> iterator() {
		return nodes.keySet().iterator();
	}
	
	public int size() {
		return nodes.size();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Vector3f node : nodes.keySet()) {
			sb.append(node.toString());
			sb.append(": ");
			sb.append(nodes.get(node).toString());
			sb.append(",\n");
		}
		return sb.substring(0, sb.length() - 2);
	}
}
