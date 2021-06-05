package com.shatteredpixel.shatteredpixeldungeon.levels;

import com.shatteredpixel.shatteredpixeldungeon.Bones;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.AnyPushablePuzzleSheep;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.HorizontalPuzzleSheep;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Sheep;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.VerticalPuzzleSheep;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.levels.puzzles.PressurePad;
import com.shatteredpixel.shatteredpixeldungeon.levels.puzzles.PuzzleRoom;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.FleecingTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.ResettingTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.Trap;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.watabou.utils.SparseArray;

import java.util.ArrayList;

public class PuzzleLevel extends Level{

    protected boolean usesHardCodedPressurePads = true;
    protected ArrayList<PuzzleRoom> rooms = new ArrayList<>();

    int[] pressurePadStages = {0, 1, 1, 2, 2, 2, 2, 3, 3, 4, 4};
    int[] pressurePadColours = {0, 0, 0, 0, 0, 0, 0, 1, 1, 2, 2};
    ArrayList<PressurePad> pads = new ArrayList<>();
    int[] stageDoors = {0, 1, 3, 4, 2};
    int[] padsPerStage = {1, 2, 4, 2, 2};
    Trap[] traps = {new ResettingTrap().reveal(), new ResettingTrap().reveal(), new ResettingTrap().reveal(), new FleecingTrap().reveal()};
    int trapCounter = 0;
    ArrayList<Integer> stageDoorsPos = new ArrayList<>();

    {
        viewDistance = 12;
    }

    @Override
    protected boolean build(){
        setSize(21, 32);

        map = MAP_START.clone();

        buildFlagMaps();
        cleanWalls();
        setup();

        for(int i = 0; i < pressurePadStages.length; i++){
            PressurePad p = new PressurePad().setColour(pressurePadColours[i]).setStage(pressurePadStages[i]);
            pads.add(p);
        }

        for(int f = 0; f < MAP_START.length; f++){
            checkTile(f);
        }

        return true;
    }

    protected void setup(){
    }

    protected void checkTile(int f){
        if(MAP_START[f] == X) exit = f;
        if(MAP_START[f] == E) entrance = f;
        if(MAP_START[f] == P && usesHardCodedPressurePads) setPressurePad(pads.get(pressurePads.size), f);
        if(MAP_START[f] == L) stageDoorsPos.add(f);
        if(MAP_START[f] == T){
            setTrap(traps[trapCounter], f);
            trapCounter++;
        }
    }

    public PressurePad setPressurePad(PressurePad pad, int pos){
        PressurePad existingPad = pressurePads.get(pos);
        if(existingPad != null){
            pressurePads.remove(pos);
        }
        pad.set(pos);
        pressurePads.put(pos, pad);
        GameScene.updateMap(pos);
        return pad;
    }

    public void turn(){
        super.turn();
        boolean pressurePadsUpdated = false;
        for(PressurePad p : pressurePads.valueList()){
            if(!p.finished){
                p.pressed = false;
                pressurePadsUpdated = true;
                GameScene.updateMap(p.pos);
            }
        }

        for(Object a : mobs.toArray()){
            Mob mob = (Mob)a;
            PressurePad p = pressurePads.get(mob.pos);
            if(p != null && mob instanceof Sheep){
                if(!p.pressed && !p.finished){
                    p.pressed = true;
                    pressurePadsUpdated = true;
                }
                GameScene.updateMap(p.pos);
            }
        }
        if(pressurePads.get(Dungeon.hero.pos) != null){
            PressurePad p = pressurePads.get(Dungeon.hero.pos);
            if(!p.pressed && !p.finished){
                p.pressed = true;
                pressurePadsUpdated = true;
            }
            GameScene.updateMap(p.pos);
        }

        if(pressurePadsUpdated){
            SparseArray<Integer> pressesPerStage = new SparseArray<>();
            //Check how many pads are pressed per stage
            for(PressurePad p : pressurePads.valueList()){
                if(p.pressed){
                    pressesPerStage.put(p.stage, pressesPerStage.get(p.stage) != null ? pressesPerStage.get(p.stage) + 1 : 1);
                }
            }
            for(int x = 0; x < padsPerStage.length; x++){
                if((pressesPerStage.get(x) != null ? pressesPerStage.get(x) : 0) >= padsPerStage[x]){
                    //Change pressure pads to green
                    for(PressurePad p : pressurePads.valueList()){
                        if(p.stage == x){
                            p.setColour(PressurePad.COMPLETE);
                            p.finished = true;
                            p.pressed = true;
                            GameScene.updateMap(p.pos);
                        }
                    }
                    //Unlock doors
                    for(int i = 0; i < stageDoorsPos.size(); i++){
                        if(stageDoors[i] == x){
                            Level.set(stageDoorsPos.get(i), Terrain.DOOR);
                            GameScene.updateMap(stageDoorsPos.get(i));
                        }
                    }
                }
            }
        }
    }

    @Override
    public Mob createMob(){
        return null;
    }

