package org.agecraft.modjam4.tileentities;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import org.agecraft.modjam4.MJMessageTile;
import org.agecraft.modjam4.ModJam4;
import org.agecraft.modjam4.network.ElectricalNetwork;
import org.agecraft.modjam4.network.ElectricalNetworkRegistry;
import org.agecraft.modjam4.util.MJUtilClient;
import org.agecraft.modjam4.util.Tuple;
import org.lwjgl.util.vector.Vector3f;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityElectrical extends TileEntityExtended {

	public static class MessageTileElectrical extends MJMessageTile {
		
		public boolean[] isConnected = new boolean[ForgeDirection.VALID_DIRECTIONS.length];
		
		public MessageTileElectrical() {
			super();
		}
		
		public MessageTileElectrical(int x, int y, int z, boolean[] isConnected) {
			super(x, y, z);
			this.isConnected = isConnected;
		}
		
		@Override
		public void encodeTo(ByteBuf target) {
			super.encodeTo(target);
			for(int i = 0; i < isConnected.length; i++) {
				target.writeBoolean(isConnected[i]);
			}
		}
		
		@Override
		public void decodeFrom(ByteBuf source) {
			super.decodeFrom(source);
			for(int i = 0; i < isConnected.length; i++) {
				isConnected[i] = source.readBoolean();
			}
		}
		
		@Override
		@SideOnly(Side.CLIENT)
		public void handle() {
			World world = MJUtilClient.getWorld();
			TileEntityElectrical tile = (TileEntityElectrical) world.getTileEntity(x, y, z);
			tile.isConnected = isConnected;
		}
	}
	
	private Vector3f position;
	private ElectricalNetwork network;
	public boolean[] isConnected = new boolean[ForgeDirection.VALID_DIRECTIONS.length];
	
	@Override
	public Packet getDescriptionPacket() {
		return ModJam4.packetHandler.getPacketToClient(new MessageTileElectrical(xCoord, yCoord, zCoord, isConnected));
	}
	
	public Vector3f getPosition() {
		return position == null ? position = new Vector3f(xCoord, yCoord, zCoord) : position;
	}
	
	public ElectricalNetwork getNetwork() {
		return network;
	}
	
	public void setNetwork(ElectricalNetwork network) {
		if(this.network != null) {
			ElectricalNetworkRegistry.networks.remove(this.network.id);
		}
		this.network = network;
	}
	
	public void createNetwork() {
		if(network == null) {
			network = new ElectricalNetwork();
			ElectricalNetworkRegistry.registerNetwork(network);
			network.addNode(getPosition());
		}
	}
	
	public void mergeNetworks(TileEntityElectrical tile) {
		if(network.id != tile.getNetwork().id) {
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
	}
	
	public void updateNetwork() {
		ArrayList<Tuple<Vector3f, Vector3f>> edges = new ArrayList<Tuple<Vector3f, Vector3f>>();
		edges.addAll(network.edges);
		ArrayList<ElectricalNetwork> trees = new ArrayList<ElectricalNetwork>();
		for(Vector3f node : network) {
			ElectricalNetwork tree = new ElectricalNetwork();
			tree.addNode(node);
			trees.add(tree);
		}
		while(edges.size() > 0) {
			Tuple<Vector3f, Vector3f> edge = edges.get(0);
			int from = -1;
			int to = -1;
			for(int i = 0; i < trees.size(); i++) {
				if(trees.get(i) != null) {
					if(from == -1 && trees.get(i).containsNode(edge.value1)) {
						from = i;
					}
					if(to == -1 && trees.get(i).containsNode(edge.value2)) {
						to = i;
					}
					if(from != -1 && to != -1) {
						break;
					}
				}
			}
			if(from != -1 && to != -1 && from != to) {
				ElectricalNetwork treeFrom = trees.get(from);
				ElectricalNetwork treeTo = trees.get(to);
				for(Vector3f node : treeTo) {
					treeFrom.addNode(node);
				}
				for(Tuple<Vector3f, Vector3f> e : treeTo.edges) {
					treeFrom.addEdge(e.value1, e.value2);
				}
				treeFrom.addEdge(edge.value1, edge.value2);
				trees.remove(to);
			}
			edges.remove(0);
		}
		ArrayList<ElectricalNetwork> networks = new ArrayList<ElectricalNetwork>();
		for(ElectricalNetwork tree : trees) {
			ElectricalNetwork net = new ElectricalNetwork();
			for(Vector3f node : tree) {
				net.addNode(node);
				List<Vector3f> list = network.nodes.get(node);
				for(Vector3f n : list) {
					if(tree.containsNode(n)) {
						net.addEdge(node, n);
					}
				}
			}
			ElectricalNetworkRegistry.registerNetwork(net);
			networks.add(net);
		}
		trees = null;
		setNetwork(null);
		for(ElectricalNetwork net : networks) {
			for(Vector3f node : net) {
				((TileEntityElectrical) worldObj.getTileEntity((int) node.x, (int) node.y, (int) node.z)).setNetwork(net);
			}
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		setNetwork(ElectricalNetworkRegistry.getNetwork(nbt.getLong("NetworkID")));
		for(int i = 0; i < isConnected.length; i++) {
			isConnected[i] = nbt.getBoolean("IsConnected" + ForgeDirection.values()[i]);
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		if(network != null) {
			nbt.setLong("NetworkID", network.id);
		} else {
			nbt.setLong("NetworkID", -1L);
		}
		for(int i = 0; i < isConnected.length; i++) {
			nbt.setBoolean("IsConnected" + ForgeDirection.values()[i], isConnected[i]);
		}
	}
}
