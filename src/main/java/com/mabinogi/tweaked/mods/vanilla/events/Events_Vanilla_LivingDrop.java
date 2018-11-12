package com.mabinogi.tweaked.mods.vanilla.events;

import com.mabinogi.tweaked.helpers.LootHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

public class Events_Vanilla_LivingDrop
{
	@SubscribeEvent
	public void lootDropEvent(LivingDropsEvent event)
	{
		//this is a special event used to replace the drops for the wither, due to the wither having no loot table
		LootTable witherTable = LootHelper.getWitherOverride();
		if (witherTable != null)
		{
			if (event.getEntity().getName().equals("Wither"))
			{
				World entityWorld = event.getEntityLiving().getEntityWorld();
				if (entityWorld instanceof WorldServer)
				{
					event.getDrops().clear();

					LootContext context = new LootContext.Builder((WorldServer) entityWorld).withLootedEntity(event.getEntityLiving()).withDamageSource(event.getSource()).build();
					for (ItemStack stack : witherTable.generateLootForPools(new Random(), context))
					{
						event.getDrops().add(new EntityItem(event.getEntityLiving().getEntityWorld(), event.getEntityLiving().posX, event.getEntityLiving().posY, event.getEntityLiving().posZ, stack));
					}
				}
			}
		}
	}
}
