package me.bunnky.idreamofeasy.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

import me.bunnky.idreamofeasy.IDreamOfEasy;

/**
 * Donde pueden trabajar las maquinas que mueven mucho terreno.
 *
 * POR QUE EXISTE
 *
 * Las Tuneladoras heredan de {@code IndustrialMiner}, asi que respetan WorldGuard y
 * ProtectionStones: no rompen nada protegido. Pero su {@code canMine} esta reescrito para minar
 * TODO lo solido, no solo minerales, y en areas de hasta 21x21. En terreno sin proteger eso no es
 * mineria, es terraformado.
 *
 * Paso en produccion el 12-08: un grupo dejo el End irreconocible en una tarde. Nadie hizo nada
 * malo -- la maquina hace exactamente lo que dice -- pero el resultado es que un mundo compartido
 * se vacia para todos porque a uno le venia bien.
 *
 * ESTRATEGIA: LISTA BLANCA
 *
 * Se permite solo donde se diga explicitamente. Con lista negra, cada mundo nuevo que se cree
 * queda abierto por olvido, y el olvido aqui se paga en terreno que no vuelve.
 */
public final class MundosPermitidos {

    private static final String CLAVE = "excavadoras.mundos-permitidos";

    private static final Set<String> MUNDOS_DEFECTO = Set.of(
        "world",
        "world_nether",
        "clasico",
        "clasico_nether",
        "bskyblock_world",
        "bskyblock_world_nether",
        "oneblock_world",
        "oneblock_world_nether"
    );

    private static Set<String> permitidos;

    private MundosPermitidos() {}

    private static Set<String> cargar() {
        Set<String> nombres = new HashSet<>();
        List<String> config = IDreamOfEasy.getInstance().getConfig().getStringList(CLAVE);
        if (config != null) {
            for (String n : config) {
                if (n != null && !n.trim().isEmpty()) {
                    nombres.add(n.trim().toLowerCase(Locale.ROOT));
                }
            }
        }
        if (nombres.isEmpty()) {
            nombres.addAll(MUNDOS_DEFECTO);
        }
        return nombres;
    }

    /** Vuelve a leer la configuracion. */
    public static void recargar() {
        permitidos = cargar();
    }

    /** Si la excavadora puede trabajar en el mundo de ese bloque. */
    public static boolean puedeExcavar(@NotNull Block b) {
        if (permitidos == null) {
            permitidos = cargar();
        }
        String worldName = b.getWorld().getName().toLowerCase(Locale.ROOT);
        if (permitidos.contains("*")) {
            // Comodín activo: permite todo excepto mundos de sistema, The End o arenas protegidas
            if (worldName.endsWith("_the_end") || worldName.contains("arena") || worldName.contains("dungeon")) {
                return false;
            }
            return true;
        }
        return permitidos.contains(worldName);
    }
}
