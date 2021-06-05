/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2021 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.HorizontalSheepSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.SheepSprite;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class HorizontalPuzzleSheep extends Sheep{

	{
		spriteClass = HorizontalSheepSprite.class;
	}

	protected boolean act() {
		spend(1);
		return true;
	}

	@Override
	public boolean interact(Char c){
		if(Dungeon.hero.pos == pos + 1 && !Dungeon.level.solid[pos-1] && Dungeon.level.findMob(pos-1) == null){
			sprite.move(pos, pos - 1);
			move(pos - 1);
			Dungeon.hero.sprite.move(Dungeon.hero.pos, Dungeon.hero.pos - 1);
			Dungeon.hero.move(Dungeon.hero.pos - 1);
			Dungeon.hero.spend(1 / Dungeon.hero.speed());
			Dungeon.hero.busy();
			return true;
		}else if(Dungeon.hero.pos == pos - 1 && !Dungeon.level.solid[pos+1] && Dungeon.level.findMob(pos+1) == null){
			sprite.move(pos, pos + 1);
			move(pos + 1);
			Dungeon.hero.sprite.move(Dungeon.hero.pos, Dungeon.hero.pos + 1);
			Dungeon.hero.move(Dungeon.hero.pos + 1);
			Dungeon.hero.spend(1 / Dungeon.hero.speed());
			Dungeon.hero.busy();
			return true;
		}else{
			super.interact(c);
		}
		return false;
	}
}