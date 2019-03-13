package com.cazsius.solcarrot;

import com.cazsius.solcarrot.capability.FoodCapability;
import com.cazsius.solcarrot.handler.CapabilityHandler;
import com.cazsius.solcarrot.handler.MaxHealthHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.management.PlayerList;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

// new config names + versioning commented out for now
// TODO switch to new names when updating to next mc version

@Mod.EventBusSubscriber(modid = SOLCarrot.MOD_ID)
@Config(modid = SOLCarrot.MOD_ID)//, name = Constants.MOD_ID + "-" + SOLCarrotConfig.version)
public class SOLCarrotConfig {
	//static final String version = "1.5"; // only change this if the new version is not backwards-compatible with the old one.
	
	//@Config.Name("Base Hearts")
	@Config.Name("defaultHeartCount")
	@Config.Comment("Number of hearts you start out with.")
	public static int baseHearts = 10;
	
	//@Config.Name("Hearts per Milestone")
	@Config.Name("heartsPerMilestone")
	@Config.Comment("Number of hearts you gain for reaching a new milestone.")
	public static int heartsPerMilestone = 2;
	
	//@Config.Name("Milestones")
	@Config.Name("Milestone amounts")
	@Config.Comment("A list of numbers of unique foods you need to eat to unlock each milestone, in ascending order.")
	public static int[] milestones = {5, 10, 15, 20, 25};
	
	//@Config.Name("Enable Food Status Tooltip")
	@Config.Name("isFoodTooltipEnabled")
	@Config.Comment("If true, foods indicate in their tooltips whether or not they have been eaten.")
	public static boolean isFoodTooltipEnabled = true;
	
	//@Config.Name("Show Progress Above Hotbar")
	@Config.Name("shouldShowProgressAboveHotbar")
	@Config.Comment("Whether the messages notifying you of reaching new milestones should be displayed above the hotbar or in chat.")
	public static boolean shouldShowProgressAboveHotbar = true;
	
	@Config.Name("Show Uneaten Foods")
	@Config.Comment("If true, the food book also lists foods that you haven't eaten, in addition to the ones you have.")
	public static boolean showUneatenFoods = true;
	
	@SubscribeEvent
	public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (!event.getModID().equals(SOLCarrot.MOD_ID)) return;
		
		ConfigManager.sync(SOLCarrot.MOD_ID, Config.Type.INSTANCE);
		
		if (event.isWorldRunning()) {
			PlayerList players = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList();
			for (EntityPlayer player : players.getPlayers()) {
				FoodCapability.get(player).updateProgressInfo();
				CapabilityHandler.syncFoodList(player);
				MaxHealthHandler.updateFoodHPModifier(player);
			}
		}
	}
}
