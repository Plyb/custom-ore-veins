package io.github.orlouge.customoreveins.fabric;

import com.google.common.base.Suppliers;
import io.github.orlouge.customoreveins.CustomOreVein;
import io.github.orlouge.customoreveins.CustomOreVeinManager;
import io.github.orlouge.customoreveins.CustomOreVeinsMod;
import io.github.orlouge.customoreveins.PlatformHelper;
import io.github.orlouge.customoreveins.mixin.ChunkNoiseSamplerMixin;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.DynamicRegistrySetupCallback;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.registry.*;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class CustomOreVeinsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        CustomOreVeinsMod.init();
        DynamicRegistrySetupCallback.EVENT.register(view -> {
            view.registerEntryAdded(RegistryKeys.NOISE_PARAMETERS, (a, b, c) -> {

            });
            if (view.asDynamicRegistryManager().getOptional(RegistryKeys.NOISE_PARAMETERS).isPresent()) {
                DynamicRegistryManager mgr = view.asDynamicRegistryManager();
                PlatformHelperImpl.CUSTOM_ORE_VEIN_MANAGER.updateRegistryAccess(mgr);
            }
        });
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(PlatformHelperImpl.CUSTOM_ORE_VEIN_MANAGER);
    }
}
