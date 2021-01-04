package me.scaldice.testmod;

import com.sun.xml.internal.ws.api.addressing.WSEndpointReference;
import me.scaldice.testmod.client.gui.*;
import me.scaldice.testmod.commands.CheckUpdateCommand;
import me.scaldice.testmod.commands.TestHelpCommand;
import me.scaldice.testmod.config.TestConfig;
import me.scaldice.testmod.config.TestConfigGui;
import me.scaldice.testmod.events.HypixelDetector;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.stream.Metadata;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;


@Mod(
        modid = TestMod.MODID,
        version = TestMod.VERSION,
        name = TestMod.NAME,
        clientSideOnly = true
)
public class TestMod {
    public static final String MODID = "testmod";
    public static final String VERSION = "1.0";
    public static final String NAME = "Test Mod";

    public TestConfig CONFIG;

    private static TestMod instance;

    private String TM_KEY_CAT = "TestMod Keys";

    public KeyBinding configKey;

    public HypixelDetector hypixelDetector;

    public static TestMod getInstance() {
        return instance;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    instance = this;
    this.CONFIG = new TestConfig(event.getSuggestedConfigurationFile());
    this.CONFIG.syncConfig();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);

        MinecraftForge.EVENT_BUS.register(new TestModEventHandler());
        MinecraftForge.EVENT_BUS.register(new HPBarOverlay());
        MinecraftForge.EVENT_BUS.register(new HungerBarOverlay());
        MinecraftForge.EVENT_BUS.register(new NoHearts());
        MinecraftForge.EVENT_BUS.register(new NoHunger());
        MinecraftForge.EVENT_BUS.register(new HypixelDetector());

        ClientCommandHandler.instance.registerCommand(new TestHelpCommand());
        ClientCommandHandler.instance.registerCommand(new CheckUpdateCommand());

        this.hypixelDetector = new HypixelDetector();

        this.configKey = new KeyBinding("Open Config", Keyboard.KEY_RSHIFT, TM_KEY_CAT);
        ClientRegistry.registerKeyBinding(this.configKey);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        Util.getInstance().admins = new ArrayList<String>();
        String[] a = Util.getInstance().getAdmins().split(",");
        for(int i = 0; i < a.length; i++) {
            Util.getInstance().admins.add(a[i]);
            System.out.println("Adding " + a[i] + " to admins.");
        }
    }


    private String str = "" + EnumChatFormatting.AQUA + EnumChatFormatting.BOLD + "TestPog";
    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent e) {
        try {
                if (Minecraft.getMinecraft().inGameHasFocus) {

                }
                if (Minecraft.getMinecraft().inGameHasFocus) {
                    InfoHud.onRender();
                }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @SubscribeEvent
    public void keyPress(InputEvent.KeyInputEvent e) {
        if(this.configKey.isPressed()) {
            FMLClientHandler.instance().getClient().displayGuiScreen(new TestConfigGui(null));
        }
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent e) {
        try {
            if(e.modID.equals(MODID)){
                this.CONFIG.syncConfig();
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public static final Logger LOGGER = LogManager.getLogger();

}

