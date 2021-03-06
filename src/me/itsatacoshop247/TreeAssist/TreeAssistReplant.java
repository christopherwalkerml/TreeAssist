package me.itsatacoshop247.TreeAssist;

import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Sapling;

import me.itsatacoshop247.TreeAssist.core.Utils;

public class TreeAssistReplant implements Runnable {
    public final TreeAssist plugin;
    public Block block;
    private byte data;
    private TreeSpecies species;
    public Material mat;

    public TreeAssistReplant(TreeAssist instance, Block importBlock, TreeSpecies species) {
        this.plugin = instance;
        this.block = importBlock;
        this.species = species;
        this.data = -1;
        this.mat = Utils.getSaplingForSpecies(species);
    }

    public TreeAssistReplant(TreeAssist instance, Block importBlock, Material logMat) {
        this.plugin = instance;
        this.block = importBlock;
        this.data = 0;
        this.mat = logMat;
    }

    @Override
    public void run() {
        Material below = this.block.getRelative(BlockFace.DOWN).getType();
        if (plugin.isEnabled() &&
                (below == Material.DIRT || below == Material.GRASS_BLOCK || below == Material.PODZOL)) {
            this.block.setType(mat);
            if (data < 0) {
                BlockState state = block.getState();
                MaterialData data = state.getData();
                Sapling sap = (Sapling) data;
                sap.setSpecies(species);
                state.setData(sap);
                state.update();
            }
            if (plugin.getConfig().getInt("Time to Block Sapling Growth (Seconds)") > 0) {
                plugin.getListener().getAntiGrow().add(this.block, plugin.getConfig().getInt("Time to Block Sapling Growth (Seconds)"));
            }
        }
    }
}
