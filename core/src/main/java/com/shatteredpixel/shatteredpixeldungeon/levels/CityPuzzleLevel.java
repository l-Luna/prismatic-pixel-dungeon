package com.shatteredpixel.shatteredpixeldungeon.levels;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.ArmoredBrute;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Piranha;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Shaman;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Sheep;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfExperience;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfEnchantment;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.StoneOfAugmentation;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.StoneOfBlast;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.StoneOfBlink;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.StoneOfFlock;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.StoneOfShock;
import com.shatteredpixel.shatteredpixeldungeon.levels.puzzles.PressurePad;
import com.shatteredpixel.shatteredpixeldungeon.levels.puzzles.PuzzleGenerator;
import com.shatteredpixel.shatteredpixeldungeon.levels.puzzles.PuzzleRoom;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.Trap;
import com.watabou.utils.Random;
import com.watabou.utils.SparseArray;

public class CityPuzzleLevel extends PuzzleLevel{

    static Item reward = Generator.random(Generator.Category.ARTIFACT);
    static Item mosterReward = new ScrollOfEnchantment();
    PuzzleRoom hardcodedRoom;
    PuzzleRoom optionalRoom1;

    String stringMap = "" +
            "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW" +
            "WWWWWWWWWWWE      d   f   f   f   f   f   WWWWWWWWWWW" +
            "WWWWWWWWWWWa          f   f   f   f   f   WWWWWWWWWWW" +
            "WWWWWWWWWWWfffffffffffffffffffffffffffffffWWWWWWWWWWW" +
            "WWWWWWWWWWW   f  af   f   f   f   f   f   WWWWWWWWWWW" +
            "WWWWWWWWWWW   f   f  af a f   f   f   f   WWWWWWWWWWW" +
            "WWWWWWWWWWW  af   f   f   f   f   f   f   WWWWWWWWWWW" +
            "WWWWWWWWWWWfffffffffffffffffffffffffffffffWWWWWWWWWWW" +
            "WWWWWWWWWWW   f  afa  f   f   f   f   f   WWWWWWWWWWW" +
            "WWWWWWWWWWW   f   f   f   f   f   f   f   WWWWWWWWWWW" +
            "WWWWWWWWWWWv af   f   f  afa  f   f   f   WWWWWWWWWWW" +
            "WWWWWWWWWWWfffffffffffffffXfffffffffffffffWWWWWWWWWWW" +
            "WWWWWWWWWWW   f   f   f  afa  f   f   f   WWWWWWWWWWW" +
            "WWWWWWWWWWW   f   f   f   f   f   f   f   WWWWWWWWWWW" +
            "WWWWWWWWWWW   f   f   f   f  af   f   f   WWWWWWWWWWW" +
            "WWWWWWWWWWWfffffffffffffffffffffffffffffffWWWWWWWWWWW" +
            "WWWWWWWWWWW   f   f   f   f   f   f   f   WWWWWWWWWWW" +
            "WWWWWWWWWWW   f   f   f   f   f   f   f   WWWWWWWWWWW" +
            "WWWWWWWWWWW   f   f   f   faaaf   f   f   WWWWWWWWWWW" +
            "WWWWWWWWWWWfffffffffffffffffffffffffffffffWWWWWWWWWWW" +
            "WWWWWWWWWWW   f   f   f   f   f   f   f   WWWWWWWWWWW" +
            "WWWWWWWWWWW   f   f   f   f   f   f   f   WWWWWWWWWWW" +
            "WWWWWWWWWWW   f   f   f  hfh  fa  f   f   WWWWWWWWWWW" +
            "WWWWWWWWWWWfffffffffffffffffffffffffffffffWWWWWWWWWWW" +
            "WWWWWWWWWWW   f   f   f  hfh  f   f   f   WWWWWWWWWWW" +
            "WWWWWWWWWWW   f   f   f   f   f   f   f   WWW     WWW" +
            "WWWWWWWWWWW   f   f   f   f   f  af   f   WWW     WWW" +
            "WWWWWWWWWWWfffffffffffffffffffffffffffffffWWW  p  WWW" +
            "WWWWWWWWWWW   f   f   f   f   f   f   f   WWW     WWW" +
            "WWWWWWWWWWW   f   f   f   f   f   f   f   WWW     WWW" +
            "WWWWWWWWWWW   f   f   f   f   f  vf   f   WWWWWDWWWWW" +
            "WWWWWWWWWWWfffffffffffffffffffffffffffffffWWWWW WWWWW" +
            "WWWWWWWWWWW   f   f   f   f   f h f   f   WWWWWLWWWWW" +
            "WSwwwwwwwSW   f   f   f   f   f   f   f   WS       SW" +
            "WwwwwwwwwwW   f   f   f   f   f   f a f   W         W" +
            "WwwwwpwwwwWfffffffffffffffffffffffffffffffW         W" + //5+35*53
            "WwwwwwwwwwW   f   f   f   f   f   f a f   W         W" +
            "WwwwwwwwwwW   f   f   f   f   f   f   f   W         W" +
            "WwwwwwwwwwW   f   f   f   f   f   f a f   W         W" +
            "WwwwwwwwwwWfffffffffffffffffffffffffffffffW         W" +
            "WwwwwwwwwwW   f   f   f   f   f   f   f   W         W" +
            "WS       SW   f   f   f   f   f   f a f   WS       SW" +
            "WWWWWDWWWWW   f   f   f   f   f   f   f   WWWWWDWWWWW" +
            "WWWWW WWWWWfffffffffffffffffffffffffffffffWWWWW WWWWW" +
            "WWWWW WWWWW   f   f   f  hf   f   f   f   WWWWW WWWWW" +
            "WWWWW     D   f   f   f   f   f   f   f   D     WWWWW" +
            "WWWWWWWWWWW   f   f   f   f   f   f   f   WWWWWWWWWWW" +
            "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW" +
            "";

