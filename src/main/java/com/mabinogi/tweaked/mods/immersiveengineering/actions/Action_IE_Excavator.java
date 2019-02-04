package com.mabinogi.tweaked.mods.immersiveengineering.actions;

import blusunrize.immersiveengineering.api.tool.ExcavatorHandler;
import blusunrize.immersiveengineering.api.tool.ExcavatorHandler.MineralMix;
import com.mabinogi.tweaked.api.actions.ActionAbstract;
import com.mabinogi.tweaked.api.annotations.TweakedAction;
import com.mabinogi.tweaked.mods.immersiveengineering.helpers.TweakedExcavatorRecipe;
import com.mabinogi.tweaked.script.objects.ObjAll;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.mabinogi.tweaked.Tweaked.LOG;

public class Action_IE_Excavator
{	
	public static Action_IE_Excavator_Add ADD = null;
	public static Action_IE_Excavator_Remove REMOVE = null;
	
	
	//**************************************************************************************//
	//										add												//
	//**************************************************************************************//
	
	@TweakedAction(value="ie.excavator.add", modid="immersiveengineering")
	public static class Action_IE_Excavator_Add extends ActionAbstract
	{
		public List<TweakedExcavatorRecipe> RECIPES = new ArrayList<>();
		
		public Action_IE_Excavator_Add()
		{
			ADD = this;
		}

		public void build(String name, Integer weight, Float fail, String ore, Float chance)
		{
			RECIPES.add(new TweakedExcavatorRecipe(name, weight, fail, ore, chance));
		}
		
		@Override
		protected void run()
		{
			//add recipes
			for (TweakedExcavatorRecipe recipe : RECIPES)
			{
				ExcavatorHandler.addMineral(recipe.getName(), recipe.getWeight(), recipe.getFail(), new String[]{recipe.getOre()}, new float[]{recipe.getChance()});
				
				//debug
				LOG.debug("ie.excavator.add : " + recipe.getName());
			}
			
			//cleanup
			RECIPES = null;
		}
	}
	
	
	//**************************************************************************************//
	//										remove											//
	//**************************************************************************************//

	@TweakedAction(value="ie.excavator.remove", modid="immersiveengineering")
	public static class Action_IE_Excavator_Remove extends ActionAbstract
	{
		public Boolean CLEAR = false;
		public List<String> NAMES = new ArrayList<>();
		
		public Action_IE_Excavator_Remove()
		{
			REMOVE = this;
		}

		@SuppressWarnings("unused")
		public void build(ObjAll all)
		{
			CLEAR = true;
		}
		
		public void build(String name)
		{
			NAMES.add(name);
		}
		
		@Override
		protected void run()
		{
			//clear recipes
			if (CLEAR)
			{
				ExcavatorHandler.mineralList.clear();
				
				//debug
				LOG.debug("ie.excavator.remove : all");
			}
			else
			{
				//remove recipes
				Iterator<MineralMix> iterator = ExcavatorHandler.mineralList.keySet().iterator();
				while(iterator.hasNext())
				{
					for (String name : NAMES)
					{
						if (name.equals(iterator.next().name))
						{
							iterator.remove();

							//debug
							LOG.debug("ie.excavator.remove : " + name);
						}
					}
				}
			}
			
			//cleanup
			CLEAR = null;
			REMOVE = null;
		}
	}
	
}
