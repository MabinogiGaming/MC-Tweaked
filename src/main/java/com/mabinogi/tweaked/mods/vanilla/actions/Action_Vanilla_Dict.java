package com.mabinogi.tweaked.mods.vanilla.actions;

import com.mabinogi.tweaked.api.actions.ActionAbstract;
import com.mabinogi.tweaked.api.annotations.TweakedAction;
import com.mabinogi.tweaked.controllers.TweakedReflection;
import com.mabinogi.tweaked.script.ScriptHelper;
import com.mabinogi.tweaked.script.objects.ObjStack;
import com.mabinogi.tweaked.script.objects.ObjStackList;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static com.mabinogi.tweaked.Tweaked.LOG;


public class Action_Vanilla_Dict
{
	public static Action_Dict_Add ADD = null;
	public static Action_Dict_Remove REMOVE = null;
	public static Action_Dict_Replace REPLACE = null;


	public static class DictHolder
	{
		public String dictName;
		public List<ObjStack> stacks;

		public DictHolder(String dictName, ObjStack stack)
		{
			this.dictName = dictName;
			this.stacks = Collections.singletonList(stack);
		}

		public DictHolder(String dictName, List<ObjStack> stackList)
		{
			this.dictName = dictName;
			this.stacks = stackList;
		}
	}


	//**************************************************************************************//
	//											add											//
	//**************************************************************************************//

	@TweakedAction("dict.add")
	public static class Action_Dict_Add extends ActionAbstract
	{
		private List<DictHolder> HOLDERS = new ArrayList<>();

		public Action_Dict_Add()
		{
			ADD = this;
		}

		public void build(String dictName, ObjStack stack)
		{
			HOLDERS.add(new DictHolder(dictName, stack));
		}

		public void build(String dictName, ObjStackList stackList)
		{
			HOLDERS.add(new DictHolder(dictName, stackList.getList()));
		}

		@Override
		protected void run()
		{
			for (DictHolder holder : HOLDERS)
			{
				for (ObjStack stack : holder.stacks)
				{
					OreDictionary.registerOre(holder.dictName, stack.getItemStack());

					//debug
					LOG.debug("dict.add : " + stack.getItemStack() + " to " + holder.dictName);
				}
			}

			//cleanup
			HOLDERS = null;
		}
	}
	
	
	//**************************************************************************************//
	//										remove											//
	//**************************************************************************************//

	@TweakedAction("dict.remove")
	public static class Action_Dict_Remove extends ActionAbstract
	{
		private List<DictHolder> HOLDERS = new ArrayList<>();
		
		public Action_Dict_Remove()
		{
			REMOVE = this;
		}
		
		public void build(String dictName, ObjStack stack)
		{
			HOLDERS.add(new DictHolder(dictName, stack));
		}

		public void build(String dictName, ObjStackList stackList)
		{
			HOLDERS.add(new DictHolder(dictName, stackList.getList()));
		}
		
		@Override
		protected void run()
		{
			for (DictHolder holder : HOLDERS)
			{
				if (!OreDictionary.doesOreNameExist(holder.dictName))
				{
					LOG.warn("Warning : Dict name '" + holder.dictName + "'doesn't exist");
				}

				int dictID = OreDictionary.getOreID(holder.dictName);
				NonNullList<ItemStack> stacks = TweakedReflection.getDictStacks().get(dictID);

				Iterator<ItemStack> iterator = stacks.iterator();
				while (iterator.hasNext())
				{
					ItemStack stack = iterator.next();

					for (ObjStack objStack : holder.stacks)
					{
						if (ItemStack.areItemsEqual(stack, objStack.getItemStack()))
						{
							iterator.remove();

							//debug
							LOG.debug("dict.remove : " + objStack.getItemStack() + " from " + holder.dictName);
						}
					}
				}
			}
			
			//cleanup
			HOLDERS = null;
		}
	}


	//**************************************************************************************//
	//										replace											//
	//**************************************************************************************//

	@TweakedAction("dict.replace")
	public static class Action_Dict_Replace extends ActionAbstract
	{
		private List<DictHolder> HOLDERS = new ArrayList<>();

		public Action_Dict_Replace()
		{
			REPLACE = this;
		}

		public void build(String dictName, ObjStack stack)
		{
			HOLDERS.add(new DictHolder(dictName, stack));
		}

		public void build(String dictName, ObjStackList stackList)
		{
			HOLDERS.add(new DictHolder(dictName, stackList.getList()));
		}

		@Override
		protected void run()
		{
			for (DictHolder holder : HOLDERS)
			{
				if (!OreDictionary.doesOreNameExist(holder.dictName))
				{
					LOG.warn("Warning : Dict name '" + holder.dictName + "'doesn't exist");
				}

				int dictID = OreDictionary.getOreID(holder.dictName);

				NonNullList<ItemStack> stacks = TweakedReflection.getDictStacks().get(dictID);
				stacks.clear();

				for (ObjStack stack : holder.stacks)
				{
					stacks.add(stack.getItemStack());

					//debug
					LOG.debug("dict.replace : " + holder.dictName + " with " + ScriptHelper.stackToScript(stack.getItemStack()));
				}
			}

			//cleanup
			HOLDERS = null;
		}
	}

}
