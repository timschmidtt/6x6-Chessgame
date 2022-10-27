package programloader;

/**
 * A class to store information about a program. Used like
 * a data class or a record.
 *
 * @author Tim Schmidt (tim.schmidt@student.ibs-ol.de)
 */
public class Program {

  private final String jarFilePath;
  private final String classFilePath;

  public Program(String jarFilePath, String classFilePath) {
    this.jarFilePath = jarFilePath;
    this.classFilePath = classFilePath;
  }

  public String getJarFilePath() {
    return jarFilePath;
  }

  public String getClassFilePath() {
    return classFilePath;
  }
}