    {
        color1 = 0x4b6636;
        color2 = 0xf2f2f2;

        viewDistance = 24;
        usesHardCodedPressurePads = false;
    }

    public CityPuzzleLevel(){
        pressurePads = new SparseArray<>();
        MAP_START = HardcodedMapHelper.mapStringToMapIntArray(stringMap);
    }

    public boolean build(){
        setSize(53, (12 * 4));

        map = MAP_START.clone();

        buildFlagMaps();
        cleanWalls();
        setup();

        for(int f = 0; f < MAP_START.length; f++){
            checkTile(f);
        }

        for(Trap t : hardcodedRoom.getTraps()){
            map[t.pos] = T;
            setTrap(t, t.pos);
        }

        return true;
    }

    public void setup(){
        hardcodedRoom = HardcodedMapHelper.mapStringToPuzzleRoom(stringMap, 0);
        optionalRoom1 = PuzzleGenerator.generatePuzzleRoom(7, 7, 14, width(), 44, 34, 1, new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, this);
        int[] padsPerStage = {optionalRoom1.getPressurePads().size()};
        this.padsPerStage = padsPerStage;
        int[] stageDoors = {1};
        this.stageDoors = stageDoors;
    }

    protected void createMobs(){
        for(Sheep sheep : hardcodedRoom.getSheep()){
            mobs.add(sheep);
        }

        for(Sheep sheep : optionalRoom1.getSheep()){
            mobs.add(sheep);
        }

        // TODO: jellyfish
        Mob[] floodedRoomMobs = {new Piranha(), new Piranha(), /*new Jellyfish(), new Jellyfish(),*/ new ArmoredBrute(), /*new Jellyfish(),*/ new ArmoredBrute(), new Shaman.BlueShaman(), new Shaman.RedShaman()};

        for(Mob b : floodedRoomMobs){
            int xpos = Random.Int(1, 9);
            int ypos = Random.Int(34, 40);
            while(findMob(xpos + ypos*width) != null || xpos+ypos*width == 1860){
                xpos = Random.Int(1, 9);
                ypos = Random.Int(34, 40);
            }
            b.pos = xpos + ypos * width;
            mobs.add(b);
        }

        for(Mob m : mobs){
            if(map[m.pos] == Terrain.HIGH_GRASS){
                map[m.pos] = Terrain.GRASS;
                losBlocking[m.pos] = false;
            }
        }
    }

    public void create(){
        super.create();

        for(PressurePad p : optionalRoom1.getPressurePads()){
            map[p.pos] = P;
            setPressurePad(p, p.pos);
        }
    }

    protected void createItems(){
        super.createItems();

        if(interResetData.get(0) == 0){
            drop(new PotionOfExperience(), 47 + 27 * width());
            drop(reward, 47 + 27 * width());
        }

        if(interResetData.get(1) == 0){
            //drop(new StoneOfDespair(), 5 + 35 * width()); //TODO
            drop(new StoneOfBlink(), 5 + 35 * width());
            drop(new StoneOfBlast(), 5 + 35 * width());
            drop(new StoneOfFlock(), 5 + 35 * width());
            drop(new StoneOfAugmentation(), 5 + 35 * width());
            drop(new StoneOfShock(), 5 + 35 * width());
            drop(mosterReward, 5 + 35 * width());
        }
    }

    @Override
    public String tilesTex(){
        return Assets.Environment.TILES_CITY;
    }

    @Override
    public String waterTex(){
        return Assets.Environment.WATER_CITY;
    }

    public void setupInterResetData(){
        interResetData.add(0);
        interResetData.set(0, 0);
        interResetData.add(0);
        interResetData.set(1, 0);
    }

    public void collectItem(Item item){
        if(item == reward){
            interResetData.set(0, 1);
        }
        if(item == mosterReward){
            interResetData.set(1, 1);
        }
    }
}