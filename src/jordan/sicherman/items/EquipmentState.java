package jordan.sicherman.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import jordan.sicherman.locales.LocaleMessage;
import jordan.sicherman.utilities.configuration.ConfigEntries;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum EquipmentState {

    DEVASTATED(1, 0, -1, -1, LocaleMessage.DEVASTATED, ConfigEntries.DEVASTATED_MOD), BROKEN(2, 1, 0, -1, LocaleMessage.BROKEN, ConfigEntries.BROKEN_MOD), NORMAL(5, 3, 3, 1, (LocaleMessage) null, (ConfigEntries) null), REINFORCED(-1, 4, 4, -1, LocaleMessage.REINFORCED, ConfigEntries.REINFORCED_MOD), SHARPENED(6, -1, -1, -1, LocaleMessage.SHARPENED, ConfigEntries.SHARPENED_MOD), TEMPERED(7, -1, 0, -1, LocaleMessage.TEMPERED, ConfigEntries.TEMPERED_MOD), SHATTERED(0, -1, 0, -1, LocaleMessage.SHATTERED, ConfigEntries.SHATTERED_MOD), DULL(4, -1, 0, -1, LocaleMessage.DULL, ConfigEntries.DULL_MOD), WEAKENED(3, 2, 0, -1, LocaleMessage.WEAKENED, ConfigEntries.WEAKENED_MOD), FORTIFIED(-1, 5, 0, -1, LocaleMessage.FORTIFIED, ConfigEntries.FORTIFIED_MOD), ORNATE(-1, 6, 0, -1, LocaleMessage.ORNATE, ConfigEntries.ORNATE_MOD), CRACKED(-1, -1, 1, -1, LocaleMessage.CRACKED, ConfigEntries.CRACKED_MOD), SLACK(-1, -1, 2, -1, LocaleMessage.SLACK, ConfigEntries.SLACK_MOD), PRECISE(-1, -1, 5, -1, LocaleMessage.PRECISE, ConfigEntries.PRECISE_MOD), BOW_SHARPENED(-1, -1, 6, -1, LocaleMessage.BOW_SHARPENED, ConfigEntries.BOW_SHARPENED_MOD), GRAPPLE_WEAK(-1, -1, -1, 0, LocaleMessage.WEAK, (ConfigEntries) null), LIGHTWEIGHT(-1, -1, -1, 2, LocaleMessage.LIGHTWEIGHT_GRAPPLE, (ConfigEntries) null);

    private static final int MAX_SWORD = 7;
    private static final int MAX_ARMOR = 6;
    private static final int MAX_BOW = 6;
    private static final int MAX_ROD = 2;
    private final List loreModifier;
    private final int sP;
    private final int gP;
    private final int bP;
    private final int rP;
    private final int effectiveness;

    public boolean isCompatibleWith(Material material) {
        if (material == null) {
            return false;
        } else if (this == EquipmentState.NORMAL) {
            return true;
        } else if (isBow(material)) {
            switch (EquipmentState.SyntheticClass_1.$SwitchMap$jordan$sicherman$items$EquipmentState[this.ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                return true;

            default:
                return false;
            }
        } else if (isRod(material)) {
            switch (EquipmentState.SyntheticClass_1.$SwitchMap$jordan$sicherman$items$EquipmentState[this.ordinal()]) {
            case 7:
            case 8:
                return true;

            default:
                return false;
            }
        } else if (isSword(material)) {
            switch (EquipmentState.SyntheticClass_1.$SwitchMap$jordan$sicherman$items$EquipmentState[this.ordinal()]) {
            case 1:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
                return true;

            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            default:
                return false;
            }
        } else if (isArmor(material)) {
            switch (EquipmentState.SyntheticClass_1.$SwitchMap$jordan$sicherman$items$EquipmentState[this.ordinal()]) {
            case 1:
            case 4:
            case 10:
            case 11:
            case 15:
            case 16:
                return true;

            case 2:
            case 3:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 12:
            case 13:
            case 14:
            default:
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean isCompatibleWith(ItemStack item) {
        return this.isCompatibleWith(item.getType());
    }

    private EquipmentState(int sP, int gP, int bP, int rP, LocaleMessage loreModifier, ConfigEntries entry) {
        this.sP = sP;
        this.gP = gP;
        this.bP = bP;
        this.rP = rP;
        if (entry == null) {
            this.effectiveness = 100;
        } else {
            this.effectiveness = ((Integer) entry.getValue()).intValue();
        }

        String lore = loreModifier == null ? null : (bP == 5 ? loreModifier.filter(new Object[] { Integer.valueOf(this.effectiveness), ConfigEntries.BOW_PRECISE_HEADSHOT_MOD.getValue()}).toString() : (bP == 6 ? loreModifier.filter(new Object[] { Integer.valueOf(this.effectiveness), ConfigEntries.BOW_SHARPENED_COMBAT_MOD.getValue()}).toString() : loreModifier.filter(new Object[] { Integer.valueOf(this.effectiveness)}).toString()));

        if (lore != null) {
            String[] cLore = lore.split("~");
            String[] loreBuilder = new String[cLore.length];

            for (int i = 0; i < cLore.length; ++i) {
                loreBuilder[i] = ChatColor.RESET + cLore[i];
            }

            this.loreModifier = Arrays.asList(loreBuilder);
        } else {
            this.loreModifier = null;
        }

    }

    public int getPosition(ItemStack itemOn) {
        boolean s = isSword(itemOn.getType());
        boolean b = isBow(itemOn.getType());
        boolean r = isRod(itemOn.getType());
        boolean a = isArmor(itemOn.getType());

        return s ? this.sP : (b ? this.bP : (r ? this.rP : (a ? this.gP : -1)));
    }

    public List getLoreModifier() {
        return this.loreModifier;
    }

    public double getEffectiveness() {
        return (double) this.effectiveness / 100.0D;
    }

    public static double getEffectiveness(ItemStack item) {
        return getState(item).getEffectiveness();
    }

    public static ItemStack applyPrevious(ItemStack item) {
        EquipmentState other = getPrevious(item);

        return other == null ? item : other.applyTo(item);
    }

    public static ItemStack applyNext(ItemStack item) {
        EquipmentState other = getNext(item);

        return other == null ? item : other.applyTo(item);
    }

    public static EquipmentState getNext(ItemStack item) {
        boolean s = isSword(item.getType());
        boolean b = isBow(item.getType());
        boolean r = isRod(item.getType());
        boolean a = isArmor(item.getType());

        if (!s && !b && !r && !a) {
            return null;
        } else {
            EquipmentState current = getState(item);
            int cur = s ? current.sP : (b ? current.bP : (r ? current.rP : current.gP));
            int max = s ? 7 : (b ? 6 : (r ? 2 : 6));

            if (cur >= max) {
                return null;
            } else {
                ++cur;
                EquipmentState[] aequipmentstate = values();
                int i = aequipmentstate.length;

                for (int j = 0; j < i; ++j) {
                    EquipmentState state = aequipmentstate[j];

                    if (state.isCompatibleWith(item) && cur == (s ? state.sP : (b ? state.bP : (r ? state.rP : state.gP)))) {
                        return state;
                    }
                }

                return null;
            }
        }
    }

    public static EquipmentState getPrevious(ItemStack item) {
        boolean s = isSword(item.getType());
        boolean b = isBow(item.getType());
        boolean r = isRod(item.getType());
        boolean a = isArmor(item.getType());

        if (!s && !b && !r && !a) {
            return null;
        } else {
            EquipmentState current = getState(item);
            int cur = s ? current.sP : (b ? current.bP : (r ? current.rP : current.gP));

            if (cur <= 0) {
                return null;
            } else {
                --cur;
                EquipmentState[] aequipmentstate = values();
                int i = aequipmentstate.length;

                for (int j = 0; j < i; ++j) {
                    EquipmentState state = aequipmentstate[j];

                    if (state.isCompatibleWith(item) && cur == (s ? state.sP : (b ? state.bP : (r ? state.rP : state.gP)))) {
                        return state;
                    }
                }

                return null;
            }
        }
    }

    private static boolean isSword(Material item) {
        switch (EquipmentState.SyntheticClass_1.$SwitchMap$org$bukkit$Material[item.ordinal()]) {
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
            return true;

        default:
            return false;
        }
    }

    public static boolean isArmor(Material item) {
        switch (EquipmentState.SyntheticClass_1.$SwitchMap$org$bukkit$Material[item.ordinal()]) {
        case 6:
        case 7:
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

        default:
            return false;
        }
    }

    private static boolean isBow(Material item) {
        return item == Material.BOW;
    }

    private static boolean isRod(Material item) {
        return item == Material.FISHING_ROD;
    }

    public ItemStack applyTo(ItemStack item) {
        if (!this.isCompatibleWith(item)) {
            return item;
        } else {
            EquipmentState state = getState(item);

            if (state != EquipmentState.NORMAL) {
                removeFrom(item);
            }

            if (this.getLoreModifier() == null) {
                return item;
            } else {
                ItemMeta meta = item.getItemMeta();

                if (!meta.hasLore()) {
                    meta.setLore(this.getLoreModifier());
                } else {
                    ArrayList lore = new ArrayList(meta.getLore());

                    for (int i = this.getLoreModifier().size() - 1; i >= 0; --i) {
                        lore.add(0, this.getLoreModifier().get(i));
                    }

                    meta.setLore(lore);
                }

                item.setItemMeta(meta);
                return item;
            }
        }
    }

    public static ItemStack removeFrom(ItemStack item) {
        EquipmentState state = getState(item);

        if (state == EquipmentState.NORMAL) {
            return item;
        } else {
            ItemMeta meta = item.getItemMeta();
            ArrayList lore = new ArrayList(meta.getLore());

            label34:
            for (int i = 0; i < meta.getLore().size(); ++i) {
                if (((String) meta.getLore().get(i)).equals(state.getLoreModifier().get(i))) {
                    int x = 0;

                    while (true) {
                        if (x >= state.getLoreModifier().size()) {
                            break label34;
                        }

                        try {
                            lore.remove(i);
                        } catch (Exception exception) {
                            ;
                        }

                        ++x;
                    }
                }
            }

            meta.setLore(lore);
            item.setItemMeta(meta);
            return item;
        }
    }

    public static EquipmentState getState(ItemStack item) {
        if (item == null) {
            return EquipmentState.NORMAL;
        } else {
            ItemMeta meta = item.getItemMeta();

            if (meta != null && meta.hasLore()) {
                EquipmentState[] aequipmentstate = values();
                int i = aequipmentstate.length;
                int j = 0;

                while (j < i) {
                    EquipmentState state = aequipmentstate[j];
                    boolean match = false;
                    int i1 = 0;

                    while (true) {
                        if (i1 < meta.getLore().size()) {
                            label51: {
                                try {
                                    if (((String) meta.getLore().get(i1)).equals(state.getLoreModifier().get(i1))) {
                                        match = true;
                                        int e = 0;

                                        while (true) {
                                            if (e >= state.getLoreModifier().size()) {
                                                break label51;
                                            }

                                            try {
                                                if (!((String) meta.getLore().get(i1 + e)).equals(state.getLoreModifier().get(e))) {
                                                    match = false;
                                                }
                                            } catch (Exception exception) {
                                                match = false;
                                            }

                                            ++e;
                                        }
                                    }
                                } catch (Exception exception1) {
                                    break label51;
                                }

                                ++i1;
                                continue;
                            }
                        }

                        if (match) {
                            return state;
                        }

                        ++j;
                        break;
                    }
                }

                return EquipmentState.NORMAL;
            } else {
                return EquipmentState.NORMAL;
            }
        }
    }

    public static EquipmentState fromString(String string) {
        EquipmentState[] aequipmentstate = values();
        int i = aequipmentstate.length;

        for (int j = 0; j < i; ++j) {
            EquipmentState state = aequipmentstate[j];

            if (state.toString().toLowerCase().equals(string)) {
                return state;
            }
        }

        return null;
    }

    static class SyntheticClass_1 {

        static final int[] $SwitchMap$jordan$sicherman$items$EquipmentState;
        static final int[] $SwitchMap$org$bukkit$Material = new int[Material.values().length];

        static {
            try {
                EquipmentState.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.WOOD_SWORD.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                EquipmentState.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.STONE_SWORD.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                EquipmentState.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.GOLD_SWORD.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                EquipmentState.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.IRON_SWORD.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            try {
                EquipmentState.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.DIAMOND_SWORD.ordinal()] = 5;
            } catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }

            try {
                EquipmentState.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.LEATHER_HELMET.ordinal()] = 6;
            } catch (NoSuchFieldError nosuchfielderror5) {
                ;
            }

            try {
                EquipmentState.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.LEATHER_CHESTPLATE.ordinal()] = 7;
            } catch (NoSuchFieldError nosuchfielderror6) {
                ;
            }

            try {
                EquipmentState.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.LEATHER_LEGGINGS.ordinal()] = 8;
            } catch (NoSuchFieldError nosuchfielderror7) {
                ;
            }

            try {
                EquipmentState.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.LEATHER_BOOTS.ordinal()] = 9;
            } catch (NoSuchFieldError nosuchfielderror8) {
                ;
            }

            try {
                EquipmentState.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.GOLD_HELMET.ordinal()] = 10;
            } catch (NoSuchFieldError nosuchfielderror9) {
                ;
            }

            try {
                EquipmentState.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.GOLD_CHESTPLATE.ordinal()] = 11;
            } catch (NoSuchFieldError nosuchfielderror10) {
                ;
            }

            try {
                EquipmentState.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.GOLD_LEGGINGS.ordinal()] = 12;
            } catch (NoSuchFieldError nosuchfielderror11) {
                ;
            }

            try {
                EquipmentState.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.GOLD_BOOTS.ordinal()] = 13;
            } catch (NoSuchFieldError nosuchfielderror12) {
                ;
            }

            try {
                EquipmentState.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.CHAINMAIL_HELMET.ordinal()] = 14;
            } catch (NoSuchFieldError nosuchfielderror13) {
                ;
            }

            try {
                EquipmentState.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.CHAINMAIL_CHESTPLATE.ordinal()] = 15;
            } catch (NoSuchFieldError nosuchfielderror14) {
                ;
            }

            try {
                EquipmentState.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.CHAINMAIL_LEGGINGS.ordinal()] = 16;
            } catch (NoSuchFieldError nosuchfielderror15) {
                ;
            }

            try {
                EquipmentState.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.CHAINMAIL_BOOTS.ordinal()] = 17;
            } catch (NoSuchFieldError nosuchfielderror16) {
                ;
            }

            try {
                EquipmentState.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.DIAMOND_HELMET.ordinal()] = 18;
            } catch (NoSuchFieldError nosuchfielderror17) {
                ;
            }

            try {
                EquipmentState.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.DIAMOND_CHESTPLATE.ordinal()] = 19;
            } catch (NoSuchFieldError nosuchfielderror18) {
                ;
            }

            try {
                EquipmentState.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.DIAMOND_LEGGINGS.ordinal()] = 20;
            } catch (NoSuchFieldError nosuchfielderror19) {
                ;
            }

            try {
                EquipmentState.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.DIAMOND_BOOTS.ordinal()] = 21;
            } catch (NoSuchFieldError nosuchfielderror20) {
                ;
            }

            try {
                EquipmentState.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.IRON_HELMET.ordinal()] = 22;
            } catch (NoSuchFieldError nosuchfielderror21) {
                ;
            }

            try {
                EquipmentState.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.IRON_CHESTPLATE.ordinal()] = 23;
            } catch (NoSuchFieldError nosuchfielderror22) {
                ;
            }

            try {
                EquipmentState.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.IRON_LEGGINGS.ordinal()] = 24;
            } catch (NoSuchFieldError nosuchfielderror23) {
                ;
            }

            try {
                EquipmentState.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.IRON_BOOTS.ordinal()] = 25;
            } catch (NoSuchFieldError nosuchfielderror24) {
                ;
            }

            $SwitchMap$jordan$sicherman$items$EquipmentState = new int[EquipmentState.values().length];

            try {
                EquipmentState.SyntheticClass_1.$SwitchMap$jordan$sicherman$items$EquipmentState[EquipmentState.BROKEN.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror25) {
                ;
            }

            try {
                EquipmentState.SyntheticClass_1.$SwitchMap$jordan$sicherman$items$EquipmentState[EquipmentState.CRACKED.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror26) {
                ;
            }

            try {
                EquipmentState.SyntheticClass_1.$SwitchMap$jordan$sicherman$items$EquipmentState[EquipmentState.SLACK.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror27) {
                ;
            }

            try {
                EquipmentState.SyntheticClass_1.$SwitchMap$jordan$sicherman$items$EquipmentState[EquipmentState.REINFORCED.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror28) {
                ;
            }

            try {
                EquipmentState.SyntheticClass_1.$SwitchMap$jordan$sicherman$items$EquipmentState[EquipmentState.PRECISE.ordinal()] = 5;
            } catch (NoSuchFieldError nosuchfielderror29) {
                ;
            }

            try {
                EquipmentState.SyntheticClass_1.$SwitchMap$jordan$sicherman$items$EquipmentState[EquipmentState.BOW_SHARPENED.ordinal()] = 6;
            } catch (NoSuchFieldError nosuchfielderror30) {
                ;
            }

            try {
                EquipmentState.SyntheticClass_1.$SwitchMap$jordan$sicherman$items$EquipmentState[EquipmentState.GRAPPLE_WEAK.ordinal()] = 7;
            } catch (NoSuchFieldError nosuchfielderror31) {
                ;
            }

            try {
                EquipmentState.SyntheticClass_1.$SwitchMap$jordan$sicherman$items$EquipmentState[EquipmentState.LIGHTWEIGHT.ordinal()] = 8;
            } catch (NoSuchFieldError nosuchfielderror32) {
                ;
            }

            try {
                EquipmentState.SyntheticClass_1.$SwitchMap$jordan$sicherman$items$EquipmentState[EquipmentState.SHATTERED.ordinal()] = 9;
            } catch (NoSuchFieldError nosuchfielderror33) {
                ;
            }

            try {
                EquipmentState.SyntheticClass_1.$SwitchMap$jordan$sicherman$items$EquipmentState[EquipmentState.DEVASTATED.ordinal()] = 10;
            } catch (NoSuchFieldError nosuchfielderror34) {
                ;
            }

            try {
                EquipmentState.SyntheticClass_1.$SwitchMap$jordan$sicherman$items$EquipmentState[EquipmentState.WEAKENED.ordinal()] = 11;
            } catch (NoSuchFieldError nosuchfielderror35) {
                ;
            }

            try {
                EquipmentState.SyntheticClass_1.$SwitchMap$jordan$sicherman$items$EquipmentState[EquipmentState.DULL.ordinal()] = 12;
            } catch (NoSuchFieldError nosuchfielderror36) {
                ;
            }

            try {
                EquipmentState.SyntheticClass_1.$SwitchMap$jordan$sicherman$items$EquipmentState[EquipmentState.SHARPENED.ordinal()] = 13;
            } catch (NoSuchFieldError nosuchfielderror37) {
                ;
            }

            try {
                EquipmentState.SyntheticClass_1.$SwitchMap$jordan$sicherman$items$EquipmentState[EquipmentState.TEMPERED.ordinal()] = 14;
            } catch (NoSuchFieldError nosuchfielderror38) {
                ;
            }

            try {
                EquipmentState.SyntheticClass_1.$SwitchMap$jordan$sicherman$items$EquipmentState[EquipmentState.FORTIFIED.ordinal()] = 15;
            } catch (NoSuchFieldError nosuchfielderror39) {
                ;
            }

            try {
                EquipmentState.SyntheticClass_1.$SwitchMap$jordan$sicherman$items$EquipmentState[EquipmentState.ORNATE.ordinal()] = 16;
            } catch (NoSuchFieldError nosuchfielderror40) {
                ;
            }

        }
    }
}
