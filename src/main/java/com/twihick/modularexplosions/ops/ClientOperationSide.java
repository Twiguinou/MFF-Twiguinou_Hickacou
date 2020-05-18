package com.twihick.modularexplosions.ops;

import com.twihick.modularexplosions.client.events.ClientEvents;
import com.twihick.modularexplosions.client.gui.ConfigurableBombScreen;
import com.twihick.modularexplosions.client.gui.TimerBombScreen;
import com.twihick.modularexplosions.client.registry.KeyBindingsList;
import com.twihick.modularexplosions.client.renderer.MasterRenderer;
import com.twihick.modularexplosions.client.renderer.entity.layers.ExplosiveBeltLayer;
import com.twihick.modularexplosions.client.renderer.entity.models.ExplosiveBeltModel;
import com.twihick.modularexplosions.client.renderer.entity.models.ExtendedPlayerModel;
import com.twihick.modularexplosions.tileentities.ConfigurableBombTileEntity;
import com.twihick.modularexplosions.tileentities.TimerBombTileEntity;
import com.twihick.modularexplosions.util.ReflectionUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.Map;

public final class ClientOperationSide extends ServerOperationSide {

    @Override
    public void client(FMLClientSetupEvent event) {
        this.setCustomPlayerModel();
        this.addExplosiveBeltLayerToPlayerModel();
        MasterRenderer.entities();
        MasterRenderer.tileEntities();
        KeyBindingsList.registerKeyBindings();
        MinecraftForge.EVENT_BUS.register(new ClientEvents());
    }

    private void setCustomPlayerModel() {
        PlayerRenderer playerRendererDefault = getSkinMap().get("default");
        PlayerRenderer playerRendererSlim = getSkinMap().get("slim");
        ReflectionUtil.setCustomPlayerModel(playerRendererDefault, new ExtendedPlayerModel<>(0.0F, false));
        ReflectionUtil.setCustomPlayerModel(playerRendererSlim, new ExtendedPlayerModel<>(0.0F, false));
    }

    private void addExplosiveBeltLayerToPlayerModel() {
        PlayerRenderer playerRendererDefault = getSkinMap().get("default");
        PlayerRenderer playerRendererSlim = getSkinMap().get("slim");
        ReflectionUtil.addLayerToPlayerModel(playerRendererDefault, new ExplosiveBeltLayer<>(playerRendererDefault, new ExplosiveBeltModel(0.0F)));
        ReflectionUtil.addLayerToPlayerModel(playerRendererSlim, new ExplosiveBeltLayer<>(playerRendererSlim, new ExplosiveBeltModel(0.0F)));
    }

    private Map<String, PlayerRenderer> getSkinMap() {
        return Minecraft.getInstance().getRenderManager().getSkinMap();
    }

    public static final class Helpers {
        public static void openTimerBombScreen(World world, BlockPos pos) {
            TileEntity te = world.getTileEntity(pos);
            if(te instanceof TimerBombTileEntity) {
                Minecraft.getInstance().displayGuiScreen(new TimerBombScreen((TimerBombTileEntity)te));
            }
        }
        public static void openConfigurableBombScreen(World world, BlockPos pos) {
            TileEntity te = world.getTileEntity(pos);
            if(te instanceof ConfigurableBombTileEntity) {
                Minecraft.getInstance().displayGuiScreen(new ConfigurableBombScreen((ConfigurableBombTileEntity)te));
            }
        }
    }

}
