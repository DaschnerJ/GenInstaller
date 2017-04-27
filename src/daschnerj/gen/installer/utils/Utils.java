package daschnerj.gen.installer.utils;

import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Utils implements IUtils{
	
	public Utils()
	{
		
	}
	
	@Override
	public Path convertStringToPath(String path) {
		try
		{
			return Paths.get(path);
		}
		catch(InvalidPathException e)
		{
			System.err.println("'" + e.getInput() + "' could not be resolved!");
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public String convertPathToString(Path path) {
		try
		{
			String target = path.toAbsolutePath().toString();
			return (target != null) ? target : null;
		}
		catch(IOError e)
		{
			System.err.print("IO Error converting Path into String!");
			e.printStackTrace();
		}
		catch(SecurityException e)
		{
			System.err.print("Path cannot be converted because system security denies it!");
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Path getJarDirectory() {
		try
		{
			if(isJarRun())
			{
				return Paths.get("");
			}
			else
			{
				return Paths.get(startLocation());
			}
		}
		catch(NullPointerException e)
		{
			System.err.print("Path could not be resolved!");
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Path getDirectory() {
		try
		{
			Path target = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().toPath();
			return (target != null) ? target : null;
		}
		catch(SecurityException e)
		{
			System.err.print("Jar path cannot be resolved because system security denies it!");
			e.printStackTrace();
		}
		catch(NullPointerException e)
		{
			System.err.println("FATAL ERROR - FILE COULD NOT BE RESOLVED");
			e.printStackTrace();
		}
		catch(InvalidPathException e)
		{
			System.err.println("File could not be resolved into Path!");
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Path getResJarDirectory() {
		if(isJarRun())
			return Paths.get("resources");
		else
			return Paths.get("\\resources");
	}
	
	@Override
	public Path getFilePathFromResFolder(String path) {
		if(isJarRun())
		{
			return Paths.get(path);
		}
		else
		{
			String res = this.getClass().getClassLoader().getResource(path.replace("\\", "/")).getPath();
			if(res.startsWith("/"))
				res = res.substring(1, res.length());
			return Paths.get(res);
		}
	}
	
	/**
	 * Copies file from internal location to external location. 
	 * @param location - Location of file to be copied.
	 * @param des - Target folder.
	 * @return Success.
	 */
	@Override
	public boolean copyFile(Path location, Path des) {
		String[] directory = location.toString().split("\\\\");
		des = Paths.get(des.toString() + "\\" + directory[directory.length-1]);
		try
		{
			File parent = des.toFile().getParentFile();
			parent.mkdirs();
			new File(parent,directory[directory.length-1]).createNewFile();
		}
		catch(IOException e)
		{
			System.err.println("Error copying file!!!");
			e.printStackTrace();
		}
		if(isJarRun())
		{
			try {
				Files.copy(this.getFileFromResFolder(location.toString()), des, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else
		{
			try {
				Files.copy(location, des, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public boolean createDirectory(String path) {
		return new File(path).mkdirs();
	}


	@Override
	public Path[] getAllFiles(Path path, boolean traverse) {
		if(isJarRun())
		{
			JarFile jar = null;

			try {
				jar = new JarFile(new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI()));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			Enumeration<JarEntry> entries =  jar.entries();
			ArrayList<JarEntry> jEntries = new ArrayList<>();
			while(entries.hasMoreElements())
			{
				JarEntry jentry = entries.nextElement();
				jEntries.add(jentry);
			}
			Path[] product = new Path[jEntries.size()];
			int i = 0;
			for(JarEntry jentry : jEntries)
			{
				if(!jentry.isDirectory())
				{
					product[i] = Paths.get(jentry.getName());
					i++;
				}
			}
			try {
				jar.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return processJarFiles(path, product, traverse);
		}
		else
		{
			File parent = null;
			parent = new File(startLocation());
			File child = new File(parent, path.toString());
			ArrayList<Path> fileList = new ArrayList<>();
			for(File file : child.listFiles())
			{
				if(file.isFile())
					fileList.add(Paths.get(file.getAbsolutePath()));
				else if(file.isDirectory() && traverse)
				{
					fileList.addAll(Arrays.asList(getAllFiles(Paths.get(file.getAbsolutePath().replace((parent.getAbsolutePath() + "\\"), "")), traverse)));
				}
			}
			Path[] paths = new Path[fileList.size()];
			for(int i = 0; i<paths.length; i++)
				paths[i] = fileList.get(i);
			return paths;
		}
	}
	
	public boolean isJarRun()
	{
		try {
			if(this.getClass().getResource(this.getClass().getSimpleName()+".class").toURI().toString().startsWith("jar:"))
				return true;
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public URI startLocation()
	{
		try {
			return this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private Path[] processJarFiles(Path original, Path[] paths, boolean traverse) {
		Path[] processed = new Path[paths.length];
		int i = 0;
		for(Path path : paths)
		{
			if(path != null && path.toString().contains(original.toString()))
			{
				if(!original.toString().equals(""))
				{
					if(path.toString().replace(original.toString() + "\\", "").contains("\\"))
					{
						if(traverse)
						{
							processed[i] = path;
							i++;
						}
					}
					else
					{
						processed[i] = path;
						i++;
					}
				}
				else
				{
					if(path.toString().contains("\\"))
					{
						if(traverse)
						{
							processed[i] = path;
							i++;
						}
					}
					else
					{
						processed[i] = path;
						i++;
					}
				}
			}
		}
		ArrayList<Path> nullFix = new ArrayList<>();
		for(int x = 0; x < processed.length-1; x++)
			if(processed[x] != null)
				nullFix.add(processed[x]);
		return nullFix.toArray(new Path[nullFix.size()]);
	}

	@Override
	public InputStream getFileFromResFolder(String path) {
		if(isJarRun())
		{
			String[] splitPath = path.split("\\\\");
			String newPath = splitPath[0];
			for(String string : splitPath) if(!string.equals(splitPath[0])) newPath +=("/" + string);
			return this.getClass().getClassLoader().getResourceAsStream(newPath);
		}
		else
		{
			try {
				return this.getClass().getClassLoader().getResource(path.replace("\\", "/")).openStream();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
}
