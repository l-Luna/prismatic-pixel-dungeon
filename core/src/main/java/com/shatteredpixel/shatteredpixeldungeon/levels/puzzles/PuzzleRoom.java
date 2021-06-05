package com.shatteredpixel.shatteredpixeldungeon.levels.puzzles;

import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Sheep;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.Trap;
import com.watabou.utils.SparseArray;

import java.util.ArrayList;

public class PuzzleRoom{

    ArrayList<Sheep> sheep = new ArrayList<>();
    ArrayList<PressurePad> pressurePads = new ArrayList<>();
    ArrayList<Trap> traps = new ArrayList<>();

    public void setSheep(ArrayList<Sheep> sheep){
        this.sheep = sheep;
    }
    public void setPressurePads(ArrayList<PressurePad> pressurePads){
        this.pressurePads = pressurePads;
    }
    public void setTraps(ArrayList<Trap> traps){
        this.traps = traps;
    }

    public ArrayList<Sheep> getSheep(){
        return sheep;
    }
    public ArrayList<PressurePad> getPressurePads(){
        return pressurePads;
    }
    public ArrayList<Trap> getTraps(){
        return traps;
    }

    public SparseArray<PressurePad> getPressurePadsAsSparseArray(){
        SparseArray<PressurePad> p = new SparseArray<>();
        for(PressurePad pad : pressurePads){
            p.put(pad.pos, pad);
        }
        return p;
    }

    public SparseArray<Trap> getTrapsAsSparseArray(){
        SparseArray<Trap> p = new SparseArray<>();
        for(Trap trap : traps){
            p.put(trap.pos, trap);
        }
        return p;
    }
}