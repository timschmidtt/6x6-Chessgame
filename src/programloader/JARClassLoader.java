package programloader;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

final class JARClassLoader extends URLClassLoader {
  /**
   * Initializes a classloader to load classes from jar files.
   *
   * @param jarPathAndFilename Path (folder + name) of the jar file.
   * @throws MalformedURLException will be thrown if there are errors
   */
  public JARClassLoader(String jarPathAndFilename) throws MalformedURLException {
    super(new URL[]{});
    this.addURL(new URL("file:" + jarPathAndFilename));
  }
}