    @Override
    protected void createMobs(){
        int[] sheepPositions = {10 + 11 * width, 4 + 15 * width, 10 + 15 * width, 16 + 15 * width, 7 + 19 * width, 13 + 19 * width, 10 + 19 * width};
        Sheep[] puzzleSheep = {new HorizontalPuzzleSheep(), new HorizontalPuzzleSheep(), new HorizontalPuzzleSheep(), new HorizontalPuzzleSheep(), new AnyPushablePuzzleSheep(), new AnyPushablePuzzleSheep(), new VerticalPuzzleSheep()};

        for(int i = 0; i < puzzleSheep.length; i++){
            Sheep sh = puzzleSheep[i];
            sh.pos = sheepPositions[i];
            mobs.add(sh);
        }

        for(Mob m : mobs){
            if(map[m.pos] == Terrain.HIGH_GRASS){
                map[m.pos] = Terrain.GRASS;
                losBlocking[m.pos] = false;
            }
        }
    }

    @Override
    protected void createItems() {
        Item item = Bones.get();
        if (item != null) {
            int pos;
            do {
                pos = randomRespawnCell(null);
            } while (pos == entrance);
            drop( item, pos ).setHauntedIfCursed().type = Heap.Type.REMAINS;
        }
    }

    protected static final int W = Terrain.WALL;
    protected static final int D = Terrain.DOOR;
    protected static final int L = Terrain.LOCKED_DOOR;
    protected static final int e = Terrain.EMPTY;
    protected static final int p = Terrain.PEDESTAL;

    protected static final int P = Terrain.PRESSUREPAD;

    protected static final int E = Terrain.ENTRANCE;
    protected static final int X = Terrain.EXIT;

    protected static final int M = Terrain.WALL_DECO;
    protected static final int S = Terrain.STATUE;
    protected static final int T = Terrain.TRAP;

    //TODO hardcodedmaphelper
    protected int[] MAP_START = {
            W, W, W, W, W, W, W, W, W, W, M, W, W, W, W, W, W, W, W, W, W,
            W, W, W, W, W, W, W, W, W, S, e, S, W, W, W, W, W, W, W, W, W,
            W, W, W, W, W, W, W, W, W, e, E, e, W, W, W, W, W, W, W, W, W,
            W, W, W, W, W, W, W, W, W, e, e, e, W, W, W, W, W, W, W, W, W,
            W, W, W, W, W, W, W, W, W, e, e, e, W, W, W, W, W, W, W, W, W,
            W, W, W, W, W, W, W, W, W, M, D, M, W, W, W, W, W, W, W, W, W,
            W, W, W, W, W, W, W, W, W, e, e, T, W, W, W, W, W, W, W, W, W,
            W, W, W, W, W, W, W, W, W, e, P, e, W, W, W, W, W, W, W, W, W,
            W, W, W, W, W, W, W, W, W, e, e, e, W, W, W, W, W, W, W, W, W,
            W, W, W, W, W, W, W, W, W, M, L, M, W, W, W, W, W, W, W, W, W,
            W, W, W, W, W, W, e, e, e, e, e, e, e, e, T, W, W, W, W, W, W,
            W, W, W, W, W, W, e, e, P, e, e, e, P, e, e, W, W, W, W, W, W,
            W, W, W, W, W, W, e, e, e, e, e, e, e, e, e, W, W, W, W, W, W,
            W, W, W, M, W, W, W, W, W, M, L, M, W, W, W, W, W, M, W, W, W,
            W, W, e, e, e, e, W, e, e, e, e, e, e, T, W, e, e, e, e, W, W,
            W, W, e, P, e, e, L, e, e, P, e, P, e, e, L, e, e, P, e, W, W,
            W, W, e, e, e, e, W, e, e, e, e, e, e, e, W, e, e, e, e, W, W,
            W, W, W, W, W, W, W, W, D, W, e, W, D, W, W, W, W, W, W, W, W,
            W, W, W, W, W, e, e, e, e, W, e, W, e, e, e, e, W, W, W, W, W,
            W, W, W, W, W, e, P, e, e, W, e, W, e, e, P, e, W, W, W, W, W,
            W, W, W, W, W, e, e, P, e, W, T, W, e, P, e, e, W, W, W, W, W,
            W, W, W, W, W, e, e, e, e, W, e, W, e, e, e, e, W, W, W, W, W,
            W, W, W, W, W, W, W, W, W, W, L, W, W, W, W, W, W, W, W, W, W,
            W, W, W, W, W, W, W, W, S, e, e, e, S, W, W, W, W, W, W, W, W,
            W, W, W, W, W, W, W, W, e, e, e, e, e, W, W, W, W, W, W, W, W,
            W, W, W, W, W, W, W, W, e, e, X, e, e, W, W, W, W, W, W, W, W,
            W, W, W, W, W, W, W, W, e, e, e, e, e, W, W, W, W, W, W, W, W,
            W, W, W, W, W, W, W, W, S, e, e, e, S, W, W, W, W, W, W, W, W,
            W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
            W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
            W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
            W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W};
}
