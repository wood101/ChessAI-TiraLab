package fi.helsinki.chessai.board;

import com.google.common.collect.ImmutableMap;
import fi.helsinki.chessai.board.pieces.Piece;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author janne
 */
public abstract class Tile {
    protected final int tileCoordinate;
    private static final Map<Integer, EmptyTile> EmptyTiles = setAllEmptyTiles();
    private static Map<Integer, EmptyTile> setAllEmptyTiles() {
        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();
        for(int i = 0; i < BoardUtil.NumTiles; i++) {
            emptyTileMap.put(i, new EmptyTile(i));
        }
        return ImmutableMap.copyOf(emptyTileMap);
    }
    
    public static Tile createTile(final int tileCoordinate, final Piece piece) {
        return piece != null ? new OccupiedTile(tileCoordinate, piece) : EmptyTiles.get(tileCoordinate);
    }
    
    Tile(int tileCoordinate) {
        this.tileCoordinate = tileCoordinate;
    }
    
    public abstract boolean occupied();
    
    public abstract Piece getPiece();
    
    public static final class EmptyTile extends Tile {
         private EmptyTile(int coordinate) {
             super(coordinate);
         }
         
         @Override
         public boolean occupied() {
             return false;
         }
         
         @Override
         public Piece getPiece() {
             return null;
         }
    }
    
    public static final class OccupiedTile extends Tile {
        private final Piece tilePiece;
                
        private OccupiedTile(int tileCoordinate, Piece tilePiece) {
            super(tileCoordinate);
            this.tilePiece = tilePiece;
        }
        
        @Override
        public boolean occupied() {
            return true;
        }
        
        @Override
        public Piece getPiece() {
            return this.tilePiece;
        }
    }
    
}
