package turkey.witherCrumbs.items;

import com.theprogrammingturkey.gobblecore.items.BaseItemFood;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CrumbStar extends BaseItemFood
{
	public CrumbStar()
	{
		super("crumb_star", 0, 0, false);
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
}
