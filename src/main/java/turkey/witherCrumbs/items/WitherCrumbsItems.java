package turkey.witherCrumbs.items;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import turkey.witherCrumbs.WitherCrumbsCore;

public class WitherCrumbsItems
{
	public static CrumbStar crumbStar;
	
	@SubscribeEvent
	public void onItemRegistry(RegistryEvent.Register<Item> e)
	{
		e.getRegistry().register(crumbStar = new CrumbStar("crumb_star"));
	}
	
	public static void registerItems()
	{
		ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();

		mesher.register(WitherCrumbsItems.crumbStar, 0, new ModelResourceLocation(WitherCrumbsCore.MODID + ":" + WitherCrumbsItems.crumbStar.getItemName(), "inventory"));
	}
}
	