package com.mabinogi.tweaked.mods.vanilla.tests;

import com.mabinogi.tweaked.api.annotations.TweakedTest;
import com.mabinogi.tweaked.api.test.ITweakedTest;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;

import java.util.List;
import java.util.Random;

public class Test_Vanilla_Loot
{
	//**************************************************************************************//
	//										clear											//
	//**************************************************************************************//

	@TweakedTest()
	public static class Test_Loot_Clear implements ITweakedTest
	{
		@Override
		public String getFilename()
		{
			return "loot";
		}

		@Override
		public String getTestDescription()
		{
			return "loot - clear";
		}

		@Override
		public String[] getVariables()
		{
			return new String[0];
		}

		@Override
		public String[] getActions()
		{
			return new String[] { "tweak.loot.clear(\"entities/creeper\");" };
		}

		@Override
		public boolean runTest(World world)
		{
			//get the loot table
			LootTable table = world.getLootTableManager().getLootTableFromLocation(new ResourceLocation("minecraft:entities/creeper"));

			//generate a loot context
			LootContext lootContext = new LootContext.Builder((WorldServer) world).withLuck(3.0f).build();

			//generate loot 100 times
			for (int i = 0; i < 100; i++)
			{
				if (!table.generateLootForPools(new Random(), lootContext).isEmpty())
				{
					//if any items drop, test has failed
					return false;
				}
			}
			return true;
		}
	}


	//**************************************************************************************//
	//										addPool											//
	//**************************************************************************************//

	@TweakedTest()
	public static class Test_Loot_AddPool implements ITweakedTest
	{
		@Override
		public String getFilename()
		{
			return "loot";
		}

		@Override
		public String getTestDescription()
		{
			return "loot - addPool";
		}

		@Override
		public String[] getVariables()
		{
			return new String[0];
		}

		@Override
		public String[] getActions()
		{
			return new String[] { "tweak.loot.addPool(\"entities/zombie\", \"pool_test\", 1, 1, 0, 0);" };
		}

		@Override
		public boolean runTest(World world)
		{
			//get zombie loot table
			LootTable table = world.getLootTableManager().getLootTableFromLocation(new ResourceLocation("minecraft:entities/zombie"));

			//check we have added a new poolName
			return (table.getPool("pool_test") != null);
		}
	}


	//**************************************************************************************//
	//										addEntry										//
	//**************************************************************************************//

	@TweakedTest()
	public static class Test_Loot_AddEntry implements ITweakedTest
	{
		@Override
		public String getFilename()
		{
			return "loot";
		}

		@Override
		public String getTestDescription()
		{
			return "loot - addEntry";
		}

		@Override
		public String[] getVariables()
		{
			return new String[0];
		}

		@Override
		public String[] getActions()
		{
			return new String[] { "tweak.loot.addEntry(\"entities/zombie\", \"pool_test\", \"entry_test\", <minecraft:torch>, 1, 0, 0, 1);" };
		}

		@Override
		public boolean runTest(World world)
		{
			//get the loot table
			LootTable table = world.getLootTableManager().getLootTableFromLocation(new ResourceLocation("minecraft:entities/zombie"));

			//generate a loot context
			LootContext lootContext = new LootContext.Builder((WorldServer) world).withLuck(3.0f).build();

			//generate loot 100 times, counting matching outputs
			int matchCount = 0;
			for (int i = 0; i < 100; i++)
			{
				for (ItemStack stack : table.generateLootForPools(new Random(), lootContext))
				{
					if (stack.getItem().equals(Item.getItemFromBlock(Blocks.TORCH)))
					{
						matchCount++;
					}
				}
			}

			//we expect to see 100 torches
			return matchCount >= 100;
		}
	}


	//**************************************************************************************//
	//										removePool										//
	//**************************************************************************************//

	@TweakedTest()
	public static class Test_Loot_RemovePool implements ITweakedTest
	{
		@Override
		public String getFilename()
		{
			return "loot";
		}

		@Override
		public String getTestDescription()
		{
			return "loot - removePool";
		}

		@Override
		public String[] getVariables()
		{
			return new String[0];
		}

		@Override
		public String[] getActions()
		{
			return new String[] { "tweak.loot.removePool(\"entities/skeleton\", \"pool1\");" };
		}

		@Override
		public boolean runTest(World world)
		{
			//get the loot table
			LootTable table = world.getLootTableManager().getLootTableFromLocation(new ResourceLocation("minecraft:entities/skeleton"));

			//generate a loot context
			LootContext lootContext = new LootContext.Builder((WorldServer) world).withLuck(3.0f).build();

			//generate loot 100 times
			for (int i = 0; i < 100; i++)
			{
				for (ItemStack stack : table.generateLootForPools(new Random(), lootContext))
				{
					if (stack.getItem().equals(Items.BONE))
					{
						//if a bone drops, test has failed
						return false;
					}
				}
			}
			return true;
		}
	}


	//**************************************************************************************//
	//										removeEntry										//
	//**************************************************************************************//

	@TweakedTest()
	public static class Test_Loot_RemoveEntry implements ITweakedTest
	{
		@Override
		public String getFilename()
		{
			return "loot";
		}

		@Override
		public String getTestDescription()
		{
			return "loot - removeEntry";
		}

		@Override
		public String[] getVariables()
		{
			return new String[0];
		}

		@Override
		public String[] getActions()
		{
			return new String[] { "tweak.loot.removeEntry(\"entities/spider\", \"main\", \"minecraft:string\");" };
		}

