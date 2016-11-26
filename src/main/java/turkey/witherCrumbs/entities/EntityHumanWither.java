package turkey.witherCrumbs.entities;

import javax.annotation.Nullable;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;

import ganymedes01.headcrumbs.api.IHumanEntity;
import ganymedes01.headcrumbs.entity.EntityHuman;
import ganymedes01.headcrumbs.utils.ThreadedProfileFiller;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
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
		if(isRealWither || WitherCrumbSettings.dropNetherStar)
		{
			this.dropItem(Items.NETHER_STAR, 1);
		}

		CelebrityWitherInfo info = CelebrityWitherRegistry.getCelebrityInfo(this.profile.getName());
		ItemStack stack = info == null ? new ItemStack(WitherCrumbsItems.crumbStar) : info.getDropStack();
		this.entityDropItem(stack, 0);
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
		TextComponentString textComponent = profile != null ? new TextComponentString("- " + profile.getName() + " -") : new TextComponentString("- WitherCrumb - ");
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

	@Nullable
	@Override
	public GameProfile getProfile()
	{
		if(profile == null)
		{
			profile = new GameProfile(null, dataManager.get(NAME));
			ThreadedProfileFiller.updateProfile(this);
		}
		return profile;
	}

	@Override
	public void setProfile(GameProfile profile)
	{
		this.profile = profile;
		this.bossInfo.setName(getDisplayName());
		isProfileReady = true;
	}

}