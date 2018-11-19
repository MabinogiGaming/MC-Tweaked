package com.mabinogi.tweaked.script;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreIngredient;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.mabinogi.tweaked.Tweaked.LOG;

@SuppressWarnings("WeakerAccess")
public class ScriptHelper {
	
	public static void reportScriptError(String script, String reason)
    {
    	if (script != null)
    	{
    		LOG.warn("Script Error : " + script);
    	}
    	if (reason != null)
    	{
    		LOG.warn("Reason : " + reason);
    	}
    }
	
	public static String cleanScript(String in)
	{
		//pattern to find strings
		Pattern pattern = Pattern.compile("\"(.*?)\"");

		//start by protecting all strings, replace " " with "|"
		Matcher matcher = pattern.matcher(in);
		while(matcher.find()) 
		{
			in = in.replace("\"" + matcher.group(1) + "\"", "\"" + matcher.group(1).replace(" ", "|") + "\"");
		}
		
		//now clear all whitespace from the string
		in = in.replaceAll("\\s", "");
		
		//finally restore the strings, replace "|" with " "
		matcher = pattern.matcher(in);
		while(matcher.find()) 
		{
			in = in.replace("\"" + matcher.group(1) + "\"", "\"" + matcher.group(1).replace("|", " ") + "\"");
		}
		
		return in;
	}
    
    public static Collection<File> listFiles(File dir) 
    {
        Set<File> fileTree = new HashSet<>();
        if(dir==null||dir.listFiles()==null)
        {
            return fileTree;
        }
        
        for (File entry : dir.listFiles()) 
        {
            if (entry.isFile()) fileTree.add(entry);
            else fileTree.addAll(listFiles(entry));
        }
        return fileTree;
    }
    
    /**
     * Parses a given itemstack into its Tweaked string representation
     * @param stack The stack
     * @return A string representation as outlined in a Tweaked Stack
     */
    public static String stackToScript(ItemStack stack)
	{
		if (stack == null) return "<>";
		
		//get item registry name
        String out = "<" + stack.getItem().getRegistryName();
        
        //add extra arguments if required
    	int meta = stack.getMetadata();
    	int count = stack.getCount();
    	int nbt = stack.getTagCompound() == null ? 0 : stack.getTagCompound().getSize();
    	
    	//metadata first
    	if (meta != 0 || count > 1 || nbt > 0)
    	{
    		out += ":" + meta;
    	}
    	
    	//count next
    	if (count > 1 || nbt > 0)
    	{
    		out += ":" + count;
    	}
    	
    	//finally nbt
    	if (nbt > 0)
    	{
    		out += ":" + stack.getTagCompound().toString();
    	}
    	
    	//close brackets
    	out += ">";
    	
    	return out;
	}
    
    /**
     * Parses a Minecraft ITweakedIngredient into a Tweaked ITweakedIngredient
     * @param ingredient The ingredient
     * @return array of strings containing either a null, dict or all its related stacks
     */
    public static String[] ingredientToScripts(Ingredient ingredient)
	{
		if (ingredient.getMatchingStacks().length == 0)
		{
			//empty
			return new String[]{"<>"};
		}
		else
		{
			//get first item
			ItemStack[] stackList = ingredient.getMatchingStacks();
			
			if (ingredient instanceof OreIngredient)
			{
				//this is a ballache but imo the best way, try to find an oredict that matches all stacks
				List<Integer> idList =  null;
				for (ItemStack stack : ingredient.getMatchingStacks())
				{
					//get ids
					int[] in = OreDictionary.getOreIDs(stack);
					
					//convert them to Integer
					Integer[] ids = new Integer[in.length];
					for(int i = 0; i < in.length; i++)
					{
					   ids[i] = in[i];
					}
					
					if (idList == null)
					{
						idList = Arrays.asList(ids);
					}
					else
					{
						idList.retainAll(Arrays.asList(ids));
					}
				}
				
				//if its empty then we don't have a match, return all the items
				if (idList.isEmpty())
				{
					String[] strs = new String[stackList.length];
					
					//convert them to strings
					for(int i = 0; i < stackList.length; i++)
					{
						strs[i] = stackToScript(stackList[i]);
					}
					
					return strs;
				}
				
				//if not, return the first oredict the matches, hopefully it's the most relevant
				return new String[]{"<" + OreDictionary.getOreName(idList.get(0)) + ">"};
			}
			else
			{
				String[] strs = new String[stackList.length];
				
				//convert them to strings
				for(int i = 0; i < stackList.length; i++)
				{
					strs[i] = stackToScript(stackList[i]);
				}
				
				return strs;
			}
		}
	}
    
