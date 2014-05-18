package org.agecraft.modjam4.network;

import java.util.HashMap;
import java.util.Random;

public class EnergyNetworkRegistry {

	private static Random random = new Random();
	public static HashMap<Long, EnergyNetwork> networks = new HashMap<Long, EnergyNetwork>();
	
	public static EnergyNetwork getNetwork(long networkID) {
		return networks.get(networkID);
	}
	
	public static void registerNetwork(EnergyNetwork network) {
		network.id = generateID();
		networks.put(network.id, network);
	}
	
	public static long generateID() {
		long id = random.nextLong();
		while(networks.containsKey(id)) {
			id = random.nextLong();
		}
		return id;
	}
}
