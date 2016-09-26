package jordan.sicherman.nms.utilities;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import jordan.sicherman.MyZ;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class NMS {

    private static final String packageName = Bukkit.getServer().getClass().getPackage().getName();
    public static final String version = NMS.packageName.substring(NMS.packageName.lastIndexOf(".") + 1);
    public static final NMS.Version compatibilityVersion = NMS.Version.fromString(NMS.version);
    private static Class craftPlayer;
    private static Class packet;
    private static Method getHandle;
    private static Method sendPacket;
    private static Field connection;

    public static Object castToCraft(Player player) {
        if (NMS.craftPlayer == null) {
            try {
                NMS.craftPlayer = Class.forName("org.bukkit.craftbukkit." + NMS.version + ".entity.CraftPlayer");
            } catch (ClassNotFoundException classnotfoundexception) {
                classnotfoundexception.printStackTrace();
                return null;
            }
        }

        return NMS.craftPlayer == null ? null : NMS.craftPlayer.cast(player);
    }

    public static Object castToNMS(Player player) {
        Object craft = castToCraft(player);

        if (craft == null) {
            return null;
        } else {
            if (NMS.getHandle == null) {
                try {
                    NMS.getHandle = NMS.craftPlayer.getMethod("getHandle", new Class[0]);
                } catch (Exception exception) {
                    return null;
                }
            }

            try {
                return NMS.getHandle.invoke(castToCraft(player), new Object[0]);
            } catch (Exception exception1) {
                return null;
            }
        }
    }

    public static void sendPacket(Object inPacket, Player inPlayer) throws Exception {
        if (NMS.packet == null) {
            NMS.packet = Class.forName("net.minecraft.server." + NMS.version + ".Packet");
        }

        Object handle = castToNMS(inPlayer);

        if (handle != null) {
            if (NMS.connection == null) {
                NMS.connection = handle.getClass().getField("playerConnection");
            }

            Object con = NMS.connection.get(handle);

            if (con != null) {
                if (NMS.sendPacket == null) {
                    NMS.sendPacket = con.getClass().getMethod("sendPacket", new Class[] { NMS.packet});
                }

                if (NMS.sendPacket != null) {
                    NMS.sendPacket.invoke(con, new Object[] { inPacket});
                }

            }
        }
    }

    private static Field getField(Class clazz, String fieldName) throws NoSuchFieldException {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException nosuchfieldexception) {
            Class superClass = clazz.getSuperclass();

            if (superClass == null) {
                throw nosuchfieldexception;
            } else {
                return getField(superClass, fieldName);
            }
        }
    }

    public static void setDeclaredField(Object obj, String fieldName, Object value) {
        try {
            Field e = getField(obj.getClass(), fieldName);

            e.setAccessible(true);
            e.set(obj, value);
            e.setAccessible(false);
        } catch (NoSuchFieldException nosuchfieldexception) {
            MyZ.log(ChatColor.RED + "Did the field \'" + fieldName + "\' change?");
        } catch (Exception exception) {
            MyZ.log(ChatColor.RED + "MyZ does not have sufficient privileges.");
        }

    }

    public static Object getPrivateStatic(Class clazz, String fieldName) {
        Object value = null;

        try {
            Field e = getField(clazz, fieldName);

            e.setAccessible(true);
            value = e.get((Object) null);
        } catch (NoSuchFieldException nosuchfieldexception) {
            MyZ.log(ChatColor.RED + "Did the field \'" + fieldName + "\' change?");
        } catch (Exception exception) {
            MyZ.log(ChatColor.RED + "MyZ does not have sufficient privileges.");
        }

        return value;
    }

    public static Object getDeclaredField(Object obj, String fieldName) {
        Object value = null;

        try {
            Field e = getField(obj.getClass(), fieldName);

            e.setAccessible(true);
            value = e.get(obj);
        } catch (NoSuchFieldException nosuchfieldexception) {
            MyZ.log(ChatColor.RED + "Did the field \'" + fieldName + "\' change?");
        } catch (Exception exception) {
            MyZ.log(ChatColor.RED + "MyZ does not have sufficient privileges.");
        }

        return value;
    }

    public static enum Version {

        v1_8_R1("v1_8_R1"), v1_8_R2("v1_8_R2"), v1_8_R3("v1_8_R3"), v1_7_R4("v1_7_R4"), v1_9_R1("v1_9_R1"), v1_9_R2("v1_9_R2"), v1_10_R1("v1_10_R1");

        private final String identifier;

        private Version(String identifier) {
            this.identifier = identifier;
        }

        public String getIdentifier() {
            return this.identifier;
        }

        public static NMS.Version fromString(String identifier) {
            NMS.Version[] anms_version = values();
            int i = anms_version.length;

            for (int j = 0; j < i; ++j) {
                NMS.Version v = anms_version[j];

                if (v.getIdentifier().equals(identifier)) {
                    return v;
                }
            }

            return null;
        }
    }
}
