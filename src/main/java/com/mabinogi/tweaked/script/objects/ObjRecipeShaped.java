package com.mabinogi.tweaked.script.objects;

import java.util.ArrayList;
import java.util.List;

import static com.mabinogi.tweaked.Tweaked.LOG;

public class ObjRecipeShaped
{
	public Object[] recipeArgs = null;
	
	public ObjRecipeShaped(Object A, Object B, Object C, Object D, Object E, Object F, Object G, Object H, Object I)
	{
		//create list to help build
		List<Object> buildList = new ArrayList<>();
		
		//create shape strings
		String rowA = "";
		String rowB = "";
		String rowC = "";
		
		if (A != null) rowA += "A";
		else rowA += " ";
		
		if (B != null) rowA += "B";
		else rowA += " ";
		
		if (C != null) rowA += "C";
		else rowA += " ";
		
		if (D != null) rowB += "D";
		else rowB += " ";
		
		if (E != null) rowB += "E";
		else rowB += " ";
		
		if (F != null) rowB += "F";
		else rowB += " ";
		
		if (G != null) rowC += "G";
		else rowC += " ";
		
		if (H != null) rowC += "H";
		else rowC += " ";
		
		if (I != null) rowC += "I";
		else rowC += " ";
		
		//trim cols
		while (rowA.endsWith(" ") && rowB.endsWith(" ") && rowC.endsWith(" "))
		{
			rowA = rowA.substring(0, rowA.length() - 1);
			rowB = rowB.substring(0, rowB.length() - 1);
			rowC = rowC.substring(0, rowC.length() - 1);
		}
		
		//trim rows
		if (rowC.trim().isEmpty()) 
		{
			rowC = null;
			
			if (rowB.trim().isEmpty()) 
			{
				rowB = null;
				
				if (rowA.trim().isEmpty()) rowA = null;
				{
					LOG.debug("Warning : Recipe with no items");
					return;
				}
			}
		}
		
		//output		
		if (rowA != null)
		{
			buildList.add(rowA);
		}
		
		if (rowB != null)
		{
			buildList.add(rowB);
		}
		
		if (rowC != null)
		{
			buildList.add(rowC);
		}
		
		if (A != null)
		{
			buildList.add('A');
			buildList.add(A);
		}
		
		if (B != null)
		{
			buildList.add('B');
			buildList.add(B);
		}
		
		if (C != null)
		{
			buildList.add('C');
			buildList.add(C);
		}
		
		if (D != null)
		{
			buildList.add('D');
			buildList.add(D);
		}
		
		if (E != null)
		{
			buildList.add('E');
			buildList.add(E);
		}
		
		if (F != null)
		{
			buildList.add('F');
			buildList.add(F);
		}
		
		if (G != null)
		{
			buildList.add('G');
			buildList.add(G);
		}
		
		if (H != null)
		{
			buildList.add('H');
			buildList.add(H);
		}
		
		if (I != null)
		{
			buildList.add('I');
			buildList.add(I);
		}
		
		recipeArgs = buildList.toArray();
	}
}
