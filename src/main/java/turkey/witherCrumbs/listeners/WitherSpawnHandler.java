package turkey.witherCrumbs.listeners;

import ganymedes01.headcrumbs.entity.EntityHuman;
import net.minecraft.entity.boss.EntityWither;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import turkey.witherCrumbs.config.WitherCrumbSettings;
import turkey.witherCrumbs.entities.EntityHumanWither;

public class WitherSpawnHandler {

	@SubscribeEvent
	public void onLivingSpawn(EntityJoinWorldEvent event) {
		if(WitherCrumbSettings.replaceVanillaWither && event.getEntity().getClass() == EntityWither.class) {
			event.setCanceled(true);
			EntityHumanWither entityWither = new EntityHumanWither(event.getWorld());
			entityWither.setRealWither(true);
			entityWither.setUsername(EntityHuman.getRandomUsername(event.getWorld().rand));
			entityWither.setLocationAndAngles(event.getEntity().posX, event.getEntity().posY, event.getEntity().posZ, event.getEntity().rotationYaw, 0f);
			entityWither.renderYawOffset = ((EntityWither) event.getEntity()).renderYawOffset;
			entityWither.ignite();
			event.getWorld().spawnEntityInWorld(entityWither);
		}
	}

}
