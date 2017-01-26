package turkey.witherCrumbs.entities;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import ganymedes01.headcrumbs.api.IHumanEntity;
import ganymedes01.headcrumbs.entity.EntityHuman;
import ganymedes01.headcrumbs.utils.ThreadedProfileFiller;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import turkey.witherCrumbs.config.WitherCrumbSettings;
import turkey.witherCrumbs.info.CelebrityWitherInfo;
import turkey.witherCrumbs.info.CelebrityWitherRegistry;
import turkey.witherCrumbs.items.WitherCrumbsItems;

public class EntityHumanWither extends EntityWither implements IHumanEntity
{
	private static final DataParameter<String> NAME = EntityDataManager.createKey(EntityHumanWither.class, DataSerializers.STRING);

	private GameProfile profile;
	private boolean isProfileReady;
	private ResourceLocation skinTexture;

	private boolean isRealWither;

	private String displayName = "";

	public EntityHumanWither(World world)
	{
		super(world);
	}

	public void setRealWither(boolean realWither)
	{
		isRealWither = realWither;
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		super.dataManager.register(NAME, "");
	}

	@Override
	protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier)
	{
		EntityItem entityItem = this.dropItem(Items.NETHER_STAR, 1);

		if(isRealWither || WitherCrumbSettings.dropNetherStar)
		{
			if (entityItem != null)
			{
				entityItem.setNoDespawn();
			}
		}

		CelebrityWitherInfo info = CelebrityWitherRegistry.getCelebrityInfo(this.profile.getName());
		ItemStack stack = info == null ? new ItemStack(WitherCrumbsItems.crumbStar) : info.getDropStack();
		this.entityDropItem(stack, 0);

		if (!this.worldObj.isRemote)
		{
			for (EntityPlayer entityplayer : this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().expand(50.0D, 100.0D, 50.0D)))
			{
				entityplayer.addStat(AchievementList.KILL_WITHER);
			}
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tagCompound)
	{
		super.writeEntityToNBT(tagCompound);
		String username = dataManager.get(NAME);
		if(!StringUtils.isNullOrEmpty(username))
		{
			tagCompound.setString("Username", username);
		}
		tagCompound.setBoolean("IsRealWither", isRealWither);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound tagCompound)
	{
		super.readEntityFromNBT(tagCompound);

		if(tagCompound.hasKey("Username"))
		{
			setUsername(tagCompound.getString("Username"));
		}
		else
		{
			setUsername(EntityHuman.getRandomUsername(rand));
		}

		isRealWither = tagCompound.getBoolean("IsRealWither");
	}

	@Override
	public ITextComponent getDisplayName()
	{
		TextComponentString textComponent = displayName != "" ? new TextComponentString(displayName) : new TextComponentString("WitherCrumb");
		textComponent.getStyle().setHoverEvent(this.getHoverEvent());
		textComponent.getStyle().setInsertion(this.getCachedUniqueIdString());
		return textComponent;
	}

	@Override
	public String getUsername()
	{
		return dataManager.get(NAME);
	}

	@Override
	public void setUsername(String name)
	{
		dataManager.set(NAME, name);

		getProfile();
	}

	@Override
	public double getInterpolatedCapeX(float v)
	{
		return 0;
	}

	@Override
	public double getInterpolatedCapeY(float v)
	{
		return 0;
	}

	@Override
	public double getInterpolatedCapeZ(float v)
	{
		return 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public SkinManager.SkinAvailableCallback getCallback()
	{
		return new SkinManager.SkinAvailableCallback()
		{
			@Override
			public void skinAvailable(MinecraftProfileTexture.Type type, ResourceLocation location, MinecraftProfileTexture profileTexture)
			{
				if(type == MinecraftProfileTexture.Type.SKIN)
				{
					skinTexture = location;
				}
			}
		};
	}

	@Override
	public boolean isTextureAvailable(MinecraftProfileTexture.Type type)
	{
		return type == MinecraftProfileTexture.Type.SKIN && skinTexture != null;
	}

	@Override
	public ResourceLocation getTexture(MinecraftProfileTexture.Type type)
	{
		return skinTexture;
	}

	@Override
	public boolean isProfileReady()
	{
		return isProfileReady;
	}

	@Override
	public GameProfile getProfile()
	{
		if(profile == null)
		{
			this.setProfile(new GameProfile(null, dataManager.get(NAME)));
			ThreadedProfileFiller.updateProfile(this);
		}
		return profile;
	}

	@Override
	public void setProfile(GameProfile profile)
	{
		this.profile = profile;
		if(this.profile != null)
		{
			CelebrityWitherInfo info = CelebrityWitherRegistry.getCelebrityInfo(this.profile.getName());
			this.displayName = info != null ? info.getDisplayName() : this.profile.getName();
		}
		this.bossInfo.setName(getDisplayName());
		isProfileReady = true;
	}

}