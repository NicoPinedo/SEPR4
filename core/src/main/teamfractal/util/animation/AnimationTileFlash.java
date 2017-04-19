package main.teamfractal.util.animation;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import main.drtn.game.GameEngine;
import main.teamfractal.util.screens.AbstractAnimationScreen;
import main.drtn.game.GameEngine;
import main.drtn.game.GameEngine;
import main.teamfractal.util.screens.AbstractAnimationScreen;
import main.drtn.game.GameEngine;

import java.util.ArrayList;
import java.util.List;

public class AnimationTileFlash implements IAnimation {
    private static final ShapeRenderer rect = new ShapeRenderer();
    private final float height;
    private final float x;
    private final float y;
    private final float width;
    private final int currentPhase;
    private float time;
    private final static float timeout = 0.5f;
    private IAnimationFinish callback;
    private boolean keepFlashing;
    private boolean reverseAnimation;
    private static final List<Integer> flashPhase = new ArrayList<Integer>(){{
        add(1);
        add(3);
    }};

    public AnimationTileFlash(float x, float y, float width, float height) {
        currentPhase = GameEngine.getInstance().getPhase();
        keepFlashing = flashPhase.contains(currentPhase);

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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
        // Phase changed, mark current animation as finished.
        if (GameEngine.getInstance().getPhase() != currentPhase) {
            return true;
        }

        time += delta;
        if (keepFlashing) {
            while (time > timeout) {
                time -= timeout;
                reverseAnimation = !reverseAnimation;
            }
        }

        synchronized (rect) {
            rect.begin(ShapeRenderer.ShapeType.Filled);
            updateRectOpacity();
            rect.rect(x, y, width, height);
            rect.end();
        }

        return time >= timeout;
    }

    /**
     * Updates rectangle opacity.
     */
    private void updateRectOpacity() {
        rect.setColor(1, 1, 1,  calculateOpacity());
    }

    /**
     * Calculate opacity for rectangle.
     * @return   Calculated opacity for current time.
     */
    private float calculateOpacity() {
        float v = time / timeout;
        v = 1 - v * v;
        if (reverseAnimation) {
            v = 1 - v;
        }
        v = 0.1f + v * 0.4f;
        return v;
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
        keepFlashing = false;
        time = timeout;
    }

    @Override
    public AnimationType getType() {
        return AnimationType.Tile;
    }
}
