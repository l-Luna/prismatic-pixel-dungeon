package com.shatteredpixel.shatteredpixeldungeon.levels.traps;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Sheep;
import com.shatteredpixel.shatteredpixeldungeon.items.EquipableItem;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.TimekeepersHourglass;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.plants.Swiftthistle;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.InterlevelScene;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Sample;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

public class FleecingTrap extends Trap{

    public static boolean removeArmour = true;

    {
        color = BLUESHIFT;
        shape = WAVES;
    }

    @Override
    public void trigger() {
        if (Dungeon.level.heroFOV[pos]){
            Sample.INSTANCE.play(Assets.Sounds.TRAP);
        }
        reveal();
        if(!(Actor.findChar(pos) instanceof Hero) && !(Actor.findChar(pos) instanceof Sheep)) {
            Level.set(pos, Terrain.TRAP);
        }else{
            disarm();
            activate();
        }
        GameScene.updateMap(pos);
    }

    @Override
    public void activate(){
        Char target = Actor.findChar(pos);

        if(target instanceof Sheep){
            //Kill the (poor) sheep
            target.die(this);
            GameScene.updateMap(pos);
        }else if(target instanceof Hero){
            //Move their armour, or destroy it if it's cursed
            if(removeArmour && hero.belongings.armor != null){
                removeArmour = false;
                if(!hero.belongings.armor.cursed){
                    EquipableItem i = hero.belongings.armor;
                    i.doUnequip(hero, false, true);
                    Dungeon.level.drop(i.detachAll(hero.belongings.backpack), Dungeon.level.exit).sprite.drop(Dungeon.level.exit);
                    GLog.w( Messages.get(this, "move_armour"));
                }else{
                    hero.belongings.armor.detachAll(hero.belongings.backpack);
                    GLog.w( Messages.get(this, "destroy_armour"));
                }
                //Teleport to start of stage
            }else{
                Buff buff = hero.buff(TimekeepersHourglass.TimeFreeze.class);
                if (buff != null) buff.detach();
                buff = Dungeon.hero.buff(Swiftthistle.TimeBubble.class);
                if (buff != null) buff.detach();

                GLog.w( Messages.get(this, "return_to_start"));
                InterlevelScene.mode = InterlevelScene.Mode.RETURN;
                InterlevelScene.returnDepth = (Dungeon.depth / 6) * 6 + 1;
                InterlevelScene.returnPos = -1;
                Game.switchScene(InterlevelScene.class);
            }
        }
    }
}