package syconn.swe.common;

import net.minecraft.resources.ResourceKey;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EndPortalBlock;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.portal.PortalForcer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.ServerLifecycleHooks;
import syconn.swe.Main;
import syconn.swe.init.ModCapabilities;
import syconn.swe.init.ModDim;
import syconn.swe.item.Parachute;
import syconn.swe.worldgen.dimension.DimChanger;

@Mod.EventBusSubscriber(modid = Main.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonHandler {

    public CommonHandler() {
        MinecraftForge.EVENT_BUS.register(new ModCapabilities());
    }

    @SubscribeEvent
    public static void entityTickEvent(LivingEvent.LivingTickEvent e){
        // MOON GRAVITY - 1/6th of Earth, default 0.08 -> 0.014
        if (e.getEntity().isAlive()) {
            AttributeInstance gravity = e.getEntity().getAttribute(ForgeMod.ENTITY_GRAVITY.get());
            if (ModDim.onMoon(e.getEntity()) && gravity.getValue() != 0.014){
                gravity.setBaseValue(0.014);
            } else if (!ModDim.onMoon(e.getEntity()) && gravity.getValue() != gravity.getAttribute().getDefaultValue()){
                gravity.setBaseValue(gravity.getAttribute().getDefaultValue());
            }
        }
    }

    @SubscribeEvent
    public static void playerTickEvent(TickEvent.PlayerTickEvent e){
        if (e.player instanceof ServerPlayer p){
            if (p.getY() >= 400 && ModDim.on(p, Level.OVERWORLD)){
                ServerLevel serverlevel = ((ServerLevel)p.level).getServer().getLevel(ModDim.MOON_KEY);
                if (serverlevel == null) {
                    return;
                }
                p.changeDimension(serverlevel, new DimChanger());
            }

            p.getCapability(ModCapabilities.SPACE_SUIT).ifPresent(ss -> {
                if (p.getInventory().armor.get(2).getItem() instanceof Parachute){
                    if (p.fallDistance > 0 && !ss.parachute())
                        ss.parachute(true);
                    else if (p.fallDistance == 0)
                        ss.parachute(false);
                } else ss.parachute(false);
            });
        }
    }

    @SubscribeEvent
    public static void fallDamageEvent(LivingFallEvent e){
        if (ModDim.onMoon(e.getEntity())){
            if (e.getDistance() < 6.5D)
                e.setCanceled(true);
            e.setDistance(e.getDistance() - 4.0f);
            e.setDamageMultiplier(0.16f);
        }
    }
}
