package turkey.witherCrumbs;

import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.entity.EntityList;
import net.minecraftforge.common.MinecraftForge;
import turkey.witherCrumbs.config.ConfigLoader;
import turkey.witherCrumbs.config.CustomWitherLoader;
import turkey.witherCrumbs.entities.EntityHumanWither;
import turkey.witherCrumbs.items.WitherCrumbsItems;
import turkey.witherCrumbs.listeners.SkullPlacedEvent;
import turkey.witherCrumbs.proxy.CommonProxy;

@Mod(modid = WitherCrumbsCore.MODID, version = WitherCrumbsCore.VERSION, name = WitherCrumbsCore.NAME, dependencies = "required-after:headcrumbs")
public class WitherCrumbsCore
{
	public static final String MODID = "withercrumbs";
	public static final String VERSION = "@version@";
	public static final String NAME = "Wither Crumbs";

	@Instance(value = MODID)
	public static WitherCrumbsCore instance;

	@SidedProxy(clientSide = "turkey.witherCrumbs.proxy.ClientProxy", serverSide = "turkey.witherCrumbs.CommonProxy")
	public static CommonProxy proxy;

	public static Logger logger;

	@SuppressWarnings("unchecked")
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		FMLInterModComms.sendMessage("headcrumbs", "add-username", "Turkey2349");
		FMLInterModComms.sendMessage("headcrumbs", "add-username", "KiwiFails");
		FMLInterModComms.sendMessage("headcrumbs", "add-username", "SlothMonster_");
		FMLInterModComms.sendMessage("headcrumbs", "add-username", "Darkosto");

		proxy.registerRenderings();
		
		WitherCrumbsItems.initItems();
		

		EntityList.stringToClassMapping.put("Wither_Crumb", EntityHumanWither.class);
		int id = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerModEntity(EntityHumanWither.class, "Wither_Crumb", id, instance, 512, 1, true);

		FMLCommonHandler.instance().bus().register(new SkullPlacedEvent());
		MinecraftForge.EVENT_BUS.register(new SkullPlacedEvent());
	}

	@EventHandler
	public void load(FMLPreInitializationEvent event)
	{
		logger = event.getModLog();
		ConfigLoader.loadConfigSettings(event.getSuggestedConfigurationFile(), event.getSourceFile());
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		CustomWitherLoader.instance.loadCustomWithers();
	}
}