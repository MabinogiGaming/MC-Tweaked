package com.mabinogi.tweaked.api.commands;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public interface ICommand
{
	/**
	 * Allows the command to be hidden from tab completions
	 * @return <b>true</b> if hidden
	 */
	public boolean isHidden();
	
	/**
     * Callback for when the command is executed
     */
	public void execute(MinecraftServer server, EntityPlayer player, String[] args);
	
	/**
     * Get a list of options for when the user presses the TAB key
     */
	public List<String> getTabCompletions(MinecraftServer server, EntityPlayer player, String[] args, @Nullable BlockPos targetPos);
}
