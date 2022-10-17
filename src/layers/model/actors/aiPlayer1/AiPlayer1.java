package layers.model.actors.aiPlayer1;

import layers.model.Square;
import layers.model.Tuple;
import layers.model.actors.Player;

/**
 * DOC: Document me Tim !
 *
 * @author Tim Schmidt (tim.schmidt@cewe.de)
 * @since 17.10.22
 */
public class AiPlayer1  extends Player {

  public AiPlayer1(Boolean color, String name) {
    super(color, name);
  }

  @Override
  public Tuple<Square, Square> getNextMove(Tuple<Square, Square> move) {
    return null;
  }

  /*
  void main() {
 ...
 Spielzug zug;
 int wert;
 if (SpielerAIstAmZug()) {
 (zug, wert) = maxWert(suchtiefe);
 } else {
 (zug, wert) = minWert(suchtiefe);
 }
 zug.ausfuehren();
 ...
}
(Spielzug, int) maxWert(int resttiefe) {
 Spielzug besterZug = null;
 int hoechsterWert = -unendlich;
 Spielzug zuege = ermittleAlleFolgeZuege();
 for (int z=0; z<zuege.length; z++) {
 zuege[z].simulieren();
 Spielzug zug;
 int zugWert;
 if (resttiefe <= 1 || keineFolgeZuegeMehrMoeglich()) {
 zug = zuege[z];
 zugWert = bewertungsfunktion();
 } else if (SpielerAIstAmZug()) {
 (zug, zugWert) = maxWert(resttiefe-1); 
 } else {
 (zug, zugWert) = minWert(resttiefe-1);
 }
 zuege[z].simulationRueckgaengigMachen();
 if (zugWert > hoechsterWert) {
 hoechsterWert = zugWert;
 besterZug = zug;
 }
 }
 return (besterZug, hoechsterWert);
}
(Spielzug, int) minWert(int resttiefe) {
 Spielzug besterZug = null;
 int niedrigsterWert = unendlich;
 Spielzug zuege = ermittleAlleFolgeZuege();
 for (int z=0; z<zuege.length; z++) {
 zuege[z].simulieren();
 Spielzug zug;
 int zugWert;
 if (resttiefe <= 1 || keineFolgeZuegeMehrMoeglich()) {
 zug = zuege[z];
 zugWert = bewertungsfunktion();
 } else if (SpielerAIstAmZug()) {
 (zug, zugWert) = maxWert(resttiefe-1);
 } else {
 (zug, zugWert) = minWert(resttiefe-1);
 }
 zuege[z].simulationRueckgaengigMachen();
 if (zugWert < niedrigsterWert) {
 niedrigsterWert = zugWert;
 besterZug = zug;
 }
 }
 return (besterZug, niedrigsterWert);
}
   */

}
