package turkey.witherCrumbs.info;

import net.minecraft.item.ItemStack;

public class CelebrityWitherInfo
{
	private String celebrityKey = "";
	private ItemStack dropStack;
	private String displayName;

	public CelebrityWitherInfo(String name, ItemStack dropStack)
	{
		this(name, dropStack, name);
	}

	public CelebrityWitherInfo(String name, ItemStack dropStack, String displayName)
	{
		this.celebrityKey = name;
		this.dropStack = dropStack;
		this.displayName = displayName;
	}

	public String getCelebrityName()
	{
		return this.celebrityKey;
	}

	public ItemStack getDropStack()
	{
		return this.dropStack.copy();
	}

	public String getDisplayName()
	{
		return this.displayName;
	}
}