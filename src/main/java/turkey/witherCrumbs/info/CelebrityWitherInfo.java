package turkey.witherCrumbs.info;

import net.minecraft.item.ItemStack;

public class CelebrityWitherInfo
{
	private String celebrityKey = "";
	private ItemStack dropStack;
	private boolean hasCustomSounds = false;
	
	
	public CelebrityWitherInfo(String name, ItemStack dropStack, boolean customSounds)
	{
		this.celebrityKey = name;
		this.dropStack = dropStack;
		this.hasCustomSounds = customSounds;
	}
	
	public String getCelebrityName()
	{
		return this.celebrityKey;
	}
	
	public ItemStack getDropStack()
	{
		return this.dropStack;
	}
	
	public boolean hasCustomSounds()
	{
		return this.hasCustomSounds;
	}
}