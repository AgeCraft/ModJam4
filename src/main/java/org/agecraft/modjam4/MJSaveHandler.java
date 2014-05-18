package org.agecraft.modjam4;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;
import net.minecraft.world.chunk.storage.IChunkLoader;
import net.minecraft.world.storage.ISaveHandler;

import org.agecraft.modjam4.network.EnergyNetwork;
import org.agecraft.modjam4.network.EnergyNetworkRegistry;

public class MJSaveHandler {

	public static enum SaveFileType {
		OBJECT(), NBT();
	}

	public ISaveHandler saveHandler;
	public World world;

	public MJSaveHandler(ISaveHandler saveHandler, World world) {
		this.saveHandler = saveHandler;
		this.world = world;
	}

	public String[] getSaveFiles() {
		return new String[]{"electrical_networks"};
	}

	public SaveFileType getSaveFileType(String fileName) {
		return SaveFileType.OBJECT;
	}

	public void load(String fileName, File file, ObjectInputStream in) {
		try {
			if(fileName.equalsIgnoreCase("electrical_networks")) {
				EnergyNetworkRegistry.networks.clear();
				EnergyNetworkRegistry.networks = (HashMap<Long, EnergyNetwork>) in.readObject();
				if(EnergyNetworkRegistry.networks == null) {
					EnergyNetworkRegistry.networks = new HashMap<Long, EnergyNetwork>();
				}
				ModJam4.log.info("Loaded " + EnergyNetworkRegistry.networks.size() + " electrical networks");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void save(String fileName, File file, ObjectOutputStream out, boolean isNewFile) {
		try {
			if(fileName.equalsIgnoreCase("electrical_networks")) {
				if(isNewFile) {
					EnergyNetworkRegistry.networks.clear();
				}
				out.writeObject(EnergyNetworkRegistry.networks);
				ModJam4.log.info("Saved " + EnergyNetworkRegistry.networks.size() + " electrical networks");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void loadNBT(String fileName, File file, NBTTagCompound nbt) {

	}

	public void saveNBT(String fileName, File file, NBTTagCompound nbt, boolean isNewFile) {

	}

	public void load() {
		if((world.provider.dimensionId == 0)) {
			String[] saveFileNames = getSaveFiles();
			for(int i = 0; i < saveFileNames.length; i++) {
				SaveFileType saveFileType = getSaveFileType(saveFileNames[i]);
				if(saveFileType == SaveFileType.OBJECT) {
					try {
						File file = getSaveFile(saveHandler, world, saveFileNames[i] + ".dat", false);
						if(file != null) {
							try {
								loadFile(saveFileNames[i], file);
							} catch(Exception e) {
								e.printStackTrace();
								File fileBackup = getSaveFile(saveHandler, world, saveFileNames[i] + ".dat", true);
								if(fileBackup.exists()) {
									loadFile(saveFileNames[i], fileBackup);
								} else {
									file.createNewFile();
									saveFile(saveFileNames[i], file, true);
								}
							}
						}
					} catch(Exception e) {
						e.printStackTrace();
					}
				} else if(saveFileType == SaveFileType.NBT) {
					try {
						File file = getSaveFile(saveHandler, world, saveFileNames[i] + ".dat", false);
						if(file != null) {
							try {
								loadFileNBT(saveFileNames[i], file);
							} catch(Exception e) {
								e.printStackTrace();
								File fileBackup = getSaveFile(saveHandler, world, saveFileNames[i] + ".dat", true);
								if(fileBackup.exists()) {
									loadFileNBT(saveFileNames[i], fileBackup);
								} else {
									file.createNewFile();
									saveFileNBT(saveFileNames[i], file, true);
								}
							}
						}
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private void loadFile(String fileName, File file) throws Exception {
		FileInputStream fis = new FileInputStream(file.getAbsolutePath());
		GZIPInputStream gzis = new GZIPInputStream(fis);
		ObjectInputStream in = new ObjectInputStream(gzis);

		load(fileName, file, in);

		in.close();
		gzis.close();
		fis.close();
	}

	private void loadFileNBT(String fileName, File file) throws Exception {
		FileInputStream fis = new FileInputStream(file);
		NBTTagCompound nbt = CompressedStreamTools.readCompressed(fis);
		fis.close();
		loadNBT(fileName, file, nbt);
	}

	public void save() {
		if((world.provider.dimensionId == 0)) {
			String[] saveFileNames =getSaveFiles();
			for(int i = 0; i < saveFileNames.length; i++) {
				SaveFileType saveFileType = getSaveFileType(saveFileNames[i]);
				if(saveFileType == SaveFileType.OBJECT) {
					File file = getSaveFile(saveHandler, world, saveFileNames[i] + ".dat", false);
					try {
						saveFile(saveFileNames[i], file, false);
					} catch(Exception e) {
						e.printStackTrace();
					}
				} else if(saveFileType == SaveFileType.NBT) {
					File file = getSaveFile(saveHandler, world, saveFileNames[i] + ".dat", false);
					try {
						saveFileNBT(saveFileNames[i], file, false);
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public void saveFile(String fileName, File file, boolean isNewFile) throws Exception {
		FileOutputStream fos = new FileOutputStream(file);
		GZIPOutputStream gzos = new GZIPOutputStream(fos);
		ObjectOutputStream out = new ObjectOutputStream(gzos);

		save(fileName, file, out, isNewFile);

		out.flush();
		out.close();
		gzos.close();
		fos.close();
		copyFile(file, new File(new StringBuilder().append(file.getAbsolutePath()).append(".bak").toString()));
	}

	public void saveFileNBT(String fileName, File file, boolean isNewFile) throws Exception {
		NBTTagCompound nbt = new NBTTagCompound();

		saveNBT(fileName, file, nbt, isNewFile);

		FileOutputStream fos = new FileOutputStream(file);
		CompressedStreamTools.writeCompressed(nbt, fos);
		fos.close();

		copyFile(file, new File(new StringBuilder().append(file.getAbsolutePath()).append(".bak").toString()));
	}

	public File getSaveFile(ISaveHandler saveHandler, World world, String name, boolean backup) {
		File worldDir = new File(saveHandler.getWorldDirectoryName());
		IChunkLoader loader = saveHandler.getChunkLoader(world.provider);
		if((loader instanceof AnvilChunkLoader)) {
			worldDir = ((AnvilChunkLoader) loader).chunkSaveLocation;
		}
		File file = new File(worldDir, name + (backup ? ".bak" : ""));
		if(!backup && !file.exists()) {
			try {
				file.createNewFile();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	public void copyFile(File sourceFile, File destFile) {
		FileChannel source = null;
		FileChannel destination = null;
		try {
			if(!destFile.exists()) {
				destFile.createNewFile();
			}
			source = new FileInputStream(sourceFile).getChannel();
			destination = new FileOutputStream(destFile).getChannel();
			destination.transferFrom(source, 0L, source.size());
			source.close();
			destination.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
