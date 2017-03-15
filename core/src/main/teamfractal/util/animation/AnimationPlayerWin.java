package teamfractal.util.animation;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Align;
import teamfractal.util.screens.AbstractAnimationScreen;
import teamfractal.util.screens.AbstractAnimationScreen;

public class AnimationPlayerWin implements IAnimation {
    private final int playerId;
    private IAnimationFinish callback;
    private static BitmapFont font = new BitmapFont();

    public AnimationPlayerWin(int playerId) {
        this.playerId = playerId;
    }

    /**
     *
     * @param delta     Time change since last call.
     * @param screen    The screen to draw on.
     * @param batch     The Batch for drawing stuff.
     * @return          return <code>true</code> if the animation has completed.
     */
    @Override
    public boolean tick(float delta, AbstractAnimationScreen screen, Batch batch) {
        AbstractAnimationScreen.Size size = screen.getScreenSize();
        batch.begin();
        font.setColor(1,1,1, 1);
        font.draw(batch,  "PLAYER " + playerId + " WINS",
                size.Width/2, size.Height/2 + font.getLineHeight()/2,
                0, Align.center, false);
        batch.end();
        return false;
    }

    @Override
    public void setAnimationFinish(IAnimationFinish callback) {
        this.callback = callback;
    }

    @Override
    public void callAnimationFinish() {
        if (callback != null)
            callback.OnAnimationFinish();
    }

    @Override
    public void cancelAnimation() {

    }

    @Override
    public AnimationType getType() {
        return AnimationType.Overlay;
    }
}
