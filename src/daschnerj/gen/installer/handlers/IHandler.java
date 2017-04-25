package daschnerj.gen.installer.handlers;

import java.io.File;

public interface IHandler {
	
	/**
	 * Copies a file from loc to des.
	 * @param loc The String location of the file.
	 * @param des The String location of the destination.
	 * @return Returns true if the file was successfully copied.
	 */
	public boolean moveResource(String loc, String des);
	
	/**
	 * Gets all the files within the res folder.
	 * @return Returns an array of files within the res folder.
	 */
	public File[] getAllResFiles();
	
	/**
	 * Creates the initial directories that the files will be located in.
	 * @return Returns true if the directories were created.
	 */
	public boolean createDirectories();
	
	/**
	 * Copies all the files from the res folder to the respective directories based on file endings.
	 * @return Returns true if the files were successfully copied.
	 */
	public boolean moveAllFiles();

}
