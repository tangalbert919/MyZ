package jordan.sicherman.listeners.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import jordan.sicherman.MyZ;
import jordan.sicherman.items.EngineerManager;
import jordan.sicherman.items.EngineerRecipe;
import jordan.sicherman.items.ItemTag;
import jordan.sicherman.items.ItemUtilities;
import jordan.sicherman.locales.LocaleMessage;
import jordan.sicherman.nms.utilities.CompatibilityManager;
import jordan.sicherman.utilities.ChestType;
import jordan.sicherman.utilities.DataWrapper;
import jordan.sicherman.utilities.ManagerManager;
import jordan.sicherman.utilities.SerializableLocation;
import jordan.sicherman.utilities.Utilities;
import jordan.sicherman.utilities.configuration.ConfigEntries;
import jordan.sicherman.utilities.configuration.Configuration;
import jordan.sicherman.utilities.configuration.FileMember;
import jordan.sicherman.utilities.configuration.FileUtilities;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.DirectionalContainer;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

public class SpectatorMode implements Listener {

    @EventHandler(
        priority = EventPriority.MONITOR
    )
    private void onCloseChest(final InventoryCloseEvent e) {
        if (Utilities.inWorld((Entity) e.getPlayer())) {
            if (((Boolean) ConfigEntries.CHESTS.getValue()).booleanValue()) {
                if (e.getPlayer().hasMetadata("MyZ.bypassCheck")) {
                    e.getPlayer().removeMetadata("MyZ.bypassCheck", MyZ.instance);
                } else {
                    if (e.getInventory().getType() == InventoryType.CHEST && e.getInventory().getHolder() instanceof Chest) {
                        if (ChestType.isEmpty(e.getInventory().getContents())) {
                            MyZ.instance.getServer().getScheduler().runTaskLater(MyZ.instance, new Runnable() {
                                public void run() {
                                    ChestType.despawn(((Chest) e.getInventory().getHolder()).getBlock(), ((Chest) e.getInventory().getHolder()).getBlock().hasMetadata("MyZ.airdrop"));
                                }
                            }, 20L);
                        }
                    } else if ("Create Lootset".equals(e.getInventory().getName())) {
                        e.getPlayer().setMetadata(ManagerManager.ManagerType.CHESTS.getID(), new FixedMetadataValue(MyZ.instance, (Object) null));
                        ((Player) e.getPlayer()).sendMessage(LocaleMessage.CHEST_MANAGER_COMPLETE.toString((CommandSender) ((Player) e.getPlayer())));
                        FileUtilities.save(new FileMember[] { Configuration.CFiles.CHESTS});
                    }

                }
            }
        }
    }

