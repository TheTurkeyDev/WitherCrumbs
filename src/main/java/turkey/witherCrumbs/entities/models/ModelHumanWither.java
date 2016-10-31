package turkey.witherCrumbs.entities.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBoat;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import turkey.witherCrumbs.entities.EntityHumanWither;

public class ModelHumanWither extends ModelBase {

	private final ModelRenderer[] bodyParts;
	private final ModelRenderer[] headParts;

	public ModelHumanWither() {
		this.textureWidth = 64;
		this.textureHeight = 64;

		bodyParts = new ModelRenderer[3];
		bodyParts[0] = new ModelRenderer(this, 11, 17);
		bodyParts[0].setTextureSize(this.textureWidth, this.textureHeight);
		bodyParts[0].addBox(-10f, 3.9f, -0.5f, 20, 3, 3);

		bodyParts[1] = new ModelRenderer(this);
		bodyParts[1].setTextureSize(this.textureWidth, this.textureHeight);
		bodyParts[1].setRotationPoint(-2f, 6.9f, -0.5f);
		bodyParts[1].setTextureOffset(17, 19).addBox(0f, 0f, 0f, 3, 10, 3);
		bodyParts[1].setTextureOffset(14, 24).addBox(-4f, 1.5f, 0.5f, 11, 2, 2);
		bodyParts[1].setTextureOffset(14, 22).addBox(-4f, 4f, 0.5f, 11, 2, 2, true);
		bodyParts[1].setTextureOffset(14, 28).addBox(-4f, 6.5f, 0.5f, 11, 2, 2);

		bodyParts[2] = new ModelRenderer(this, 4, 20);
		bodyParts[2].addBox(0f, 0f, 0f, 3, 6, 3);

		headParts = new ModelRenderer[3];
		headParts[0] = new ModelRenderer(this, 0, 0);
		headParts[0].setTextureSize(this.textureWidth, this.textureHeight);
		headParts[0].addBox(-4f, -4f, -4f, 8, 8, 8);

		headParts[1] = new ModelRenderer(this, 0, 0);
		headParts[1].setTextureSize(this.textureWidth, this.textureHeight);
		headParts[1].addBox(-4f, -4f, -4f, 8, 8, 8);
		headParts[1].rotationPointX = -9f;
		headParts[1].rotationPointY = 4f;

		headParts[2] = new ModelRenderer(this, 0, 0);
		headParts[2].setTextureSize(this.textureWidth, this.textureHeight);
		headParts[2].addBox(-4f, -4f, -4f, 8, 8, 8);
		headParts[2].rotationPointX = 9f;
		headParts[2].rotationPointY = 4f;
	}

	@Override
	public void render(Entity entity, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
		this.setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, entity);

		for (ModelRenderer head : headParts) {
			head.render(p_78088_7_);
		}

		for (ModelRenderer body : bodyParts) {
			body.render(p_78088_7_);
		}
	}

	@Override
	public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
		float f6 = MathHelper.cos(p_78087_3_ * 0.1f);
		this.bodyParts[1].rotateAngleX = (0.065f + 0.05f * f6) * (float) Math.PI;
		this.bodyParts[2].setRotationPoint(-2f, 6.9f + MathHelper.cos(this.bodyParts[1].rotateAngleX) * 10f, -0.5f + MathHelper.sin(this.bodyParts[1].rotateAngleX) * 10f);
		this.bodyParts[2].rotateAngleX = (0.265f + 0.1f * f6) * (float) Math.PI;
		this.headParts[0].rotateAngleY = p_78087_4_ / (180f / (float) Math.PI);
		this.headParts[0].rotateAngleX = p_78087_5_ / (180f / (float) Math.PI);
	}

	@Override
	public void setLivingAnimations(EntityLivingBase entity, float p_78086_2_, float p_78086_3_, float p_78086_4_) {
		EntityHumanWither entityWither = (EntityHumanWither) entity;

		for (int i = 1; i < 3; i++) {
			this.headParts[i].rotateAngleY = (entityWither.getHeadYRotation(i - 1) - entity.renderYawOffset) / (180f / (float) Math.PI);
			this.headParts[i].rotateAngleX = entityWither.getHeadXRotation(i - 1) / (180f / (float) Math.PI);
		}
	}
}