package com.shatteredpixel.shatteredpixeldungeon.items;

import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ShieldBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.TimekeepersHourglass;
import com.shatteredpixel.shatteredpixeldungeon.items.food.Food;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfLevitation;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfMagicMapping;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTransmutation;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.plants.Swiftthistle;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndOptions;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndSendDepth;
import com.watabou.utils.Random;

import java.util.ArrayList;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

public class DebugScroll extends Item {

    {
        image = ItemSpriteSheet.SCROLL_DEBUG;
        stackable = true;
        defaultAction = AC_READ;
    }

    public static final String AC_READ = "READ";

    public ArrayList<String> actions(Hero hero){
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_READ);
        return actions;
    }

    public void execute(Hero hero, String action){
        super.execute(hero, action);
        if(action.equals(AC_READ)){
            GameScene.show(
                    new WndOptions(Messages.get(this, "name"),
                            Messages.get(this, "use_prompt"),
                            Messages.get(this, "get_item"),
                            Messages.get(this, "send_to_level"),
                            Messages.get(this, "invincibility")){
                        protected void onSelect(int index){
                            if(index == 0){
                                Item i;
                                int itemToGive = Random.Int(5);
                                switch(itemToGive){
                                    case 1:
                                        i = new PotionOfLevitation();
                                        break;
                                    case 2:
                                        i = new ScrollOfMagicMapping();
                                        break;
                                    case 3:
                                        i = new ScrollOfUpgrade();
                                        break;
                                    case 4:
                                        i = new ScrollOfTransmutation();
                                        break;
                                    default:
                                        i = new Food();
                                        break;
                                }
                                i.identify();
                                i.quantity(100);
                                i.level(100);
                                i.collect();
                            }
                            if(index == 1)
                                GameScene.show(new WndSendDepth());
                            if(index == 2) {
                                Buff buff = hero.buff(DebugInvincibility.class);
                                if (buff != null)
                                    buff.detach();
                                else
                                    Buff.affect(hero, DebugInvincibility.class);
                            }
                        }
                    }
            );
        }
    }

    public boolean isUpgradable(){
        return false;
    }

    public static class DebugInvincibility extends ShieldBuff{

        {
            type = buffType.POSITIVE;
        }

        // just ignore all damage
        @Override
        public int absorbDamage(int dmg){
            return 0;
        }

        @Override
        public int icon() {
            return BuffIndicator.ARMOR;
        }

        @Override
        public String toString() {
            return Messages.get(this, "name");
        }

        @Override
        public String desc() {
            return Messages.get(this, "desc");
        }

        @Override
        public boolean act() {
            spend(TICK);
            return super.act();
        }
    }
}