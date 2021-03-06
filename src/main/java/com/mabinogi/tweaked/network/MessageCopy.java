package com.mabinogi.tweaked.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

@SuppressWarnings("unused")
public class MessageCopy implements IMessage, IMessageHandler<MessageCopy, IMessage>
{
	//the message data transferred
	private String data;
	
	public MessageCopy() { }
	
	public MessageCopy(String data)
	{
		this.data = data;
	}

	@Override
	public IMessage onMessage(MessageCopy message, MessageContext ctx)
	{
		Minecraft.getMinecraft().addScheduledTask(() -> handle(message, ctx));
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		data = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeUTF8String(buf, data);
	}
	
	private String getData()
	{
        return data;
	}
	
	private void handle(MessageCopy message, MessageContext ctx)
	{
		//copy the message to the clipboard using standard desktop toolkit
        if(Desktop.isDesktopSupported()) 
        {
            StringSelection selection = new StringSelection(message.getData());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, null);
        }
	}

}
