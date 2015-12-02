package net.urk.steblina.akkatest;

import java.io.Serializable;

public class ID  implements Serializable{

	private static final long serialVersionUID = 4832171601657005446L;
	private int id;
	private long operations;
	

	public ID(int id, long operations) {
		this.id = id;
		this.operations = operations;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getOperations() {
		return operations;
	}
	public void setOperations(long operations) {
		this.operations = operations;
	}
}
