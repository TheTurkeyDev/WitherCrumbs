package turkey.witherCrumbs.entities;

import org.apache.commons.lang3.StringUtils;

import com.mojang.authlib.GameProfile;

import ganymedes01.headcrumbs.entity.EntityHuman;
import ganymedes01.headcrumbs.utils.UsernameUtils;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityHumanWither extends EntityWither
{
	private GameProfile profile;

	private static final int NAME = 21;

	public EntityHumanWither(World p_i1701_1_)
	{
		super(p_i1701_1_);
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		getDataWatcher().addObject(NAME, "");
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
		String username = getDataWatcher().getWatchableObjectString(NAME);
		if(StringUtils.isBlank(username))
			getDataWatcher().updateObject(NAME, username = EntityHuman.getRandomUsername(rand));
		return username;
	}

	public void setUsername(String name)
	{
		getDataWatcher().updateObject(NAME, UsernameUtils.getFixedUsername(name));
	}
}