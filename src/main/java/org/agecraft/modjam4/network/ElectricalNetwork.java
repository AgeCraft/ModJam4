package org.agecraft.modjam4.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

public class ElectricalNetwork {

	public static HashMap<Vector3f, List<Vector3f>> nodes = new HashMap<Vector3f, List<Vector3f>>();

	public ElectricalNetwork() {
	}

	public void addNode(Vector3f node) {
		nodes.put(node, new ArrayList<Vector3f>());
	}

	public void removeNode(Vector3f node) {
		nodes.remove(node);
	}

	public void addEdge(Vector3f from, Vector3f to) {
		if(nodes.containsKey(from) && nodes.containsKey(to)) {
			nodes.get(from).add(to);
			nodes.get(to).add(from);
		}
	}
	
	public void removeEdge(Vector3f from, Vector3f to) {
		if(nodes.containsKey(from)) {
			nodes.get(from).remove(to);
		}
		if(nodes.containsKey(to)) {
			nodes.get(to).remove(from);
		}
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
}
