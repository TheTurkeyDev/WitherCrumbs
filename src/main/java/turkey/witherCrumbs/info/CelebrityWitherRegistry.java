package turkey.witherCrumbs.info;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;

public class CelebrityWitherRegistry
{
	private static Map<String, CelebrityWitherInfo> info = new HashMap<String, CelebrityWitherInfo>();

	public static CelebrityWitherInfo addCelebrityInfo(String name, ItemStack drop, boolean customSounds)
	{
		CelebrityWitherInfo newCelebrity = new CelebrityWitherInfo(name, drop, customSounds);
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