		@Override
		public boolean runTest(World world)
		{
			//get the loot table
			LootTable table = world.getLootTableManager().getLootTableFromLocation(new ResourceLocation("minecraft:entities/spider"));

			//generate a loot context
			LootContext lootContext = new LootContext.Builder((WorldServer) world).withLuck(3.0f).build();

			//generate loot 100 times
			for (int i = 0; i < 100; i++)
			{
				for (ItemStack stack : table.generateLootForPools(new Random(), lootContext))
				{
					if (stack.getItem().equals(Items.STRING))
					{
						//if a string drops, test has failed
						return false;
					}
				}
			}
			return true;
		}
	}


	//**************************************************************************************//
	//										addCondition									//
	//**************************************************************************************//

	@TweakedTest()
	public static class Test_Loot_AddCondition implements ITweakedTest
	{
		@Override
		public String getFilename()
		{
			return "loot";
		}

		@Override
		public String getTestDescription()
		{
			return "loot - addCondition";
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
					"tweak.loot.addPool(\"entities/pig\", \"pool_test\", 1, 1, 0, 0);",
					"tweak.loot.addCondition(\"entities/pig\", \"pool_test\", \"chance\", 0.5);",
					"tweak.loot.addEntry(\"entities/pig\", \"pool_test\", \"entry_test\", <minecraft:diamond>, 1, 0);"
				};
		}

		@Override
		public boolean runTest(World world)
		{
			//get the loot table
			LootTable table = world.getLootTableManager().getLootTableFromLocation(new ResourceLocation("minecraft:entities/pig"));

			//generate a loot context
			LootContext lootContext = new LootContext.Builder((WorldServer) world).withLuck(3.0f).build();

			//generate loot 100 times
			int count = 0;
			for (int i = 0; i < 100; i++)
			{
				for (ItemStack stack : table.generateLootForPools(new Random(), lootContext))
				{
					if (stack.getItem().equals(Items.DIAMOND))
					{
						count++;
					}
				}
			}
			return (count > 0 && count < 100);
		}
	}


	//**************************************************************************************//
	//										addFunctionA									//
	//**************************************************************************************//

	@TweakedTest()
	public static class Test_Loot_AddFunctionA implements ITweakedTest
	{
		@Override
		public String getFilename()
		{
			return "loot";
		}

		@Override
		public String getTestDescription()
		{
			return "loot - addFunctionA";
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
					"tweak.loot.clear(\"chests/village_blacksmith\");",
					"tweak.loot.addPool(\"chests/village_blacksmith\", \"pool_test\", 10, 10, 0, 0);",
					"tweak.loot.addEntry(\"chests/village_blacksmith\", \"pool_test\", \"entry_testA\", <minecraft:iron_sword>, 1, 0);",
					"tweak.loot.addEntry(\"chests/village_blacksmith\", \"pool_test\", \"entry_testB\", <minecraft:golden_sword>, 1, 0);",
					"tweak.loot.addEntry(\"chests/village_blacksmith\", \"pool_test\", \"entry_testC\", <minecraft:diamond_sword>, 1, 0);",
					"tweak.loot.addFunction(\"chests/village_blacksmith\", \"pool_test\", \"entry_testA\", \"enchant\", \"sharpness\", 5);",
					"tweak.loot.addFunction(\"chests/village_blacksmith\", \"pool_test\", \"entry_testB\", \"enchant\", \"sharpness\", 3);",
					"tweak.loot.addFunction(\"chests/village_blacksmith\", \"pool_test\", \"entry_testB\", \"enchant\", \"fire_aspect\", 1);",
					"tweak.loot.addFunction(\"chests/village_blacksmith\", \"pool_test\", \"entry_testC\", \"enchantRandomly\", 10, 30, false);"
				};
		}

		@Override
		public boolean runTest(World world)
		{
			return true;
		}
	}


	//**************************************************************************************//
	//										addFunctionB									//
	//**************************************************************************************//

	@TweakedTest()
	public static class Test_Loot_AddFunctionB implements ITweakedTest
	{
		@Override
		public String getFilename()
		{
			return "loot";
		}

		@Override
		public String getTestDescription()
		{
			return "loot - addFunctionB";
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
					"tweak.loot.addPool(\"entities/cow\", \"pool_test\", 1, 1, 0, 0);",
					"tweak.loot.addEntry(\"entities/cow\", \"pool_test\", \"entry_test\", <minecraft:cobblestone>, 1, 0);",
					"tweak.loot.addFunction(\"entities/cow\", \"pool_test\", \"entry_test\", \"smelt\");"
				};
		}

		@Override
		public boolean runTest(World world)
		{
			return true;
		}
	}


	//**************************************************************************************//
	//										overrideWither									//
	//**************************************************************************************//

	@TweakedTest()
	public static class Test_Loot_OverrideWither implements ITweakedTest
	{
		@Override
		public String getFilename()
		{
			return "loot";
		}

		@Override
		public String getTestDescription()
		{
			return "loot - overrideWither";
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
					"tweak.loot.addPool(\"entities/wither\", \"pool_test\", 10, 10, 0, 0);",
					"tweak.loot.addEntry(\"entities/wither\", \"pool_test\", \"entry_testA\", <minecraft:cobblestone>, 1, 0);",
					"tweak.loot.addEntry(\"entities/wither\", \"pool_test\", \"entry_testB\", <minecraft:dirt>, 1, 0);"
				};
		}

		@Override
		public boolean runTest(World world)
		{
			return true;
		}
	}
}
