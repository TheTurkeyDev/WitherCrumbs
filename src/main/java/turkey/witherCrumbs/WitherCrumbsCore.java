package turkey.witherCrumbs;

import org.apache.logging.log4j.Logger;

import com.google.gson.JsonElement;
import com.theprogrammingturkey.gobblecore.IModCore;
import com.theprogrammingturkey.gobblecore.entity.EntityLoader;
import com.theprogrammingturkey.gobblecore.entity.EntityManager;
import com.theprogrammingturkey.gobblecore.entity.IEntityHandler;
import com.theprogrammingturkey.gobblecore.items.ItemManager;
import com.theprogrammingturkey.gobblecore.managers.WebHookManager;
import com.theprogrammingturkey.gobblecore.managers.WebHookManager.ModWebHook;
import com.theprogrammingturkey.gobblecore.modhooks.BaseModHook;
import com.theprogrammingturkey.gobblecore.modhooks.ModHookManager;
import com.theprogrammingturkey.gobblecore.proxy.ProxyManager;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import turkey.witherCrumbs.config.ConfigLoader;
import turkey.witherCrumbs.config.CustomWitherLoader;
import turkey.witherCrumbs.entities.EntityHumanWither;
import turkey.witherCrumbs.entities.renderers.RenderHumanWither;
import turkey.witherCrumbs.info.CelebrityWitherRegistry;
import turkey.witherCrumbs.items.WitherCrumbsItems;
import turkey.witherCrumbs.proxy.CommonProxy;

@Mod(modid = WitherCrumbsCore.MODID, version = WitherCrumbsCore.VERSION, name = WitherCrumbsCore.NAME, dependencies = "required-after:headcrumbs,required-after:gobblecore")
public class WitherCrumbsCore implements IModCore
{
	public static final String MODID = "withercrumbs";
	public static final String VERSION = "@version@";
	public static final String NAME = "Wither Crumbs";

	@Instance(value = MODID)
	public static WitherCrumbsCore instance;

	@SidedProxy(clientSide = "turkey.witherCrumbs.proxy.ClientProxy", serverSide = "turkey.witherCrumbs.proxy.CommonProxy")
	public static CommonProxy proxy;

	public static Logger logger;

	// @formatter:off
	private static String[] mhfNames = {"MHF_Herobrine", "MHF_Enderman", "MHF_Sheep", "MHF_Cow", "MHF_Villager",
			"MHF_Blaze", "MHF_MushroomCow", "MHF_Slime", "MHF_Spider", "MHF_Chicken", "MHF_Ghast", "MHF_LavaSlime",
			"MHF_Squid", "MHF_PigZombie", "MHF_Pig", "MHF_CaveSpider", "MHF_Golem", "MHF_Ocelot"};
	// @formatter:on

	public WitherCrumbsCore()
	{
		ItemManager.registerItemHandler(new WitherCrumbsItems(), this);
		EntityManager.registerEntityHandler(new IEntityHandler()
		{
			@Override
			public void registerEntities(EntityLoader loader)
			{
				loader.registerEntity("Wither_Crumb", EntityHumanWither.class, 512, 1, true);
			}

			@Override
			public void registerRenderings(EntityLoader loader)
			{
				loader.registerEntityRendering(EntityHumanWither.class, RenderHumanWither.class);
			}
		}, this);
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		logger = event.getModLog();
		ConfigLoader.loadConfigSettings(event.getSuggestedConfigurationFile(), event.getSourceFile());

		ProxyManager.registerModProxy(proxy);

		WebHookManager.registerHook(new ModWebHook(this)
		{
			@Override
			public void onResponse(JsonElement json)
			{
			}
		});
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		FMLInterModComms.sendMessage("headcrumbs", "add-username", "Turkey2349");
		FMLInterModComms.sendMessage("headcrumbs", "add-username", "KiwiFails");
		FMLInterModComms.sendMessage("headcrumbs", "add-username", "SlothMonster_");
		FMLInterModComms.sendMessage("headcrumbs", "add-username", "Darkosto");

		for(String name : mhfNames)
		{
			FMLInterModComms.sendMessage("headcrumbs", "add-username", name);
			CelebrityWitherRegistry.addCelebrityInfo(name, new ItemStack(WitherCrumbsItems.crumbStar), name.replace("MHF_", "") + " Wither");
		}
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		CustomWitherLoader.instance.loadCustomWithers();
		ItemStack stack = new ItemStack(Items.CAKE);
		stack.setStackDisplayName("Happy Birthday Darkosto!");
		CelebrityWitherRegistry.addCelebrityInfo("Darkosto", stack);

		ModHookManager.loadModHook(new BaseModHook("chancecubes")
		{
			@Override
			public void initHook()
			{
				CelebrityWitherRegistry.addCelebrityInfo("Turkey2349", new ItemStack(Block.REGISTRY.getObject(new ResourceLocation("chancecubes", "chance_Icosahedron"))));
			}
		});
	}

	@Override
	public String getModID()
	{
		return MODID;
	}

	@Override
	public String getName()
	{
		return NAME;
	}

	@Override
	public String getVersion()
	{
		return VERSION;
	}
}