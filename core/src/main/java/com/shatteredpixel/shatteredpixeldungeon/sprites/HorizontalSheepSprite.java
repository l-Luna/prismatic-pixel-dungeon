package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;
import com.watabou.utils.Random;

public class HorizontalSheepSprite extends MobSprite {

    public HorizontalSheepSprite() {
        super();

        texture( Assets.Sprites.SHEEP );

        TextureFilm frames = new TextureFilm(texture, 16, 16);

        idle = new Animation( 8, true );
        idle.frames( frames, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 5, 6, 7, 4 );

        run = idle.clone();
        attack = idle.clone();

        die = new Animation( 20, false );
        die.frames( frames, 4 );

        play( idle );
        curFrame = Random.Int( curAnim.frames.length );
    }
}