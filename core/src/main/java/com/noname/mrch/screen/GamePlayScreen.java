package com.noname.mrch.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.noname.mrch.GameWorld;

public class GamePlayScreen implements Screen {
    private GameWorld gameWorld;

    public GamePlayScreen(){
        gameWorld = new GameWorld();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        gameWorld.getCurrentRoom().act();
        gameWorld.getCurrentRoom().draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
