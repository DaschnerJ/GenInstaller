package daschnerj.gen.installer;

import daschnerj.gen.installer.handlers.Handler;

public class Initialize {
	
	private Handler handler;
	
	public Initialize()
	{
		handler = new Handler();
	}
	
	private boolean exportResources()
	{
		return false;
	}

}
