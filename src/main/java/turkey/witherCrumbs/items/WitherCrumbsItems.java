package turkey.witherCrumbs.items;

import com.theprogrammingturkey.gobblecore.items.IItemHandler;
import com.theprogrammingturkey.gobblecore.items.ItemLoader;

import ganymedes01.headcrumbs.Headcrumbs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;

public class WitherCrumbsItems implements IItemHandler
{
	public static CrumbStar crumbStar;

	@Override
	public void registerItems(ItemLoader loader)
	{
		loader.setCreativeTab(Headcrumbs.tab);
		loader.registerItem(crumbStar = new CrumbStar());

	}

	@Override
	public void registerModels(ItemLoader loader)
	{
		ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();

		loader.registerItemModel(mesher, crumbStar, 0);
	}
}
