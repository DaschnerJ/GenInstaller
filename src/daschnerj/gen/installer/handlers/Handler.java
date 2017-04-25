package daschnerj.gen.installer.handlers;

import daschnerj.gen.installer.utils.Utils;

public class Handler implements IHandler{
	
	@SuppressWarnings("unused")
	private Utils utils;
	
	public Handler()
	{
		utils = new Utils();
	}

	@Override
	public boolean moveResource(String loc, String des) {
		// TODO Auto-generated method stub
		return false;
	}

}
