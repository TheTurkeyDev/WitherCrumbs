package turkey.witherCrumbs.items;

import ganymedes01.headcrumbs.Headcrumbs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import turkey.witherCrumbs.WitherCrumbsCore;

public class CrumbStar extends ItemFood
{
	private String name;
	public CrumbStar(String name)
	{
		super(0, 0, false);
		this.name = name;
		this.setUnlocalizedName(name);
		this.setRegistryName(WitherCrumbsCore.MODID, name);
		this.setCreativeTab(Headcrumbs.tab);
	}

	protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player)
	{
		if(!world.isRemote)
		{
			player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 150, 1));
			player.getFoodStats().addStats(20, 20);
		}
	}

	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack)
	{
		return true;
	}

	public String getItemName()
	{
		return name;
	}
}