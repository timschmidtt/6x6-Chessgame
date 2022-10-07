package utils;

/**
 * DOC: Document me Tim !
 *
 * @author Tim Schmidt (tim.schmidt@cewe.de)
 * @since 21.09.22
 */
public interface Observer {
  /**
   * This method is called whenever the observed object is changed. An
   * application calls an {@code Observable} object's
   * {@code notifyObservers} method to have all the object's
   * observers notified of the change.
   *
   * @param   observable the observable object.
   * @param   arg   an argument passed to the {@code notifyObservers}
   *                 method.
   */
  void update(Observable observable, Object arg);
}
