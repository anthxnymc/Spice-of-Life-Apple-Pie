package com.cazsius.solcarrot.handler;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.io.IOException;

public abstract class NBTMessage implements IMessage {
	private static final String NBT_KEY_CONTENTS = "contents";
	
	@Override
	public void fromBytes(ByteBuf buf) {
		PacketBuffer pb = new PacketBuffer(buf);
		try {
			NBTTagCompound tag = new PacketBuffer(buf).readCompoundTag();
			assert tag != null;
			deserializeNBT(tag.getTag(NBT_KEY_CONTENTS));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public abstract void deserializeNBT(NBTBase tag);
	
	@Override
	public void toBytes(ByteBuf buf) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setTag(NBT_KEY_CONTENTS, serializeNBT());
		new PacketBuffer(buf).writeCompoundTag(tag);
	}
	
	public abstract NBTBase serializeNBT();
}