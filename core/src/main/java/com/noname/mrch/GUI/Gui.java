package com.noname.mrch.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.noname.mrch.GameWorld;
import com.noname.mrch.MurderSilentGame;
import com.noname.mrch.gameobject.GameActor;
import com.noname.mrch.gameobject.GameCharacter;
import com.noname.mrch.helper.AssetLoader;

/**
 * Contains all the gui elements. Requires a game world to interact with.
 */

public class Gui {
    private GameWorld gameWorld;

    private Stage stage;
    private Stack lowerScreenStack;

    private Table lowerGuiGroup;
    private GuiButton notebookButton;
    private GuiButton investigateButton;
    private GuiButton mapButton;

    private NoteBookWindow notebookWindow;
    private GuiWindow mapWindow;
    private GuiWindow infoWindow;

    private InteractionBox interactionBox;


    public Gui(AssetLoader assetLoader, final GameWorld gameWorld){
        this.gameWorld = gameWorld;
        Skin skin = assetLoader.skin;

        stage = new Stage(new FitViewport(MurderSilentGame.GAME_WIDTH, MurderSilentGame.GAME_HEIGHT));

        //stack containing all the lower screen gui actors
        lowerScreenStack = new Stack();
        lowerScreenStack.setSize(MurderSilentGame.GAME_WIDTH, MurderSilentGame.GAME_HEIGHT / 4);


        //notebook UI init
        notebookWindow = new NoteBookWindow(skin, this, gameWorld);
        notebookButton = new NoteBookButton(skin, this);

        //investigate UI init
        investigateButton = new InvestigateButton(skin, this);

        //map UI init
        mapWindow = new MapWindow(skin, this, gameWorld);
        mapButton = new MapButton(skin, this);

        lowerGuiGroup = new Table();
        lowerGuiGroup.align(Align.bottom);
        lowerGuiGroup.add(notebookButton);
        lowerGuiGroup.add(investigateButton).expandX();
        lowerGuiGroup.add(mapButton);

        //info window init
        infoWindow = new InfoWindow(skin, this, gameWorld);

        //interaction box init
        interactionBox = new InteractionBox(skin, this, gameWorld);
        interactionBox.background(skin.getDrawable("default-round"));
        interactionBox.setVisible(false);


        lowerScreenStack.add(lowerGuiGroup);
        lowerScreenStack.add(interactionBox);

        stage.addActor(lowerScreenStack);
    }


    /**
     * Show a pop up window that displays an actor and a string below.
     * @param actor Actor to show.
     * @param info String to be added below.
     */
    public void displayInfo(GameActor actor, String info){
        infoWindow.getContentTable().clearChildren();
        infoWindow.getContentTable().add(actor).row();
        infoWindow.text(info);
        infoWindow.show(stage);
    }

    /**
     * Closes interaction box and stop interaction.
     */
    public void haltInteraction(){
        interactionBox.setVisible(false);
        lowerGuiGroup.setTouchable(Touchable.enabled);

        if (interactionBox.getInteraction().getInteractingCharacter() != null) {
            interactionBox.getInteraction().getInteractingCharacter().setTouchable(Touchable.enabled);
        }
    }

    /**
     * Opens interaction box and start interaction.
     * @param character Game character to start interaction with.
     */
    public void startInteraction(GameCharacter character){
        interactionBox.getInteraction().setInteractingCharacter(character);
        interactionBox.setVisible(true);
        lowerGuiGroup.setTouchable(Touchable.disabled);

        character.setTouchable(Touchable.disabled);
    }

    public Stage getStage(){
        return stage;
    }

    public GameWorld getGameWorld() {
        return gameWorld;
    }

    InteractionBox getInteractionBox(){
        return interactionBox;
    }

    public NoteBookWindow getNotebookWindow() {
        return notebookWindow;
    }

    public GuiWindow getMapWindow() {
        return mapWindow;
    }
}
