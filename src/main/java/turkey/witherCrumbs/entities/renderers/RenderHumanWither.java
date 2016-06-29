package turkey.witherCrumbs.entities.renderers;

import ganymedes01.headcrumbs.utils.TextureUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import turkey.witherCrumbs.entities.EntityHumanWither;
import turkey.witherCrumbs.entities.models.ModelHumanWither;

@SideOnly(Side.CLIENT)
public class RenderHumanWither extends RenderLiving<EntityHumanWither>
{
    public RenderHumanWither(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelHumanWither(), 1.0F);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityHumanWither entity)
	{
		return TextureUtils.getPlayerSkin(((EntityHumanWither) entity).getProfile());
	}

    /**
     * Allows the render to do state modifications necessary before the model is rendered.
     */
    protected void preRenderCallback(EntityHumanWither entitylivingbaseIn, float partialTickTime)
    {
        float f = 2.0F;
        int i = entitylivingbaseIn.getInvulTime();

        if (i > 0)
        {
            f -= ((float)i - partialTickTime) / 220.0F * 0.5F;
        }

        GlStateManager.scale(f, f, f);
    }
}