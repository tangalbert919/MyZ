package jordan.sicherman.particles;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;

public final class ReflectionUtils {

    public static Constructor getConstructor(Class clazz, Class... parameterTypes) throws NoSuchMethodException {
        Class[] primitiveTypes = ReflectionUtils.DataType.getPrimitive(parameterTypes);
        Constructor[] aconstructor = clazz.getConstructors();
        int i = aconstructor.length;

        for (int j = 0; j < i; ++j) {
            Constructor constructor = aconstructor[j];

            if (ReflectionUtils.DataType.compare(ReflectionUtils.DataType.getPrimitive(constructor.getParameterTypes()), primitiveTypes)) {
                return constructor;
            }
        }

        throw new NoSuchMethodException("There is no such constructor in this class with the specified parameter types");
    }

    public static Constructor getConstructor(String className, ReflectionUtils.PackageType packageType, Class... parameterTypes) throws NoSuchMethodException, ClassNotFoundException {
        return getConstructor(packageType.getClass(className), parameterTypes);
    }

    public static Object instantiateObject(Class clazz, Object... arguments) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        return getConstructor(clazz, ReflectionUtils.DataType.getPrimitive(arguments)).newInstance(arguments);
    }

    public static Object instantiateObject(String className, ReflectionUtils.PackageType packageType, Object... arguments) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException {
        return instantiateObject(packageType.getClass(className), arguments);
    }

    public static Method getMethod(Class clazz, String methodName, Class... parameterTypes) throws NoSuchMethodException {
        Class[] primitiveTypes = ReflectionUtils.DataType.getPrimitive(parameterTypes);
        Method[] amethod = clazz.getMethods();
        int i = amethod.length;

        for (int j = 0; j < i; ++j) {
            Method method = amethod[j];

            if (method.getName().equals(methodName) && ReflectionUtils.DataType.compare(ReflectionUtils.DataType.getPrimitive(method.getParameterTypes()), primitiveTypes)) {
                return method;
            }
        }

        throw new NoSuchMethodException("There is no such method in this class with the specified name and parameter types");
    }

    public static Method getMethod(String className, ReflectionUtils.PackageType packageType, String methodName, Class... parameterTypes) throws NoSuchMethodException, ClassNotFoundException {
        return getMethod(packageType.getClass(className), methodName, parameterTypes);
    }

    public static Object invokeMethod(Object instance, String methodName, Object... arguments) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        return getMethod(instance.getClass(), methodName, ReflectionUtils.DataType.getPrimitive(arguments)).invoke(instance, arguments);
    }

    public static Object invokeMethod(Object instance, Class clazz, String methodName, Object... arguments) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        return getMethod(clazz, methodName, ReflectionUtils.DataType.getPrimitive(arguments)).invoke(instance, arguments);
    }

    public static Object invokeMethod(Object instance, String className, ReflectionUtils.PackageType packageType, String methodName, Object... arguments) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException {
        return invokeMethod(instance, packageType.getClass(className), methodName, arguments);
    }

    public static Field getField(Class clazz, boolean declared, String fieldName) throws NoSuchFieldException, SecurityException {
        Field field = declared ? clazz.getDeclaredField(fieldName) : clazz.getField(fieldName);

        field.setAccessible(true);
        return field;
    }

    public static Field getField(String className, ReflectionUtils.PackageType packageType, boolean declared, String fieldName) throws NoSuchFieldException, SecurityException, ClassNotFoundException {
        return getField(packageType.getClass(className), declared, fieldName);
    }

    public static Object getValue(Object instance, Class clazz, boolean declared, String fieldName) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        return getField(clazz, declared, fieldName).get(instance);
    }

    public static Object getValue(Object instance, String className, ReflectionUtils.PackageType packageType, boolean declared, String fieldName) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException, ClassNotFoundException {
        return getValue(instance, packageType.getClass(className), declared, fieldName);
    }

    public static Object getValue(Object instance, boolean declared, String fieldName) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        return getValue(instance, instance.getClass(), declared, fieldName);
    }

    public static void setValue(Object instance, Class clazz, boolean declared, String fieldName, Object value) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        getField(clazz, declared, fieldName).set(instance, value);
    }

    public static void setValue(Object instance, String className, ReflectionUtils.PackageType packageType, boolean declared, String fieldName, Object value) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException, ClassNotFoundException {
        setValue(instance, packageType.getClass(className), declared, fieldName, value);
    }

    public static void setValue(Object instance, boolean declared, String fieldName, Object value) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        setValue(instance, instance.getClass(), declared, fieldName, value);
    }

    public static enum DataType {

        BYTE(Byte.TYPE, Byte.class), SHORT(Short.TYPE, Short.class), INTEGER(Integer.TYPE, Integer.class), LONG(Long.TYPE, Long.class), CHARACTER(Character.TYPE, Character.class), FLOAT(Float.TYPE, Float.class), DOUBLE(Double.TYPE, Double.class), BOOLEAN(Boolean.TYPE, Boolean.class);

        private static final Map CLASS_MAP = new HashMap();
        private final Class primitive;
        private final Class reference;

        private DataType(Class primitive, Class reference) {
            this.primitive = primitive;
            this.reference = reference;
        }

        public Class getPrimitive() {
            return this.primitive;
        }

        public Class getReference() {
            return this.reference;
        }

        public static ReflectionUtils.DataType fromClass(Class clazz) {
            return (ReflectionUtils.DataType) ReflectionUtils.DataType.CLASS_MAP.get(clazz);
        }

        public static Class getPrimitive(Class clazz) {
            ReflectionUtils.DataType type = fromClass(clazz);

            return type == null ? clazz : type.getPrimitive();
        }

        public static Class getReference(Class clazz) {
            ReflectionUtils.DataType type = fromClass(clazz);

            return type == null ? clazz : type.getReference();
        }

        public static Class[] getPrimitive(Class[] classes) {
            int length = classes == null ? 0 : classes.length;
            Class[] types = new Class[length];

            for (int index = 0; index < length; ++index) {
                types[index] = getPrimitive(classes[index]);
            }

            return types;
        }

        public static Class[] getReference(Class[] classes) {
            int length = classes == null ? 0 : classes.length;
            Class[] types = new Class[length];

            for (int index = 0; index < length; ++index) {
                types[index] = getReference(classes[index]);
            }

            return types;
        }

        public static Class[] getPrimitive(Object[] objects) {
            int length = objects == null ? 0 : objects.length;
            Class[] types = new Class[length];

            for (int index = 0; index < length; ++index) {
                types[index] = getPrimitive(objects[index].getClass());
            }

            return types;
        }

        public static Class[] getReference(Object[] objects) {
            int length = objects == null ? 0 : objects.length;
            Class[] types = new Class[length];

            for (int index = 0; index < length; ++index) {
                types[index] = getReference(objects[index].getClass());
            }

            return types;
        }

        public static boolean compare(Class[] primary, Class[] secondary) {
            if (primary != null && secondary != null && primary.length == secondary.length) {
                for (int index = 0; index < primary.length; ++index) {
                    Class primaryClass = primary[index];
                    Class secondaryClass = secondary[index];

                    if (!primaryClass.equals(secondaryClass) && !primaryClass.isAssignableFrom(secondaryClass)) {
                        return false;
                    }
                }

                return true;
            } else {
                return false;
            }
        }

        static {
            ReflectionUtils.DataType[] areflectionutils_datatype = values();
            int i = areflectionutils_datatype.length;

            for (int j = 0; j < i; ++j) {
                ReflectionUtils.DataType type = areflectionutils_datatype[j];

                ReflectionUtils.DataType.CLASS_MAP.put(type.primitive, type);
                ReflectionUtils.DataType.CLASS_MAP.put(type.reference, type);
            }

        }
    }

    public static enum PackageType {

        MINECRAFT_SERVER("net.minecraft.server." + getServerVersion()), CRAFTBUKKIT("org.bukkit.craftbukkit." + getServerVersion()), CRAFTBUKKIT_BLOCK(ReflectionUtils.PackageType.CRAFTBUKKIT, "block"), CRAFTBUKKIT_CHUNKIO(ReflectionUtils.PackageType.CRAFTBUKKIT, "chunkio"), CRAFTBUKKIT_COMMAND(ReflectionUtils.PackageType.CRAFTBUKKIT, "command"), CRAFTBUKKIT_CONVERSATIONS(ReflectionUtils.PackageType.CRAFTBUKKIT, "conversations"), CRAFTBUKKIT_ENCHANTMENS(ReflectionUtils.PackageType.CRAFTBUKKIT, "enchantments"), CRAFTBUKKIT_ENTITY(ReflectionUtils.PackageType.CRAFTBUKKIT, "entity"), CRAFTBUKKIT_EVENT(ReflectionUtils.PackageType.CRAFTBUKKIT, "event"), CRAFTBUKKIT_GENERATOR(ReflectionUtils.PackageType.CRAFTBUKKIT, "generator"), CRAFTBUKKIT_HELP(ReflectionUtils.PackageType.CRAFTBUKKIT, "help"), CRAFTBUKKIT_INVENTORY(ReflectionUtils.PackageType.CRAFTBUKKIT, "inventory"), CRAFTBUKKIT_MAP(ReflectionUtils.PackageType.CRAFTBUKKIT, "map"), CRAFTBUKKIT_METADATA(ReflectionUtils.PackageType.CRAFTBUKKIT, "metadata"), CRAFTBUKKIT_POTION(ReflectionUtils.PackageType.CRAFTBUKKIT, "potion"), CRAFTBUKKIT_PROJECTILES(ReflectionUtils.PackageType.CRAFTBUKKIT, "projectiles"), CRAFTBUKKIT_SCHEDULER(ReflectionUtils.PackageType.CRAFTBUKKIT, "scheduler"), CRAFTBUKKIT_SCOREBOARD(ReflectionUtils.PackageType.CRAFTBUKKIT, "scoreboard"), CRAFTBUKKIT_UPDATER(ReflectionUtils.PackageType.CRAFTBUKKIT, "updater"), CRAFTBUKKIT_UTIL(ReflectionUtils.PackageType.CRAFTBUKKIT, "util");

        private final String path;

        private PackageType(String path) {
            this.path = path;
        }

        private PackageType(ReflectionUtils.PackageType parent, String path) {
            this(parent + "." + path);
        }

        public String getPath() {
            return this.path;
        }

        public Class getClass(String className) throws ClassNotFoundException {
            return Class.forName(this + "." + className);
        }

        public String toString() {
            return this.path;
        }

        public static String getServerVersion() {
            return Bukkit.getServer().getClass().getPackage().getName().substring(23);
        }
    }
}
