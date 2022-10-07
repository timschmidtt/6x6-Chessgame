package programloader;

public class Program {

  private String className;

  private String program;
  private String path;

  public Program(String className, String program, String path) {
    this.className = className;
    this.program = program;
    this.path = path;
  }

  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public String getProgram() {
    return program;
  }

  public void setProgram(String program) {
    this.program = program;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }
}