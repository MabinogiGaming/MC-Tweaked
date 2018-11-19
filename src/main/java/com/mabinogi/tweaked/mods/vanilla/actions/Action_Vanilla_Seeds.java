package com.mabinogi.tweaked.mods.vanilla.actions;

import com.mabinogi.tweaked.api.actions.ActionAbstract;
import com.mabinogi.tweaked.api.annotations.TweakedAction;
import com.mabinogi.tweaked.controllers.TweakedReflection;
import com.mabinogi.tweaked.script.ScriptHelper;
import com.mabinogi.tweaked.script.objects.ObjAll;
import com.mabinogi.tweaked.script.objects.ObjStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;

import java.util.ArrayList;
import java.util.List;

import static com.mabinogi.tweaked.Tweaked.LOG;

@SuppressWarnings("unused")
public class Action_Vanilla_Seeds
{
	public static Action_Seeds_Add ADD = null;
	public static Action_Seeds_Remove REMOVE = null;


	//**************************************************************************************//
	//										add												//
	//**************************************************************************************//

	@TweakedAction("seeds.add")
	public static class Action_Seeds_Add extends ActionAbstract
	{
		private List<WeightedRandom.Item> SEED_ENTRIES = new ArrayList<>();

		public Action_Seeds_Add()
		{
			ADD = this;
		}

		public void build(ObjStack stack, Integer weight)
		{
			WeightedRandom.Item seedEntry = TweakedReflection.createSeedEntry(stack.getItemStack(), weight);
			if (seedEntry != null)
			{
				SEED_ENTRIES.add(seedEntry);
			}
		}

		@Override
		protected void run()
		{
			for (WeightedRandom.Item seedEntry : SEED_ENTRIES)
			{
				TweakedReflection.getSeedEntries().add(seedEntry);

				//debug
				ItemStack seedStack = TweakedReflection.getSeedStack(seedEntry);
				LOG.debug("seeds.add : " + seedStack);
			}

			//cleanup
			SEED_ENTRIES = null;
		}
	}


	//**************************************************************************************//
	//										remove											//
	//**************************************************************************************//

	@TweakedAction("seeds.remove")
	public static class Action_Seeds_Remove extends ActionAbstract
	{
		private boolean CLEAR = false;
		private List<ItemStack> STACKS = new ArrayList<>();

		public Action_Seeds_Remove()
		{
			REMOVE = this;
		}

		public void build(ObjAll all)
		{
			CLEAR = true;
		}

		public void build(ObjStack stack)
		{
			STACKS.add(stack.getItemStack());
		}

		@Override
		protected void run()
		{
			if (CLEAR)
			{
				TweakedReflection.getSeedEntries().clear();
			}
			else
			{
				List<WeightedRandom.Item> remove = new ArrayList<>();
				for (ItemStack stack : STACKS)
				{
					for(WeightedRandom.Item entry : TweakedReflection.getSeedEntries())
					{
						ItemStack seedStack = TweakedReflection.getSeedStack(entry);

						if (ScriptHelper.areScriptItemsEqual(stack, seedStack))
						{
							remove.add(entry);

							//debug
							LOG.debug("seeds.remove : " + seedStack);
						}
					}
				}
				TweakedReflection.getSeedEntries().removeAll(remove);
			}

			//cleanup
			STACKS = null;
		}
	}

}
