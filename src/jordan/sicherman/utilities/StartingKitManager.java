package jordan.sicherman.utilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import jordan.sicherman.utilities.configuration.UserEntries;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class StartingKitManager {

    private static StartingKitManager instance;

    public StartingKitManager() {
        StartingKitManager.instance = this;
    }

    public static StartingKitManager getInstance() {
        return StartingKitManager.instance;
    }

    public void addToKit(Player player, ItemStack... item) {
        ArrayList contents = new ArrayList((Collection) DataWrapper.get(player, UserEntries.KIT_INVENTORY));
        ItemStack[] aitemstack = item;
        int i = item.length;

        for (int j = 0; j < i; ++j) {
            ItemStack i1 = aitemstack[j];

            contents.add(i1);
        }

        DataWrapper.set((OfflinePlayer) player, UserEntries.KIT_INVENTORY, contents);
    }

    public void setEquipment(Player player, ItemStack item, StartingKitManager.EquipmentPiece piece) {
        DataWrapper.set((OfflinePlayer) player, piece.entry, item);
    }

    public ItemStack getEquipment(Player playerFor, StartingKitManager.EquipmentPiece piece) {
        ItemStack item = (ItemStack) DataWrapper.get(playerFor, piece.entry);

        if (item == null || item.getType() == Material.AIR) {
            MyZRank rank = Utilities.getRank(playerFor);

            if (rank != null) {
                item = rank.getEquipment(piece);
            }
        }

        return item != null ? item.clone() : item;
    }

    public ItemStack[] getInventory(Player playerFor) {
        List custom = (List) DataWrapper.get(playerFor, UserEntries.KIT_INVENTORY);
        ArrayList inventory = new ArrayList();

        if (custom != null && !custom.isEmpty()) {
            Iterator rank = custom.iterator();

            while (rank.hasNext()) {
                ItemStack i = (ItemStack) rank.next();

                if (i != null) {
                    inventory.add(i.clone());
                }
            }
        }

        MyZRank myzrank = Utilities.getRank(playerFor);

        if (myzrank != null) {
            ItemStack[] aitemstack = myzrank.getInventory();
            int i = aitemstack.length;

            for (int j = 0; j < i; ++j) {
                ItemStack i1 = aitemstack[j];

                if (i1 != null) {
                    inventory.add(i1.clone());
                }
            }
        }

        return (ItemStack[]) inventory.toArray(new ItemStack[0]);
    }

    public static enum EquipmentPiece {

        HELMET(UserEntries.KIT_HELMET), CHESTPLATE(UserEntries.KIT_CHESTPLATE), LEGGINGS(UserEntries.KIT_LEGGINGS), BOOTS(UserEntries.KIT_BOOTS);

        private final UserEntries entry;

        private EquipmentPiece(UserEntries entry) {
            this.entry = entry;
        }
    }
}
