package turkey.witherCrumbs.entities;

import org.apache.commons.lang3.StringUtils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;

import ganymedes01.headcrumbs.api.IHumanEntity;
import ganymedes01.headcrumbs.entity.EntityHuman;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import turkey.witherCrumbs.WitherCrumbsCore;
import turkey.witherCrumbs.config.WitherCrumbSettings;
import turkey.witherCrumbs.info.CelebrityWitherRegistry;

public class EntityHumanWither extends EntityWither implements IHumanEntity
{
	private GameProfile profile;
	private ResourceLocation skin;
	private boolean skinAvailable = false;

	private static final DataParameter<String> NAME = EntityDataManager.<String> createKey(EntityHumanWither.class, DataSerializers.STRING);

	public EntityHumanWither(World p_i1701_1_)
	{
		super(p_i1701_1_);
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		super.dataManager.register(NAME, "");
	}

	/**
	 * Drop 0-2 items of this living's type. @param par1 - Whether this entity has recently been hit by a player. @param par2 - Level of Looting used to kill this mob.
	 */
	protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
	{
		if(WitherCrumbSettings.dropNetherStar)
			this.dropItem(Items.NETHER_STAR, 1);

		this.entityDropItem(CelebrityWitherRegistry.getCelebrityInfo(this.profile.getName()).getDropStack(), 0);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt)
	{
		super.writeEntityToNBT(nbt);

		String username = getUsername();
		if(!StringUtils.isBlank(username))
			nbt.setString("Username", username);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt)
	{
		super.readEntityFromNBT(nbt);

		String username;
		if(nbt.hasKey("Username"))
			username = nbt.getString("Username");
		else
			username = EntityHuman.getRandomUsername(rand);
		setUsername(username);
	}

	public ITextComponent getDisplayName()
	{
		TextComponentString textcomponentstring = new TextComponentString(this.getUsername());
		textcomponentstring.getStyle().setHoverEvent(this.getHoverEvent());
		textcomponentstring.getStyle().setInsertion(this.getCachedUniqueIdString());
		return textcomponentstring;
	}

	public GameProfile getProfile()
	{
		if(profile == null)
			profile = new GameProfile(null, getUsername());
		return this.profile;
	}

	public void setProfile(GameProfile profile)
	{
		this.profile = profile;
		this.setUsername(profile.getName());
		BossInfoServer bossInfo;
		if(WitherCrumbsCore.VERSION.equalsIgnoreCase("@version@"))
			bossInfo = ObfuscationReflectionHelper.getPrivateValue(EntityWither.class, this, "bossInfo");
		else
			bossInfo = ObfuscationReflectionHelper.getPrivateValue(EntityWither.class, this, "buttonList");
		bossInfo.setName(this.getDisplayName());
	}

	public String getUsername()
	{
		String username = super.dataManager.get(NAME);
		if(StringUtils.isBlank(username))
			super.dataManager.set(NAME, username = EntityHuman.getRandomUsername(rand));
		return username;
	}

	public void setUsername(String name)
	{
		super.dataManager.set(NAME, name);
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
				switch(type)
				{
					case SKIN:
						skin = location;
						break;
					default:
						break;

				}
				setTextureAvailable(type);
			}
		};
	}

	@Override
	public double getInterpolatedCapeX(float arg0)
	{
		return 0;
	}

	@Override
	public double getInterpolatedCapeY(float arg0)
	{
		return 0;
	}

	@Override
	public double getInterpolatedCapeZ(float arg0)
	{
		return 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isTextureAvailable(MinecraftProfileTexture.Type type)
	{
		switch(type)
		{
			default:
				return skinAvailable;
		}
	}

	@SideOnly(Side.CLIENT)
	private void setTextureAvailable(MinecraftProfileTexture.Type type)
	{
		switch(type)
		{
			case SKIN:
				skinAvailable = true;
				break;
			default:
				break;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ResourceLocation getTexture(MinecraftProfileTexture.Type type)
	{
		switch(type)
		{
			case SKIN:
				return skin;
			default:
				return null;
		}
	}

	@Override
	public boolean isProfileReady()
	{
		return this.profile != null;
	}
}