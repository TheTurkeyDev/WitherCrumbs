package turkey.witherCrumbs.config;

import java.io.File;
import java.io.FileReader;
import java.util.Map.Entry;

import org.apache.logging.log4j.Level;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import turkey.witherCrumbs.WitherCrumbsCore;
import turkey.witherCrumbs.info.CelebrityWitherRegistry;
import turkey.witherCrumbs.items.WitherCrumbsItems;

public class CustomWitherLoader
{
	public static CustomWitherLoader instance;
	private JsonParser json;
	private File file;

	public CustomWitherLoader(File file)
	{
		instance = this;
		json = new JsonParser();
		this.file = file;
	}

	public void loadCustomWithers()
	{
		JsonElement fileJson;
		try
		{
			fileJson = json.parse(new FileReader(file));
		} catch(Exception e)
		{
			WitherCrumbsCore.logger.log(Level.ERROR, "Unable to parse the custom withers file! Skipping file loading.");
			WitherCrumbsCore.logger.log(Level.ERROR, "Parse Error: " + e.getMessage());
			return;
		}

		if(fileJson.isJsonNull())
			return;

		for(Entry<String, JsonElement> witherUser : fileJson.getAsJsonObject().entrySet())
		{
			JsonObject info = witherUser.getValue().getAsJsonObject();
			String name = witherUser.getKey();
			ItemStack stack = new ItemStack(WitherCrumbsItems.crumbStar, 1);

			if(info.has("DropItem"))
			{
				String jsonRaw = info.get("DropItem").getAsJsonObject().toString();
				try
				{
					String jsonEdited = this.removedKeyQuotes(jsonRaw);
					NBTBase nbtbase = JsonToNBT.getTagFromJson(jsonEdited);
					if(!(nbtbase instanceof NBTTagCompound))
					{
						WitherCrumbsCore.logger.log(Level.ERROR, "Failed to convert the JSON to NBT for: " + jsonRaw);
						continue;
					}
					else
					{
						NBTTagCompound nbtcomp = (NBTTagCompound) nbtbase;
						stack = ItemStack.loadItemStackFromNBT(nbtcomp);
						if(stack == null)
						{
							WitherCrumbsCore.logger.log(Level.ERROR, "Failed to create an itemstack from the JSON of: " + jsonEdited + " and the NBT of: " + ((NBTTagCompound) nbtbase).toString());
							continue;
						}
					}
				} catch(NBTException e1)
				{
					WitherCrumbsCore.logger.log(Level.ERROR, e1.getMessage());
					continue;
				}
			}

			WitherCrumbsCore.logger.log(Level.INFO, "Added custom WitherCrumb drop for " + name);
			CelebrityWitherRegistry.addCelebrityInfo(name, stack);
		}
	}

	public String removedKeyQuotes(String raw)
	{
		StringBuilder sb = new StringBuilder(raw.toString());
		int index = 0;
		while((index = sb.indexOf("\"", index)) != -1)
		{
			int secondQuote = sb.indexOf("\"", index + 1);
			if(secondQuote == -1)
				break;
			if(sb.charAt(secondQuote + 1) == ':')
			{
				sb.deleteCharAt(index);
				sb.delete(secondQuote - 1, secondQuote);
				index = secondQuote;
			}
			else
			{
				index++;
			}
		}
		return sb.toString();
	}
}
