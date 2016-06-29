package turkey.witherCrumbs.listeners;

import java.util.Iterator;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;

import ganymedes01.headcrumbs.ModBlocks;
import ganymedes01.headcrumbs.tileentities.TileEntityBlockSkull;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.block.state.pattern.BlockStateMatcher;
import net.minecraft.block.state.pattern.FactoryBlockPattern;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.stats.AchievementList;
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

	private BlockPattern witherPattern;
	
    private static final Predicate<BlockWorldState> IS_SAME_SKULL = new Predicate<BlockWorldState>()
    {
        public boolean apply(@Nullable BlockWorldState p_apply_1_)
        {
            return p_apply_1_.getBlockState() != null && p_apply_1_.getBlockState().getBlock() == HeadCrumbs.SKULL && p_apply_1_.getTileEntity() instanceof TileEntitySkull && ((TileEntitySkull)p_apply_1_.getTileEntity()).getSkullType() == 1;
        }
    };
	
	@SubscribeEvent
	public void onSkullPlacedEvent(PlaceEvent e)
	{
		if(!e.getState().getBlock().equals(ModBlocks.blockSkull) || !(e.getWorld().getTileEntity(e.getPos()) instanceof TileEntityBlockSkull))
			return;

		World worldIn = e.getWorld();
		BlockPos pos = e.getPos();
		TileEntityBlockSkull te = (TileEntityBlockSkull) worldIn.getTileEntity(pos);

		if(te.getSkullType() == 1 && pos.getY() >= 2 && worldIn.getDifficulty() != EnumDifficulty.PEACEFUL && !worldIn.isRemote)
		{
			BlockPattern blockpattern = this.getWitherPattern();
			BlockPattern.PatternHelper blockpattern$patternhelper = blockpattern.match(worldIn, pos);

			if(blockpattern$patternhelper != null)
			{
				for(int i = 0; i < 3; ++i)
				{
					BlockWorldState blockworldstate = blockpattern$patternhelper.translateOffset(i, 0, 0);
					worldIn.setBlockState(blockworldstate.getPos(), blockworldstate.getBlockState().withProperty(BlockSkull.NODROP, Boolean.valueOf(true)), 2);
				}

				for(int j = 0; j < blockpattern.getPalmLength(); ++j)
				{
					for(int k = 0; k < blockpattern.getThumbLength(); ++k)
					{
						BlockWorldState blockworldstate1 = blockpattern$patternhelper.translateOffset(j, k, 0);
						worldIn.setBlockState(blockworldstate1.getPos(), Blocks.AIR.getDefaultState(), 2);
					}
				}

				BlockPos blockpos = blockpattern$patternhelper.translateOffset(1, 0, 0).getPos();
				EntityWither entitywither = new EntityWither(worldIn);
				BlockPos blockpos1 = blockpattern$patternhelper.translateOffset(1, 2, 0).getPos();
				entitywither.setLocationAndAngles((double) blockpos1.getX() + 0.5D, (double) blockpos1.getY() + 0.55D, (double) blockpos1.getZ() + 0.5D, blockpattern$patternhelper.getForwards().getAxis() == EnumFacing.Axis.X ? 0.0F : 90.0F, 0.0F);
				entitywither.renderYawOffset = blockpattern$patternhelper.getForwards().getAxis() == EnumFacing.Axis.X ? 0.0F : 90.0F;
				entitywither.ignite();

				for(EntityPlayer entityplayer : worldIn.getEntitiesWithinAABB(EntityPlayer.class, entitywither.getEntityBoundingBox().expandXyz(50.0D)))
				{
					entityplayer.addStat(AchievementList.SPAWN_WITHER);
				}

				worldIn.spawnEntityInWorld(entitywither);

				for(int l = 0; l < 120; ++l)
				{
					worldIn.spawnParticle(EnumParticleTypes.SNOWBALL, (double) blockpos.getX() + worldIn.rand.nextDouble(), (double) (blockpos.getY() - 2) + worldIn.rand.nextDouble() * 3.9D, (double) blockpos.getZ() + worldIn.rand.nextDouble(), 0.0D, 0.0D, 0.0D, new int[0]);
				}

				for(int i1 = 0; i1 < blockpattern.getPalmLength(); ++i1)
				{
					for(int j1 = 0; j1 < blockpattern.getThumbLength(); ++j1)
					{
						BlockWorldState blockworldstate2 = blockpattern$patternhelper.translateOffset(i1, j1, 0);
						worldIn.notifyNeighborsRespectDebug(blockworldstate2.getPos(), Blocks.AIR);
					}
				}
			}
		}
	}

	protected BlockPattern getWitherPattern()
	{
		if(this.witherPattern == null)
		{
			this.witherPattern = FactoryBlockPattern.start().aisle(new String[] { "^^^", "###", "~#~" }).where('#', BlockWorldState.hasState(BlockStateMatcher.forBlock(Blocks.SOUL_SAND))).where('^', IS_WITHER_SKELETON).where('~', BlockWorldState.hasState(BlockStateMatcher.forBlock(Blocks.AIR))).build();
		}

		return this.witherPattern;
	}
}
