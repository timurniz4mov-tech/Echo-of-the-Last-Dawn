package com.echoes.lastdawn.client;

import com.echoes.lastdawn.EchoesMod;
import com.echoes.lastdawn.entity.StoryKeeper;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class StoryKeeperModel extends HierarchicalModel<StoryKeeper> {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(new ResourceLocation(EchoesMod.MODID, "story_keeper"), "main");

    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart rightArm;
    private final ModelPart leftArm;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;
    private final ModelPart cape;

    public StoryKeeperModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.rightArm = root.getChild("right_arm");
        this.leftArm = root.getChild("left_arm");
        this.rightLeg = root.getChild("right_leg");
        this.leftLeg = root.getChild("left_leg");
        this.cape = root.getChild("cape");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        root.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F), PartPose.ZERO);
        root.addOrReplaceChild("hood", CubeListBuilder.create()
                .texOffs(32, 0).addBox(-4.5F, -9.0F, -4.5F, 9.0F, 3.0F, 9.0F), PartPose.ZERO);
        root.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F), PartPose.ZERO);
        root.addOrReplaceChild("collar", CubeListBuilder.create()
                .texOffs(32, 16).addBox(-4.5F, -0.5F, -2.5F, 9.0F, 2.0F, 5.0F), PartPose.ZERO);
        root.addOrReplaceChild("right_arm", CubeListBuilder.create()
                .texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(-5.0F, 2.0F, 0.0F));
        root.addOrReplaceChild("left_arm", CubeListBuilder.create()
                .texOffs(40, 32).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(5.0F, 2.0F, 0.0F));
        root.addOrReplaceChild("right_leg", CubeListBuilder.create()
                .texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(-1.9F, 12.0F, 0.0F));
        root.addOrReplaceChild("left_leg", CubeListBuilder.create()
                .texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(1.9F, 12.0F, 0.0F));
        root.addOrReplaceChild("cape", CubeListBuilder.create()
                .texOffs(32, 32).addBox(-4.5F, 0.0F, 2.0F, 9.0F, 12.0F, 1.0F), PartPose.ZERO);

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public ModelPart root() {
        return root;
    }

    @Override
    public void setupAnim(StoryKeeper entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        float walk = limbSwing * 0.6662F;
        rightArm.xRot = Mth.cos(walk + Mth.PI) * 1.8F * limbSwingAmount - 0.08F;
        leftArm.xRot = Mth.cos(walk) * 1.8F * limbSwingAmount - 0.08F;
        rightLeg.xRot = Mth.cos(walk) * 1.35F * limbSwingAmount;
        leftLeg.xRot = Mth.cos(walk + Mth.PI) * 1.35F * limbSwingAmount;

        head.yRot = netHeadYaw * (Mth.PI / 180.0F);
        head.xRot = headPitch * (Mth.PI / 180.0F) + Mth.sin(ageInTicks * 0.045F) * 0.025F;
        body.y = Mth.sin(ageInTicks * 0.08F) * 0.12F;
        rightArm.zRot = -0.06F + Mth.sin(ageInTicks * 0.06F) * 0.025F;
        leftArm.zRot = 0.06F - Mth.sin(ageInTicks * 0.06F) * 0.025F;
        cape.xRot = 0.04F + Mth.sin(ageInTicks * 0.07F) * 0.025F + limbSwingAmount * 0.12F;
    }
}
