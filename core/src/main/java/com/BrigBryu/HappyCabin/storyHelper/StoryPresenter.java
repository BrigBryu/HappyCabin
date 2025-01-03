package com.BrigBryu.HappyCabin.storyHelper;

import com.BrigBryu.HappyCabin.HappyCabin;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

/**
 * Presents current message: Label and options:TextButton
 * When a member of options is clicked QuestionNode = QuestionNode.getOption(String optionWanted)
 */
public class StoryPresenter implements Screen {
    private HappyCabin game;
    private Stage stage;
    private QuestionNode currentQuestion;
    private Map<String, AdventurePair> adventureInfo;
    private Texture texture;
    SpriteBatch batch;

    // constants :
    protected final float X_MENU_OFFSET = 50;
    protected final float Y_MENU_OFFSET = 20;
    protected final int HEIGHT_OF_LABEL = 42;
    protected final int BOTTOM_OF_PICTURE_AREA = 400;
    protected final float TOP_OF_MENU = BOTTOM_OF_PICTURE_AREA - HEIGHT_OF_LABEL - Y_MENU_OFFSET;
    public static final String RETURN_TO_CABIN_KEY = "return to cabin and end this adventure";

    public StoryPresenter(HappyCabin game, Stage stage, Texture texture, QuestionNode startNode){
        this.game = game;
        this.texture = texture;
        this.stage = stage;
        currentQuestion = startNode;
    }

    public StoryPresenter(HappyCabin game, Stage stage, SpriteBatch batch, Texture texture){
        this.game = game;
        this.stage = stage;
        this.texture =texture;
        this.batch = batch;
        initializeTestDefault();
    }

    private void initializeTestDefault() {
        // Create root question node
        QuestionNode root = new QuestionNode("Start", "Welcome to the Happy Cabin! What would you like to do today?");

        // Create primary child nodes
        QuestionNode exploreCabin = new QuestionNode("Explore the cabin", "You decide to go into the cabin. It's cozy and warm. What do you explore first?");
        QuestionNode exploreOutside = new QuestionNode("Explore outside the cabin", "You step outside into the fresh air. What do you notice?");
        QuestionNode returnCabin = new QuestionNode(RETURN_TO_CABIN_KEY, "You return to the cabin. Adventure ends.");

        // Set parent relationships
        exploreCabin.setParent(root);
        exploreOutside.setParent(root);
        returnCabin.setParent(root);

        // Add children to root
        root.addChild(exploreCabin);
        root.addChild(exploreOutside);
        //root.addChild(returnCabin);

        // Add children to "Explore the cabin"
        QuestionNode sitFireplace = new QuestionNode("Sit by the fireplace", "You sit by the fireplace and feel relaxed.");
        QuestionNode keepExploringCabin = new QuestionNode("Keep exploring", "You keep exploring and find a well stocked kitchen. Looks like you could do some fun baking here...");
        QuestionNode returnFromCabin = new QuestionNode(RETURN_TO_CABIN_KEY, "You decide to return to the cabin.");

        sitFireplace.setParent(exploreCabin);
        keepExploringCabin.setParent(exploreCabin);
        returnFromCabin.setParent(exploreCabin);

        exploreCabin.addChild(sitFireplace);
        exploreCabin.addChild(keepExploringCabin);
        exploreCabin.addChild(exploreOutside);

        // Add children to "Explore outside the cabin"
        QuestionNode seeFirewood = new QuestionNode("See a pile of firewood", "You notice a neatly stacked pile of firewood ready for use.");
        QuestionNode seeSquirrels = new QuestionNode("See two squirrels", "You watch as two playful squirrels chase each other around a tree.");
        QuestionNode talkToPerson = new QuestionNode("Talk to a cool person", "Bridger: Bridger: Hi Grace <3 I think this cabin and the property is perfect!");
        QuestionNode returnFromOutside = new QuestionNode(RETURN_TO_CABIN_KEY, "You return to the cabin.");

        seeFirewood.setParent(exploreOutside);
        seeSquirrels.setParent(exploreOutside);
        talkToPerson.setParent(exploreOutside);
        returnFromOutside.setParent(exploreCabin);

        exploreOutside.addChild(seeFirewood);
        exploreOutside.addChild(seeSquirrels);
        exploreOutside.addChild(talkToPerson);
        exploreOutside.addChild(returnFromOutside);

        // Optionally, add further depth to "Talk to a person"
        QuestionNode personResponse = new QuestionNode("Respond positively", "Grace: Yes, I think it will be wonderful stay :)");
        personResponse.setParent(talkToPerson);
        talkToPerson.addChild(personResponse);
        personResponse.setParent(exploreOutside);

        // Set the current question to root
        currentQuestion = root;

        // Update the UI to reflect the root question
        updateUI("startNode");
    }


    private void updateUI(String choice){
        if(!choice.equals("startNode")) {
            currentQuestion = currentQuestion.getOption(choice);
        } else if(choice.equals(RETURN_TO_CABIN_KEY)) {
            goToCabin();
        }
        String message = currentQuestion.message;
        LinkedList<String> options = currentQuestion.getOptions();

        Label titleLabel = new Label(message, game.getSkin());
        titleLabel.setPosition(X_MENU_OFFSET, TOP_OF_MENU);
        stage.addActor(titleLabel);

        ArrayList<TextButton> buttonList = new ArrayList<>();
        for (int i = 0; i < options.size(); i++) {
            TextButton button = new TextButton(options.get(i), game.getSkin());
            button.setPosition(
                X_MENU_OFFSET,
                TOP_OF_MENU - ((Y_MENU_OFFSET + HEIGHT_OF_LABEL) * (i + 1))
            );
            stage.addActor(button);
            buttonList.add(button);

            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Remove question label and all buttons
                    stage.getActors().removeValue(titleLabel, true);
                    for (TextButton btn : buttonList) {
                        stage.getActors().removeValue(btn, true);
                    }
                    updateUI(button.getText().toString());
                }
            });
        }
    }

    private void goToCabin(){
        //TODO
    }
    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     */
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.109f, 0.166f, 0.209f, 1f);
        batch.begin();
        batch.draw(texture, (HappyCabin.SCREEN_WIDTH - texture.getWidth()) / 2, BOTTOM_OF_PICTURE_AREA);
        batch.end();

        // Don’t forget stage if you want UI:
        stage.act(delta);
        stage.draw();
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

    /**
     * Called when this screen is no longer the current screen for a {@link Game}.
     */
    @Override
    public void hide() {

    }

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {

    }
}
