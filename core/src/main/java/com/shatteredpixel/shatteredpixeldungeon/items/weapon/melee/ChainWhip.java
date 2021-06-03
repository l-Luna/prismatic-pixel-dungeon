package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class ChainWhip extends MeleeWeapon{

    {
        image = ItemSpriteSheet.CHAIN_WHIP;
        hitSound = Assets.Sounds.HIT;
        hitSoundPitch = 1.1f;

        tier = 5;
        RCH = 3;    //lots of extra reach
    }

    @Override
    public int max(int lvl) {
        return  23 +        // 23 base, down from 30
                lvl * tier; // +5 per level, down from +6
    }
}