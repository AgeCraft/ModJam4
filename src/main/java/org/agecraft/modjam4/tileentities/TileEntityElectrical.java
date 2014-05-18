package org.agecraft.modjam4.tileentities;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import org.agecraft.modjam4.MJMessageTile;
import org.agecraft.modjam4.ModJam4;
import org.agecraft.modjam4.network.EnergyNetwork;
import org.agecraft.modjam4.network.EnergyNetworkRegistry;
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
			world.markBlockForUpdate(x, y, z);
		}
	}
	
	private Vector3f position;
	private EnergyNetwork network;
	public boolean[] isConnected = new boolean[ForgeDirection.VALID_DIRECTIONS.length];
	public double[] energy = new double[ForgeDirection.values().length];
	public double energyTotal;
	public boolean energyRequiresUpdate;
	
	@Override
	public Packet getDescriptionPacket() {
		return ModJam4.packetHandler.getPacketToClient(new MessageTileElectrical(xCoord, yCoord, zCoord, isConnected));
	}
	
	@Override
	public boolean canUpdate() {
		return true;
	}
	
	@Override
	public void updateEntity() {
		if(energyRequiresUpdate) {
			List<Vector3f> edges = network.edgesFrom(getPosition());
			if(edges != null) {
				HashMap<ForgeDirection, TileEntityElectrical> validEdges = new HashMap<ForgeDirection, TileEntityElectrical>();
				for(int i = 0; i < energy.length; i++) {
					if(energy[i] > 0) {
						ForgeDirection direction = ForgeDirection.values()[i];
						Vector3f blockedEdge = new Vector3f(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ);
						validEdges.clear();
						double[] maxEnergy = new double[ForgeDirection.VALID_DIRECTIONS.length];
						double maxEnergyTotal = 0.0D;
					
						for(Vector3f edge : edges) {
							if(edge != blockedEdge) {
								TileEntityElectrical tile = (TileEntityElectrical) worldObj.getTileEntity((int) edge.x, (int) edge.y, (int) edge.z);
								ForgeDirection newDirection = getDirectionFromPosition(((int) edge.x) - xCoord, ((int) edge.y) - yCoord, ((int) edge.z) - zCoord);
								maxEnergy[newDirection.ordinal()] = tile.getMaxEnergy(newDirection);
								maxEnergyTotal += maxEnergy[newDirection.ordinal()];
								if(maxEnergy[newDirection.ordinal()] > 0.0D) {
									validEdges.put(newDirection, tile);
								}
							}
						}
						
						if(maxEnergyTotal > 0.0D) {
							ArrayList<ForgeDirection> edgesLeft = new ArrayList<ForgeDirection>();
							edgesLeft.addAll(validEdges.keySet());
							ArrayList<ForgeDirection> removedEdges = new ArrayList<ForgeDirection>();
							double[] energyPerEdge = new double[ForgeDirection.VALID_DIRECTIONS.length];
							double energyToDivide = energy[i];
							double energyDivided = energy[i] / validEdges.size();
							while(energyToDivide > 0.0D && edgesLeft.size() > 0) {
								energyDivided = energy[i] / edgesLeft.size();
								for(ForgeDirection newDirection : edgesLeft) {
									if(energyDivided > maxEnergy[newDirection.ordinal()]) {
										energyPerEdge[newDirection.ordinal()] = maxEnergy[newDirection.ordinal()];
										energyToDivide -= maxEnergy[newDirection.ordinal()];
										maxEnergy[newDirection.ordinal()] = 0.0D;
									} else {
										energyPerEdge[newDirection.ordinal()] = energyDivided;
										energyToDivide -= maxEnergy[newDirection.ordinal()];
										maxEnergy[newDirection.ordinal()] -= energyDivided;
									}
								}
								for(ForgeDirection removedEdge : removedEdges) {
									edgesLeft.remove(removedEdge);
								}
							}
							double energyRemoved = 0.0D;
							for(ForgeDirection edge : validEdges.keySet()) {
								validEdges.get(edge).addEnergy(edge, energyPerEdge[edge.ordinal()]);
								energyRemoved += energyPerEdge[edge.ordinal()];
							}
							energy[i] -= energyRemoved;
							energyTotal -= energyRemoved;
						}
					}
				}
			}
			energyRequiresUpdate = energyTotal > 0.0D;
		}
	}
	
	public ForgeDirection getDirectionFromPosition(int deltaX, int deltaY, int deltaZ) {
		for(int i = 0; i < ForgeDirection.VALID_DIRECTIONS.length; i++) {
			ForgeDirection direction = ForgeDirection.VALID_DIRECTIONS[i];
			if(direction.offsetX == deltaX && direction.offsetY == deltaY && direction.offsetZ == deltaZ) {
				return direction;
			}
		}
		return ForgeDirection.UNKNOWN;
	}
	
	public Vector3f getPosition() {
		return position == null ? position = new Vector3f(xCoord, yCoord, zCoord) : position;
	}
	
	public EnergyNetwork getNetwork() {
		return network;
	}
	
	public void setNetwork(EnergyNetwork network) {
		if(this.network != null) {
			EnergyNetworkRegistry.networks.remove(this.network.id);
		}
		this.network = network;
	}
	
	public void createNetwork() {
		if(network == null) {
			network = new EnergyNetwork();
			EnergyNetworkRegistry.registerNetwork(network);
			network.addNode(getPosition());
		}
	}
	
	public void mergeNetworks(TileEntityElectrical tile) {
		if(network.id != tile.getNetwork().id) {
			EnergyNetwork otherNetwork = tile.getNetwork();
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
		ArrayList<EnergyNetwork> trees = new ArrayList<EnergyNetwork>();
		for(Vector3f node : network) {
			EnergyNetwork tree = new EnergyNetwork();
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
				EnergyNetwork treeFrom = trees.get(from);
				EnergyNetwork treeTo = trees.get(to);
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
		ArrayList<EnergyNetwork> networks = new ArrayList<EnergyNetwork>();
		for(EnergyNetwork tree : trees) {
			EnergyNetwork net = new EnergyNetwork();
			for(Vector3f node : tree) {
				net.addNode(node);
				List<Vector3f> list = network.nodes.get(node);
				for(Vector3f n : list) {
					if(tree.containsNode(n)) {
						net.addEdge(node, n);
					}
				}
			}
			EnergyNetworkRegistry.registerNetwork(net);
			networks.add(net);
		}
		trees = null;
		setNetwork(null);
		for(EnergyNetwork net : networks) {
			for(Vector3f node : net) {
				TileEntityElectrical tile = (TileEntityElectrical) worldObj.getTileEntity((int) node.x, (int) node.y, (int) node.z);
				if(tile != null) {
					tile.setNetwork(net);
				}
			}
		}
	}
	
	public void addEnergy(ForgeDirection direction, double energy) {
		this.energy[direction.ordinal()] += energy;
		energyTotal += energy;
		energyRequiresUpdate = true;
	}
	
	public double getMaxEnergy(ForgeDirection direction) {
		return getMaxEnergy() - energyTotal;
	}
	
	public double getMaxEnergy() {
		return 100.0D;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		setNetwork(EnergyNetworkRegistry.getNetwork(nbt.getLong("NetworkID")));
		energyTotal = 0.0D;
		for(int i = 0; i < ForgeDirection.VALID_DIRECTIONS.length; i++) {
			isConnected[i] = nbt.getBoolean("IsConnected" + ForgeDirection.values()[i]);
			energy[i] = nbt.getDouble("Energy" + ForgeDirection.values()[i]);
			energyTotal += energy[i];
		}
		energyRequiresUpdate = energyTotal > 0.0D;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		if(network != null) {
			nbt.setLong("NetworkID", network.id);
		} else {
			nbt.setLong("NetworkID", -1L);
		}
		for(int i = 0; i < ForgeDirection.VALID_DIRECTIONS.length; i++) {
			nbt.setBoolean("IsConnected" + ForgeDirection.values()[i], isConnected[i]);
			nbt.setDouble("Energy" + ForgeDirection.values()[i], energy[i]);
		}
	}
}
