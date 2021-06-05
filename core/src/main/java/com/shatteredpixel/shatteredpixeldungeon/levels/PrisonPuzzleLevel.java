package com.shatteredpixel.shatteredpixeldungeon.levels;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Sheep;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfExperience;
import com.shatteredpixel.shatteredpixeldungeon.levels.puzzles.PressurePad;
import com.shatteredpixel.shatteredpixeldungeon.levels.puzzles.PuzzleGenerator;
import com.shatteredpixel.shatteredpixeldungeon.levels.puzzles.PuzzleRoom;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.FleecingTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.ResettingTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.Trap;
import com.watabou.utils.SparseArray;

public class PrisonPuzzleLevel extends PuzzleLevel{

    static Item reward = Generator.random(Generator.Category.SCROLL);
    PuzzleRoom room;
    PuzzleRoom room2;

    String stringMap = ""+
            "WWWWWWWWWWMWWWWWWWWWW" +
            "WWWWWWWWWSTSWWWWWWWWW" +
            "WWWWWWWWW E WWWWWWWWW" +
            "WWWWWWWWW   WWWWWWWWW" +
            "WWWWWWWMW   WMWWWWWWW" +
            "WWWWWW  WWDWW  WWWWWW" +
            "WWWWW           WWWWW" +
            "WWWWW           WWWWW" +
            "WWWWWW         WWWWWW" +
            "WWWWWW         WWWWWW" +
            "WWWWWW         WWWWWW" +
            "WWWWW           WWWWW" +
            "WWWWW           WWWWW" +
            "WWWWWW  WM MW  WWWWWW" +
            "WWWWWWWWWMLMWWWWWWWWW" +
            "WWWWWW  WM MW  WWWWWW" +
            "WWWWW           WWWWW" +
            "WWWWW      WW   WWWWW" +
            "WWWWWW     WW  WWWWWW" +
            "WWWWWW         WWWWWW" +
            "WWWWWW WW      WWWWWW" +
            "WWWWW  WW       WWWWW" +
            "WWWWW           WWWWW" +
            "WWWWWW  WWTWW  WWWWWW" +
            "WWWWWWWWMWLWMWWWWWWWW" +
            "WWWWWWWWS   SWWWWWWWW" +
            "WWWWWWWW  p  WWWWWWWW" +
            "WWWWWWWW     WWWWWWWW" +
            "WWWWWWWW     WWWWWWWW" +
            "WWWWWWWW  X  WWWWWWWW" +
            "WWWWWWWWS   SWWWWWWWW" +
            "WWWWWWWWWWWWWWWWWWWWW";

    {
        color1 = 0x6a723d;
        color2 = 0x88924c;

        usesHardCodedPressurePads = false;
        viewDistance = 12;
    }

    public PrisonPuzzleLevel(){
        pressurePads = new SparseArray<>();
        MAP_START = HardcodedMapHelper.mapStringToMapIntArray(stringMap);
    }

    @Override
    protected void setup(){
        super.setup();

        room = PuzzleGenerator.generatePuzzleRoom(9, 7, 10, 21, 6, 6, 0, new int[0], this);
        room2 = PuzzleGenerator.generatePuzzleRoom(9, 7, 6, 21, 6, 16, 1, new int[0], this);
        int[] stageDoors = {0, 1};
        int[] padsPerStage = {room.getPressurePads().size(), room2.getPressurePads().size()};
        Trap[] traps = {new ResettingTrap().reveal(), new FleecingTrap().reveal()};
        this.stageDoors = stageDoors;
        this.padsPerStage = padsPerStage;
        this.traps = traps;
    }

    @Override
    public String tilesTex(){
        return Assets.Environment.TILES_PRISON;
    }

    @Override
    public String waterTex(){
        return Assets.Environment.WATER_PRISON;
    }

    protected void createMobs(){
        mobs.addAll(room.getSheep());
        mobs.addAll(room2.getSheep());

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
            MAP_START[p.pos] = P;
            setPressurePad(p, p.pos);
        }

        for(PressurePad p : room2.getPressurePads()){
            MAP_START[p.pos] = P;
            setPressurePad(p, p.pos);
        }
    }

    protected void createItems(){
        super.createItems();

        if(interResetData.get(0) == 0){
            drop(new PotionOfExperience(), 10 + 26 * width());
            drop(reward, 10 + 26 * width());
        }
    }

    public void collectItem(Item item){
        if(item == reward){
            interResetData.set(0, 1);
        }
    }

    public void setupInterResetData(){
        interResetData.add(0);
    }
}