package layers.model;

public class Move {

  private int fromColumn;
  private int fromRow;
  private int toColumn;
  private int toRow;
  private Square fromSquare;
  private Square toSquare;

  public Move(int fromColumn, int fromRow, int toColumn, int toRow) {
    this.fromColumn = fromColumn;
    this.fromRow = fromRow;
    this.toColumn = toColumn;
    this.toRow = toRow;
  }

  public Move(Tuple<Square, Square> move) {
    this.fromColumn = move.getFirst().getColumn();
    this.fromRow = move.getFirst().getRow();
    this.toColumn = move.getSecond().getColumn();
    this.toRow = move.getSecond().getRow();
  }

  public Move getMove() {
    return this;
  }

  public Tuple<Integer, Integer> getTo() {
    return new Tuple<>(this.toColumn, this.toRow);
  }

  public Tuple<Integer, Integer> getFrom() {
    return new Tuple<>(this.fromColumn, this.fromRow);
  }

  public void setTo(int toColumn, int toRow) {
    this.toColumn = toColumn;
    this.toRow = toRow;
  }

  public void setFrom(int fromColumn, int fromRow) {
    this.fromColumn = fromColumn;
    this.fromRow = fromRow;
  }

  public int getFromColumn() {
    return fromColumn;
  }

  public Move withFromColumn(int fromColumn) {
    this.fromColumn = fromColumn;
    return this;
  }

  public int getToColumn() {
    return toColumn;
  }

  public Move withToColumn(int toColumn) {
    this.toColumn = toColumn;
    return this;
  }

  public int getFromRow() {
    return fromRow;
  }

  public Move withFromRow(int fromRow) {
    this.fromRow = fromRow;
    return this;
  }

  public int getToRow() {
    return toRow;
  }

  public Move withToRow(int toRow) {
    this.toRow = toRow;
    return this;
  }
}
