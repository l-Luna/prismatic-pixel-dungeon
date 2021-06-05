package com.shatteredpixel.shatteredpixeldungeon.levels;

import com.shatteredpixel.shatteredpixeldungeon.Assets;

public class SewerPuzzleLevel extends PuzzleLevel{

    {
        color1 = 0x48763c;
        color2 = 0x59994a;
    }

    @Override
    public String tilesTex(){
        return Assets.Environment.TILES_SEWERS;
    }

    @Override
    public String waterTex(){
        return Assets.Environment.WATER_SEWERS;
    }

    protected void setup(){
        int[] pressurePadStages = {0, 1, 1, 2, 2, 2, 2, 3, 3, 4, 4};
        int[] pressurePadColours = {0, 0, 0, 0, 0, 0, 0, 1, 1, 2, 2};
        int[] stageDoors = {0, 1, 3, 4, 2};
        int[] padsPerStage = {1, 2, 4, 2, 2};
        this.pressurePadStages = pressurePadStages;
        this.pressurePadColours = pressurePadColours;
        this.stageDoors = stageDoors;
        this.padsPerStage = padsPerStage;
    }
}