package io.github.orlouge.customoreveins;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceFinder;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.world.dimension.DimensionType;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class CustomOreVeinManager extends JsonDataLoader<CustomOreVein> {
//    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static final Identifier ID = Identifier.of("worldgen/custom_ore_veins");
    public static final RegistryKey<Registry<CustomOreVein>> CUSTOM_ORE_VEINS_REGISTRY_KEY = RegistryKey.ofRegistry(ID);
    private Map<Identifier, CustomOreVein> customOreVeins = Map.of();
    public Supplier<DynamicRegistryManager> registryAccess = () -> null;
    private final ResourceFinder finder;

    public CustomOreVeinManager(DynamicRegistryManager dynamicRegistryManager) {
        super(dynamicRegistryManager, CustomOreVein.CODEC, CUSTOM_ORE_VEINS_REGISTRY_KEY);
        finder = ResourceFinder.json(CUSTOM_ORE_VEINS_REGISTRY_KEY);
        registryAccess = () -> dynamicRegistryManager;
    }



    @Override
    protected void apply(Map<Identifier, CustomOreVein> prepared, ResourceManager manager, Profiler profiler) {
        Map<Identifier, CustomOreVein> veins = new HashMap<>();
//        DynamicRegistryManager registryAccess = this.registryAccess.get();
//        RegistryOps<JsonElement> ops = RegistryOps.of(JsonOps.INSTANCE, registryAccess == null ? BuiltinRegistries.createWrapperLookup() : registryAccess);

        System.out.println("prepared: " + prepared);
        System.out.println("path: " + RegistryKeys.getPath(CUSTOM_ORE_VEINS_REGISTRY_KEY));
        System.out.println("finder: " + ResourceFinder.json(CUSTOM_ORE_VEINS_REGISTRY_KEY).findAllResources(manager));
        prepared.forEach((identifier, customOreVein) -> {
            if (customOreVein == null) return;
//            CustomOreVein vein = CustomOreVein.CODEC.decode(ops, customOreVein).getOrThrow().getFirst();
            veins.put(identifier, customOreVein);
            System.out.println("Loaded " + identifier);
        });

        this.customOreVeins = veins;
    }

    @Override
    protected Map<Identifier, CustomOreVein> prepare(ResourceManager resourceManager, Profiler profiler) {
        Map<Identifier, CustomOreVein> map = new HashMap();
        System.out.println("existing: " + registryAccess.get().getOrThrow(RegistryKeys.NOISE_PARAMETERS).getKeys());
        var ops = registryAccess.get().getOps(JsonOps.INSTANCE);
        load(resourceManager, this.finder, ops, CustomOreVein.CODEC, map);
        return map;
    }

    public Collection<CustomOreVein> getCustomOreVeins(RegistryEntry<DimensionType> dimension) {
        if (dimension == null || dimension.getKey().isEmpty()) return Collections.emptyList();
        return customOreVeins.values().stream().filter(cov -> cov.dimension().equals(dimension.getKey().get())).toList();
    }
}
