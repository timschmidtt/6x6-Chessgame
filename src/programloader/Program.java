package programloader;

public class Program {

  private String jarFileName;
  private String jarFilePath;
  private String classFilePath;

  public Program(String jarFileName, String jarFilePath, String classFilePath) {
    this.jarFileName = jarFileName;
    this.jarFilePath = jarFilePath;
    this.classFilePath = classFilePath;
  }

  public String getJarFileName() {
    return jarFileName;
  }

  public void setJarFileName(String jarFileName) {
    this.jarFileName = jarFileName;
  }

  public String getJarFilePath() {
    return jarFilePath;
  }

  public void setJarFilePath(String jarFilePath) {
    this.jarFilePath = jarFilePath;
  }

  public String getClassFilePath() {
    return classFilePath;
  }

  public void setClassFilePath(String classFilePath) {
    this.classFilePath = classFilePath;
  }
}