package com.distraction.cm.state;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TestState extends State {

    public TestState(GSM gsm){
        super(gsm);
    }

    @Override
    public void update(float dt) {
        System.out.println("test state updating");
    }

    @Override
    public void render(SpriteBatch sb) {
        System.out.println("test state rendering");
    }
}
