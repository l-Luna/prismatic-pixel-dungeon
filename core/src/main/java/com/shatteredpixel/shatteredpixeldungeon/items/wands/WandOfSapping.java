package com.shatteredpixel.shatteredpixeldungeon.items.wands;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Charm;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Chill;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Healing;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Ooze;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Weakness;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments.Vampiric;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagesStaff;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.tiles.DungeonTilemap;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

public class WandOfSapping extends DamageWand{
	
	{
		image = ItemSpriteSheet.WAND_SAPPING;
	}
	
	@Override
	public int min(int lvl){
		return 3 + lvl;
	}
	
	@Override
	public int max(int lvl){
		return 6 + 3 * lvl;
	}
	
	@Override
	public void onZap(Ballistica attack){
		int cell = attack.collisionPos;
		
		Char ch = Actor.findChar(cell);
		if(ch != null){
			wandProc(ch, chargesPerCast());
			int damageAmount = damageRoll();
			ch.damage(damageAmount, this);
			Buff.affect(curUser, Healing.class).setHeal((int)(0.5 * damageAmount), 0.333f, 0);
			
			ch.sprite.burst(0xFF9818FF, level() / 2 + 2);
			
			if(ch.properties().contains(Char.Property.UNDEAD))
				Buff.affect(curUser, Weakness.class, Math.max(3, 8 - level()));
			else if(ch.properties().contains(Char.Property.ACIDIC))
				Buff.affect(curUser, Ooze.class);
			else if(ch.properties().contains(Char.Property.FIERY))
				Buff.affect(curUser, Burning.class);
			else if(ch.properties().contains(Char.Property.ICY))
				Buff.affect(curUser, Chill.class, 2f);
			else if(ch.properties().contains(Char.Property.ELECTRIC))
				Buff.affect(curUser, Paralysis.class, 1f);
			else
				Buff.affect(curUser, Charm.class, Math.max(2, 5 - level())).object = ch.id();
		}else
			Dungeon.level.pressCell(attack.collisionPos);
	}
	
	@Override
	public void onHit(MagesStaff staff, Char attacker, Char defender, int damage){
		//Acts as Vampiric, could add direct sapping but eh
		new Vampiric().proc(staff, attacker, defender, damage);
	}
	
	public void staffFx(MagesStaff.StaffParticle particle){
		particle.color(0x9818FF);
		particle.am = 0.6f;
		particle.setLifespan(3f);
		float amt = Random.Float(PointF.PI2);
		particle.speed.polar(amt, 0.3f);
		particle.setSize(1f, 2f);
		particle.radiateXY(5f);
		
		particle.speed.polar(amt + PointF.PI, 2.5f);
	}
	
	public void fx(Ballistica bolt, Callback callback){
		MagicMissile missile = ((MagicMissile)curUser.sprite.parent.recycle(MagicMissile.class));
		missile.reset(MagicMissile.MAGIC_MISSILE, DungeonTilemap.raisedTileCenterToWorld(bolt.collisionPos), curUser.sprite.center(), callback);
		Sample.INSTANCE.play(Assets.Sounds.ZAP);
	}
}