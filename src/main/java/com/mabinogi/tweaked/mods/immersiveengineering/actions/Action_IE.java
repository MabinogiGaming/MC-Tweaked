package com.mabinogi.tweaked.mods.immersiveengineering.actions;

import com.mabinogi.tweaked.api.actions.ActionAbstract;
import com.mabinogi.tweaked.api.annotations.TweakedAction;
import com.mabinogi.tweaked.mods.immersiveengineering.events.Events_IE;
import com.mabinogi.tweaked.script.objects.ObjAll;
import com.mabinogi.tweaked.script.objects.ObjStringList;

import static com.mabinogi.tweaked.Tweaked.LOG;

public class Action_IE
{
	public static Action_IE_DisableMultiblock DISABLE_MULTIBLOCK = null;
	
	
	//**************************************************************************************//
	//								disableMultiblock										//
	//**************************************************************************************//
	
	@TweakedAction(value="ie.disablemultiblock", modid="immersiveengineering")
	public static class Action_IE_DisableMultiblock extends ActionAbstract
	{
		public Action_IE_DisableMultiblock()
		{
			DISABLE_MULTIBLOCK = this;
		}
		
		public void build(String name)
		{
			Events_IE.MULTIBLOCK_BLACKLIST.add(name);
			
			//debug
			LOG.debug("ie.disableMultiblock : Disabled " + name);
		}
		
		public void build(ObjStringList names)
		{
			for (String name : names.getList())
			{
				build(name);
			}
		}
		
		public void build(ObjAll all)
		{
			Events_IE.MULTIBLOCK_BLACKLIST_ALL = true;
			
			//debug
			LOG.debug("ie.disableMultiblock : Disabled all");
		}

		@Override
		protected void run()
		{
			//do nothing, build handles everything
		}
	}
	
	
}


