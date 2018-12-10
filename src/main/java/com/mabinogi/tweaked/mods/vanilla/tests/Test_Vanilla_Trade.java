package com.mabinogi.tweaked.mods.vanilla.tests;

import com.mabinogi.tweaked.api.annotations.TweakedTest;
import com.mabinogi.tweaked.api.test.ITweakedTest;
import com.mabinogi.tweaked.controllers.TweakedReflection;
import com.mabinogi.tweaked.helpers.trade.FakeVillager;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

import java.util.List;
import java.util.Random;

@SuppressWarnings("unused")
public class Test_Vanilla_Trade
{
	//**************************************************************************************//
	//											setName										//
	//**************************************************************************************//

	@TweakedTest()
	public static class Test_Trade_SetName implements ITweakedTest
	{
		@Override
		public String getFilename()
		{
			return "trade";
		}

		@Override
		public String getTestDescription()
		{
			return "trade - setname";
		}

		@Override
		public String[] getVariables()
		{
			return new String[0];
		}

		@Override
		public String[] getActions()
		{
			return new String[] { "tweak.trade.setName(\"minecraft:farmer/fisherman\", \"fishlord\");" };
		}

		@Override
		public boolean runTest(World world)
		{
			for (VillagerRegistry.VillagerProfession profession : ForgeRegistries.VILLAGER_PROFESSIONS)
			{
				List<VillagerRegistry.VillagerCareer> careers = TweakedReflection.getVillagerCareers(profession);
				if (careers != null)
				{
					for (VillagerRegistry.VillagerCareer career : careers)
					{
						if ("minecraft:farmer/fishlord".equals(profession.getRegistryName() + "/" + career.getName()))
						{
							return true;
						}
					}
				}
			}
			return false;
		}
	}


	//**************************************************************************************//
	//										clear											//
	//**************************************************************************************//

	@TweakedTest()
	public static class Test_Trade_Clear implements ITweakedTest
	{
		@Override
		public String getFilename()
		{
			return "trade";
		}

		@Override
		public String getTestDescription()
		{
			return "trade - clear";
		}

		@Override
		public String[] getVariables()
		{
			return new String[0];
		}

		@Override
		public String[] getActions()
		{
			return new String[]
				{
					"tweak.trade.clear(\"minecraft:librarian/cartographer\");",
					"tweak.trade.clear(\"minecraft:farmer/farmer\");",
					"tweak.trade.clear(\"minecraft:farmer/shepherd\");",
					"tweak.trade.clear(\"minecraft:farmer/fletcher\");"
				};
		}

		@Override
		public boolean runTest(World world)
		{
			for (VillagerRegistry.VillagerProfession profession : ForgeRegistries.VILLAGER_PROFESSIONS)
			{
				List<VillagerRegistry.VillagerCareer> careers = TweakedReflection.getVillagerCareers(profession);
				if (careers != null)
				{
					for (VillagerRegistry.VillagerCareer career : careers)
					{
						if ("minecraft:farmer/fletcher".equals(profession.getRegistryName() + "/" + career.getName()))
						{
							List<List<EntityVillager.ITradeList>> trades = TweakedReflection.getVillagerTrades(career);
							return trades.isEmpty();
						}
					}
				}
			}
			return false;
		}
	}


	//**************************************************************************************//
	//										addCareer										//
	//**************************************************************************************//

	@TweakedTest()
	public static class Test_Trade_AddCareer implements ITweakedTest
	{
		@Override
		public String getFilename()
		{
			return "trade";
		}

		@Override
		public String getTestDescription()
		{
			return "trade - addcareer";
		}

		@Override
		public String[] getVariables()
		{
			return new String[0];
		}

		@Override
		public String[] getActions()
		{
			return new String[] { "tweak.trade.addCareer(\"minecraft:farmer\", \"farmlord\");" };
		}

