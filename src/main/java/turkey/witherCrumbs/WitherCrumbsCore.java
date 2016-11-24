package turkey.witherCrumbs;

import org.apache.logging.log4j.Logger;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import turkey.witherCrumbs.config.ConfigLoader;
import turkey.witherCrumbs.config.CustomWitherLoader;
import turkey.witherCrumbs.entities.EntityHumanWither;
import turkey.witherCrumbs.info.CelebrityWitherRegistry;
import turkey.witherCrumbs.items.WitherCrumbsItems;
import turkey.witherCrumbs.listeners.SkullPlacedEvent;
import turkey.witherCrumbs.listeners.WitherSpawnHandler;
import turkey.witherCrumbs.proxy.CommonProxy;

@Mod(modid = WitherCrumbsCore.MODID, version = WitherCrumbsCore.VERSION, name = WitherCrumbsCore.NAME, dependencies = "required-after:headcrumbs")
public class WitherCrumbsCore
{
	public static final String MODID = "withercrumbs";
	public static final String VERSION = "@version@";
	public static final String NAME = "Wither Crumbs";

	@Instance(value = MODID)
	public static WitherCrumbsCore instance;

	@SidedProxy(clientSide = "turkey.witherCrumbs.proxy.ClientProxy", serverSide = "turkey.witherCrumbs.proxy.CommonProxy")
	public static CommonProxy proxy;

	public static Logger logger;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		logger = event.getModLog();
		ConfigLoader.loadConfigSettings(event.getSuggestedConfigurationFile(), event.getSourceFile());

		EntityRegistry.registerModEntity(EntityHumanWither.class, "Wither_Crumb", 0, instance, 512, 1, true);

		proxy.registerRenderings();
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		FMLInterModComms.sendMessage("headcrumbs", "add-username", "Turkey2349");
		FMLInterModComms.sendMessage("headcrumbs", "add-username", "KiwiFails");
		FMLInterModComms.sendMessage("headcrumbs", "add-username", "SlothMonster_");
		FMLInterModComms.sendMessage("headcrumbs", "add-username", "Darkosto");

		WitherCrumbsItems.initItems();

		MinecraftForge.EVENT_BUS.register(new SkullPlacedEvent());
		MinecraftForge.EVENT_BUS.register(new WitherSpawnHandler());

		if(event.getSide() == Side.CLIENT)
		{
			WitherCrumbsItems.registerItems();
		}
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		CustomWitherLoader.instance.loadCustomWithers();
		ItemStack stack = new ItemStack(Items.CAKE);
		stack.setStackDisplayName("Happy Birthday Darkosto!");
		CelebrityWitherRegistry.addCelebrityInfo("Darkosto", stack, false);
	}
}