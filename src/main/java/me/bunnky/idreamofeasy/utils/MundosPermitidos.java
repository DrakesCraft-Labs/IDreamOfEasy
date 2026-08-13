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

    /**
     * Por defecto: ninguno.
     *
     * Es deliberado que haya que activarlo a mano. Un dueño que instala el addon no espera que
     * le vacien el mundo principal el primer dia.
     */
    private static Set<String> permitidos;

    private MundosPermitidos() {}

    private static Set<String> cargar() {
        Set<String> nombres = new HashSet<>();
        List<String> config = IDreamOfEasy.getInstance().getConfig().getStringList(CLAVE);
        for (String n : config) {
            nombres.add(n.toLowerCase(Locale.ROOT));
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
        return permitidos.contains(b.getWorld().getName().toLowerCase(Locale.ROOT));
    }
}
