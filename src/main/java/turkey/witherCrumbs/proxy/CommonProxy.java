package turkey.witherCrumbs.proxy;

import com.theprogrammingturkey.gobblecore.events.EventManager;
import com.theprogrammingturkey.gobblecore.proxy.IBaseProxy;

import net.minecraft.entity.player.EntityPlayer;
import turkey.witherCrumbs.listeners.SkullPlacedEvent;
import turkey.witherCrumbs.listeners.WitherSpawnHandler;

public class CommonProxy implements IBaseProxy
{

	public boolean isClient()
	{
		return false;
	}

	public void registerRenderings()
	{

	}

	@Override
	public void registerGuis()
	{

	}

	@Override
	public void registerEvents()
	{
		EventManager.registerListener(new SkullPlacedEvent());
		EventManager.registerListener(new WitherSpawnHandler());
	}

	public EntityPlayer getClientPlayer()
	{
		return null;
	}

}
