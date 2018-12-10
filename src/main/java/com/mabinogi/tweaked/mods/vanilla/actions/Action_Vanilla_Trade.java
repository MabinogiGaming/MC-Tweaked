package com.mabinogi.tweaked.mods.vanilla.actions;

import com.mabinogi.tweaked.api.actions.ActionAbstract;
import com.mabinogi.tweaked.api.annotations.TweakedAction;
import com.mabinogi.tweaked.controllers.TweakedReflection;
import com.mabinogi.tweaked.helpers.trade.TweakedTradeCareer;
import com.mabinogi.tweaked.helpers.trade.TweakedTradeEntry;
import com.mabinogi.tweaked.helpers.trade.TweakedTradeName;
import com.mabinogi.tweaked.helpers.trade.TweakedTrades;
import com.mabinogi.tweaked.script.objects.ObjAll;
import com.mabinogi.tweaked.script.objects.ObjStack;
import com.mabinogi.tweaked.script.objects.ObjStringList;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerCareer;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Action_Vanilla_Trade
{
	public static Action_Trade_SetName SET_NAME = null;
	public static Action_Trade_ClearTrades CLEAR_TRADES = null;
	public static Action_Trade_AddCareer ADD_CAREER = null;
	public static Action_Trade_AddTrade ADD_TRADE = null;
	public static Action_Trade_AddTradeEnchant ADD_TRADE_ENCHANT = null;
	public static Action_Trade_AddTradeMap ADD_TRADE_MAP = null;



	//**************************************************************************************//
	//										setName											//
	//**************************************************************************************//

	@TweakedAction("trade.setname")
	public static class Action_Trade_SetName extends ActionAbstract
	{
		private List<TweakedTradeName> NAMES = new ArrayList<>();

		public Action_Trade_SetName()
		{
			SET_NAME = this;
		}

		public void build(String career, String name)
		{
			NAMES.add(new TweakedTradeName(career, name));
		}

		@Override
		protected void run()
		{
			for (VillagerProfession profession : ForgeRegistries.VILLAGER_PROFESSIONS)
			{
				List<VillagerCareer> careers = TweakedReflection.getVillagerCareers(profession);
				if (careers != null)
				{
					for (VillagerCareer career : careers)
					{
						for (TweakedTradeName entry : NAMES)
						{
							if (entry.getCareer().equals(profession.getRegistryName() + "/" + career.getName()))
							{
								TweakedReflection.setVillagerName(career, entry.getName());
							}
						}
					}
				}
			}

			//cleanup
			NAMES = null;
		}
	}


	//**************************************************************************************//
	//										clear											//
	//**************************************************************************************//

	@TweakedAction("trade.clear")
	public static class Action_Trade_ClearTrades extends ActionAbstract
	{
		private boolean CLEAR = false;
		private List<String> NAMES = new ArrayList<>();

		public Action_Trade_ClearTrades()
		{
			CLEAR_TRADES = this;
		}

		public void build(ObjAll all)
		{
			CLEAR = true;
		}

		public void build(String name)
		{
			NAMES.add(name);
		}

		public void build(ObjStringList names)
		{
			for (String name : names.getList())
			{
				build(name);
			}
		}

		@Override
		protected void run()
		{
			if (CLEAR)
			{
				for (VillagerProfession profession : ForgeRegistries.VILLAGER_PROFESSIONS)
				{
					List<VillagerCareer> careers = TweakedReflection.getVillagerCareers(profession);
					if (careers != null)
					{
						for (VillagerCareer career : careers)
						{
							List<List<EntityVillager.ITradeList>> trades = TweakedReflection.getVillagerTrades(career);
							if (trades != null)
							{
								trades.clear();
							}
						}
					}
				}
			}
			else
			{
				for (VillagerProfession profession : ForgeRegistries.VILLAGER_PROFESSIONS)
				{
					List<VillagerCareer> careers = TweakedReflection.getVillagerCareers(profession);
					if (careers != null)
					{
						for (VillagerCareer career : careers)
						{
							for (String name : NAMES)
							{
								if (name.equals(profession.getRegistryName() + "/" + career.getName()))
								{
									List<List<EntityVillager.ITradeList>> trades = TweakedReflection.getVillagerTrades(career);
									if (trades != null)
									{
										trades.clear();
									}
								}
							}
						}
					}
				}
			}

			//cleanup
			NAMES = null;
		}
	}


	//**************************************************************************************//
	//										addCareer										//
	//**************************************************************************************//

	@TweakedAction("trade.addcareer")
	public static class Action_Trade_AddCareer extends ActionAbstract
	{
		private List<TweakedTradeCareer> CAREERS = new ArrayList<>();

		public Action_Trade_AddCareer()
		{
			ADD_CAREER = this;
		}

		public void build(String profession, String name)
		{
			CAREERS.add(new TweakedTradeCareer(profession, name));
		}

		@Override
		protected void run()
		{
			for (VillagerProfession profession : ForgeRegistries.VILLAGER_PROFESSIONS)
			{
				for (TweakedTradeCareer career : CAREERS)
				{
					if (career.getProfession().equals(profession.getRegistryName().toString()))
					{
						List<VillagerCareer> careers = TweakedReflection.getVillagerCareers(profession);
						if (careers != null)
						{
							//creating a villager career will actually get it to register itself
							new VillagerCareer(profession, career.getName());
							break;
						}
					}
				}
			}

			//cleanup
			CAREERS = null;
		}
	}


	//**************************************************************************************//
	//										addTrade										//
	//**************************************************************************************//

	@TweakedAction("trade.addtrade")
	public static class Action_Trade_AddTrade extends ActionAbstract
	{
		private List<TweakedTradeEntry> ENTRIES = new ArrayList<>();

		public Action_Trade_AddTrade()
		{
			ADD_TRADE = this;
		}

		public void build(String career, Integer level, ObjStack inputA, ObjStack output)
		{
			ENTRIES.add(new TweakedTradeEntry(career, level, inputA.getItemStack(), null, output.getItemStack()));
		}

		public void build(String career, Integer level, ObjStack inputA, ObjStack inputB, ObjStack output)
		{
			ENTRIES.add(new TweakedTradeEntry(career, level, inputA.getItemStack(), inputB.getItemStack(), output.getItemStack()));
		}

		@Override
		protected void run()
		{
			for (VillagerProfession profession : ForgeRegistries.VILLAGER_PROFESSIONS)
			{
				List<VillagerCareer> careers = TweakedReflection.getVillagerCareers(profession);
				if (careers != null)
				{
					for (VillagerCareer career : careers)
					{
						for (TweakedTradeEntry entry : ENTRIES)
						{
							if (entry.getCareer().equals(profession.getRegistryName() + "/" + career.getName()))
							{
								career.addTrade(entry.getLevel(), new TweakedTrades.TweakedSimpleTrade(entry.getInputA(), entry.getInputB(), entry.getOutput()));
							}
						}
					}
				}
			}

			//cleanup
			ENTRIES = null;
		}
	}


	//**************************************************************************************//
	//										addTradeEnchant									//
	//**************************************************************************************//

	@TweakedAction("trade.addtradeenchant")
	public static class Action_Trade_AddTradeEnchant extends ActionAbstract
	{
		private List<TweakedTradeEntry> ENTRIES = new ArrayList<>();

		public Action_Trade_AddTradeEnchant()
		{
			ADD_TRADE_ENCHANT = this;
		}

		public void build(String career, Integer level, ObjStack input, ObjStack output, Integer powerMin, Integer powerMax, Boolean treasure)
		{
			ENTRIES.add(new TweakedTradeEntry(career, level, input.getItemStack(), output.getItemStack(), powerMin, powerMax, treasure));
		}

		@Override
		protected void run()
		{
			for (VillagerProfession profession : ForgeRegistries.VILLAGER_PROFESSIONS)
			{
				List<VillagerCareer> careers = TweakedReflection.getVillagerCareers(profession);
				if (careers != null)
				{
					for (VillagerCareer career : careers)
					{
						for (TweakedTradeEntry entry : ENTRIES)
						{
							if (entry.getCareer().equals(profession.getRegistryName() + "/" + career.getName()))
							{
								career.addTrade(entry.getLevel(), new TweakedTrades.TweakedEnchantedTrade(entry.getInputA(), entry.getOutput(), entry.getPowerMin(), entry.getPowerMax(), entry.isTreasure()));
							}
						}
					}
				}
			}

			//cleanup
			ENTRIES = null;
		}
	}


	//**************************************************************************************//
	//										addTradeMap										//
	//**************************************************************************************//

	@TweakedAction("trade.addtrademap")
	public static class Action_Trade_AddTradeMap extends ActionAbstract
	{
		private List<TweakedTradeEntry> ENTRIES = new ArrayList<>();

		public Action_Trade_AddTradeMap()
		{
			ADD_TRADE_MAP = this;
		}

		public void build(String career, Integer level, ObjStack input, String mapType)
		{
			ENTRIES.add(new TweakedTradeEntry(career, level, input.getItemStack(), null, mapType));
		}

		public void build(String career, Integer level, ObjStack inputA, ObjStack inputB, String mapType)
		{
			ENTRIES.add(new TweakedTradeEntry(career, level, inputA.getItemStack(), inputB.getItemStack(), mapType));
		}

		@Override
		protected void run()
		{
			for (VillagerProfession profession : ForgeRegistries.VILLAGER_PROFESSIONS)
			{
				List<VillagerCareer> careers = TweakedReflection.getVillagerCareers(profession);
				if (careers != null)
				{
					for (VillagerCareer career : careers)
					{
						for (TweakedTradeEntry entry : ENTRIES)
						{
							if (entry.getCareer().equals(profession.getRegistryName() + "/" + career.getName()))
							{
								career.addTrade(entry.getLevel(), new TweakedTrades.TweakedMapTrade(entry.getInputA(), entry.getInputB(), entry.getMapType()));
							}
						}
					}
				}
			}

			//cleanup
			ENTRIES = null;
		}
	}

}
