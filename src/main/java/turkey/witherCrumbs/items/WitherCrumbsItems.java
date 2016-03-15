package turkey.witherCrumbs.items;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class WitherCrumbsItems
{
	public static Item crumbStar;
	
	public static void initItems()
	{
		GameRegistry.registerItem(crumbStar = new CrumbStar("Crumb_Star"), "Crumb_Star");
	}
}
