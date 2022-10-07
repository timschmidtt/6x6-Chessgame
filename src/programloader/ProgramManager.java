package programloader;


import layers.model.actors.Player;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

public class ProgramManager {

    private static final String PATH = "programs";
    private final List<Program> programs;

    public ProgramManager() {
        this.programs = new ArrayList<>();
        readOutFile();
    }

    private void readOutFile() {
        File programFiles = new File(PATH);
        String[] fileNames = programFiles.list();
        if (fileNames != null) {
            for (String fileName : fileNames) {
                if (fileName.toLowerCase().endsWith(".jar")) {
                    String pathName = ProgramManager.PATH + File.separator + fileName;
                    String programName = this.getProgramClassName(pathName);
                    if (programName != null) {
                        boolean colorW = this.checkLoading(pathName, programName, true, "Spieler A");
                        boolean colorB = this.checkLoading(pathName, programName, false, "Spieler B");
                        if (colorW && colorB) {
                            this.programs.add(new Program(fileName, programName, pathName));
                        }
                    }
                }
            }
        }
    }

    /**
     * Ermittelt den Wert des Attributs "Player" in der Manifest-Datei der
     * angegebenen jar-Datei
     *
     * @param jarPathAndFilename Pfad (Ordner + Name) der jar-Datei
     * @return der Wert des Attributs Player
     */
    private String getProgramClassName(String jarPathAndFilename) {
        try {
            URL fileURL = new URL("file:" + jarPathAndFilename);
            URL jarURL = new URL("jar", "", fileURL + "!/");
            JarURLConnection urlConnection = (JarURLConnection) jarURL.openConnection();
            Attributes attributes = urlConnection.getMainAttributes();
            return attributes != null ? attributes.getValue("Player"): null;
        } catch (IOException e) {
            return null;
        }
    }

    private boolean checkLoading(String jarFileName, String className, boolean color, String playerName) {
        try {
            JARClassLoader classLoader = new JARClassLoader(jarFileName);
            Class<?> cl = classLoader.loadClass(className);
            Constructor<?> constructor = cl.getDeclaredConstructor(Boolean.class, String.class);
            Object player = constructor.newInstance(color, playerName);
            return player instanceof Player;
        } catch (Throwable th) {
            th.printStackTrace();
            return false;
        }
    }

    public Player loadPlayer(Program program, boolean color, String playerName) {
        try {
            JARClassLoader classLoader = new JARClassLoader(program.getProgram());
            Class<?> cl = classLoader.loadClass(program.getClassName());
            Constructor<?> constructor = cl.getDeclaredConstructor(Boolean.class, String.class);
            Object player = constructor.newInstance(color, playerName);
            return (Player) player;
        }
        catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    public List<Program> getPrograms() {
        return this.programs;
    }
}
