package turkey.witherCrumbs.entities.renderers;

import org.lwjgl.opengl.GL11;

import ganymedes01.headcrumbs.utils.TextureUtils;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import turkey.witherCrumbs.entities.EntityHumanWither;
import turkey.witherCrumbs.entities.models.ModelHumanWither;

public class RenderHumanWither extends RenderLiving
{
	private int field_82419_a;

	public RenderHumanWither()
	{
		super(new ModelHumanWither(), 1.0F);
		this.field_82419_a = ((ModelHumanWither) this.mainModel).func_82903_a();
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1, double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
	 */
	public void doRender(EntityHumanWither p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		BossStatus.setBossStatus(p_76986_1_, true);
		int i = ((ModelHumanWither) this.mainModel).func_82903_a();

		if(i != this.field_82419_a)
		{
			this.field_82419_a = i;
			this.mainModel = new ModelHumanWither();
		}

		super.doRender((EntityLiving) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(EntityHumanWither entity)
	{
		return TextureUtils.getPlayerSkin(((EntityHumanWither) entity).getProfile());
	}

	/**
	 * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args: entityLiving, partialTickTime
	 */
	protected void preRenderCallback(EntityHumanWither p_77041_1_, float p_77041_2_)
	{
		int i = p_77041_1_.func_82212_n();

		if(i > 0)
		{
			float f1 = 2.0F - ((float) i - p_77041_2_) / 220.0F * 0.5F;
			GL11.glScalef(f1, f1, f1);
		}
		else
		{
			GL11.glScalef(2.0F, 2.0F, 2.0F);
		}
	}

	/**
	 * Queries whether should render the specified pass or not.
	 */
	protected int shouldRenderPass(EntityHumanWither p_77032_1_, int p_77032_2_, float p_77032_3_)
	{
		if(p_77032_1_.isArmored())
		{
			if(p_77032_1_.isInvisible())
			{
				GL11.glDepthMask(false);
			}
			else
			{
				GL11.glDepthMask(true);
			}

			if(p_77032_2_ == 1)
			{
				float f1 = (float) p_77032_1_.ticksExisted + p_77032_3_;
				this.bindEntityTexture(p_77032_1_);
				GL11.glMatrixMode(GL11.GL_TEXTURE);
				GL11.glLoadIdentity();
				float f2 = MathHelper.cos(f1 * 0.02F) * 3.0F;
				float f3 = f1 * 0.01F;
				GL11.glTranslatef(f2, f3, 0.0F);
				this.setRenderPassModel(this.mainModel);
				GL11.glMatrixMode(GL11.GL_MODELVIEW);
				GL11.glEnable(GL11.GL_BLEND);
				float f4 = 0.5F;
				GL11.glColor4f(f4, f4, f4, 1.0F);
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
				GL11.glTranslatef(0.0F, -0.01F, 0.0F);
				GL11.glScalef(1.1F, 1.1F, 1.1F);
				return 1;
			}

			if(p_77032_2_ == 2)
			{
				GL11.glMatrixMode(GL11.GL_TEXTURE);
				GL11.glLoadIdentity();
				GL11.glMatrixMode(GL11.GL_MODELVIEW);
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glDisable(GL11.GL_BLEND);
			}
		}

		return -1;
	}

	protected int inheritRenderPass(EntityHumanWither p_77035_1_, int p_77035_2_, float p_77035_3_)
	{
		return -1;
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1, double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
	 */
	public void doRender(EntityLiving p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		this.doRender((EntityHumanWither) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}

	/**
	 * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args: entityLiving, partialTickTime
	 */
	protected void preRenderCallback(EntityLivingBase p_77041_1_, float p_77041_2_)
	{
		this.preRenderCallback((EntityHumanWither) p_77041_1_, p_77041_2_);
	}

	/**
	 * Queries whether should render the specified pass or not.
	 */
	protected int shouldRenderPass(EntityLivingBase p_77032_1_, int p_77032_2_, float p_77032_3_)
	{
		return this.shouldRenderPass((EntityHumanWither) p_77032_1_, p_77032_2_, p_77032_3_);
	}

	protected int inheritRenderPass(EntityLivingBase p_77035_1_, int p_77035_2_, float p_77035_3_)
	{
		return this.inheritRenderPass((EntityHumanWither) p_77035_1_, p_77035_2_, p_77035_3_);
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1, double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
	 */
	public void doRender(EntityLivingBase p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		this.doRender((EntityHumanWither) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(Entity p_110775_1_)
	{
		return this.getEntityTexture((EntityHumanWither) p_110775_1_);
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1, double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
	 */
	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		this.doRender((EntityHumanWither) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}
}