package utils;

import java.util.ArrayList;

/**
 * DOC: Document me Tim !
 *
 * @author Tim Schmidt (tim.schmidt@cewe.de)
 * @since 21.09.22
 */
public class Observable {
  private final ArrayList<Observer> observers = new ArrayList<>();

  public void addObserver(Observer observer) {
    this.observers.add(observer);
  }

  public void notifyObservers(Observable observable, Object object) {
    for (Observer observer : this.observers) {
      observer.update(observable, object);
    }
  }
}
