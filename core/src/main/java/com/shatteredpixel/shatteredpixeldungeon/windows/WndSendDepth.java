package com.shatteredpixel.shatteredpixeldungeon.windows;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.TimekeepersHourglass;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.plants.Swiftthistle;
import com.shatteredpixel.shatteredpixeldungeon.scenes.InterlevelScene;
import com.shatteredpixel.shatteredpixeldungeon.ui.OptionSlider;
import com.shatteredpixel.shatteredpixeldungeon.ui.RedButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.watabou.noosa.Game;

public class WndSendDepth extends Window {

    static int depthToSend = 1;

    private static final int SLIDER_HEIGHT = 24;
    private static final int BUTTON_HEIGHT = 20;
    private static final int WIDTH = 140;

    public WndSendDepth(){
        final RedButton sendToDepth = new RedButton(Messages.get(this, "send", depthToSend)){
            protected void onClick(){
                Buff buff = Dungeon.hero.buff(TimekeepersHourglass.TimeFreeze.class);
                if(buff != null) buff.detach();
                buff = Dungeon.hero.buff(Swiftthistle.TimeBubble.class);
                if (buff != null) buff.detach();

                InterlevelScene.mode = InterlevelScene.Mode.RETURN;
                InterlevelScene.returnDepth = depthToSend;
                InterlevelScene.returnPos = -1;
                Game.switchScene(InterlevelScene.class);
            }
        };

        OptionSlider depthScale = new OptionSlider(Messages.get(this, "depth"), "Floor 1", "Floor 30", 1, 30){
            protected void onChange(){
                depthToSend = getSelectedValue();
                sendToDepth.text(Messages.get(WndSendDepth.class, "send", depthToSend));
            }
        };
        depthScale.setSelectedValue(1);
        depthScale.setRect(0, 0, WIDTH, SLIDER_HEIGHT);
        add(depthScale);

        sendToDepth.setRect(0, SLIDER_HEIGHT + 10, WIDTH, BUTTON_HEIGHT);
        add(sendToDepth);
        resize(WIDTH, SLIDER_HEIGHT + 10 + BUTTON_HEIGHT);
    }
}