		@Override
		public boolean runTest(World world)
		{
			for (VillagerRegistry.VillagerProfession profession : ForgeRegistries.VILLAGER_PROFESSIONS)
			{
				List<VillagerRegistry.VillagerCareer> careers = TweakedReflection.getVillagerCareers(profession);
				if (careers != null)
				{
					for (VillagerRegistry.VillagerCareer career : careers)
					{
						if ("minecraft:farmer/farmlord".equals(profession.getRegistryName() + "/" + career.getName()))
						{
							return true;
						}
					}
				}
			}
			return false;
		}
	}


	//**************************************************************************************//
	//										addTrade										//
	//**************************************************************************************//

	@TweakedTest()
	public static class Test_Trade_AddTrade implements ITweakedTest
	{
		@Override
		public String getFilename()
		{
			return "trade";
		}

		@Override
		public String getTestDescription()
		{
			return "trade - addtrade";
		}

		@Override
		public String[] getVariables()
		{
			return new String[0];
		}

		@Override
		public String[] getActions()
		{
			return new String[]
				{
					"tweak.trade.addTrade(\"minecraft:farmer/farmer\", 1, <minecraft:apple:0:10>, <minecraft:diamond>);",
					"tweak.trade.addTrade(\"minecraft:farmer/shepherd\", 1, <minecraft:apple:0:10>, <minecraft:diamond>, <minecraft:diamond_sword>.enchant{minecraft:sharpness:1});"
				};
		}

		@Override
		public boolean runTest(World world)
		{
			for (VillagerRegistry.VillagerProfession profession : ForgeRegistries.VILLAGER_PROFESSIONS)
			{
				List<VillagerRegistry.VillagerCareer> careers = TweakedReflection.getVillagerCareers(profession);
				if (careers != null)
				{
					for (VillagerRegistry.VillagerCareer career : careers)
					{
						if ("minecraft:farmer/farmer".equals(profession.getRegistryName() + "/" + career.getName()))
						{
							List<List<EntityVillager.ITradeList>> trades = TweakedReflection.getVillagerTrades(career);

							//safety check
							if (trades.isEmpty()) return false;
							if (trades.get(0).isEmpty()) return false;

							//get the trade where we expect it to be
							EntityVillager.ITradeList trade = trades.get(0).get(0);

							//build the trade
							MerchantRecipeList recipeList = new MerchantRecipeList();
							trade.addMerchantRecipe(new FakeVillager(), recipeList, new Random());

							//safety check
							if (recipeList.isEmpty()) return false;

							//check the trade is correct
							MerchantRecipe recipe = recipeList.get(0);
							if (recipe.getItemToBuy().getItem() == Items.APPLE && recipe.getItemToSell().getItem() == Items.DIAMOND)
							{
								return true;
							}
						}
						if ("minecraft:farmer/shepherd".equals(profession.getRegistryName() + "/" + career.getName()))
						{
							List<List<EntityVillager.ITradeList>> trades = TweakedReflection.getVillagerTrades(career);

							//safety check
							if (trades.isEmpty()) return false;
							if (trades.get(0).isEmpty()) return false;

							//get the trade where we expect it to be
							EntityVillager.ITradeList trade = trades.get(0).get(0);

							//build the trade
							MerchantRecipeList recipeList = new MerchantRecipeList();
							trade.addMerchantRecipe(new FakeVillager(), recipeList, new Random());

							//safety check
							if (recipeList.isEmpty()) return false;

							//check the trade is correct
							MerchantRecipe recipe = recipeList.get(0);
							if (recipe.getItemToBuy().getItem() == Items.APPLE && recipe.getItemToSell().getItem() == Items.DIAMOND_SWORD)
							{
								if (recipe.getItemToSell().getEnchantmentTagList() != null)
								{
									return true;
								}
							}
						}
					}
				}
			}
			return false;
		}
	}


	//**************************************************************************************//
	//										addTradeEnchant									//
	//**************************************************************************************//

	@TweakedTest()
	public static class Test_Trade_AddTradeEnchant implements ITweakedTest
	{
		@Override
		public String getFilename()
		{
			return "trade";
		}

