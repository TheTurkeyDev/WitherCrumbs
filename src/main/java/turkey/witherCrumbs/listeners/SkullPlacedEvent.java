package turkey.witherCrumbs.listeners;

import java.util.Iterator;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import ganymedes01.headcrumbs.ModBlocks;
import ganymedes01.headcrumbs.tileentities.TileEntityBlockSkull;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.stats.AchievementList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;
import turkey.witherCrumbs.entities.EntityHumanWither;

public class SkullPlacedEvent
{

	@SuppressWarnings("rawtypes")
	@SubscribeEvent
	public void onSkullPlacedEvent(PlaceEvent e)
	{
		if(!e.block.equals(ModBlocks.blockSkull) || !(e.world.getTileEntity(e.x, e.y, e.z) instanceof TileEntityBlockSkull))
			return;

		World world = e.world;
		int blockX = e.x;
		int blockY = e.y;
		int blockZ = e.z;
		TileEntityBlockSkull tileSkull = (TileEntityBlockSkull) e.world.getTileEntity(e.x, e.y, e.z);

		if(tileSkull.func_145904_a() == 0 && blockY >= 2 && world.difficultySetting != EnumDifficulty.PEACEFUL && !world.isRemote)
		{
			int l;
			EntityHumanWither entitywither;
			Iterator iterator;
			EntityPlayer entityplayer;
			int i1;

			for(l = -2; l <= 0; ++l)
			{
				// System.out.println(this.func_149966_a(world, tileSkull, blockX, blockY, blockZ + l, 1));
				if(world.getBlock(blockX, blockY - 1, blockZ + l) == Blocks.soul_sand && world.getBlock(blockX, blockY - 1, blockZ + l + 1) == Blocks.soul_sand && world.getBlock(blockX, blockY - 2, blockZ + l + 1) == Blocks.soul_sand && world.getBlock(blockX, blockY - 1, blockZ + l + 2) == Blocks.soul_sand && this.func_149966_a(world, tileSkull, blockX, blockY, blockZ + l) && this.func_149966_a(world, tileSkull, blockX, blockY, blockZ + l + 1) && this.func_149966_a(world, tileSkull, blockX, blockY, blockZ + l + 2))
				{
					world.setBlockMetadataWithNotify(blockX, blockY, blockZ + l, 8, 2);
					world.setBlockMetadataWithNotify(blockX, blockY, blockZ + l + 1, 8, 2);
					world.setBlockMetadataWithNotify(blockX, blockY, blockZ + l + 2, 8, 2);
					world.setBlock(blockX, blockY, blockZ + l, Block.getBlockById(0), 0, 2);
					world.setBlock(blockX, blockY, blockZ + l + 1, Block.getBlockById(0), 0, 2);
					world.setBlock(blockX, blockY, blockZ + l + 2, Block.getBlockById(0), 0, 2);
					world.setBlock(blockX, blockY - 1, blockZ + l, Block.getBlockById(0), 0, 2);
					world.setBlock(blockX, blockY - 1, blockZ + l + 1, Block.getBlockById(0), 0, 2);
					world.setBlock(blockX, blockY - 1, blockZ + l + 2, Block.getBlockById(0), 0, 2);
					world.setBlock(blockX, blockY - 2, blockZ + l + 1, Block.getBlockById(0), 0, 2);

					if(!world.isRemote)
					{
						entitywither = new EntityHumanWither(world);
						entitywither.setProfile(tileSkull.func_152108_a());
						System.out.println(entitywither);
						entitywither.setLocationAndAngles((double) blockX + 0.5D, (double) blockY - 1.45D, (double) (blockZ + l) + 1.5D, 90.0F, 0.0F);
						entitywither.renderYawOffset = 90.0F;
						entitywither.func_82206_m();
						entitywither.setCustomNameTag(tileSkull.func_152108_a().getName());

						if(!world.isRemote)
						{
							iterator = world.getEntitiesWithinAABB(EntityPlayer.class, entitywither.boundingBox.expand(50.0D, 50.0D, 50.0D)).iterator();

							while(iterator.hasNext())
							{
								entityplayer = (EntityPlayer) iterator.next();
								entityplayer.triggerAchievement(AchievementList.field_150963_I);
							}
						}

						world.spawnEntityInWorld(entitywither);
					}

					for(i1 = 0; i1 < 120; ++i1)
					{
						world.spawnParticle("snowballpoof", (double) blockX + world.rand.nextDouble(), (double) (blockY - 2) + world.rand.nextDouble() * 3.9D, (double) (blockZ + l + 1) + world.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
					}

					world.notifyBlockChange(blockX, blockY, blockZ + l, Block.getBlockById(0));
					world.notifyBlockChange(blockX, blockY, blockZ + l + 1, Block.getBlockById(0));
					world.notifyBlockChange(blockX, blockY, blockZ + l + 2, Block.getBlockById(0));
					world.notifyBlockChange(blockX, blockY - 1, blockZ + l, Block.getBlockById(0));
					world.notifyBlockChange(blockX, blockY - 1, blockZ + l + 1, Block.getBlockById(0));
					world.notifyBlockChange(blockX, blockY - 1, blockZ + l + 2, Block.getBlockById(0));
					world.notifyBlockChange(blockX, blockY - 2, blockZ + l + 1, Block.getBlockById(0));
					return;
				}
			}

			for(l = -2; l <= 0; ++l)
			{
				if(world.getBlock(blockX + l, blockY - 1, blockZ) == Blocks.soul_sand && world.getBlock(blockX + l + 1, blockY - 1, blockZ) == Blocks.soul_sand && world.getBlock(blockX + l + 1, blockY - 2, blockZ) == Blocks.soul_sand && world.getBlock(blockX + l + 2, blockY - 1, blockZ) == Blocks.soul_sand && this.func_149966_a(world, tileSkull, blockX + l, blockY, blockZ) && this.func_149966_a(world, tileSkull, blockX + l + 1, blockY, blockZ) && this.func_149966_a(world, tileSkull, blockX + l + 2, blockY, blockZ))
				{
					world.setBlockMetadataWithNotify(blockX + l, blockY, blockZ, 8, 2);
					world.setBlockMetadataWithNotify(blockX + l + 1, blockY, blockZ, 8, 2);
					world.setBlockMetadataWithNotify(blockX + l + 2, blockY, blockZ, 8, 2);
					world.setBlock(blockX + l, blockY, blockZ, Block.getBlockById(0), 0, 2);
					world.setBlock(blockX + l + 1, blockY, blockZ, Block.getBlockById(0), 0, 2);
					world.setBlock(blockX + l + 2, blockY, blockZ, Block.getBlockById(0), 0, 2);
					world.setBlock(blockX + l, blockY - 1, blockZ, Block.getBlockById(0), 0, 2);
					world.setBlock(blockX + l + 1, blockY - 1, blockZ, Block.getBlockById(0), 0, 2);
					world.setBlock(blockX + l + 2, blockY - 1, blockZ, Block.getBlockById(0), 0, 2);
					world.setBlock(blockX + l + 1, blockY - 2, blockZ, Block.getBlockById(0), 0, 2);

					if(!world.isRemote)
					{
						entitywither = new EntityHumanWither(world);
						entitywither.setProfile(tileSkull.func_152108_a());
						System.out.println(entitywither);
						entitywither.setLocationAndAngles((double) (blockX + l) + 1.5D, (double) blockY - 1.45D, (double) blockZ + 0.5D, 0.0F, 0.0F);
						entitywither.func_82206_m();
						entitywither.setCustomNameTag(tileSkull.func_152108_a().getName());

						if(!world.isRemote)
						{
							iterator = world.getEntitiesWithinAABB(EntityPlayer.class, entitywither.boundingBox.expand(50.0D, 50.0D, 50.0D)).iterator();

							while(iterator.hasNext())
							{
								entityplayer = (EntityPlayer) iterator.next();
								entityplayer.triggerAchievement(AchievementList.field_150963_I);
							}
						}

						world.spawnEntityInWorld(entitywither);
					}

					for(i1 = 0; i1 < 120; ++i1)
					{
						world.spawnParticle("snowballpoof", (double) (blockX + l + 1) + world.rand.nextDouble(), (double) (blockY - 2) + world.rand.nextDouble() * 3.9D, (double) blockZ + world.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
					}

					world.notifyBlockChange(blockX + l, blockY, blockZ, Block.getBlockById(0));
					world.notifyBlockChange(blockX + l + 1, blockY, blockZ, Block.getBlockById(0));
					world.notifyBlockChange(blockX + l + 2, blockY, blockZ, Block.getBlockById(0));
					world.notifyBlockChange(blockX + l, blockY - 1, blockZ, Block.getBlockById(0));
					world.notifyBlockChange(blockX + l + 1, blockY - 1, blockZ, Block.getBlockById(0));
					world.notifyBlockChange(blockX + l + 2, blockY - 1, blockZ, Block.getBlockById(0));
					world.notifyBlockChange(blockX + l + 1, blockY - 2, blockZ, Block.getBlockById(0));
					return;
				}
			}
		}
	}

	private boolean func_149966_a(World p_149966_1_, TileEntityBlockSkull placed, int p_149966_2_, int p_149966_3_, int p_149966_4_)
	{
		if(p_149966_1_.getBlock(p_149966_2_, p_149966_3_, p_149966_4_) != ModBlocks.blockSkull)
		{
			return false;
		}
		else
		{

			TileEntity tileentity = p_149966_1_.getTileEntity(p_149966_2_, p_149966_3_, p_149966_4_);
			if(tileentity != null && tileentity instanceof TileEntityBlockSkull)
			{
				return ((TileEntityBlockSkull) tileentity).func_152108_a().getName().equalsIgnoreCase(placed.func_152108_a().getName());
			}
			return false;
		}
	}
}