    @EventHandler(
        priority = EventPriority.MONITOR,
        ignoreCancelled = true
    )
    private void onModifyInventory(final InventoryClickEvent e) {
        if (Utilities.inWorld((Entity) e.getWhoClicked())) {
            if (((Boolean) ConfigEntries.CHESTS.getValue()).booleanValue()) {
                if ("Create Lootset".equals(e.getInventory().getName())) {
                    (new BukkitRunnable() {
                        public void run() {
                            int[] aint = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8};
                            int i = aint.length;

                            for (int j = 0; j < i; ++j) {
                                int i1 = aint[j];

                                if (e.getInventory().getItem(i1) != null && e.getInventory().getItem(i1).getType() != Material.AIR) {
                                    ChestType.fromName(((MetadataValue) e.getWhoClicked().getMetadata(ManagerManager.ManagerType.CHESTS.getID()).get(0)).asString()).addItem(e.getInventory().getItem(i1));
                                    e.getWhoClicked().setMetadata("MyZ.bypassCheck", new FixedMetadataValue(MyZ.instance, Boolean.valueOf(true)));
                                    e.getWhoClicked().closeInventory();
                                    ((Player) e.getWhoClicked()).sendMessage(LocaleMessage.ADD_ITEM_INSTRUCTIONS.toString((CommandSender) ((Player) e.getWhoClicked())));
                                }
                            }

                        }
                    }).runTaskLater(MyZ.instance, 1L);
                }

            }
        }
    }

    @EventHandler(
        priority = EventPriority.LOWEST
    )
    private void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();

        if (Utilities.inWorld(player)) {
            if (ManagerManager.isManager(player, ManagerManager.ManagerType.ENGINEER) && ItemUtilities.getInstance().hasTag(player.getItemOnCursor(), ItemTag.WAND)) {
                switch (SpectatorMode.SyntheticClass_1.$SwitchMap$org$bukkit$event$block$Action[e.getAction().ordinal()]) {
                case 1:
                case 2:
                    e.setCancelled(true);
                    CompatibilityManager.interactAnvil(player, null);
                    break;

                case 3:
                case 4:
                    e.setCancelled(true);
                    int i = ((MetadataValue) player.getMetadata(ManagerManager.ManagerType.ENGINEER.getID()).get(0)).asInt() + 1;
                    List list = EngineerManager.getInstance().getRecipes();

                    if (list.isEmpty()) {
                        player.sendMessage(LocaleMessage.NO_RECIPES.toString((CommandSender) player));
                    } else {
                        EngineerRecipe engineerrecipe;

                        try {
                            engineerrecipe = (EngineerRecipe) list.get(i);
                        } catch (Exception exception) {
                            engineerrecipe = (EngineerRecipe) list.get(0);
                        }

                        player.sendMessage(LocaleMessage.VIEWING_RECIPE.filter(new Object[] { Integer.valueOf(i)}).toString(player));
                        player.setMetadata(ManagerManager.ManagerType.ENGINEER.getID(), new FixedMetadataValue(MyZ.instance, Integer.valueOf(i)));
                        CompatibilityManager.interactAnvil(player, null);
                    }
                }
            } else if (ManagerManager.isManager(player, ManagerManager.ManagerType.SPAWN) && ItemUtilities.getInstance().hasTag(player.getItemInHand(), ItemTag.WAND)) {
                switch (SpectatorMode.SyntheticClass_1.$SwitchMap$org$bukkit$event$block$Action[e.getAction().ordinal()]) {
                case 1:
                case 2:
                    e.setCancelled(true);
                    List list1 = Utilities.getSpawns();

                    if (list1.isEmpty()) {
                        player.sendMessage(LocaleMessage.NO_SPAWNS.toString((CommandSender) player));
                    } else {
                        int j = ((MetadataValue) player.getMetadata(ManagerManager.ManagerType.SPAWN.getID()).get(0)).asInt() + 1;

                        try {
                            player.teleport((Location) list1.get(j));
                        } catch (Exception exception1) {
                            j = 0;
                            player.teleport((Location) list1.get(j));
                        }

                        player.sendMessage(LocaleMessage.VIEWING_SPAWN.filter(new Object[] { Integer.valueOf(j)}).toString((CommandSender) player));
                        player.setMetadata(ManagerManager.ManagerType.SPAWN.getID(), new FixedMetadataValue(MyZ.instance, Integer.valueOf(j)));
                    }
                    break;

                case 3:
                case 4:
                    e.setCancelled(true);
                    if (player.isSneaking()) {
                        DataWrapper.set(ConfigEntries.HOME_SPAWN, SerializableLocation.fromLocation(player.getLocation()).serialize(), true);
                        player.sendMessage(LocaleMessage.HOME_SET.toString((CommandSender) player));
                        return;
                    }

                    ArrayList arraylist = new ArrayList((Collection) ConfigEntries.SPAWN_POINTS.getValue());
                    Iterator iterator = Utilities.getSpawns().iterator();

                    while (iterator.hasNext()) {
                        Location location = (Location) iterator.next();

                        if (player.getLocation().equals(location)) {
                            arraylist.remove(SerializableLocation.fromLocation(location).serialize());
                            DataWrapper.set(ConfigEntries.SPAWN_POINTS, arraylist, true);
                            player.sendMessage(LocaleMessage.REMOVED_SPAWN.toString((CommandSender) player));
                            return;
                        }
                    }

                    arraylist.add(SerializableLocation.fromLocation(player.getLocation()).serialize());
                    DataWrapper.set(ConfigEntries.SPAWN_POINTS, arraylist, true);
                    player.sendMessage(LocaleMessage.ADDED_SPAWN.toString((CommandSender) player));
                }
            } else if (ManagerManager.isManager(e.getPlayer(), ManagerManager.ManagerType.CHESTS) && ItemUtilities.getInstance().hasTag(e.getPlayer().getItemOnCursor(), ItemTag.WAND)) {
                if (e.getAction() != Action.RIGHT_CLICK_BLOCK && e.getAction() != Action.LEFT_CLICK_BLOCK) {
                    if (e.getAction() == Action.LEFT_CLICK_AIR) {
                        e.setCancelled(true);
                        e.getPlayer().sendMessage(LocaleMessage.CREATE_CHEST_INSTRUCTIONS.toString((CommandSender) e.getPlayer()));
                        e.getPlayer().setMetadata(ManagerManager.ManagerType.CHESTS.getID(), new FixedMetadataValue(MyZ.instance, ""));
                    } else if (e.getAction() == Action.RIGHT_CLICK_AIR) {
                        e.setCancelled(true);
                        e.getPlayer().sendMessage(LocaleMessage.CHEST_MANAGER_REMOVE.toString((CommandSender) e.getPlayer()));
                        ChatColor chatcolor = ChatColor.BLUE;
                        ChestType[] achesttype = ChestType.values();
                        int spawnpoints = achesttype.length;

                        for (int exc = 0; exc < spawnpoints; ++exc) {
                            ChestType type1 = achesttype[exc];

                            e.getPlayer().sendMessage(chatcolor + type1.getName());
                            chatcolor = chatcolor == ChatColor.BLUE ? ChatColor.DARK_GRAY : ChatColor.BLUE;
                        }

                        e.getPlayer().setMetadata(ManagerManager.ManagerType.CHESTS.getID(), new FixedMetadataValue(MyZ.instance, "a769bH3"));
                    }
                } else {
                    e.setCancelled(true);
                    Block color = e.getClickedBlock();

                    if (color.getState().getData() instanceof DirectionalContainer) {
                        ChestType type = ChestType.nextType(ChestType.getType(color));

                        ChestType.setType(color, type);
                        e.getPlayer().sendMessage(LocaleMessage.CHEST_SET.filter(new Object[] { type == null ? LocaleMessage.NOTHING.toString((CommandSender) e.getPlayer()) : type.getName()}).toString((CommandSender) e.getPlayer()));
                    }
                }
            }

        }
    }

    @EventHandler(
        priority = EventPriority.LOWEST
    )
    private void onDrop(PlayerDropItemEvent e) {
        if (Utilities.inWorld(e.getPlayer())) {
            if (ManagerManager.isManager(e.getPlayer()) && ItemUtilities.getInstance().hasTag(e.getItemDrop().getItemStack(), ItemTag.WAND)) {
                ManagerManager.stopManaging(e.getPlayer());
                e.getItemDrop().remove();
                e.getPlayer().sendMessage(LocaleMessage.MANAGING_OVER.toString((CommandSender) e.getPlayer()));
            }

        }
    }

    static class SyntheticClass_1 {

        static final int[] $SwitchMap$org$bukkit$event$block$Action = new int[Action.values().length];

        static {
            try {
                SpectatorMode.SyntheticClass_1.$SwitchMap$org$bukkit$event$block$Action[Action.RIGHT_CLICK_AIR.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                SpectatorMode.SyntheticClass_1.$SwitchMap$org$bukkit$event$block$Action[Action.RIGHT_CLICK_BLOCK.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                SpectatorMode.SyntheticClass_1.$SwitchMap$org$bukkit$event$block$Action[Action.LEFT_CLICK_AIR.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                SpectatorMode.SyntheticClass_1.$SwitchMap$org$bukkit$event$block$Action[Action.LEFT_CLICK_BLOCK.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

        }
    }
}
