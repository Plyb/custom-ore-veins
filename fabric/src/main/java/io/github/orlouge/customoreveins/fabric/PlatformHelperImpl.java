package io.github.orlouge.customoreveins.fabric;

import io.github.orlouge.customoreveins.CustomOreVein;
import io.github.orlouge.customoreveins.CustomOreVeinManager;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.resource.ResourceFinder;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceReloader;
import net.minecraft.util.Identifier;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import com.google.common.base.Supplier;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.profiler.Profilers;

public class PlatformHelperImpl {
    public static Supplier<CustomOreVeinManagerFabric> CUSTOM_ORE_VEIN_MANAGER = () -> null;

    public static Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }

    public static CustomOreVeinManager getCustomOreVeinManager() {
        return CUSTOM_ORE_VEIN_MANAGER.get();
    }

    public static class CustomOreVeinManagerFabric extends CustomOreVeinManager implements IdentifiableResourceReloadListener {
        public CustomOreVeinManagerFabric(DynamicRegistryManager dynamicRegistryManager) {
            super(dynamicRegistryManager);
        }

        @Override
        public Identifier getFabricId() {
            return ID;
        }
    }
}
