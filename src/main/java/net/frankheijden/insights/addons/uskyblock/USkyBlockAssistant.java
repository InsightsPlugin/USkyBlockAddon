package net.frankheijden.insights.addons.uskyblock;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.frankheijden.insights.entities.Area;
import net.frankheijden.insights.entities.CacheAssistant;
import net.frankheijden.insights.entities.CuboidSelection;
import org.bukkit.Location;
import org.bukkit.World;
import us.talabrek.ultimateskyblock.handler.WorldGuardHandler;

import java.util.Collections;

public class USkyBlockAssistant extends CacheAssistant {

    public USkyBlockAssistant() {
        super("USkyBlock", "USkyBlock", "island", "1.1.0");
    }

    public String getId(ProtectedRegion region) {
        return getPluginName() + "@" + region.getId();
    }

    public Location adapt(World world, BlockVector3 vector) {
        return new Location(world, vector.getX(), vector.getY(), vector.getZ());
    }

    public Area adapt(World world, ProtectedRegion region) {
        if (region == null) return null;
        return Area.from(this, getId(region), Collections.singletonList(new CuboidSelection(
                adapt(world, region.getMinimumPoint()),
                adapt(world, region.getMaximumPoint())
        )));
    }

    @Override
    public Area getArea(Location location) {
        ProtectedRegion region = null;
        switch (location.getWorld().getEnvironment()) {
            case NORMAL:
                region = WorldGuardHandler.getIslandRegionAt(location);
                break;
            case NETHER:
                region = WorldGuardHandler.getNetherRegionAt(location);
                break;
            default:
                break;
        }
        return adapt(location.getWorld(), region);
    }
}
