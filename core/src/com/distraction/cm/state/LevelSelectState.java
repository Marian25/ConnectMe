package com.distraction.cm.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.distraction.cm.CM;
import com.distraction.cm.game.Header;
import com.distraction.cm.util.ClickListener;
import com.distraction.cm.util.LevelItem;

import java.util.ArrayList;
import java.util.List;

public class LevelSelectState extends State {

    private Header header;

    private List<LevelItem> list;
    private OrthographicCamera listCam;
    private float listCamSpeed;

    private int selectedLevel = -1;
    private float my;
    private boolean dragged;
    private float scrollFriction = 35;
    private float scrollMultiplier = 1.2f;

    private float dry;
    private boolean useDry;
    private float[] vely = new float[3];
    private int velyi;

    public LevelSelectState(GSM gsm){

        super(gsm);

        header = new Header();
        header.setTitle("Level Select");
        header.setRefreshVisible(false);
        header.setBackClizkListener(new ClickListener() {
            @Override
            public void onClick() {
                Gdx.app.exit();
            }
        });

        list = new ArrayList<LevelItem>();
        for(int i = 0; i < 200; i++){
            LevelItem item = new LevelItem("Level " + (i + 1), (int)(Math.random() * 5));
            item.setIndex(i);
            list.add(item);
        }
        listCam = new OrthographicCamera();
        listCam.setToOrtho(false, cam.viewportWidth, cam.viewportHeight);

        selectedLevel = -1;

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void update(float dt) {

        if(selectedLevel > 0){
            PlayState newState = new PlayState(gsm, selectedLevel, list.get(selectedLevel - 1).getStars());
            CheckeredTransitionState state = new CheckeredTransitionState(gsm, this, newState);
            gsm.set(state);
            return;
        }

        if(listCamSpeed > 0) {
            listCamSpeed -= scrollFriction * dt;
            if (listCamSpeed < 0) {
                listCamSpeed = 0;
            }
        } else if(listCamSpeed < 0){
            listCamSpeed += scrollFriction * dt;
            if(listCamSpeed > 0){
                listCamSpeed = 0;
            }
        }
        listCam.position.y += listCamSpeed;

        if(listCam.position.y > CM.HEIGHT / 2){
            listCam.position.y = CM.HEIGHT / 2;
            listCamSpeed = 0;
        }
        if(listCam.position.y < CM.HEIGHT / 2 - (list.size() * LevelItem.HEIGHT - CM.HEIGHT + Header.HEIGHT)) {
            listCam.position.y = CM.HEIGHT / 2 - (list.size() * LevelItem.HEIGHT - CM.HEIGHT + Header.HEIGHT);
            listCamSpeed = 0;
        }
        listCam.update();
    }

    @Override
    public void render(SpriteBatch sb) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sb.begin();

        sb.setProjectionMatrix(listCam.combined);
        int start = (int)((-listCam.position.y + listCam.viewportHeight / 2 + Header.HEIGHT) / LevelItem.HEIGHT) - 1;
        int end = (int)(start + (listCam.viewportHeight / LevelItem.HEIGHT)) + 1;
        if(start < 0){
            start = 0;
        }
        if(end > list.size()){
            end = list.size();
        }
        for (int i = start; i < end; i++){
            list.get(i).render(sb);
        }
        sb.setProjectionMatrix(cam.combined);
        header.render(sb);

        sb.end();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(listCamSpeed < -10 || listCamSpeed > 10){
            dragged = true;
        }
        listCamSpeed = 0;
        for (int i = 0; i < vely.length; i++){
            vely[i] = 0;
        }
        unproject(m, cam);
        header.click(m.x, m.y);
        my = m.y;
        useDry = true;
        dry = m.y;
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        unproject(m, listCam);
        if(!dragged){
            m.y -= CM.HEIGHT;
            m.y -= Header.HEIGHT;
            if(m.y < 0) selectedLevel = (int) -(m.y / LevelItem.HEIGHT) + 1;
        }
        dragged = false;
        useDry = false;
        float ave = 0;
        for (float aVely : vely) {
            ave += aVely;
        }
        ave /= vely.length;
        listCamSpeed += -ave * scrollMultiplier;
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        unproject(m, cam);
        float diff = m.y - my;
        if(diff < -10 || diff > 10){
            dragged = true;
        }
        if(useDry){
            vely[velyi] = m.y - dry;
            listCam.position.y -= vely[velyi];
            velyi++;
            if(velyi == vely.length){
                velyi = 0;
            }
            listCam.update();
            dry = m.y;
        }
        return true;
    }
}
