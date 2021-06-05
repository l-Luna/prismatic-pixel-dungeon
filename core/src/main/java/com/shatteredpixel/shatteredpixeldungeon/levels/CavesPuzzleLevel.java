package com.shatteredpixel.shatteredpixeldungeon.levels;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.WaterOfAwareness;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.WaterOfHealth;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.WellWater;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Statue;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Sheep;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfExperience;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfEnchantment;
import com.shatteredpixel.shatteredpixeldungeon.levels.puzzles.PressurePad;
import com.shatteredpixel.shatteredpixeldungeon.levels.puzzles.PuzzleGenerator;
import com.shatteredpixel.shatteredpixeldungeon.levels.puzzles.PuzzleRoom;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.CursingTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.Trap;
import com.watabou.utils.Random;
import com.watabou.utils.SparseArray;

public class CavesPuzzleLevel extends PuzzleLevel{

    static Item reward = Generator.random(Generator.Category.WAND);
    static Item reward2 = new ScrollOfEnchantment();
    // TODO: PuzzleStatue
    static Statue firstStatue;
    static Statue secondStatue;
    private static final Class<?>[] WATERS = {WaterOfAwareness.class, WaterOfHealth.class};
    PuzzleRoom hardcodedRoom;
    PuzzleRoom room;
    PuzzleRoom optionalRoom1;
    PuzzleRoom optionalRoom2;

    //interResetData: 0 is wand, 1 is first statue, 2 is second statue, 3 is scroll, 4 is water

    String stringMap = "" +
            "WWWMWMWMWWWWWWWWWWWWWWWWWMWWWWWWWWWWWWWWMWWMMMWMW" +
            "WS       SWWWWWWWWWWWWWW E WWWWWMWWWWWWS       SW" +
            "W         WWWWWWWWf MWWW   WWW f  WWWWW         W" +
            "W         WWWWWM  f   dMWDWM   f   WWWW         W" +
            "W         WWWWW   f            f    WWW         W" +
            "W    p    WWWWM   f            f a  WWW    p    W" +
            "W         WWWW    f            f  a MWW         W" +
            "W         WWWW    f   a a a a  f     WW         W" +
            "W         WWWWfffffffffffffffffffffffWW         W" +
            "WS       SWWWWf   f   f   f  af   f  WWS       SW" +
            "WWWWWDWWWWWWWWf   f   f   f   f a f  WWWWWWDWWWWW" +
            "WWWWW WWWWWWWWf   f   f   f   f  af  WWWWWW WWWWW" +
            "WWWWW WWWWWWWWfffffffffffffffffffffffWWWWWW WWWWW" +
            "WWWWW WWWWWWWWf   f   f   f  af   f  WWWWWW WwWWW" +
            "WWWWW WWWWWWWWf   f   f a f   f   f  WWWWWw WWWWW" +
            "WWWWW             f   f  af  af   f         WWWWW" +
            "WWWWWWWWWWWWWWfffffffffffffffffffffffffWWWWWWWWWW" +
            "WWWWWWWWWWWWWWf   f  af   f   f   f  WWWWWWWWWWWW" +
            "WWWWWWWWWWWWWWW a f   f   f   f   f WWWWWWWWWWWWW" +
            "WWWWWWWWWWWWWWW a f   fa  f   f   f WWWWWWWWWWWWW" +
            "WWWWWWWWWWWWWWWWfffffffffffffffffffWWWWWWWWWWWWWW" +
            "WWWWWWWWWWWWWWWWW f   fa  WWWWWWWWWWWWWWWWWWWWWWW" +
            "WWWWWWWWWWWWWWWWW     fWWWWWWWWWWWWWWWWWWWWWWWWWW" +
            "WWWWWWWWWWWWWWWWWWDWWWfWWWWMWWWWWWWWWWWWWWWWWWWWW" +
            "WWWWWWWWWWWWWWWWWW  WMWWd        MWWWWWWWWWWWWWWW" +
            "WWWWWWWWWWWWWWWWW                 WWWWWWWWWWWWWWW" +
            "WWWWWWWWWWWWWWM                   WWWWWWWWWWWWWWW" +
            "WWWWWWWWWWWWWW                     WWWWWWWWWWWWWW" +
            "WWWWWWWWWWWWM                      MWWWWWWWWWWWWW" +
            "WWWWWWWWWWWW                        WWWWWWWWWWWWW" +
            "WWWWWWWWWWWW                        WWWWWWWWWWWWW" +
            "WWWWWWWWWWWW                        WWWWWWWWWWWWW" +
            "WWWWWWWWWWWW                        WWWWWWWWWWWWW" +
            "WWW   WWWWWM                        WWWWWWW   WWW" +
            "WWW p WW                                 WW p WWW" +
            "WWW   WW WW                         WWWW WW   WWW" +
            "WMMWLWMWLWW                         WWWWLMWWLMWWW" +
            "WS       SWW                       WWWWS       SW" +
            "W         WWW                      WWWW         W" +
            "W         WWW                     WWWWW         W" +
            "W         WWW                    WWWWWW         W" +
            "W    p    WWWW                 WWWWWWWW    p    W" +
            "W         WWWWW             WWWWWWWWWWW         W" +
            "W         WWWWWWWWWWWWWWMLMWWWWWWWWWWWW         W" +
            "W         WWWWWWWWWWWWWW   WWWWWWWWWWWW         W" +
            "WS       SWWWWWWWWWWWWWW p WWWWWWWWWWWWS       SW" +
            "WWWWWWWWWWWWWWWWWWWWWWWW   WWWWWWWWWWWWWWWWWWWWWW" +
            "WWWWWWWWWWWWWWWWWWWWWWWW X WWWWWWWWWWWWWWWWWWWWWW" +
            "WWWWWWWWWWWWWWWWWWWWWWWW   WWWWWWWWWWWWWWWWWWWWWW" +
            "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW";