		@Override
		public String getTestDescription()
		{
			return "trade - addtradeenchant";
		}

		@Override
		public String[] getVariables()
		{
			return new String[0];
		}

		@Override
		public String[] getActions()
		{
			return new String[] { "tweak.trade.addTradeEnchant(\"minecraft:farmer/farmlord\", 1, <minecraft:carrot>, <minecraft:diamond_sword>, 10, 30, false);" };
		}

		@Override
		public boolean runTest(World world)
		{
			for (VillagerRegistry.VillagerProfession profession : ForgeRegistries.VILLAGER_PROFESSIONS)
			{
				List<VillagerRegistry.VillagerCareer> careers = TweakedReflection.getVillagerCareers(profession);
				if (careers != null)
				{
					for (VillagerRegistry.VillagerCareer career : careers)
					{
						if ("minecraft:farmer/farmlord".equals(profession.getRegistryName() + "/" + career.getName()))
						{
							List<List<EntityVillager.ITradeList>> trades = TweakedReflection.getVillagerTrades(career);

							//safety check
							if (trades.isEmpty()) return false;
							if (trades.get(0).isEmpty()) return false;

							//get the trade where we expect it to be
							EntityVillager.ITradeList trade = trades.get(0).get(0);

							//build the trade
							MerchantRecipeList recipeList = new MerchantRecipeList();
							trade.addMerchantRecipe(new FakeVillager(), recipeList, new Random());

							//safety check
							if (recipeList.isEmpty()) return false;

							//check the trade is correct
							MerchantRecipe recipe = recipeList.get(0);
							if (recipe.getItemToBuy().getItem() == Items.CARROT && recipe.getItemToSell().getItem() == Items.DIAMOND_SWORD)
							{
								if (recipe.getItemToSell().getEnchantmentTagList() != null)
								{
									return true;
								}
							}
						}
					}
				}
			}
			return false;
		}
	}


	//**************************************************************************************//
	//										addTradeMap										//
	//**************************************************************************************//

	@TweakedTest()
	public static class Test_Trade_AddTradeMap implements ITweakedTest
	{
		@Override
		public String getFilename()
		{
			return "trade";
		}

		@Override
		public String getTestDescription()
		{
			return "trade - addtrademap";
		}

		@Override
		public String[] getVariables()
		{
			return new String[0];
		}

		@Override
		public String[] getActions()
		{
			return new String[] { "tweak.trade.addTradeMap(\"minecraft:librarian/cartographer\", 1, <minecraft:diamond>, \"Village\");" };
		}

		@Override
		public boolean runTest(World world)
		{
			for (VillagerRegistry.VillagerProfession profession : ForgeRegistries.VILLAGER_PROFESSIONS)
			{
				List<VillagerRegistry.VillagerCareer> careers = TweakedReflection.getVillagerCareers(profession);
				if (careers != null)
				{
					for (VillagerRegistry.VillagerCareer career : careers)
					{
						if ("minecraft:librarian/cartographer".equals(profession.getRegistryName() + "/" + career.getName()))
						{
							List<List<EntityVillager.ITradeList>> trades = TweakedReflection.getVillagerTrades(career);

							//safety check
							if (trades.isEmpty()) return false;
							if (trades.get(0).isEmpty()) return false;

							//get the trade where we expect it to be
							EntityVillager.ITradeList trade = trades.get(0).get(0);

							//build the trade
							MerchantRecipeList recipeList = new MerchantRecipeList();
							trade.addMerchantRecipe(new FakeVillager(), recipeList, new Random());

							//safety check
							if (recipeList.isEmpty()) return false;

							//check the trade is correct
							MerchantRecipe recipe = recipeList.get(0);
							if (recipe.getItemToBuy().getItem() == Items.DIAMOND && recipe.getItemToSell().getItem() == Items.FILLED_MAP)
							{
								return true;
							}
						}
					}
				}
			}
			return false;
		}
	}
}
