package daschnerj.gen.installer;

import daschnerj.gen.installer.handlers.Handler;

public class Initialize {
	
	@SuppressWarnings("unused")
	private Handler handler;
	
	public Initialize()
	{
		handler = new Handler();
	}
	
	@SuppressWarnings("unused")
	private boolean exportResources()
	{
		return false;
	}

}
