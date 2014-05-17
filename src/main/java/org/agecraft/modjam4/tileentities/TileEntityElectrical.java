package org.agecraft.modjam4.tileentities;

import java.util.List;

import net.minecraft.nbt.NBTTagCompound;

import org.agecraft.modjam4.network.ElectricalNetwork;
import org.agecraft.modjam4.network.ElectricalNetworkRegistry;
import org.lwjgl.util.vector.Vector3f;

public class TileEntityElectrical extends TileEntityExtended {

	private Vector3f position;
	private ElectricalNetwork network;
	
	public Vector3f getPosition() {
		return position == null ? position = new Vector3f(xCoord, yCoord, zCoord) : position;
	}
	
	public ElectricalNetwork getNetwork() {
		return network;
	}
	
	public void setNetwork(ElectricalNetwork network) {
		this.network = network;
	}
	
	public void createNetwork() {
		network = new ElectricalNetwork();
		ElectricalNetworkRegistry.registerNetwork(network);
		network.addNode(getPosition());
	}
	
	public void mergeNetworks(TileEntityElectrical tile) {
		ElectricalNetwork otherNetwork = tile.getNetwork();
		for(Vector3f node : otherNetwork) {
			network.addNode(node);
			List<Vector3f> list = otherNetwork.nodes.get(node);
			for(Vector3f n : list) {
				network.addEdge(node, n);
			}
		}
		tile.setNetwork(network);
		otherNetwork = null;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		setNetwork(ElectricalNetworkRegistry.getNetwork(nbt.getLong("NetworkID")));
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		if(network != null) {
			nbt.setLong("NetworkID", network.id);
		} else {
			nbt.setLong("NetworkID", -1L);
		}
	}
}
