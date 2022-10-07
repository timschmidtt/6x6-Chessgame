package layers.model.pieces;

import javafx.scene.image.Image;
import layers.model.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Rook extends Piece {

    public Rook(boolean color) {
        super(color, "Rook");
    }

    @Override
    public List<Tuple<Integer, Integer>> createMoves() {
        List<Tuple<Integer, Integer>> movesList = new ArrayList<>();
        movesList.add(new Tuple<>(2,0));
        movesList.add(new Tuple<>( 0,2));
        movesList.add(new Tuple<>(-2,0));
        movesList.add(new Tuple<>( 0, -2));
        movesList.add(new Tuple<>(1,0));
        movesList.add(new Tuple<>( 0,1));
        movesList.add(new Tuple<>(-1,0));
        movesList.add(new Tuple<>( 0, -1));
        return movesList;
    }

    @Override
    public Image setImage() {
        Image image;
        if (!getColor()) {
            image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pieceIcons/BlackRook.png")));
        } else {
            image =  new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pieceIcons/WhiteRook.png")));
        }
        return image;
    }
}
