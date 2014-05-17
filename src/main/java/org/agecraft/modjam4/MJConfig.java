package org.agecraft.modjam4;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class MJConfig {

	public Configuration config;
	
	public static int cableRenderID;
		
	public MJConfig(Configuration config) {
		this.config = config;
	}
	
	public MJConfig(File config) {
		this(new Configuration(config));
	}
	
	public void load() {
		config.load();
	}
	
	public void save() {
		config.save();
	}
}
