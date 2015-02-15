/**
 * 
 */
package jordan.sicherman.nms.v1_8_R1.mobs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jordan.sicherman.nms.utilities.NMS;
import jordan.sicherman.utilities.configuration.ConfigEntries;
import net.minecraft.server.v1_8_R1.BiomeBase;
import net.minecraft.server.v1_8_R1.BiomeMeta;
import net.minecraft.server.v1_8_R1.EntityGiantZombie;
import net.minecraft.server.v1_8_R1.EntityInsentient;
import net.minecraft.server.v1_8_R1.EntityPigZombie;
import net.minecraft.server.v1_8_R1.EntitySkeleton;
import net.minecraft.server.v1_8_R1.EntityTypes;
import net.minecraft.server.v1_8_R1.EntityZombie;

import org.bukkit.entity.EntityType;

/**
 * @author Jordan
 * 
 */
public enum CustomEntityType {

	PIG_ZOMBIE("PigZombie", 57, EntityType.PIG_ZOMBIE, new CustomBiomeMeta(EntityPigZombie.class, CustomEntityPigZombie.class,
			ConfigEntries.PIGMAN_PACK_SPAWN.<Integer> getValue(), ConfigEntries.PIGMAN_PACK_MIN.<Integer> getValue(),
			ConfigEntries.PIGMAN_PACK_MAX.<Integer> getValue())), GUARD("Skeleton", 51, EntityType.SKELETON, new CustomBiomeMeta(
			EntitySkeleton.class, CustomEntityGuard.class, ConfigEntries.GUARD_PACK_SPAWN.<Integer> getValue(),
			ConfigEntries.GUARD_PACK_MIN.<Integer> getValue(), ConfigEntries.GUARD_PACK_MAX.<Integer> getValue())), ZOMBIE("Zombie", 54,
			EntityType.ZOMBIE, new CustomBiomeMeta(EntityZombie.class, CustomEntityZombie.class,
					ConfigEntries.ZOMBIE_PACK_SPAWN.<Integer> getValue(), ConfigEntries.ZOMBIE_PACK_MIN.<Integer> getValue(),
					ConfigEntries.ZOMBIE_PACK_MAX.<Integer> getValue())), GIANT_ZOMBIE("Giant", 53, EntityType.GIANT, new CustomBiomeMeta(
			EntityGiantZombie.class, CustomEntityGiantZombie.class, ConfigEntries.GIANT_PACK_SPAWN.<Integer> getValue(),
			ConfigEntries.GIANT_PACK_MIN.<Integer> getValue(), ConfigEntries.GIANT_PACK_MAX.<Integer> getValue()));

	private static final Map<BiomeBase, DefaultCache> cache = new HashMap<BiomeBase, DefaultCache>();

	private static class CustomBiomeMeta {

		private final Class<? extends EntityInsentient> nms, custom;
		private final BiomeMeta meta;
		private final boolean enabled;

		public CustomBiomeMeta(Class<? extends EntityInsentient> nms, Class<? extends EntityInsentient> custom, int spawn_chance,
				int range_min, int range_max) {
			this.nms = nms;
			this.custom = custom;
			enabled = spawn_chance > 0;
			meta = new BiomeMeta(custom, spawn_chance, range_min, range_max);
		}

		public BiomeMeta toBiomeMeta() {
			return meta;
		}
	}

	private static class DefaultCache {

		List<BiomeMeta> at, au, av, aw;

		private static final String[] keys = new String[] { "at", "au", "av", "aw" };

		@SuppressWarnings("unchecked")
		public DefaultCache(BiomeBase baseFor) {
			for (int i = 0; i < keys.length; i++) {
				switch (i) {
				case 0:
					at = new ArrayList<BiomeMeta>((List<BiomeMeta>) NMS.getDeclaredField(baseFor, keys[i]));
					break;
				case 1:
					au = new ArrayList<BiomeMeta>((List<BiomeMeta>) NMS.getDeclaredField(baseFor, keys[i]));
					break;
				case 2:
					av = new ArrayList<BiomeMeta>((List<BiomeMeta>) NMS.getDeclaredField(baseFor, keys[i]));
					break;
				case 3:
					aw = new ArrayList<BiomeMeta>((List<BiomeMeta>) NMS.getDeclaredField(baseFor, keys[i]));
					break;
				}
			}
		}
	}

	private String name;
	private int id;
	private EntityType entityType;
	private CustomBiomeMeta meta;

	private CustomEntityType(String name, int id, EntityType entityType, CustomBiomeMeta meta) {
		this.name = name;
		this.id = id;
		this.entityType = entityType;
		this.meta = meta;
	}

	public String getName() {
		return name;
	}

	public int getID() {
		return id;
	}

	public EntityType getEntityType() {
		return entityType;
	}

	public Class<? extends EntityInsentient> getNMSClass() {
		return meta.nms;
	}

	public Class<? extends EntityInsentient> getCustomClass() {
		return meta.custom;
	}

