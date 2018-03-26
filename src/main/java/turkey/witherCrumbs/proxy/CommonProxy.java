package turkey.witherCrumbs.proxy;

import net.minecraft.entity.player.EntityPlayer;

public class CommonProxy
{

	public boolean isClient()
	{
		return false;
	}

	public void registerRenderings()
	{

	}

	public EntityPlayer getClientPlayer()
	{
		return null;
	}
}