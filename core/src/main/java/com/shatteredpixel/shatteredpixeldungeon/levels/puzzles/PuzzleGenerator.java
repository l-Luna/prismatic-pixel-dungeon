package com.shatteredpixel.shatteredpixeldungeon.levels.puzzles;

import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.AnyPushablePuzzleSheep;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.HorizontalPuzzleSheep;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Sheep;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.VerticalPuzzleSheep;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class PuzzleGenerator{

    public static PuzzleRoom generatePuzzleRoom(int width, int height, int padnumber, int levelwidth, int xpos, int ypos, int stage, int[] colours, Level level){
        PuzzleRoom room = new PuzzleRoom();
        ArrayList<PressurePad> pads = new ArrayList<>();
        ArrayList<Sheep> sheep = new ArrayList<>();
        for(int i = 0; i < padnumber/2; i++){
            int pos;
            int wiidth;
            int heiight;
            do{
                wiidth = Random.Int(width);
                heiight = Random.Int(height);
                pos = (xpos + wiidth) + (heiight+ypos)*levelwidth;
            }while(padsAtAxis(pads, pos, levelwidth) > 0 || isPadAtPosition(pads, pos) || !level.passable[pos]);
            PressurePad p = new PressurePad();
            p.pos = pos;
            p.stage = stage;
            if(colours.length > i) p.colour = colours[i];
            pads.add(p);

            generateSingleSheep(sheep, width, height, wiidth, heiight, level, xpos, ypos);
        }
        for(int i = 0; i < padnumber/2; i++){
            int pos;
            int wiidth;
            int heiight;
            do{
                wiidth = Random.Int(width);
                heiight = Random.Int(height);
                pos = (xpos + wiidth) + (heiight+ypos)*levelwidth;
            }while(isPadAtPosition(pads, pos) || !(padsAtAxis(pads, pos, levelwidth) > 0) || !level.passable[pos]);
            PressurePad p = new PressurePad();
            p.pos = pos;
            p.stage = stage;
            if(colours.length > i+padnumber/2) p.colour = colours[i];
            pads.add(p);

            generateSingleSheep(sheep, width, height, wiidth, heiight, level, xpos, ypos);
        }
        room.setPressurePads(pads);
        room.setSheep(sheep);

        return room;
    }

    private static void generateSingleSheep(ArrayList<Sheep> sheep, int width, int height, int wiidth, int heiight, Level level, int xpos, int ypos){
        int pos;
        int sheepDir = Random.Int(3);
        int tries = 0;
        if(sheepDir == 0){
            HorizontalPuzzleSheep sh = new HorizontalPuzzleSheep();
            do{
                int shWiidth = Random.Int(width);
                pos = (xpos + shWiidth) + (heiight+ypos)*level.width();
                tries++;
            }while(isSheepAtPosition(sheep, pos) || isPositionInvalidForHorizontalSheep(pos, level, sheep) && tries < 1000);
            if(tries > 1000) sh.pos = (xpos + wiidth) + (heiight*level.width());
            sh.pos = pos;
            sheep.add(sh);
        }else if(sheepDir == 1){
            VerticalPuzzleSheep sh = new VerticalPuzzleSheep();
            do{
                int shHeiight = Random.Int(height);
                pos = (xpos + wiidth) + (shHeiight+ypos)*level.width();
                tries++;
            }while(isSheepAtPosition(sheep, pos) || isPositionInvalidForVerticalSheep(pos, level, sheep) && tries < 1000);
            if(tries > 1000) sh.pos = (xpos + wiidth) + (heiight*level.width());
            sh.pos = pos;
            sheep.add(sh);
        }else{
            AnyPushablePuzzleSheep sh = new AnyPushablePuzzleSheep();
            do{
                int shWiidth = Random.Int(width);
                int shHeiight = Random.Int(height);
                pos = (xpos + shWiidth) + (shHeiight+ypos)*level.width();
                tries++;
            }while(isSheepAtPosition(sheep, pos) || isPositionInvalidForAnyPushSheep(pos, level, sheep) && tries < 1000);
            if(tries > 1000) sh.pos = (xpos + wiidth) + (heiight*level.width());
            sh.pos = pos;
            sheep.add(sh);
        }
    }

    private static boolean isPositionPassable(int pos, Level level, ArrayList<Sheep> sheep){
        return level.passable[pos] && !isSheepAtPosition(sheep, pos);
    }

    private static boolean isPositionInvalidForVerticalSheep(int pos, Level level, ArrayList<Sheep> sheep){
        if(!isPositionPassable(pos+level.width(), level, sheep) || !isPositionPassable(pos-level.width(), level, sheep)){
            return true;
        }
        return false;
    }

    private static boolean isPositionInvalidForAnyPushSheep(int pos, Level level, ArrayList<Sheep> sheep){
        int i = 0;
        int[] NEIGHBOURS4 = {1, -1, level.width(), -level.width()}; //Have to duplicate this, used during levelgen.
        for(int f : NEIGHBOURS4){
            if(!isPositionPassable(pos+f, level, sheep)) i++;
        }
        return i >= 2;
    }

    private static boolean isPositionInvalidForHorizontalSheep(int pos, Level level, ArrayList<Sheep> sheep){
        if(!isPositionPassable(pos+1, level, sheep) || !isPositionPassable(pos-1, level, sheep)){
            return true;
        }
        return false;
    }

    private static boolean isPadAtPosition(ArrayList<PressurePad> pads, int pos){
        boolean toRet = false;
        for(PressurePad pad : pads){
            if(pad.pos == pos) toRet = true;
        }
        return toRet;
    }

    private static int padsAtAxis(ArrayList<PressurePad> pads, int pos, int levelwidth){
        int toRet = 0;
        for(PressurePad pad : pads){
            if((pad.pos%levelwidth) == (pos%levelwidth)) toRet++;
            if((pad.pos/levelwidth) == (pos/levelwidth)) toRet++;
        }
        return toRet;
    }

    private static int padsAtXAxis(ArrayList<PressurePad> pads, int pos, int levelwidth){
        int toRet = 0;
        for(PressurePad pad : pads){
            if((pad.pos%levelwidth) == (pos%levelwidth)) toRet++;
        }
        return toRet;
    }

    private static int padsAtYAxis(ArrayList<PressurePad> pads, int pos, int levelwidth){
        int toRet = 0;
        for(PressurePad pad : pads){
            if((pad.pos/levelwidth) == (pos/levelwidth)) toRet++;
        }
        return toRet;
    }

    private static boolean isSheepAtPosition(ArrayList<Sheep> sheep, int pos){
        boolean toRet = false;
        for(Sheep sh : sheep){
            if(sh.pos == pos) toRet = true;
        }
        return toRet;
    }
}