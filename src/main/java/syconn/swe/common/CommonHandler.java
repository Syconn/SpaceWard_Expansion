package syconn.swe.common;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import syconn.swe.Main;
import syconn.swe.common.inventory.ExtendedPlayerInventory;
import syconn.swe.init.ModCapabilities;
import syconn.swe.init.ModDim;
import syconn.swe.item.EquipmentItem;
import syconn.swe.item.Parachute;
import syconn.swe.item.SpaceArmor;
import syconn.swe.worldgen.dimension.DimChanger;
import syconn.swe.worldgen.dimension.DimSettings;

@Mod.EventBusSubscriber(modid = Main.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonHandler {

    public CommonHandler() {
        MinecraftForge.EVENT_BUS.register(new ModCapabilities());
    }

    @SubscribeEvent
    public static void entityTickEvent(LivingEvent.LivingTickEvent e){
        if (e.getEntity().isAlive()) {
            AttributeInstance gravity = e.getEntity().getAttribute(ForgeMod.ENTITY_GRAVITY.get());
            double g = DimSettings.get(e.getEntity().level.dimension()).getG();
            if (gravity.getValue() != g) gravity.setBaseValue(g);
            e.getEntity().getCapability(ModCapabilities.SPACE_SUIT).ifPresent(iSpaceSuit -> { if (iSpaceSuit.parachute()) gravity.setBaseValue(g / 12.0); });
        }
    }

    @SubscribeEvent
    public static void playerTickEvent(TickEvent.PlayerTickEvent e){
        if (e.player instanceof ServerPlayer p){
            if (p.getY() >= 400 && ModDim.on(p, Level.OVERWORLD)){
                ServerLevel serverlevel = ((ServerLevel)p.level).getServer().getLevel(ModDim.MOON_KEY);
                if (serverlevel == null) return;
                p.changeDimension(serverlevel, new DimChanger());
            }
        }
        Player p = e.player; //BOTH CLIENT AND SERVER
        p.getCapability(ModCapabilities.SPACE_SUIT).ifPresent(ss -> {
            if (p.getInventory().armor.get(2).getItem() instanceof Parachute || SpaceArmor.hasParachute(p)){
                if (p.fallDistance > 2 && !ss.parachute()) ss.parachute(true);
                else if (p.fallDistance == 0) ss.parachute(false);
            } else ss.parachute(false);
        });
        if (ModDim.onMoon(p)){
            if (!p.getAbilities().instabuild) {
                p.getCapability(ModCapabilities.SPACE_SUIT).ifPresent(ss -> {
                    ss.setO2(ss.maxO2());
                });
            }
        }
    }

    @SubscribeEvent
    public static void fallDamageEvent(LivingFallEvent e){
        e.getEntity().getCapability(ModCapabilities.SPACE_SUIT).ifPresent(ss -> { if (ss.parachute()) e.setCanceled(true); });
        if (ModDim.onMoon(e.getEntity())){
            if (e.getDistance() < 6.5D) e.setCanceled(true);
            e.setDistance(e.getDistance() - 4.0f);
            e.setDamageMultiplier(0.16f);
        }
    }
}
