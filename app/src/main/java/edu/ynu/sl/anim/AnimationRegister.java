package edu.ynu.sl.anim;

/**
 * Created by ku on 2014/12/27.
 */
public class AnimationRegister {
    private AnimationCotroller topAnimationCotroller;
    private AnimationCotroller homeButtonAnimationCotroller;
    private AnimationCotroller floatingButtonAnimationCotroller;

    private Boolean topAnimEnd = false;
    private Boolean homeButtonAnimEnd = false;
    private Boolean animEndBoolean = false;
    private static AnimationRegister single = null;

    public synchronized static AnimationRegister getInstance() {
        if (single == null) {
            single = new AnimationRegister();
        }
        return single;
    }

    public AnimationCotroller getTopAnimationCotroller() {
        return topAnimationCotroller;
    }

    public void setTopAnimationCotroller(AnimationCotroller topAnimationCotroller) {
        this.topAnimationCotroller = topAnimationCotroller;
    }

    public Boolean getTopAnimEnd() {
        return topAnimEnd;
    }

    public void setTopAnimEnd(Boolean topAnimEnd) {
        this.topAnimEnd = topAnimEnd;
    }

    public void close() {
        single = null;
    }

    public AnimationCotroller getHomeButtonAnimationCotroller() {
        return homeButtonAnimationCotroller;
    }

    public void setHomeButtonAnimationCotroller(
            AnimationCotroller homeButtonAnimationCotroller) {
        this.homeButtonAnimationCotroller = homeButtonAnimationCotroller;
    }

    public Boolean getHomeButtonAnimEnd() {
        return homeButtonAnimEnd;
    }

    public void setHomeButtonAnimEnd(Boolean homeButtonAnimEnd) {
        this.homeButtonAnimEnd = homeButtonAnimEnd;
    }

    public AnimationCotroller getFloatingButtonAnimationCotroller() {
        return floatingButtonAnimationCotroller;
    }

    public void setFloatingButtonAnimationCotroller(
            AnimationCotroller floatingButtonAnimationCotroller) {
        this.floatingButtonAnimationCotroller = floatingButtonAnimationCotroller;
    }

    public Boolean getAnimEndBoolean() {
        return animEndBoolean;
    }

    public void setAnimEndBoolean(Boolean animEndBoolean) {
        this.animEndBoolean = animEndBoolean;
    }

}