    {
        color1 = 0x534f3e;
        color2 = 0xb9d661;

        viewDistance = 12;
        usesHardCodedPressurePads = false;
    }

    public CavesPuzzleLevel(){
        pressurePads = new SparseArray<>();
        MAP_START = HardcodedMapHelper.mapStringToMapIntArray(stringMap);
    }

    public boolean build(){
        setSize(49, 50);

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

        if(interResetData.get(4) == 0){
            set(44 + 34 * width, Terrain.WELL, this);
            Class<? extends WellWater> waterClass = (Class<? extends WellWater>)Random.element(WATERS);
            WellWater.seed(44 + 34 * width, 1, waterClass, this);
        }

        return true;
    }

    public void setup(){
        hardcodedRoom = HardcodedMapHelper.mapStringToPuzzleRoom(stringMap, 0);
        room = PuzzleGenerator.generatePuzzleRoom(21, 15, 15, 49, 14, 26, 1, new int[0], this);
        optionalRoom1 = PuzzleGenerator.generatePuzzleRoom(7, 7, 14, 49, 2, 38, 2, new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, this);
        optionalRoom2 = PuzzleGenerator.generatePuzzleRoom(7, 7, 14, 49, 40, 38, 3, new int[]{2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, this);
        int[] stageDoors = {2, 1, 1, 3, 1};
        this.stageDoors = stageDoors;
        int[] padsPerStage = {hardcodedRoom.getPressurePads().size(), room.getPressurePads().size(), optionalRoom1.getPressurePads().size(), optionalRoom2.getPressurePads().size()};
        this.padsPerStage = padsPerStage;
    }

    protected void createMobs(){
        for(Sheep sheep : hardcodedRoom.getSheep()){
            mobs.add(sheep);
        }

        for(Sheep sheep : room.getSheep()){
            mobs.add(sheep);
        }

        for(Sheep sheep : optionalRoom1.getSheep()){
            mobs.add(sheep);
        }

        for(Sheep sheep : optionalRoom2.getSheep()){
            mobs.add(sheep);
        }

        if(interResetData.get(1) == 0){
            firstStatue = new Statue();
            firstStatue.pos = 43 + 5 * width;
            mobs.add(firstStatue);
        }

        if(interResetData.get(2) == 0){
            secondStatue = new Statue();
            secondStatue.pos = 5 + 5 * width;
            mobs.add(secondStatue);
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

        for(PressurePad p : room.getPressurePads()){
            map[p.pos] = P;
            setPressurePad(p, p.pos);
        }

        for(PressurePad p : hardcodedRoom.getPressurePads()){
            map[p.pos] = P;
            setPressurePad(p, p.pos);
        }

        for(PressurePad p : optionalRoom1.getPressurePads()){
            map[p.pos] = P;
            setPressurePad(p, p.pos);
        }

        for(PressurePad p : optionalRoom2.getPressurePads()){
            map[p.pos] = P;
            setPressurePad(p, p.pos);
        }
    }

    protected void createItems(){
        super.createItems();

        if(interResetData.get(0) == 0){
            reward = Generator.random(Generator.Category.WAND);
            reward.upgrade();
            reward.cursed = reward.cursedKnown = true;
            drop(new PotionOfExperience(), 25 + 45 * width());
            drop(reward, 25 + 45 * width());
        }

        if(interResetData.get(3) == 0){
            drop(reward2, 4 + 34 * width());
        }
    }

    public void collectItem(Item item){
        if(item == reward){
            interResetData.set(0, 1);
        }

        if(item == reward2){
            interResetData.set(3, 1);
        }
    }

    public void killMob(Mob mob){
        if(mob == firstStatue){
            interResetData.set(1, 1);
        }

        if(mob == secondStatue){
            interResetData.set(2, 1);
        }
    }

    public void setupInterResetData(){
        interResetData.add(0);
        interResetData.add(0);
        interResetData.add(0);
        interResetData.add(0);
        interResetData.add(0);

        interResetData.set(0, 0);
        interResetData.set(1, 0);
        interResetData.set(2, 0);
        interResetData.set(3, 0);
        interResetData.set(4, 0);
    }

    @Override
    public String tilesTex(){
        return Assets.Environment.TILES_CAVES;
    }

    @Override
    public String waterTex(){
        return Assets.Environment.WATER_CAVES;
    }
}