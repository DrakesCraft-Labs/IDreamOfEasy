package me.bunnky.idreamofeasy;

import com.github.drakescraft_labs.slimefun4.api.SlimefunAddon;
import me.bunnky.idreamofeasy.listeners.IdolListener;
import me.bunnky.idreamofeasy.listeners.MagnetoidListener;
import me.bunnky.idreamofeasy.slimefun.setup.Setup;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.text.MessageFormat;
import java.util.logging.Level;

public class IDreamOfEasy extends JavaPlugin implements SlimefunAddon {
    private static IDreamOfEasy instance;
    private final String username;
    private final String repo;

    public IDreamOfEasy() {
        this.username = "SlimefunGuguProject";
        this.repo = "IDreamOfEasy";
    }

    @Override
    public void onEnable() {
        instance = this;



        getLogger().info(" ┳  ┳┓┳┓┏┓┏┓┳┳┓  ┏┓┏┓  ┏┓┏┓┏┓┓┏ ");
        getLogger().info(" ┃  ┃┃┣┫┣ ┣┫┃┃┃  ┃┃┣   ┣ ┣┫┗┓┗┫ ");
        getLogger().info(" ┻  ┻┛┛┗┗┛┛┗┛ ┗  ┗┛┻   ┗┛┛┗┗┛┗┛ ");
        getLogger().info("        IDOE by Bunnky          ");
        getLogger().info("    易梦 - 粘液科技简中汉化组汉化    ");
        saveDefaultConfig();

        setupMetrics();
        tryUpdate();

        Setup.setup();

        new MagnetoidListener(this);
        new IdolListener(this);
    }

    public void setupMetrics() {
        Metrics metrics = new Metrics(this, 23610);
    }

    public void tryUpdate() {
        if (getConfig().getBoolean("options.auto-update", true)
                && getDescription().getVersion().startsWith("Build")
        ) {
        }
    }

    public static void consoleMsg(@Nonnull String string) {
        instance.getLogger().info(string);
    }

    public static IDreamOfEasy getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {
    }

    @Override
    public String getBugTrackerURL() {
        return MessageFormat.format("https://github.com/{0}/{1}/issues", this.username, this.repo);
    }

    @Nonnull
    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

}
