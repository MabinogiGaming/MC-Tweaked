package com.mabinogi.tweaked.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@SuppressWarnings("unused")
public class MessageBrowser implements IMessage, IMessageHandler<MessageBrowser, IMessage>
{
	//the message data transferred
	private String url;

	public MessageBrowser() { }

	public MessageBrowser(String url)
	{
		this.url = url;
	}

	@Override
	public IMessage onMessage(MessageBrowser message, MessageContext ctx)
	{
		Minecraft.getMinecraft().addScheduledTask(() -> handle(message, ctx));
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		url = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeUTF8String(buf, url);
	}
	
	private String getUrl()
	{
        return url;
	}
	
	private void handle(MessageBrowser message, MessageContext ctx)
	{
		//open a browser at the specified url
        if(Desktop.isDesktopSupported()) 
        {
			Desktop desktop = Desktop.getDesktop();
			try
			{
				desktop.browse(new URI(message.getUrl()));
			}
			catch(IOException | URISyntaxException ignored){}
        }
	}

}
