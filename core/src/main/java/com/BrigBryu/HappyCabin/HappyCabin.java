package com.BrigBryu.HappyCabin;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.List;

import static jdk.jfr.internal.instrument.JDKEvents.remove;

public class HappyCabin extends ApplicationAdapter {
    private Stage stage;
    private Skin skin;
    private SpriteBatch batch;
    final private int SCREEN_WIDTH =  1280;
    final private int SCREEN_HEIGHT = 960;
    final private int HEIGHT_OF_LABEL = 42;
    final float X_MENU_OFFSET = 50;
    final float Y_MENU_OFFSET = 20;
    final private int BOTTOM_OF_PICTURE_AREA = 400;//SCREEN_HEIGHT/3;
    final float TOP_OF_MENU = BOTTOM_OF_PICTURE_AREA - HEIGHT_OF_LABEL - Y_MENU_OFFSET;
    private Texture treeLoadingScreen;

    //game variables
    private boolean renderStartScreen = true;

    @Override
    public void create() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        //textures
        treeLoadingScreen = new Texture(Gdx.files.internal("pictures/treeAndMountain.jpg"));
        batch = new SpriteBatch();
        // Load the skin
        skin = new Skin(Gdx.files.internal("simpleUISkin.json"));

//Welcome to Happy Cabin. This is my first time building anything that has much of a user interface. I hope you enjoy it! <3
        Label titleLabel = new Label("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", skin); //42 height
        System.out.println(titleLabel.getHeight());
        titleLabel.setPosition(X_MENU_OFFSET, TOP_OF_MENU);

        // Create a button using the skin
        TextButton startButton = new TextButton("Start Game", skin);
        startButton.setPosition(X_MENU_OFFSET, TOP_OF_MENU - HEIGHT_OF_LABEL - Y_MENU_OFFSET);

        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                renderStartScreen = false;
                stage.getActors().removeValue(titleLabel, true);
                stage.getActors().removeValue(startButton, true);
            }
        });

        stage.addActor(titleLabel);
        stage.addActor(startButton);
    }

    @Override
    public void render() {
        // Clear the screen
        ScreenUtils.clear(0.109f, 0.166f, 0.209f, 1f);
        batch.begin();
        if(renderStartScreen) {
            batch.draw(treeLoadingScreen, (SCREEN_WIDTH - treeLoadingScreen.getWidth()) / 2, BOTTOM_OF_PICTURE_AREA);
        }
        batch.end();
        // Render the stage
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        // Dispose of resources
        stage.dispose();
        skin.dispose();
    }

    public void presentLabels(List<String> messages){

    }

    public List<String> makeLabelText(String message){
        String[] words = message.split(" ");
        final int maxCharacters = 80;
        int sum = 0;
        int lastWordToInclude = -1;
        for(int i = 0; i < words.length; i++) {
            sum += words[i].length();
            if(sum > maxCharacters) {
                lastWordToInclude = i - 1;
                break;
            }
        }

        if(lastWordToInclude == -1) { //No overflow
//            ArrayList<DynamicLabel> list = new ArrayList<>();
//            list.add(new DynamicLabel(message, skin));
            ArrayList<String> list = new ArrayList<>();
            list.add(message);
            return list;
        } else { //Has overflow
            StringBuilder shortenedMessage = new StringBuilder();
            StringBuilder leftOverMessage = new StringBuilder();
            ArrayList<String> list = new ArrayList<>();
            for(int i = 0; i < message.length(); i++) {
                if(i < lastWordToInclude) {
                    shortenedMessage.append(words[i]);
                } else {
                    leftOverMessage.append(words[i]);
                }
            }
            list.add(shortenedMessage.toString());
            //list.add(new DynamicLabel(shortenedMessage.toString(), skin));
            list.addAll(makeLabelText(leftOverMessage.toString()));
            return list;
        }
    }
}
