package programloader;


import layers.model.actors.Player;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.jar.Attributes;

/**
 * The ProgramManager is a class to load all jar files from the program directory. At the initialization
 * it will automatically check for every jar file if a class of a {@link Player} is found and try if this
 * class can be loaded and then put it into a HashMap that can be accessed by getter.
 */
public class ProgramManager {

    private static final String PATH = "programs";
    private final HashMap<String, Program> programHashMap;

    public ProgramManager() {
        this.programHashMap = new HashMap<>();
        readOutProgramsFolder();
    }

    /**
     * Can load a program (class of {@link Player}) dynamically while the runtime into a game.
     *
     * @param program The program to load.
     * @param playerColor The color of the player.
     * @param playerName The name of the player.
     *
     * @return A class of player.
     */
    public Player loadPlayerJarFile(Program program, boolean playerColor, String playerName) {
        try {
            JARClassLoader classLoader = new JARClassLoader(program.getJarFilePath());
            Class<?> loadedClass = classLoader.loadClass(program.getClassFilePath());
            Constructor<?> constructor = loadedClass.getDeclaredConstructor(Boolean.class, String.class);
            Object player = constructor.newInstance(playerColor, playerName);
            return (Player) player;
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    /**
     * This method will check for every file in the programs folder if it is a jar file and then
     * try if it's loadable {@link #checkLoading(String, String, boolean, String)}. If this is
     * the case the program will be added to a HashMap.
     */
    private void readOutProgramsFolder() {
        File programFiles = new File(PATH);
        String[] fileNames = programFiles.list();
        if (fileNames != null) {
            for (String jarFileName : fileNames) {
                if (jarFileName.toLowerCase().endsWith(".jar")) {
                    String jarFilePath = ProgramManager.PATH + File.separator + jarFileName;
                    String classFilePath = this.getClassFilePath(jarFilePath);
                    if (classFilePath != null) {
                        boolean colorW = this.checkLoading(jarFilePath, classFilePath, true, "Spieler A");
                        boolean colorB = this.checkLoading(jarFilePath, classFilePath, false, "Spieler B");
                        if (colorW && colorB) {
                            this.programHashMap.put(jarFileName, new Program(jarFileName, jarFilePath, classFilePath));
                        }
                    }
                }
            }
        }
    }

    /**
     * Gets the value of the player attribute in the manifest file of the
     * specified jar file.
     *
     * @param jarFilePath Path (folder + name) of the jar file.
     * @return the value of the Player attribute.
     */
    private String getClassFilePath(String jarFilePath) {
        try {
            URL fileURL = new URL("file:" + jarFilePath);
            URL jarURL = new URL("jar", "", fileURL + "!/");
            JarURLConnection urlConnection = (JarURLConnection) jarURL.openConnection();
            Attributes attributes = urlConnection.getMainAttributes();
            return attributes != null ? attributes.getValue("Player") : null;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Checks if the given jar file with a specified class file path could be loaded.
     *
     * @param jarFilePath The jar file path.
     * @param classFilePath The class file path from the manifest.
     * @param playerColor The player color.
     * @param playerName The player name.
     *
     * @return A boolean if the jar file can be loaded.
     */
    private boolean checkLoading(String jarFilePath, String classFilePath, boolean playerColor, String playerName) {
        try {
            JARClassLoader classLoader = new JARClassLoader(jarFilePath);
            Class<?> cl = classLoader.loadClass(classFilePath);
            Constructor<?> constructor = cl.getDeclaredConstructor(Boolean.class, String.class);
            Object player = constructor.newInstance(playerColor, playerName);
            return player instanceof Player;
        } catch (Throwable th) {
            th.printStackTrace();
            return false;
        }
    }

    public HashMap<String, Program> getProgramHashMap() {
        return this.programHashMap;
    }
}
