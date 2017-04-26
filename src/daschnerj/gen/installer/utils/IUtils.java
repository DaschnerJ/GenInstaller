package daschnerj.gen.installer.utils;

import java.io.InputStream;
import java.nio.file.Path;

public interface IUtils {
	
	/**
	 * Converts a String to a Path data object.
	 * @param path The String to be converted.
	 * @return Returns the converted Path version of the String.
	 */
	public Path convertStringToPath(String path);
	
	/**
	 * Converts Path to String data object.
	 * @param path The Path to be converted.
	 * @return Returns the converted String version of the Path.
	 */
	public String convertPathToString(Path path);
	
	/**
	 * Gets the Path to the inner jar directory.
	 * @return Returns the Path of inisde the jar.
	 */
	public Path getJarDirectory();
	
	/**
	 * Gets the Path to the folder the jar is located in.
	 * @return Returns path of the where the jar is located.
	 */
	public Path getDirectory();
	
	/**
	 * Gets the Path to the inner res folder within the jar.
	 * @return Returns the path of the inner res folder.
	 */
	public Path getResJarDirectory();
	
	/**
	 * Gets a file from the res folder.
	 * @param path The String path to the file inside the res folder.
	 * @return Returns the file located inside the res folder.
	 */
	public Path getFilePathFromResFolder(String path);
	
	public InputStream getFileFromResFolder(String path);
	
	/**
	 * Copies the file to the Path destination des.
	 * @param file The file to be copied.
	 * @param des The Path des of where to copy to.
	 * @return Returns true if successful copy, otherwise returns false.
	 */
	public boolean copyFile(Path location, Path des);
	
	/**
	 * Creates a directory if the directory does not exist.
	 * @param path The directory path to be created.
	 * @return Returns true if the directory was successfully created, otherwise returns false.
	 */
	public boolean createDirectory(String path);
	
	/**
	 * Gets all the files as an array within a folder at the Path.
	 * @param path The Path of the folder to get the files.
	 * @param traverse Boolean to traverse all folders within the folders.
	 * @return Returns an array of files located within the folder.
	 */
	public Path[] getAllFiles(Path path, boolean traverse);
	

}
