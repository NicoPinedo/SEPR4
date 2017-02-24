package com.teamfractal.util.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.teamfractal.util.animation.IAnimation;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class AbstractAnimationScreen {
    protected abstract Batch getBatch();

    private final ArrayList<IAnimation> animations = new ArrayList<IAnimation>();
    private final ArrayList<IAnimation> queueAnimations = new ArrayList<IAnimation>();

    /**
     * Add a new animation to current Screen.
     * @param animation    The animation to be added.
     */
    public void addAnimation(IAnimation animation) {
        if (!animations.contains(animation) && !queueAnimations.contains(animation)) {
            synchronized (queueAnimations) {
                queueAnimations.add(animation);
            }
        }
    }

    /**
     * Request to render animation.
     * @param delta   Time delta from last render call.
     * @param type    The animation type to render.
     */
    public void renderAnimation(float delta, IAnimation.AnimationType type) {
        Batch batch = getBatch();

        synchronized (animations) {
            synchronized (queueAnimations) {
                animations.addAll(queueAnimations);
                queueAnimations.clear();
            }

            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

            Iterator<IAnimation> iterator = animations.iterator();

            while (iterator.hasNext()) {
                IAnimation animation = iterator.next();
                if (type == animation.getType()) {
                    if (animation.tick(delta, this, batch)) {
                        iterator.remove();
                        animation.callAnimationFinish();
                    }
                }
            }

            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
    }

    private static IAnimation.AnimationType[] animationTypes = IAnimation.AnimationType.Overlay.getDeclaringClass().getEnumConstants();

    /**
     * Request to render all animation registered.
     * @param delta   Time delta from last render call.
     */
    public void renderAnimation(float delta) {
        for (IAnimation.AnimationType type : animationTypes) {
            renderAnimation(delta, type);
        }
    }

    /**
     * The screen size.
     * @return  Size of the screen.
     */
    abstract public Size getScreenSize();

    /**
     * A size structure with Width and Height property.
     */
    public class Size {
        public float Width;
        public float Height;
    }
}
