package com.mabinogi.tweaked.events;

import com.mabinogi.tweaked.Tweaked;
import com.mabinogi.tweaked.TweakedController;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.RecipeBookClient;
import net.minecraft.client.util.SearchTree;
import net.minecraft.client.util.SearchTreeManager;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClientEventHandler 
{
	@SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onGuiOpenEvent(GuiOpenEvent event) 
	{
        Minecraft minecraft = Minecraft.getMinecraft();
        if(minecraft.player != null && !TweakedController.RECIPEBOOK_FIXED) 
        {
        	//only do this once
        	TweakedController.RECIPEBOOK_FIXED = true;
            
            //rebuild the recipes table used by the recipe book
            RecipeBookClient.rebuildTable();
            
            //update the search tree
            minecraft.populateSearchTreeManager();
            ((SearchTree<?>) minecraft.getSearchTreeManager().get(SearchTreeManager.ITEMS)).recalculate();
            ((SearchTree<?>) minecraft.getSearchTreeManager().get(SearchTreeManager.RECIPES)).recalculate();
            
            Tweaked.LOG.debug("RecipeBook fixes applied");
        }
	}
}
