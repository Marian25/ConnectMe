package com.distraction.cm.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.distraction.cm.game.Grid;
import com.distraction.cm.game.Header;
import com.distraction.cm.game.LevelData;
import com.distraction.cm.game.LevelFactory;
import com.distraction.cm.util.ClickListener;
import com.distraction.cm.util.Screenshot;

public class PlayState extends State {

    private final int DRAG_DEST = 50;

    private Grid grid;
    private Header header;

    private float startx;
    private float starty;

    public PlayState(final GSM gsm, int level, int stars){

        super(gsm);

        final LevelData data = LevelFactory.getLevel(level);
        System.out.println("created level " + level + " with " + stars + " stars");
        grid = new Grid(data.getGrid());

        header = new Header();
        header.setTitle("Level " + level);
        header.setStars(stars);
        header.setBackClizkListener(new ClickListener() {
            @Override
            public void onClick() {
                LevelSelectState nextState = new LevelSelectState(gsm);
                CheckeredTransitionState state = new CheckeredTransitionState(gsm, PlayState.this, nextState); // to write constructor with 3 parameters
                gsm.set(state);
            }
        });
        header.setRefreshClickListener(new ClickListener() {
            @Override
            public void onClick() {
                grid = new Grid(data.getGrid());
            }
        });

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void update(float dt) {
        grid.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sb.setProjectionMatrix(cam.combined);
        sb.begin();

        grid.render(sb);
        header.render(sb);

        sb.end();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        unproject(m, cam);
        grid.click(m.x, m.y);
        startx = m.x;
        starty = m.y;
        header.click(m.x, m.y);
        return true;
    }
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        unproject(m, cam);
        if(m.x > startx + DRAG_DEST){
            grid.move(1, 0);
        }
        else if(m.x < startx - DRAG_DEST){
            grid.move(-1, 0);
        }
        else if(m.y > starty + DRAG_DEST){
            grid.move(0, 1);
        }
        else if(m.y < starty - DRAG_DEST){
            grid.move(0, -1);
        }
        return true;
    }
}