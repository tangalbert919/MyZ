package jordan.sicherman.utilities;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import jordan.sicherman.utilities.configuration.ConfigEntries;
import jordan.sicherman.utilities.configuration.FileMember;
import jordan.sicherman.utilities.configuration.FileUtilities;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class MyZRank {

    private static Set values = new HashSet();
    private final int identifier;
    private ItemStack[] inventory;
    private ItemStack helmet;
    private ItemStack chestplate;
    private ItemStack leggings;
    private ItemStack boots;
    private String chat;

    public MyZRank(int identifier) {
        this.identifier = identifier;
    }

    public MyZRank(int identifier, String chat, ItemStack[] inventory, ItemStack helmet, ItemStack chestplate, ItemStack leggings, ItemStack boots) {
        this.identifier = identifier;
        this.chat = chat;
        this.inventory = inventory;
        this.helmet = helmet;
        this.chestplate = chestplate;
        this.leggings = leggings;
        this.boots = boots;
    }

    public void setPrefix(String prefix) {
        ConfigurationSection ranks = (ConfigurationSection) ConfigEntries.RANKS.getValue();

        ranks.set(this.identifier + ".chat_prefix", prefix);
        FileUtilities.save(new FileMember[] { ConfigEntries.RANKS.getFile()});
        this.chat = prefix;
    }

    public void setEquipment(StartingKitManager.EquipmentPiece piece, ItemStack item) {
        ConfigurationSection ranks = (ConfigurationSection) ConfigEntries.RANKS.getValue();
        String slug = "helmet";

        switch (MyZRank.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$StartingKitManager$EquipmentPiece[piece.ordinal()]) {
        case 1:
            slug = "boots";
            this.boots = item;
            break;

        case 2:
            slug = "chestplate";
            this.chestplate = item;
            break;

        case 3:
            slug = "helmet";
            this.helmet = item;
            break;

        case 4:
            slug = "leggings";
            this.leggings = item;
        }

        ranks.set(this.identifier + ".equipment." + slug, item);
        FileUtilities.save(new FileMember[] { ConfigEntries.RANKS.getFile()});
    }

    public void setInventory(List inventory) {
        ConfigurationSection ranks = (ConfigurationSection) ConfigEntries.RANKS.getValue();

        ranks.set(this.identifier + ".equipment.inventory", inventory);
        FileUtilities.save(new FileMember[] { ConfigEntries.RANKS.getFile()});
        this.inventory = (ItemStack[]) inventory.toArray(new ItemStack[0]);
    }

    public static void load() {
        ConfigurationSection ranks = (ConfigurationSection) ConfigEntries.RANKS.getValue();

        MyZRank.values.clear();

        String key;
        String chat;
        ItemStack boots;
        ItemStack helmet;
        ItemStack chestplate;
        ItemStack leggings;
        ItemStack[] items;

        for (Iterator iterator = ranks.getKeys(false).iterator(); iterator.hasNext(); MyZRank.values.add(new MyZRank(Integer.valueOf(key).intValue(), chat, items, helmet, chestplate, leggings, boots))) {
            key = (String) iterator.next();
            chat = ranks.getString(key + ".chat_prefix");
            boots = ranks.getItemStack(key + ".equipment.boots");
            helmet = ranks.getItemStack(key + ".equipment.helmet");
            chestplate = ranks.getItemStack(key + ".equipment.chestplate");
            leggings = ranks.getItemStack(key + ".equipment.leggings");
            List inventory = ranks.getList(key + ".equipment.inventory");

            items = new ItemStack[0];
            if (inventory != null) {
                items = (ItemStack[]) inventory.toArray(new ItemStack[0]);
            }
        }

    }

    public static MyZRank forInt(int identifier, boolean create) {
        MyZRank nearest = null;
        Iterator iterator = MyZRank.values.iterator();

        while (iterator.hasNext()) {
            MyZRank rank = (MyZRank) iterator.next();

            if (rank.identifier == identifier) {
                return rank;
            }

            if (rank.identifier < identifier && (nearest == null || rank.identifier > nearest.identifier)) {
                nearest = rank;
            }
        }

        if (create) {
            nearest = new MyZRank(identifier);
            MyZRank.values.add(nearest);
        }

        return nearest;
    }

    public static MyZRank forInt(int identifier) {
        return forInt(identifier, false);
    }

    public ItemStack getEquipment(StartingKitManager.EquipmentPiece piece) {
        ItemStack item = null;

        switch (MyZRank.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$StartingKitManager$EquipmentPiece[piece.ordinal()]) {
        case 1:
            item = this.boots;
            break;

        case 2:
            item = this.chestplate;
            break;

        case 3:
            item = this.helmet;
            break;

        case 4:
            item = this.leggings;
            break;

        default:
            return null;
        }

        return item == null && this.identifier > 0 ? (((Boolean) ConfigEntries.RANK_CARRYOVER.getValue()).booleanValue() ? forInt(this.identifier - 1).getEquipment(piece) : null) : item;
    }

    public String getChatPrefix() {
        return ChatColor.translateAlternateColorCodes('&', this.chat);
    }

    public ItemStack[] getInventory() {
        HashSet items = new HashSet();

        items.addAll(Arrays.asList(this.inventory));
        if (((Boolean) ConfigEntries.RANK_CARRYOVER.getValue()).booleanValue()) {
            HashSet ids = new HashSet();

            for (int i = this.identifier - 1; i >= 0; --i) {
                MyZRank rank = forInt(i);

                if (!ids.contains(Integer.valueOf(rank.identifier))) {
                    ids.add(Integer.valueOf(rank.identifier));
                    items.addAll(Arrays.asList(rank.inventory));
                }
            }
        }

        return (ItemStack[]) items.toArray(new ItemStack[0]);
    }

    static class SyntheticClass_1 {

        static final int[] $SwitchMap$jordan$sicherman$utilities$StartingKitManager$EquipmentPiece = new int[StartingKitManager.EquipmentPiece.values().length];

        static {
            try {
                MyZRank.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$StartingKitManager$EquipmentPiece[StartingKitManager.EquipmentPiece.BOOTS.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                MyZRank.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$StartingKitManager$EquipmentPiece[StartingKitManager.EquipmentPiece.CHESTPLATE.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                MyZRank.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$StartingKitManager$EquipmentPiece[StartingKitManager.EquipmentPiece.HELMET.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                MyZRank.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$StartingKitManager$EquipmentPiece[StartingKitManager.EquipmentPiece.LEGGINGS.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

        }
    }
}
