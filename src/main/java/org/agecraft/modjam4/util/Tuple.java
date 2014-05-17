package org.agecraft.modjam4.util;

import java.io.Serializable;

public class Tuple<I, J> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public I value1;
	public J value2;
	
	public Tuple(I value1, J value2) {
		this.value1 = value1;
		this.value2 = value2;
	}
	
	@Override
	public String toString() {
		return "[" + value1 + ", " + value2 + "]";
	}
}
