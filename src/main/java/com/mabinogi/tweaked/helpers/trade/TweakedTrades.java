package com.mabinogi.tweaked.helpers.trade;

import com.mabinogi.tweaked.Tweaked;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;
import net.minecraft.world.storage.MapDecoration;
import net.minecraft.world.storage.loot.RandomValueRange;

import java.util.Locale;
import java.util.Random;

import static com.mabinogi.tweaked.Tweaked.*;

public class TweakedTrades
{
	public static class TweakedSimpleTrade implements EntityVillager.ITradeList
	{
		public ItemStack inputA;
		public ItemStack inputB;
		public ItemStack output;

		public TweakedSimpleTrade(ItemStack inputA, ItemStack inputB, ItemStack output)
		{
			this.inputA = inputA;
			this.inputB = inputB;
			this.output = output;
		}

		public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random)
		{
			if (output == null)
			{
				LOG.warn("Warning : Invalid trade recipe, no output");
				return;
			}

			if (inputA == null && inputB == null)
			{
				LOG.warn("Warning : Invalid trade recipe, no inputs");
				return;
			}

			if (inputA == null)
			{
				recipeList.add(new MerchantRecipe(inputB, output));
			}
			else if (inputB == null)
			{
				recipeList.add(new MerchantRecipe(inputA, output));
			}
			else
			{
				recipeList.add(new MerchantRecipe(inputA, inputB, output));
			}
		}
	}

	public static class TweakedEnchantedTrade implements EntityVillager.ITradeList
	{
		public ItemStack inputA;
		public ItemStack output;
		public int powerMin;
		public int powerMax;
		public boolean treasure;

		public TweakedEnchantedTrade(ItemStack inputA, ItemStack output, int powerMin, int powerMax, boolean treasure)
		{
			this.inputA = inputA;
			this.output = output;
			this.powerMin = powerMin;
			this.powerMax = powerMax;
			this.treasure = treasure;
		}

		public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random)
		{
			if (output == null)
			{
				LOG.warn("Warning : Invalid trade recipe, no output");
				return;
			}

			if (inputA == null)
			{
				LOG.warn("Warning : Invalid trade recipe, no input");
				return;
			}

			ItemStack stack = output.copy();

			Random rand = new Random();
			RandomValueRange range = new RandomValueRange(powerMin, powerMax);
			stack = EnchantmentHelper.addRandomEnchantment(rand, stack, range.generateInt(rand), treasure);

			recipeList.add(new MerchantRecipe(inputA, stack));
		}
	}

	public static class TweakedMapTrade implements EntityVillager.ITradeList
	{
		public ItemStack inputA;
		public ItemStack inputB;
		public String mapType;

		public TweakedMapTrade(ItemStack inputA, ItemStack inputB, String mapType)
		{
			this.inputA = inputA;
			this.inputB = inputB;
			this.mapType = mapType;
		}

		public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random)
		{
			if (inputA == null && inputB == null)
			{
				LOG.warn("Warning : Invalid trade recipe, no inputs");
				return;
			}

			//generate map
			World world = merchant.getWorld();
			BlockPos blockpos = world.findNearestStructure(mapType, merchant.getPos(), true);
			if (blockpos != null)
			{
				ItemStack stack = ItemMap.setupNewMap(world, (double)blockpos.getX(), (double)blockpos.getZ(), (byte)2, true, true);
				ItemMap.renderBiomePreviewMap(world, stack);

				//generate marker depending on mapType
				if (mapType.equals("Monument"))
				{
					MapData.addTargetDecoration(stack, blockpos, "+", MapDecoration.Type.MONUMENT);
				}
				else if (mapType.equals("Mansion"))
				{
					MapData.addTargetDecoration(stack, blockpos, "+", MapDecoration.Type.MANSION);
				}
				else
				{
					MapData.addTargetDecoration(stack, blockpos, "+", MapDecoration.Type.TARGET_X);
				}
				stack.setTranslatableName("filled_map." + mapType.toLowerCase(Locale.ROOT));


				if (inputA == null)
				{
					recipeList.add(new MerchantRecipe(inputB, stack));
				}
				else if (inputB == null)
				{
					recipeList.add(new MerchantRecipe(inputA, stack));
				}
				else
				{
					recipeList.add(new MerchantRecipe(inputA, inputB, stack));
				}
			}
		}
	}
}
