package com.mabinogi.tweaked.mods.vanilla.actions;

import com.mabinogi.tweaked.api.actions.ActionAbstract;
import com.mabinogi.tweaked.api.annotations.TweakedAction;
import com.mabinogi.tweaked.helpers.LootHelper;
import com.mabinogi.tweaked.script.objects.ObjStack;

import static com.mabinogi.tweaked.Tweaked.LOG;

public class Action_Vanilla_Loot
{
	public static Action_Loot_Clear CLEAR = null;
	public static Action_Loot_AddPool ADD_POOL = null;
	public static Action_Loot_AddEntry ADD_ENTRY = null;
	public static Action_Loot_RemovePool REMOVE_POOL = null;
	public static Action_Loot_RemoveEntry REMOVE_ENTRY = null;
	public static Action_Loot_AddCondition ADD_CONDITION = null;
	public static Action_Loot_AddFunction ADD_FUNCTION = null;


	//**************************************************************************************//
	//										clear											//
	//**************************************************************************************//

	@TweakedAction("loot.clear")
	public static class Action_Loot_Clear extends ActionAbstract
	{
		public Action_Loot_Clear()
		{
			CLEAR = this;
		}

		public void build(String key)
		{
			LootHelper.clearTable(key);
		}

		@Override
		protected void run()
		{
			//do nothing, build handles everything
		}
	}


	//**************************************************************************************//
	//										addPool											//
	//**************************************************************************************//

	@TweakedAction("loot.addPool")
	public static class Action_Loot_AddPool extends ActionAbstract
	{
		public Action_Loot_AddPool()
		{
			ADD_POOL = this;
		}

		public void build(String key, String poolName, Integer minCount, Integer maxCount)
		{
			LootHelper.addPool(key, poolName, minCount, maxCount);
		}

		public void build(String key, String poolName, Integer minCount, Integer maxCount, Integer minBonus, Integer maxBonus)
		{
			LootHelper.addPool(key, poolName, minCount, maxCount, minBonus, maxBonus);
		}

		@Override
		protected void run()
		{
			//do nothing, build handles everything
		}
	}


	//**************************************************************************************//
	//										addEntry										//
	//**************************************************************************************//

	@TweakedAction("loot.addEntry")
	public static class Action_Loot_AddEntry extends ActionAbstract
	{
		public Action_Loot_AddEntry()
		{
			ADD_ENTRY = this;
		}

		public void build(String key, String pool, String name, ObjStack stack, Integer weight, Integer quality)
		{
			LootHelper.addEntry(key, pool, name, stack.getItemStack(), weight, quality);
		}

		public void build(String key, String pool, String name, ObjStack stack, Integer weight, Integer quality, Integer lootBonusMin, Integer lootBonusMax)
		{
			LootHelper.addEntry(key, pool, name, stack.getItemStack(), weight, quality, lootBonusMin, lootBonusMax);
		}

		@Override
		protected void run()
		{
			//do nothing, build handles everything
		}
	}


	//**************************************************************************************//
	//										removePool										//
	//**************************************************************************************//

	@TweakedAction("loot.removePool")
	public static class Action_Loot_RemovePool extends ActionAbstract
	{
		public Action_Loot_RemovePool()
		{
			REMOVE_POOL = this;
		}

		public void build(String key, String name)
		{
			LootHelper.removePool(key, name);
		}

		@Override
		protected void run()
		{
			//do nothing, build handles everything
		}
	}


	//**************************************************************************************//
	//										removeEntry										//
	//**************************************************************************************//

	@TweakedAction("loot.removeEntry")
	public static class Action_Loot_RemoveEntry extends ActionAbstract
	{
		public Action_Loot_RemoveEntry()
		{
			REMOVE_ENTRY = this;
		}

		public void build(String key, String pool, String name)
		{
			LootHelper.removeEntry(key, pool, name);
		}

		@Override
		protected void run()
		{
			//do nothing, build handles everything
		}
	}


	//**************************************************************************************//
	//										addCondition									//
	//**************************************************************************************//

	private static final String CONDITION_PLAYERONLY = "playeronly";
	private static final String CONDITION_CHANCE = "chance";
	private static final String CONDITION_CHANCE_LOOTING = "chancewithlooting";

	@TweakedAction("loot.addCondition")
	public static class Action_Loot_AddCondition extends ActionAbstract
	{
		public Action_Loot_AddCondition()
		{
			ADD_CONDITION = this;
		}

		public void build(String key, String pool, String value)
		{
			if (!value.toLowerCase().equals(CONDITION_PLAYERONLY))
			{
				LOG.warn("Invalid loot condition, expected '" + CONDITION_PLAYERONLY + "'");
			}

			LootHelper.addConditionPlayerKill(key, pool);
		}

		public void build(String key, String pool, String value, Float chance)
		{
			if (!value.toLowerCase().equals(CONDITION_CHANCE))
			{
				LOG.warn("Invalid loot condition, expected '" + CONDITION_CHANCE + "'");
			}

			LootHelper.addConditionChance(key, pool, chance);
		}

		public void build(String key, String pool, String value, Float chance, Float lootMultiplier)
		{
			if (!value.toLowerCase().equals(CONDITION_CHANCE_LOOTING))
			{
				LOG.warn("Invalid loot condition, expected '" + CONDITION_CHANCE_LOOTING + "'");
			}

			LootHelper.addConditionLooting(key, pool, chance, lootMultiplier);
		}

		@Override
		protected void run()
		{
			//do nothing, build handles everything
		}
	}


	//**************************************************************************************//
	//										addFunction										//
	//**************************************************************************************//

	private static final String FUNCTION_ENCHANT = "enchant";
	private static final String FUNCTION_ENCHANT_BY_LEVEL = "enchantrandomly";
	private static final String FUNCTION_SMELT = "smelt";

	@TweakedAction("loot.addFunction")
	public static class Action_Loot_AddFunction extends ActionAbstract
	{
		public Action_Loot_AddFunction()
		{
			ADD_FUNCTION = this;
		}

		public void build(String key, String pool, String entry, String value)
		{
			if (!value.toLowerCase().equals(FUNCTION_SMELT))
			{
				LOG.warn("Invalid loot function, expected '" + FUNCTION_SMELT + "'");
			}

			LootHelper.addFunctionSmelt(key, pool, entry);
		}

		public void build(String key, String pool, String entry, String value, String enchant, Integer level)
		{
			if (!value.toLowerCase().equals(FUNCTION_ENCHANT))
			{
				LOG.warn("Invalid loot function, expected '" + FUNCTION_ENCHANT + "'");
			}

			LootHelper.addFunctionEnchant(key, pool, entry, enchant, level);
		}

		public void build(String key, String pool, String entry, String value, Integer enchantMin, Integer enchantMax, Boolean enchantTreasure)
		{
			if (!value.toLowerCase().equals(FUNCTION_ENCHANT_BY_LEVEL))
			{
				LOG.warn("Invalid loot function, expected '" + FUNCTION_ENCHANT_BY_LEVEL + "'");
			}

			LootHelper.addFunctionEnchantRandomly(key, pool, entry, enchantMin, enchantMax, enchantTreasure);
		}

		@Override
		protected void run()
		{
			//do nothing, build handles everything
		}
	}

}