    /**
     * Parses a recipe into all its related Tweaked recipes
     * @param recipe The recipe
     * @param debug Whether to print debug information such as recipe type
     * @return A list of all related Tweaked recipes
     */
    public static List<String> recipeToScript(IRecipe recipe, boolean debug)
	{
		if (recipe instanceof ShapedRecipes)
		{
			return shapedToScript((ShapedRecipes) recipe, debug);
		}
		else if (recipe instanceof ShapedOreRecipe)
		{
			return shapedOreToScript((ShapedOreRecipe) recipe, debug);
		}
		else if (recipe instanceof ShapelessRecipes)
		{
			return shapelessToScript((ShapelessRecipes) recipe, debug);
		}
		else if (recipe instanceof ShapelessOreRecipe)
		{
			return shapelessOreToScript((ShapelessOreRecipe) recipe, debug);
		}
		
		return new ArrayList<>(Collections.singleton(recipe.toString()));
	}
	
    /**
     * Parses a shaped recipe into all its related Tweaked recipes
     * @param recipe The recipe
     * @return A list of all related Tweaked recipes
     */
	private static List<String> shapedToScript(ShapedRecipes recipe, boolean debug)
	{
		List<String> ret = new ArrayList<>();
		StringBuilder out = new StringBuilder();
		
		//inputs
		List<String[]> inputs = new ArrayList<>();
		for (Ingredient ingredient : recipe.getIngredients())
		{
			inputs.add(ingredientToScripts(ingredient));
		}
		
		//we have to go through each ingredient, and build a recipe for all possibilities
		List<String[]> permutations = new ArrayList<>();
		generatePermutations(inputs, permutations, 0, null);
		
		for (String[] permutation : permutations)
		{
			//debug
			if (debug)
			{
				out.append("shaped = ");
			}
			
			//recipe name
			out.append("\"").append(recipe.getRegistryName()).append("\"");
			out.append(", ");
			
			//output
			out.append(stackToScript(recipe.getRecipeOutput()));
			out.append(", ");
			
			out.append("[");
			for (int i = 0; i < recipe.getRecipeHeight(); i++)
			{
				out.append("[");
				for (int j = 0; j < recipe.getRecipeWidth(); j++)
				{
					out.append(permutation[(i * recipe.getRecipeWidth()) + j]);
					if ((j + 1) != recipe.getRecipeWidth()) out.append(", ");
				}
				out.append("]");
			}
			out.append("]");
			
			ret.add(out.toString());
			out = new StringBuilder();
		}
		
		return ret;
	}
	
	/**
     * Parses a shaped ore recipe into all its related Tweaked recipes
     * @param recipe The recipe
     * @return A list of all related Tweaked recipes
     */
	private static List<String> shapedOreToScript(ShapedOreRecipe recipe, boolean debug)
	{
		List<String> ret = new ArrayList<>();
		StringBuilder out = new StringBuilder();
		
		//inputs
		List<String[]> inputs = new ArrayList<>();
		for (Ingredient ingredient : recipe.getIngredients())
		{
			inputs.add(ingredientToScripts(ingredient));
		}
		
		//we have to go through each ingredient, and build a recipe for all possibilities
		List<String[]> permutations = new ArrayList<>();
		generatePermutations(inputs, permutations, 0, null);
		
		for (String[] permutation : permutations)
		{
			//debug
			if (debug)
			{
				out.append("shaped = ");
			}
			
			//recipe name
			out.append("\"").append(recipe.getRegistryName()).append("\"");
			out.append(", ");
			
			//output
			out.append(stackToScript(recipe.getRecipeOutput()));
			out.append(", ");
			
			out.append("[");
			for (int i = 0; i < recipe.getRecipeHeight(); i++)
			{
				out.append("[");
				for (int j = 0; j < recipe.getRecipeWidth(); j++)
				{
					out.append(permutation[(i * recipe.getRecipeWidth()) + j]);
					if ((j + 1) != recipe.getRecipeWidth()) out.append(", ");
				}
				out.append("]");
			}
			out.append("]");
			
			ret.add(out.toString());
			out = new StringBuilder();
		}
		
		return ret;
	}
	
