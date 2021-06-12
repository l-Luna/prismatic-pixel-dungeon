package com.shatteredpixel.shatteredpixeldungeon.items.wands;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.CorrosionParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagesStaff;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.HashSet;

public class WandOfChanneling extends Wand{
	
	{
		image = ItemSpriteSheet.WAND_CHANNELING;
	}
	
	@Override
	public void onZap(Ballistica attack){
		int cell = attack.collisionPos;
		
		Char ch = Actor.findChar(cell);
		if(ch != null){
			wandProc(ch, chargesPerCast());
			
			ch.sprite.burst(0xFF561313, level() / 2 + 2);
			
			for(Buff buff : getMobEffectsBuffs())
				if(buff instanceof FlavourBuff)
					Buff.affect(ch, ((FlavourBuff)buff).getClass(), 3 + level());
				else
					Buff.affect(ch, buff.getClass());
		}
		
		for(Blob blob : getAreaEffectsBlobs()){
			Blob blob1 = Blob.seed(cell, 50 + 10 * level(), blob.getClass());
			CellEmitter.center(cell).burst(CorrosionParticle.SPLASH, level() / 2 + 2);
			GameScene.add(blob1);
		}
	}
	
	@Override
	public void onHit(MagesStaff staff, Char attacker, Char defender, int damage){
		// flat 10% chance
		if(Random.Float() < 0.1f)
			for(Buff buff : getMobEffectsBuffs())
				if(buff instanceof FlavourBuff)
					Buff.affect(defender, ((FlavourBuff)buff).getClass(), 1 + level());
				else
					Buff.affect(defender, buff.getClass());
	}
	
	@Override
	public String statsDesc(){
		return Messages.get(this, "stats_desc", getAreaEffects(), getMobEffects());
	}
	
	String getAreaEffects(){
		int heroCell;
		String toDo = "";
		
		if(Dungeon.hero == null)
			heroCell = 0;
		else
			heroCell = Dungeon.hero.pos;
		
		for(Blob blob : Dungeon.level.blobs.values())
			if(blob.volume > 0 && blob.cur[heroCell] > 0){
				if(toDo.length() == 0)
					toDo += "create ";
				toDo += Messages.get(blob, "name");
			}
		
		if(toDo.length() == 0)
			return "do nothing";
		
		return toDo;
	}
	
	ArrayList<Blob> getAreaEffectsBlobs(){
		int heroCell;
		ArrayList<Blob> blobs = new ArrayList<>();
		if(Dungeon.hero == null){
			heroCell = 0;
		}else{
			heroCell = Dungeon.hero.pos;
		}
		for(Blob blob : Dungeon.level.blobs.values()){
			if(blob.volume > 0 && blob.cur[heroCell] > 0){
				blobs.add(blob);
			}
		}
		return blobs;
	}
	
	String getMobEffects(){
		if(Dungeon.hero == null)
			return "something or other";
		HashSet<Buff> f = Dungeon.hero.buffs();
		ArrayList<Buff> negativeBuffs = new ArrayList<>();
		for(Buff b : f)
			if(b != null && b.type == Buff.buffType.NEGATIVE)
				negativeBuffs.add(b);
		if(negativeBuffs.size() == 0)
			return "do nothing";
		else{
			String toDo = "";
			int effectCount = 0;
			for(Object x : negativeBuffs){
				effectCount++;
				toDo += Messages.get(x, "affect");
				if(effectCount != negativeBuffs.size())
					toDo += ", ";
			}
			return toDo;
		}
	}
	
	ArrayList<Buff> getMobEffectsBuffs(){
		if(Dungeon.hero == null)
			return new ArrayList<>();
		HashSet<Buff> f = Dungeon.hero.buffs();
		ArrayList<Buff> negativeBuffs = new ArrayList<>();
		for(Buff b : f)
			if(b != null && b.type == Buff.buffType.NEGATIVE)
				negativeBuffs.add(b);
		if(negativeBuffs.size() == 0)
			return new ArrayList<>();
		else{
			ArrayList<Buff> buffs = new ArrayList<>();
			for(Object x : negativeBuffs){
				Buff negativeBuff = (Buff)x;
				buffs.add(negativeBuff);
			}
			return buffs;
		}
	}
	
	public void staffFx(MagesStaff.StaffParticle particle){
		particle.color(0x6d3030);
		particle.am = 0.6f;
		particle.setLifespan(3f);
		float amt = PointF.PI * 0.25f + Random.Float(-PointF.PI / 4, PointF.PI / 4);
		particle.speed.polar(amt, 0.3f);
		particle.setSize(1f, 2f);
		particle.radiateXY(5f);
		
		particle.speed.polar(amt + PointF.PI, 4f);
	}
}