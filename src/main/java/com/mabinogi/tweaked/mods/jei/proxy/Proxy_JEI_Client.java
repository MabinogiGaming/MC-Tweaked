package com.mabinogi.tweaked.mods.jei.proxy;

import com.mabinogi.tweaked.mods.jei.Plugin_JEI;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.item.ItemStack;

import java.util.List;

public class Proxy_JEI_Client extends Proxy_JEI_Common
{
    @Override
    public void addIngredients(List<ItemStack> stacks)
    {
        Plugin_JEI.itemRegistry.addIngredientsAtRuntime(ItemStack.class, stacks);
    }

    @Override
    public void hideIngredients(List<ItemStack> stacks)
    {
        Plugin_JEI.itemRegistry.removeIngredientsAtRuntime(ItemStack.class, stacks);
    }

    @Override
    public void addInfo(ItemStack stack, String info)
    {

    }
}
