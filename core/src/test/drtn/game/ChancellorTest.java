package drtn.game;

import com.badlogic.gdx.Game;
import drtn.game.entity.Chancellor;
import drtn.game.entity.Player;
import drtn.game.entity.Tile;
import drtn.game.enums.ResourceType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ChancellorTest {
    private Game game = new Main();
    private Tile[] tiles = new Tile[16];
    private Player TestPlayer = new Player(0);


    @Test
    public void testMoveChancellor(){
        for (int i = 0; i < 16;i++) {
            tiles[i] = new Tile(game, i + 1, 5, 5, 5, false, new Runnable() {
                @Override
                public void run() {

                }
            });
        }
        Chancellor TestChancellor = new Chancellor(tiles);
        int startX = TestChancellor.getCoordX();
        int startY = TestChancellor.getCoordY();

        TestChancellor.move();
        assertNotEquals(startX, TestChancellor.getCoordX());
        assertNotEquals(startY, TestChancellor.getCoordY());
    }
    @Test
    public void testChancellorCaptured(){
        Chancellor TestChancellor = new Chancellor(tiles);
        TestPlayer.setResource(ResourceType.MONEY, 100);
        int reward = TestChancellor.getReward();
        TestChancellor.updatePlayer(TestPlayer);
        TestChancellor.captured();
        assertEquals(TestPlayer.getResource(ResourceType.MONEY),reward + 100);
    }


}
