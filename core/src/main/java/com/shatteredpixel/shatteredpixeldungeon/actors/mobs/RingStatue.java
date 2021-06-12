package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Doom;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.Ring;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfAccuracy;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfElements;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfEvasion;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfFuror;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfHaste;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfTenacity;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments.Grim;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.StatueSprite;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class RingStatue extends Statue{
	
	public Class[] rings = new Class[]{
			RingOfAccuracy.class,
			RingOfEvasion.class,
			RingOfElements.class,
			RingOfHaste.class,
			RingOfTenacity.class,
			RingOfFuror.class,
	};
	
	{
		spriteClass = StatueSprite.class;
		
		EXP = 12;
		state = PASSIVE;
		
		immunities.add(Doom.class);
	}
	
	protected Ring ring;
	
	public RingStatue(){
		super();
		
		HP = HT = 130;
		defenseSkill = 30;
		
		try{
			ring = (Ring)Random.element(rings).newInstance();
			ring.level(2);
			ring.identify();
		}catch(Exception e){
			ShatteredPixelDungeon.reportException(e);
		}
		
		ring.activate(this);
	}
	
	private static final String RING	= "ring";
	
	@Override
	public int attackSkill( Char target ) {
		return (int)((11 + Dungeon.depth) * weapon.accuracyFactor(this));
	}
	
	@Override
	public void storeInBundle(Bundle bundle){
		super.storeInBundle(bundle);
		bundle.put(RING, ring);
	}
	
	@Override
	public void restoreFromBundle(Bundle bundle){
		super.restoreFromBundle(bundle);
		ring = (Ring)bundle.get(RING);
	}
	
	public void die(Object cause){
		ring.identify();
		ring.degrade();
		Dungeon.level.drop(ring, pos).sprite.drop();
		super.die(cause);
	}
	
	@Override
	public void damage(int dmg, Object src){
		
		if(state == PASSIVE){
			state = HUNTING;
		}
		
		super.damage(dmg, src);
	}
	
	public String description(){
		return Messages.get(this, "desc", weapon.name(), ring.name());
	}
}