package com.mabinogi.tweaked.mods.vanilla.actions;

import com.mabinogi.tweaked.api.actions.ActionAbstract;
import com.mabinogi.tweaked.api.annotations.TweakedAction;
import com.mabinogi.tweaked.helpers.AnvilHelper;
import com.mabinogi.tweaked.helpers.anvil.TweakedAnvilRecipe;
import com.mabinogi.tweaked.script.objects.ObjAll;
import com.mabinogi.tweaked.script.objects.ObjStack;

import static com.mabinogi.tweaked.Tweaked.LOG;

@SuppressWarnings("unused")
public class Action_Vanilla_Anvil
{
	public static Action_Anvil_Add ADD = null;
	public static Action_Anvil_Remove REMOVE = null;
	public static Action_Anvil_Disable DISABLE = null;
	public static Action_Anvil_SetBreakChance BREAK_CHANCE = null;


	//**************************************************************************************//
	//											add											//
	//**************************************************************************************//

	@TweakedAction("anvil.add")
	public static class Action_Anvil_Add extends ActionAbstract
	{
		public Action_Anvil_Add()
		{
			ADD = this;
		}

		public void build(ObjStack left, ObjStack right, ObjStack output, Integer cost)
		{
			AnvilHelper.addAddition(new TweakedAnvilRecipe(left.getItemStack(), right.getItemStack(), output.getItemStack(), cost));
		}

		@Override
		protected void run()
		{
			//do nothing, build handles everything
		}
	}


	//**************************************************************************************//
	//										remove											//
	//**************************************************************************************//

	@TweakedAction("anvil.remove")
	public static class Action_Anvil_Remove extends ActionAbstract
	{
		public Action_Anvil_Remove()
		{
			REMOVE = this;
		}

		public void build(ObjAll all)
		{
			AnvilHelper.setClear(true);
		}

		public void build(ObjStack stack)
		{
			AnvilHelper.addRemoval(stack.getItemStack());
		}

		@Override
		protected void run()
		{
			//do nothing, build handles everything
		}
	}


	//**************************************************************************************//
	//										disable											//
	//**************************************************************************************//

	private static final String DISABLE_BOOKS = "books";
	private static final String DISABLE_REPAIRS = "repairs";
	private static final String DISABLE_COMBINE_BOOKS = "combinebooks";
	private static final String DISABLE_COMBINE_REPAIRS = "combinerepairs";

	@TweakedAction("anvil.disable")
	public static class Action_Anvil_Disable extends ActionAbstract
	{
		public Action_Anvil_Disable()
		{
			DISABLE = this;
		}

		public void build(String value)
		{
			switch (value.toLowerCase())
			{
				case DISABLE_BOOKS:
					AnvilHelper.setDisableBooks(true);
					break;
				case DISABLE_REPAIRS:
					AnvilHelper.setDisableRepairs(true);
					break;
				case DISABLE_COMBINE_BOOKS:
					AnvilHelper.setDisableCombineBooks(true);
					break;
				case DISABLE_COMBINE_REPAIRS:
					AnvilHelper.setDisableCombineRepairs(true);
					break;
				default:
					LOG.warn("anvil.disable : Invalid disable value '" + value + "'");
					break;
			}
		}

		@Override
		protected void run()
		{
			//do nothing, build handles everything
		}
	}


	//**************************************************************************************//
	//										setBreakChance									//
	//**************************************************************************************//

	@TweakedAction("anvil.setbreakchance")
	public static class Action_Anvil_SetBreakChance extends ActionAbstract
	{
		public Action_Anvil_SetBreakChance()
		{
			BREAK_CHANCE = this;
		}

		public void build(Float f)
		{
			AnvilHelper.setBreakChance(f);
		}

		@Override
		protected void run()
		{
			//do nothing, build handles everything
		}
	}
}
