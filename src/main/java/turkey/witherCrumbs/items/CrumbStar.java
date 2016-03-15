package turkey.witherCrumbs.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.headcrumbs.Headcrumbs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import turkey.witherCrumbs.WitherCrumbsCore;

public class CrumbStar extends ItemFood
{
	public CrumbStar(String name)
	{
		super(0, 0, false);
		this.setUnlocalizedName(name);
		this.setTextureName(WitherCrumbsCore.MODID + ":" + name.toLowerCase());
		this.setCreativeTab(Headcrumbs.tab);
	}

	protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player)
	{
		if(!world.isRemote)
		{
			player.addPotionEffect(new PotionEffect(Potion.regeneration.id, 150, 1));
			player.getFoodStats().addStats(20, 20);
		}
	}

	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack)
	{
		return true;
	}
}
