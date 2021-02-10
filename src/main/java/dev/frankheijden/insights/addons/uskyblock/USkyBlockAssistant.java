package dev.frankheijden.insights.addons.uskyblock;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import dev.frankheijden.insights.api.addons.InsightsAddon;
import dev.frankheijden.insights.api.addons.Region;
import dev.frankheijden.insights.api.addons.SimpleCuboidRegion;
import dev.frankheijden.insights.api.objects.math.Vector3;
import org.bukkit.Location;
import org.bukkit.World;
import us.talabrek.ultimateskyblock.handler.WorldGuardHandler;
import java.util.Optional;

public class USkyBlockAssistant implements InsightsAddon {

    public String getId(ProtectedRegion region) {
        return getPluginName() + "@" + region.getId();
    }

    public Optional<Region> adapt(ProtectedRegion region, World world) {
        if (region == null) return Optional.empty();
        BlockVector3 min = region.getMinimumPoint();
        BlockVector3 max = region.getMaximumPoint();
        return Optional.of(new SimpleCuboidRegion(
                world,
                new Vector3(min.getX(), min.getY(), min.getZ()),
                new Vector3(max.getX(), max.getY(), max.getZ()),
                getPluginName(),
                getId(region)
        ));
    }

    @Override
    public String getPluginName() {
        return "USkyBlock";
    }

    @Override
    public String getAreaName() {
        return "island";
    }

    @Override
    public String getVersion() {
        return "{version}";
    }

    @Override
    public Optional<Region> getRegion(Location location) {
        if (location.getWorld().getEnvironment() == World.Environment.NORMAL) {
            return adapt(WorldGuardHandler.getIslandRegionAt(location), location.getWorld());
        } else if (location.getWorld().getEnvironment() == World.Environment.NETHER) {
            return adapt(WorldGuardHandler.getNetherRegionAt(location), location.getWorld());
        }
        return Optional.empty();
    }
}
