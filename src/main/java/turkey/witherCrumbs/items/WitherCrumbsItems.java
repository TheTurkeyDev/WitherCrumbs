package turkey.witherCrumbs.items;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import turkey.witherCrumbs.WitherCrumbsCore;

public class WitherCrumbsItems
{
	public static CrumbStar crumbStar;
	
	public static void initItems()
	{
		GameRegistry.register(crumbStar = new CrumbStar("Crumb_Star"));
	}
	
	public static void registerItems()
	{
		ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();

		mesher.register(WitherCrumbsItems.crumbStar, 0, new ModelResourceLocation(WitherCrumbsCore.MODID + ":" + WitherCrumbsItems.crumbStar.getItemName(), "inventory"));
	}
}
	