	@SuppressWarnings("unchecked")
	public static void registerEntities() {
		for (CustomEntityType entity : values()) {
			a(entity.getCustomClass(), entity.getName(), entity.getID());
		}

		BiomeBase[] biomes;
		try {
			biomes = (BiomeBase[]) NMS.getPrivateStatic(BiomeBase.class, "biomes");
		} catch (Exception exc) {
			return;
		}

		List<String> zombieExcluded = ConfigEntries.ZOMBIE_EXCLUDES.<List<String>> getValue();
		List<String> pigmanExcluded = ConfigEntries.PIGMAN_EXCLUDES.<List<String>> getValue();
		List<String> guardExcluded = ConfigEntries.GUARD_EXCLUDES.<List<String>> getValue();
		List<String> generalExcluded = ConfigEntries.GENERAL_EXCLUDES.<List<String>> getValue();

		for (BiomeBase biomeBase : biomes) {
			if (biomeBase == null) {
				break;
			}
			if (biomeBase == BiomeBase.HELL) {
				continue;
			}

			cache.put(biomeBase, new DefaultCache(biomeBase));

			for (String key : DefaultCache.keys) {
				List<BiomeMeta> list = (List<BiomeMeta>) NMS.getDeclaredField(biomeBase, key);
				if (list != null) {
					list.clear();
				}
			}

			if (!generalExcluded.contains(biomeBase.ah)) {
				if (PIG_ZOMBIE.meta.enabled && !pigmanExcluded.contains(biomeBase.ah)
						&& !cache.get(biomeBase).at.contains(PIG_ZOMBIE.meta.toBiomeMeta())) {
					List<BiomeMeta> list = (List<BiomeMeta>) NMS.getDeclaredField(biomeBase, DefaultCache.keys[0]);
					if (list != null) {
						list.add(PIG_ZOMBIE.meta.toBiomeMeta());
					}
				}
				if (ZOMBIE.meta.enabled && !zombieExcluded.contains(biomeBase.ah)
						&& !cache.get(biomeBase).at.contains(ZOMBIE.meta.toBiomeMeta())) {
					List<BiomeMeta> list = (List<BiomeMeta>) NMS.getDeclaredField(biomeBase, DefaultCache.keys[0]);
					if (list != null) {
						list.add(ZOMBIE.meta.toBiomeMeta());
					}
				}
				if (GUARD.meta.enabled && !guardExcluded.contains(biomeBase.ah)
						&& !cache.get(biomeBase).at.contains(GUARD.meta.toBiomeMeta())) {
					List<BiomeMeta> list = (List<BiomeMeta>) NMS.getDeclaredField(biomeBase, DefaultCache.keys[0]);
					if (list != null) {
						list.add(GUARD.meta.toBiomeMeta());
					}
				}
			}

			if (GIANT_ZOMBIE.meta.enabled && ConfigEntries.GIANT_INCLUDES.<List<String>> getValue().contains(biomeBase.ah)
					&& !cache.get(biomeBase).at.contains(GIANT_ZOMBIE.meta.toBiomeMeta())) {
				List<BiomeMeta> list = (List<BiomeMeta>) NMS.getDeclaredField(biomeBase, DefaultCache.keys[0]);
				if (list != null) {
					list.add(GIANT_ZOMBIE.meta.toBiomeMeta());
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static void unregisterEntities() {
		for (CustomEntityType entity : values()) {
			try {
				((Map<Class<?>, String>) NMS.getPrivateStatic(EntityTypes.class, "c")).remove(entity.getCustomClass());
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				((Map<Class<?>, String>) NMS.getPrivateStatic(EntityTypes.class, "e")).remove(entity.getCustomClass());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		for (CustomEntityType entity : values()) {
			a(entity.getNMSClass(), entity.getName(), entity.getID());
		}

		BiomeBase[] biomes;
		try {
			biomes = (BiomeBase[]) NMS.getPrivateStatic(BiomeBase.class, "biomes");
		} catch (Exception exc) {
			return;
		}

		for (BiomeBase biomeBase : biomes) {
			if (biomeBase == null) {
				break;
			}
			if (biomeBase == BiomeBase.HELL) {
				continue;
			}

			NMS.setDeclaredField(biomeBase, DefaultCache.keys[0], cache.get(biomeBase).at);
			NMS.setDeclaredField(biomeBase, DefaultCache.keys[1], cache.get(biomeBase).au);
			NMS.setDeclaredField(biomeBase, DefaultCache.keys[2], cache.get(biomeBase).av);
			NMS.setDeclaredField(biomeBase, DefaultCache.keys[3], cache.get(biomeBase).aw);
		}

		cache.clear();
	}

	@SuppressWarnings("unchecked")
	private static void a(Class<?> paramClass, String paramString, int paramInt) {
		try {
			((Map<String, Class<?>>) NMS.getPrivateStatic(EntityTypes.class, "c")).put(paramString, paramClass);
			((Map<Class<?>, String>) NMS.getPrivateStatic(EntityTypes.class, "d")).put(paramClass, paramString);
			((Map<Integer, Class<?>>) NMS.getPrivateStatic(EntityTypes.class, "e")).put(Integer.valueOf(paramInt), paramClass);
			((Map<Class<?>, Integer>) NMS.getPrivateStatic(EntityTypes.class, "f")).put(paramClass, Integer.valueOf(paramInt));
			((Map<String, Integer>) NMS.getPrivateStatic(EntityTypes.class, "g")).put(paramString, Integer.valueOf(paramInt));
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}
}