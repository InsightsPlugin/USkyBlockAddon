package net.frankheijden.insights.addons.uskyblock;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.frankheijden.insights.entities.CacheAssistant;
import net.frankheijden.insights.entities.Selection;
import org.bukkit.Location;
import org.bukkit.World;
import us.talabrek.ultimateskyblock.handler.WorldGuardHandler;

public class USkyBlockAssistant extends CacheAssistant {

    public USkyBlockAssistant() {
        super("USkyBlock", "island");
    }

    public Location adapt(World world, BlockVector3 vector) {
        return new Location(world, vector.getX(), vector.getY(), vector.getZ());
    }

    public Selection adapt(World world, ProtectedRegion region) {
        if (region == null) return null;
        return new Selection(
                adapt(world, region.getMinimumPoint()),
                adapt(world, region.getMaximumPoint())
        );
    }

    @Override
    public Selection getSelection(Location location) {
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
