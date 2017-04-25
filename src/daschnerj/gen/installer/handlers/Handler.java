package daschnerj.gen.installer.handlers;

import java.io.File;
import java.nio.file.Path;

import daschnerj.gen.installer.utils.Utils;

public class Handler implements IHandler{
	
	private Utils utils;
	
	public Handler()
	{
		utils = new Utils();
		createDirectories();
		moveAllFiles();
	}

	public boolean moveResource(String loc, String des) {
		File file = new File(loc);
		Path desPath = utils.convertStringToPath(des);
		return utils.copyFile(file, desPath);
	}

	public File[] getAllResFiles() {
		Path r = utils.getResJardirectory();
		File[] files = utils.getAllFiles(r, true);
		return files;
	}
	
	public boolean moveAllFiles()
	{
		File[] files = getAllResFiles();
		
		for(File f : files)
		{
			String[] split = f.getName().split(".");
			String ending = split[split.length-1];
			String dir = utils.convertPathToString(utils.getDirectory());
			switch(ending)
			{
				case "ttf":
					utils.copyFile(f, utils.convertStringToPath(dir + "\\Gen\\Fonts"));
					break;
				case "png":
					utils.copyFile(f, utils.convertStringToPath(dir + "\\Gen\\Textures"));
					break;
				case "gcf":
					utils.copyFile(f, utils.convertStringToPath(dir+ "\\Gen\\Configs"));
					break;
				case "gwf":
					utils.copyFile(f, utils.convertStringToPath(dir + "\\Gen\\Worlds"));
					break;
				default:
					utils.copyFile(f, utils.convertStringToPath(dir + "\\Gen\\Bin"));
					break;
			}
		}
		
		return true;
	}
	
	public boolean createDirectories()
	{
		String dir = utils.convertPathToString(utils.getDirectory());
		utils.createDirectory(dir + "\\Gen\\Fonts");
		utils.createDirectory(dir + "\\Gen\\Textures");
		utils.createDirectory(dir + "\\Gen\\Configs");
		utils.createDirectory(dir + "\\Gen\\Worlds");
		utils.createDirectory(dir + "\\Gen\\Bin");
		return true;
	}

}
