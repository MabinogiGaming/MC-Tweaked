package com.mabinogi.tweaked.script.objects;

import java.util.ArrayList;
import java.util.List;

public class ObjRecipeShapeless
{
	public Object[] recipeArgs = null;
	
	public ObjRecipeShapeless(Object A, Object B, Object C, Object D, Object E, Object F, Object G, Object H, Object I)
	{
		//create list to help build
		List<Object> buildList = new ArrayList<>();
				
		if (A != null)
		{
			buildList.add(A);
		}
		
		if (B != null)
		{
			buildList.add(B);
		}
		
		if (C != null)
		{
			buildList.add(C);
		}
		
		if (D != null)
		{
			buildList.add(D);
		}
		
		if (E != null)
		{
			buildList.add(E);
		}
		
		if (F != null)
		{
			buildList.add(F);
		}
		
		if (G != null)
		{
			buildList.add(G);
		}
		
		if (H != null)
		{
			buildList.add(H);
		}
		
		if (I != null)
		{
			buildList.add(I);
		}
		
		recipeArgs = buildList.toArray();
	}
}
