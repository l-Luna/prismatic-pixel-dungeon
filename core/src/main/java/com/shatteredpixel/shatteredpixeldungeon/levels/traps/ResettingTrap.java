package com.shatteredpixel.shatteredpixeldungeon.levels.traps;

import com.shatteredpixel.shatteredpixeldungeon.scenes.InterlevelScene;
import com.watabou.noosa.Game;

public class ResettingTrap extends Trap {

    {
        color = BLUESHIFT;
        shape = DOTS;
    }

    @Override
    public void activate() {
        InterlevelScene.mode = InterlevelScene.Mode.RESET;
        InterlevelScene.returnPos = -1;
        Game.switchScene( InterlevelScene.class );
    }
}