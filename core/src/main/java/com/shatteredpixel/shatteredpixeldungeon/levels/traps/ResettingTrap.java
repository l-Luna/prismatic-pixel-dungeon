package com.shatteredpixel.shatteredpixeldungeon.levels.traps;

import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.InterlevelScene;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Game;

public class ResettingTrap extends Trap {

    {
        color = BLUESHIFT;
        shape = STARS;
    }

    @Override
    public void activate() {
        GLog.w( Messages.get(this, "level_shifts"));
        InterlevelScene.mode = InterlevelScene.Mode.RESET;
        InterlevelScene.returnPos = -1;
        Game.switchScene( InterlevelScene.class );
    }
}