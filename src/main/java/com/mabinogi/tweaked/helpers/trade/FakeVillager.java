package com.mabinogi.tweaked.helpers.trade;

import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

import javax.annotation.Nullable;

public class FakeVillager implements IMerchant
{
	@Override
	public void setCustomer(@Nullable EntityPlayer player)
	{

	}

	@Nullable
	@Override
	public EntityPlayer getCustomer()
	{
		return null;
	}

	@Nullable
	@Override
	public MerchantRecipeList getRecipes(EntityPlayer player)
	{
		return null;
	}

	@Override
	public void setRecipes(@Nullable MerchantRecipeList recipeList)
	{

	}

	@Override
	public void useRecipe(MerchantRecipe recipe)
	{

	}

	@Override
	public void verifySellingItem(ItemStack stack)
	{

	}

	@Override
	public ITextComponent getDisplayName()
	{
		return null;
	}

	@Override
	public World getWorld()
	{
		return DimensionManager.getWorld(0);
	}

	@Override
	public BlockPos getPos()
	{
		int x = DimensionManager.getWorld(0).getWorldInfo().getSpawnX();
		int y = DimensionManager.getWorld(0).getWorldInfo().getSpawnY();
		int z = DimensionManager.getWorld(0).getWorldInfo().getSpawnZ();

		return new BlockPos(x, y, z);
	}
}
