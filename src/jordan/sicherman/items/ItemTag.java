package jordan.sicherman.items;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import jordan.sicherman.locales.LocaleMessage;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum ItemTag {

    MURKY_WATER(Material.POTION, new ItemTag.TagMeta(0, LocaleMessage.MURKY_WATER, (LocaleMessage) null, (Map) null, (ItemTag.SyntheticClass_1) null), new ItemTag.TagDefinition[] { ItemTag.TagDefinition.TITLE}), SALTY_WATER(Material.POTION, new ItemTag.TagMeta(0, LocaleMessage.SALT_WATER, (LocaleMessage) null, (Map) null, (ItemTag.SyntheticClass_1) null), new ItemTag.TagDefinition[] { ItemTag.TagDefinition.TITLE}), WAND(Material.BLAZE_ROD, new ItemTag.TagMeta(0, ChatColor.BLUE + "MyZ Wand", ChatColor.GOLD + "Drop to stop managing", (Map) null, (ItemTag.SyntheticClass_1) null), new ItemTag.TagDefinition[] { ItemTag.TagDefinition.TITLE, ItemTag.TagDefinition.LORE}), STARTER_CHESTPLATE(Material.LEATHER_CHESTPLATE, new ItemTag.TagMeta(0, LocaleMessage.STARTER_TUNIC_DISPLAY, LocaleMessage.STARTER_TUNIC_LORE, (Map) null, (ItemTag.SyntheticClass_1) null), new ItemTag.TagDefinition[] { ItemTag.TagDefinition.TITLE}), STARTER_SWORD(Material.WOOD_SWORD, new ItemTag.TagMeta(0, LocaleMessage.STARTER_SWORD_DISPLAY, LocaleMessage.STARTER_SWORD_LORE, (Map) null, (ItemTag.SyntheticClass_1) null), new ItemTag.TagDefinition[] { ItemTag.TagDefinition.TITLE}), CHAIN(Material.IRON_FENCE, new ItemTag.TagMeta(0, LocaleMessage.CHAIN, (LocaleMessage) null, (Map) null, (ItemTag.SyntheticClass_1) null), new ItemTag.TagDefinition[] { ItemTag.TagDefinition.TITLE}), GRAPPLE(Material.FISHING_ROD, new ItemTag.TagMeta(0, LocaleMessage.GRAPPLE_DISPLAY, LocaleMessage.GRAPPLE_LORE, (Map) null, (ItemTag.SyntheticClass_1) null), new ItemTag.TagDefinition[] { ItemTag.TagDefinition.TITLE}), COLD_WATER(Material.POTION, new ItemTag.TagMeta(0, LocaleMessage.COLD_WATER, (LocaleMessage) null, (Map) null, (ItemTag.SyntheticClass_1) null), new ItemTag.TagDefinition[] { ItemTag.TagDefinition.TITLE}), WARM_WATER(Material.POTION, new ItemTag.TagMeta(0, LocaleMessage.WARM_WATER, (LocaleMessage) null, (Map) null, (ItemTag.SyntheticClass_1) null), new ItemTag.TagDefinition[] { ItemTag.TagDefinition.TITLE});

    private final short data;
    private final Material material;
    private final String displayName;
    private final ItemTag.TagDefinition[] definition;
    private final Map enchants;
    private final List lore;

    private ItemTag(Material material, ItemTag.TagMeta meta, ItemTag.TagDefinition... definition) {
        this.material = material;
        this.definition = definition;
        this.enchants = meta.getEnchants();
        this.data = meta.getData();
        this.displayName = meta.getDisplayName();
        if (meta.getLore() != null && !meta.getLore().isEmpty()) {
            String[] cLore = meta.getLore().split("~");
            String[] loreBuilder = new String[cLore.length];

            for (int i = 0; i < cLore.length; ++i) {
                loreBuilder[i] = ChatColor.RESET + cLore[i];
            }

            this.lore = Arrays.asList(loreBuilder);
        } else {
            this.lore = null;
        }

    }

    public short getDurability() {
        return this.data;
    }

    public Material getType() {
        return this.material;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public ItemTag.TagDefinition[] getDefinition() {
        return this.definition;
    }

    public Map getEnchantments() {
        return this.enchants;
    }

    public List getLore() {
        return this.lore;
    }

    public boolean matches(ItemStack item) {
        if (item != null && item.getType() == this.getType()) {
            ItemTag.TagDefinition[] aitemtag_tagdefinition = this.getDefinition();
            int i = aitemtag_tagdefinition.length;

            for (int j = 0; j < i; ++j) {
                ItemTag.TagDefinition def = aitemtag_tagdefinition[j];

                if (!this.matches(item, def)) {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }

    private boolean matches(ItemStack item, ItemTag.TagDefinition definition) {
        switch (ItemTag.SyntheticClass_1.$SwitchMap$jordan$sicherman$items$ItemTag$TagDefinition[definition.ordinal()]) {
        case 1:
            return item.getDurability() == this.getDurability();

        case 2:
            return !item.hasItemMeta() && this.getEnchantments() == null || this.getEnchantments().equals(item.getItemMeta().getEnchants());

        case 3:
            return !item.hasItemMeta() && this.getLore() == null || this.getLore().equals(item.getItemMeta().getLore());

        case 4:
            return !item.hasItemMeta() && this.getDisplayName() == null || (ChatColor.RESET + this.getDisplayName()).equals(item.getItemMeta().getDisplayName());

        default:
            return true;
        }
    }

    public static ItemTag fromString(String string) {
        ItemTag[] aitemtag = values();
        int i = aitemtag.length;

        for (int j = 0; j < i; ++j) {
            ItemTag tag = aitemtag[j];

            if (tag.toString().toLowerCase().equals(string)) {
                return tag;
            }
        }

        return null;
    }

    static class SyntheticClass_1 {

        static final int[] $SwitchMap$jordan$sicherman$items$ItemTag$TagDefinition = new int[ItemTag.TagDefinition.values().length];

        static {
            try {
                ItemTag.SyntheticClass_1.$SwitchMap$jordan$sicherman$items$ItemTag$TagDefinition[ItemTag.TagDefinition.DURABILITY.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                ItemTag.SyntheticClass_1.$SwitchMap$jordan$sicherman$items$ItemTag$TagDefinition[ItemTag.TagDefinition.ENCHANTS.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                ItemTag.SyntheticClass_1.$SwitchMap$jordan$sicherman$items$ItemTag$TagDefinition[ItemTag.TagDefinition.LORE.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                ItemTag.SyntheticClass_1.$SwitchMap$jordan$sicherman$items$ItemTag$TagDefinition[ItemTag.TagDefinition.TITLE.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

        }
    }

    private static class TagMeta {

        private final short data;
        private final String name;
        private final String lore;
        private final Map enchants;

        private TagMeta(int data, LocaleMessage name, LocaleMessage lore, Map enchants) {
            this(data, name != null ? name.toString() : null, lore != null ? lore.toString() : null, enchants);
        }

        private TagMeta(int data, String name, String lore, Map enchants) {
            this.data = (short) data;
            this.name = name;
            this.lore = lore;
            this.enchants = enchants;
        }

        public String getDisplayName() {
            return this.name;
        }

        public String getLore() {
            return this.lore;
        }

        public Map getEnchants() {
            return this.enchants;
        }

        public short getData() {
            return this.data;
        }

        TagMeta(int x0, LocaleMessage x1, LocaleMessage x2, Map x3, ItemTag.SyntheticClass_1 x4) {
            this(x0, x1, x2, x3);
        }

        TagMeta(int x0, String x1, String x2, Map x3, ItemTag.SyntheticClass_1 x4) {
            this(x0, x1, x2, x3);
        }
    }

    private static enum TagDefinition {

        DURABILITY, TITLE, LORE, ENCHANTS;
    }
}
