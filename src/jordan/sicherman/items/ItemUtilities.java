package jordan.sicherman.items;

import java.util.Iterator;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtilities {

    private static ItemUtilities instance;

    public ItemUtilities() {
        ItemUtilities.instance = this;
    }

    public static ItemUtilities getInstance() {
        return ItemUtilities.instance;
    }

    public ItemStack getTagItem(ItemTag tag, int amount) {
        return this.tag(new ItemStack(tag.getType(), amount), tag);
    }

    public ItemStack tag(ItemStack item, ItemTag tag) {
        if (item != null && tag != null) {
            if (item.getType() != tag.getType()) {
                return item;
            } else {
                item.setDurability(tag.getDurability());
                ItemMeta meta = item.getItemMeta();

                if (tag.getDisplayName() != null && !tag.getDisplayName().isEmpty()) {
                    meta.setDisplayName(ChatColor.RESET + tag.getDisplayName());
                }

                if (tag.getLore() != null && !tag.getLore().isEmpty()) {
                    meta.setLore(tag.getLore());
                }

                if (tag.getEnchantments() != null && !tag.getEnchantments().isEmpty()) {
                    Iterator iterator = tag.getEnchantments().keySet().iterator();

                    while (iterator.hasNext()) {
                        Enchantment key = (Enchantment) iterator.next();

                        meta.addEnchant(key, ((Integer) tag.getEnchantments().get(key)).intValue(), true);
                    }
                }

                item.setItemMeta(meta);
                return item;
            }
        } else {
            return null;
        }
    }

    public boolean hasTag(ItemStack item, ItemTag tag) {
        return item != null && tag != null ? tag.matches(item) : false;
    }

    public boolean isPoison(ItemStack item) {
        switch (ItemUtilities.SyntheticClass_1.$SwitchMap$org$bukkit$Material[item.getType().ordinal()]) {
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
            return true;

        default:
            return false;
        }
    }

    public boolean isFood(ItemStack item) {
        switch (ItemUtilities.SyntheticClass_1.$SwitchMap$org$bukkit$Material[item.getType().ordinal()]) {
        case 2:
        case 8:
        case 9:
        case 10:
        case 11:
        case 12:
        case 13:
        case 14:
        case 15:
        case 16:
        case 17:
        case 18:
        case 19:
        case 20:
        case 21:
        case 22:
        case 23:
        case 24:
        case 25:
            return true;

        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
        default:
            return false;
        }
    }

    static class SyntheticClass_1 {

        static final int[] $SwitchMap$org$bukkit$Material = new int[Material.values().length];

        static {
            try {
                ItemUtilities.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.POISONOUS_POTATO.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                ItemUtilities.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.PORK.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                ItemUtilities.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.RABBIT.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                ItemUtilities.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.RAW_BEEF.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            try {
                ItemUtilities.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.RAW_CHICKEN.ordinal()] = 5;
            } catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }

            try {
                ItemUtilities.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.RAW_FISH.ordinal()] = 6;
            } catch (NoSuchFieldError nosuchfielderror5) {
                ;
            }

            try {
                ItemUtilities.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.SPIDER_EYE.ordinal()] = 7;
            } catch (NoSuchFieldError nosuchfielderror6) {
                ;
            }

            try {
                ItemUtilities.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.APPLE.ordinal()] = 8;
            } catch (NoSuchFieldError nosuchfielderror7) {
                ;
            }

            try {
                ItemUtilities.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.BAKED_POTATO.ordinal()] = 9;
            } catch (NoSuchFieldError nosuchfielderror8) {
                ;
            }

            try {
                ItemUtilities.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.BREAD.ordinal()] = 10;
            } catch (NoSuchFieldError nosuchfielderror9) {
                ;
            }

            try {
                ItemUtilities.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.CARROT.ordinal()] = 11;
            } catch (NoSuchFieldError nosuchfielderror10) {
                ;
            }

            try {
                ItemUtilities.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.COOKED_BEEF.ordinal()] = 12;
            } catch (NoSuchFieldError nosuchfielderror11) {
                ;
            }

            try {
                ItemUtilities.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.COOKED_CHICKEN.ordinal()] = 13;
            } catch (NoSuchFieldError nosuchfielderror12) {
                ;
            }

            try {
                ItemUtilities.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.COOKED_FISH.ordinal()] = 14;
            } catch (NoSuchFieldError nosuchfielderror13) {
                ;
            }

            try {
                ItemUtilities.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.COOKED_MUTTON.ordinal()] = 15;
            } catch (NoSuchFieldError nosuchfielderror14) {
                ;
            }

            try {
                ItemUtilities.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.COOKED_RABBIT.ordinal()] = 16;
            } catch (NoSuchFieldError nosuchfielderror15) {
                ;
            }

            try {
                ItemUtilities.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.COOKIE.ordinal()] = 17;
            } catch (NoSuchFieldError nosuchfielderror16) {
                ;
            }

            try {
                ItemUtilities.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.GOLDEN_APPLE.ordinal()] = 18;
            } catch (NoSuchFieldError nosuchfielderror17) {
                ;
            }

            try {
                ItemUtilities.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.GRILLED_PORK.ordinal()] = 19;
            } catch (NoSuchFieldError nosuchfielderror18) {
                ;
            }

            try {
                ItemUtilities.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.MELON.ordinal()] = 20;
            } catch (NoSuchFieldError nosuchfielderror19) {
                ;
            }

            try {
                ItemUtilities.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.MUSHROOM_SOUP.ordinal()] = 21;
            } catch (NoSuchFieldError nosuchfielderror20) {
                ;
            }

            try {
                ItemUtilities.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.MUTTON.ordinal()] = 22;
            } catch (NoSuchFieldError nosuchfielderror21) {
                ;
            }

            try {
                ItemUtilities.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.POTATO.ordinal()] = 23;
            } catch (NoSuchFieldError nosuchfielderror22) {
                ;
            }

            try {
                ItemUtilities.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.PUMPKIN_PIE.ordinal()] = 24;
            } catch (NoSuchFieldError nosuchfielderror23) {
                ;
            }

            try {
                ItemUtilities.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.RABBIT_STEW.ordinal()] = 25;
            } catch (NoSuchFieldError nosuchfielderror24) {
                ;
            }

        }
    }
}
