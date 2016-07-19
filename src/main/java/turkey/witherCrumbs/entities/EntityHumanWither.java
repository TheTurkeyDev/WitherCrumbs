package turkey.witherCrumbs.entities;

import org.apache.commons.lang3.StringUtils;

import com.mojang.authlib.GameProfile;

import ganymedes01.headcrumbs.entity.EntityHuman;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import turkey.witherCrumbs.config.WitherCrumbSettings;
import turkey.witherCrumbs.info.CelebrityWitherRegistry;

public class EntityHumanWither extends EntityWither
{
	private GameProfile profile;

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
	}

	public String getUsername()
	{
		String username = super.dataManager.get(NAME);
		if(StringUtils.isBlank(username))
			super.dataManager.set(NAME,  username = EntityHuman.getRandomUsername(rand));
		return username;
	}

	public void setUsername(String name)
	{
		super.dataManager.set(NAME, name);
	}
}