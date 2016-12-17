package turkey.witherCrumbs.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
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
		RenderingRegistry.registerEntityRenderingHandler(EntityHumanWither.class, new IRenderFactory<EntityHumanWither>()
		{
			@Override
			public Render<? super EntityHumanWither> createRenderFor(RenderManager manager)
			{
				return new RenderHumanWither(manager);
			}
		});

	}

	@Override
	public EntityPlayer getClientPlayer()
	{
		return Minecraft.getMinecraft().thePlayer;
	}
}
