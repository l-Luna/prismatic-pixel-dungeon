package com.shatteredpixel.shatteredpixeldungeon.levels;

import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.AnyPushablePuzzleSheep;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.HorizontalPuzzleSheep;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Sheep;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.VerticalPuzzleSheep;
import com.shatteredpixel.shatteredpixeldungeon.levels.puzzles.PressurePad;
import com.shatteredpixel.shatteredpixeldungeon.levels.puzzles.PuzzleRoom;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.FleecingTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.ResettingTrap;

public class HardcodedMapHelper{

    protected static final int W = Terrain.WALL;
    protected static final int D = Terrain.DOOR;
    protected static final int L = Terrain.LOCKED_DOOR;
    protected static final int e = Terrain.EMPTY;
    protected static final int w = Terrain.WATER;
    protected static final int p = Terrain.PEDESTAL;

    protected static final int P = Terrain.PRESSUREPAD;
    protected static final int o = Terrain.WELL;

    protected static final int E = Terrain.ENTRANCE;
    protected static final int X = Terrain.EXIT;

    protected static final int M = Terrain.WALL_DECO;
    protected static final int S = Terrain.STATUE;
    protected static final int T = Terrain.TRAP;

    public static int[] mapStringToMapIntArray(String f){
        int[] ret = new int[f.length()];
        int cha = 0;

        for(char c : f.toCharArray()){
            switch(c){
                case 'W':
                    ret[cha] = W;
                    break;
                case 'D':
                    ret[cha] = D;
                    break;
                case 'L':
                    ret[cha] = L;
                    break;
                case 'e':
                    ret[cha] = e;
                    break;
                case ' ':
                    ret[cha] = e;
                    break;
                case 'p':
                    ret[cha] = p;
                    break;
                case 'P':
                    ret[cha] = P;
                    break;
                case 'E':
                    ret[cha] = E;
                    break;
                case 'X':
                    ret[cha] = X;
                    break;
                case 'M':
                    ret[cha] = M;
                    break;
                case 'S':
                    ret[cha] = S;
                    break;
                case 'T':
                    ret[cha] = T;
                    break;
                case 'w':
                    ret[cha] = w;
                    break;
                default:
                    ret[cha] = e;
                    break;
            }
            cha++;
        }
        return ret;
    }

    public static PuzzleRoom mapStringToPuzzleRoom(String f, int stage){
        PuzzleRoom ret = new PuzzleRoom();
        int cha = 0;

        for(char c : f.toCharArray()){
            switch(c){
                case 'f':
                    ret.getTraps().add(new FleecingTrap().set(cha).reveal());
                    break;
                case 'd':
                    ret.getTraps().add(new ResettingTrap().set(cha).reveal());
                    break;
                case 'h':
                    Sheep shep = new HorizontalPuzzleSheep();
                    shep.pos = cha;
                    ret.getSheep().add(shep);
                    break;
                case 'v':
                    Sheep shep2 = new VerticalPuzzleSheep();
                    shep2.pos = cha;
                    ret.getSheep().add(shep2);
                    break;
                case 'a':
                    Sheep shep3 = new AnyPushablePuzzleSheep();
                    shep3.pos = cha;
                    ret.getSheep().add(shep3);
                    break;
                case 'b':
                    ret.getPressurePads().add(new PressurePad().setColour(PressurePad.BLUE).set(cha).setStage(stage));
                    break;
                case 'y':
                    ret.getPressurePads().add(new PressurePad().setColour(PressurePad.YELLOW).set(cha).setStage(stage));
                    break;
                case 'r':
                    ret.getPressurePads().add(new PressurePad().setColour(PressurePad.RED).set(cha).setStage(stage));
                    break;
                case 'c':
                    ret.getPressurePads().add(new PressurePad().setColour(PressurePad.COMPLETE).set(cha).setStage(stage));
                    break;
            }
            cha++;
        }

        return ret;
    }
}