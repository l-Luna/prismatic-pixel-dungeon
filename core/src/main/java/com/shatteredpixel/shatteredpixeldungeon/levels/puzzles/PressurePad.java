package com.shatteredpixel.shatteredpixeldungeon.levels.puzzles;

import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;

public class PressurePad implements Bundlable {

    public static final int RED         = 0;
    public static final int YELLOW      = 1;
    public static final int BLUE        = 2;
    public static final int COMPLETE    = 3;

    public int colour = 0;
    public int pos;
    public int stage = 0;
    public boolean pressed = false;
    public boolean finished = false;

    private static final String POS	       = "pos";
    private static final String STAGE	   = "stage";
    private static final String PRESSED    = "pressed";
    private static final String FINISHED   = "finished";
    private static final String COLOUR     = "colour";

    public PressurePad set(int pos){
        this.pos = pos;
        return this;
    }

    public PressurePad setStage(int stage){
        this.stage = stage;
        return this;
    }

    public PressurePad setColour(int colour){
        this.colour = colour;
        return this;
    }

    @Override
    public void restoreFromBundle(Bundle bundle){
        pos = bundle.getInt(POS);
        stage = bundle.getInt(STAGE);
        pressed = bundle.getBoolean(PRESSED);
        finished = bundle.getBoolean(FINISHED);
        colour = bundle.getInt(COLOUR);
    }

    @Override
    public void storeInBundle( Bundle bundle ){
        bundle.put(POS, pos);
        bundle.put(STAGE, stage);
        bundle.put(PRESSED, pressed);
        bundle.put(FINISHED, finished);
        bundle.put(COLOUR, colour);
    }

    public String desc() {
        return Messages.get(this, "desc");
    }
}