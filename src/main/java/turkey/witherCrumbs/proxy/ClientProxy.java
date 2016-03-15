package turkey.witherCrumbs.proxy;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import turkey.witherCrumbs.entities.EntityHumanWither;
import turkey.witherCrumbs.entities.renderers.RenderHumanWither;

public class ClientProxy extends CommonProxy
{

	@Override
	public boolean isClient()
	{
		return true;
	}

	public void registerRenderings()
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityHumanWither.class, new RenderHumanWither());

	}

	@Override
	public EntityPlayer getClientPlayer()
	{
		return Minecraft.getMinecraft().thePlayer;
	}
}
