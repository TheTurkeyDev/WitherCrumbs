package turkey.witherCrumbs.listeners;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.mojang.authlib.GameProfile;

import ganymedes01.headcrumbs.entity.EntityHuman;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.block.state.pattern.BlockStateMatcher;
import net.minecraft.block.state.pattern.FactoryBlockPattern;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import turkey.witherCrumbs.entities.EntityHumanWither;

public class SkullPlacedEvent
{

	private static BlockPattern witherPattern;

	private static BlockPattern getWitherPattern()
	{
		if(witherPattern == null)
		{
			witherPattern = FactoryBlockPattern.start().aisle(new String[] { "^^^", "###", "~#~" }).where('#', BlockWorldState.hasState(BlockStateMatcher.forBlock(Blocks.SOUL_SAND))).where('^', IS_SAME_SKULL).where('~', BlockWorldState.hasState(BlockStateMatcher.forBlock(Blocks.AIR))).build();
		}
		return witherPattern;
	}

	private static final Predicate<BlockWorldState> IS_SAME_SKULL = new Predicate<BlockWorldState>()
	{
		public boolean apply(@Nullable BlockWorldState blockWorldState)
		{
			return blockWorldState.getBlockState() != null && blockWorldState.getBlockState().getBlock() == Blocks.SKULL && blockWorldState.getTileEntity() instanceof TileEntitySkull && ((TileEntitySkull) blockWorldState.getTileEntity()).getSkullType() == 3;
		}
	};

	@SubscribeEvent
	public void onSkullPlacedEvent(PlaceEvent event)
	{
		if(event.getState().getBlock() != Blocks.SKULL)
		{
			return;
		}

		World world = event.getWorld();
		BlockPos pos = event.getPos();
		TileEntity tileEntity = world.getTileEntity(pos);
		if(tileEntity instanceof TileEntitySkull)
		{
			TileEntitySkull tileSkull = (TileEntitySkull) tileEntity;
			if(tileSkull.getSkullType() == 3 && pos.getY() >= 2 && world.getDifficulty() != EnumDifficulty.PEACEFUL && !world.isRemote)
			{
				BlockPattern witherPattern = getWitherPattern();
				BlockPattern.PatternHelper patternHelper = witherPattern.match(world, pos);
				if(patternHelper != null)
				{
					GameProfile profile = tileSkull.getPlayerProfile();
					for(int i = 0; i < 3; i++)
					{
						BlockWorldState blockWorldState = patternHelper.translateOffset(i, 0, 0);
						world.setBlockState(blockWorldState.getPos(), blockWorldState.getBlockState().withProperty(BlockSkull.NODROP, true), 2);
					}

					for(int i = 0; i < witherPattern.getPalmLength(); i++)
					{
						for(int j = 0; j < witherPattern.getThumbLength(); j++)
						{
							BlockWorldState blockWorldState = patternHelper.translateOffset(i, j, 0);
							world.setBlockState(blockWorldState.getPos(), Blocks.AIR.getDefaultState(), 2);
						}
					}

					BlockPos particlePos = patternHelper.translateOffset(1, 0, 0).getPos();
					EntityHumanWither entityWither = new EntityHumanWither(world);
					entityWither.setUsername(profile != null ? profile.getName() : EntityHuman.getRandomUsername(world.rand));
					BlockPos entityPos = patternHelper.translateOffset(1, 2, 0).getPos();
					entityWither.setLocationAndAngles((double) entityPos.getX() + 0.5, (double) entityPos.getY() + 0.55, (double) entityPos.getZ() + 0.5, patternHelper.getForwards().getAxis() == EnumFacing.Axis.X ? 0f : 90f, 0f);
					entityWither.renderYawOffset = patternHelper.getForwards().getAxis() == EnumFacing.Axis.X ? 0f : 90f;
					entityWither.ignite();

					for(EntityPlayerMP entityplayermp : world.getEntitiesWithinAABB(EntityPlayerMP.class, entityWither.getEntityBoundingBox().expandXyz(50.0D)))
					{
						CriteriaTriggers.field_192133_m.func_192229_a(entityplayermp, entityWither);
					}

					world.spawnEntityInWorld(entityWither);

					for(int i = 0; i < 120; i++)
					{
						world.spawnParticle(EnumParticleTypes.SNOWBALL, (double) particlePos.getX() + world.rand.nextDouble(), (double) (particlePos.getY() - 2) + world.rand.nextDouble() * 3.9, (double) particlePos.getZ() + world.rand.nextDouble(), 0.0, 0.0, 0.0);
					}

					for(int i = 0; i < witherPattern.getPalmLength(); i++)
					{
						for(int j = 0; j < witherPattern.getThumbLength(); j++)
						{
							BlockWorldState blockWorldState = patternHelper.translateOffset(i, j, 0);
							world.notifyNeighborsRespectDebug(blockWorldState.getPos(), Blocks.AIR, false);
						}
					}
				}
			}
		}
	}
}
