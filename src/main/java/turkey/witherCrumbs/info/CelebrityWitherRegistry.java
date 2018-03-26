package turkey.witherCrumbs.info;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;

public class CelebrityWitherRegistry
{
	private static Map<String, CelebrityWitherInfo> info = new HashMap<String, CelebrityWitherInfo>();

	public static CelebrityWitherInfo addCelebrityInfo(String name, ItemStack drop)
	{
		return CelebrityWitherRegistry.addCelebrityInfo(name, drop, name);
	}

	public static CelebrityWitherInfo addCelebrityInfo(String name, ItemStack drop, String displayName)
	{
		CelebrityWitherInfo newCelebrity = new CelebrityWitherInfo(name, drop, displayName);
		info.put(name, newCelebrity);
		return newCelebrity;
	}

	public static CelebrityWitherInfo getCelebrityInfo(String name)
	{
		if(info.containsKey(name))
			return info.get(name);
		return null;
	}
}
