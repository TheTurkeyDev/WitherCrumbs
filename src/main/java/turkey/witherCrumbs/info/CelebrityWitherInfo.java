package turkey.witherCrumbs.info;

public class CelebrityWitherInfo
{
	private String celebrityKey = "";
	private boolean hasCustomSounds = false;
	
	public CelebrityWitherInfo(String name, boolean customSounds)
	{
		this.celebrityKey = name;
		this.hasCustomSounds = customSounds;
	}
}