	/**
     * Parses a shapeless recipe into all its related Tweaked recipes
     * @param recipe The recipe
     * @return A list of all related Tweaked recipes
     */
	private static List<String> shapelessToScript(ShapelessRecipes recipe, boolean debug)
	{
		List<String> ret = new ArrayList<>();
		StringBuilder out = new StringBuilder();
		
		//inputs
		List<String[]> inputs = new ArrayList<>();
		for (Ingredient ingredient : recipe.getIngredients())
		{
			inputs.add(ingredientToScripts(ingredient));
		}
		
		//we have to go through each ingredient, and build a recipe for all possibilities
		List<String[]> permutations = new ArrayList<>();
		generatePermutations(inputs, permutations, 0, null);
		
		for (String[] permutation : permutations)
		{
			//debug
			if (debug)
			{
				out.append("shapeless = ");
			}
			
			//recipe name
			out.append("\"").append(recipe.getRegistryName()).append("\"");
			out.append(", ");
			
			//output
			out.append(stackToScript(recipe.getRecipeOutput()));
			out.append(", ");
			
			out.append("[");
			
			for (int i = 0; i < permutation.length; i++)
			{
				out.append(permutation[i]);
				if ((i + 1) != permutation.length) out.append(", ");
			}
			out.append("]");
			
			ret.add(out.toString());
			out = new StringBuilder();
		}
		
		return ret;
	}
	
	/**
     * Parses a shapeless ore recipe into all its related Tweaked recipes
     * @param recipe The recipe
     * @return A list of all related Tweaked recipes
     */
	private static List<String> shapelessOreToScript(ShapelessOreRecipe recipe, boolean debug)
	{
		List<String> ret = new ArrayList<>();
		StringBuilder out = new StringBuilder();
		
		//inputs
		List<String[]> inputs = new ArrayList<>();
		for (Ingredient ingredient : recipe.getIngredients())
		{
			inputs.add(ingredientToScripts(ingredient));
		}
		
		//we have to go through each ingredient, and build a recipe for all possibilities
		List<String[]> permutations = new ArrayList<>();
		generatePermutations(inputs, permutations, 0, null);
		
		for (String[] permutation : permutations)
		{
			//debug
			if (debug)
			{
				out.append("shapeless = ");
			}
			
			//recipe name
			out.append("\"").append(recipe.getRegistryName()).append("\"");
			out.append(", ");
			
			//output
			out.append(stackToScript(recipe.getRecipeOutput()));
			out.append(", ");
			
			out.append("[");
			
			for (int i = 0; i < permutation.length; i++)
			{
				out.append(permutation[i]);
				if ((i + 1) != permutation.length) out.append(", ");
			}
			out.append("]");
			
			ret.add(out.toString());
			out = new StringBuilder();
		}
		
		return ret;
	}
	
	/**
	 * Special function used to generate a list of all possible recipe input permutations when given the inputs in individual arrays
	 * @param inputs The inputs, each one being an array of valid items
	 * @param result The outputs, each one being an array of a valid permutation
	 * @param depth How deep we have scanned (start at 0)
	 * @param current The current outputs (start as null)
	 */
	public static void generatePermutations(List<String[]> inputs, List<String[]> result, int depth, String[] current)
    {
        if (current == null) current = new String[inputs.size()];
        
        if(depth == inputs.size())
        {
            result.add(Arrays.copyOf(current, inputs.size()));
            return;
        }

        for(int i = 0; i < inputs.get(depth).length; ++i)
        {
            current[depth] = inputs.get(depth)[i];
            generatePermutations(inputs, result, depth + 1, current);
        }
    }

	/**
	 * A special item match that only checks nbt data if specified by the script item.
	 * This allows users to specify all items by having no nbt, or more specific items with nbt
	 * @param scriptItem The itemstack that was generated by a Tweaked script
	 * @param otherItem The itemstack we are comparing it to
	 * @return True if the items match
	 */
    public static boolean areScriptItemsEqual(ItemStack scriptItem, ItemStack otherItem)
	{
		if (scriptItem.hasTagCompound())
		{
			return ItemStack.areItemStacksEqual(scriptItem, otherItem);
		}
		else
		{
			return ItemStack.areItemsEqualIgnoreDurability(scriptItem, otherItem);
		}
	}

}
