package jonathan;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/** Starts the JavaFX GUI for Jonathan. */
public class Main extends Application {
    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/jonathan/images/darthVader.png"));
    private Image yodaImage = new Image(this.getClass().getResourceAsStream("/jonathan/images/yoda.png"));

    private Jonathan jonathan = new Jonathan("data/jonathan.txt");

    @Override
    public void start(Stage stage) {

        scrollPane = new ScrollPane();
        dialogContainer = new VBox();
        scrollPane.setContent(dialogContainer);
        userInput = new TextField();
        sendButton = new Button("Send");

        sendButton.setOnMouseClicked((event) -> {
            handleUserInput();
        });
        userInput.setOnAction((event) -> {
            handleUserInput();
        });

        dialogContainer.heightProperty().addListener((observable) -> scrollPane.setVvalue(1.0));


        AnchorPane mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);

        stage.setTitle("Jonathan");
        stage.setResizable(false);
        stage.setMinHeight(600.0);
        stage.setMinWidth(400.0);

        mainLayout.setPrefSize(400.0, 600.0);

        scrollPane.setPrefSize(385, 535);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        scrollPane.setVvalue(1.0);
        scrollPane.setFitToWidth(true);

        dialogContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);
        dialogContainer.setSpacing(10.0);
        dialogContainer.setFillWidth(true);

        userInput.setPrefWidth(325.0);
        userInput.setPromptText("Enter a command...");

        sendButton.setPrefWidth(55.0);
        sendButton.setDefaultButton(true);

        AnchorPane.setTopAnchor(scrollPane, 1.0);
        AnchorPane.setBottomAnchor(sendButton, 1.0);
        AnchorPane.setRightAnchor(sendButton, 1.0);

        AnchorPane.setLeftAnchor(userInput, 1.0);
        AnchorPane.setBottomAnchor(userInput, 1.0);

        Scene scene = new Scene(mainLayout); // Setting the scene to be our Label
        stage.setScene(scene); // Setting the stage to show our scene
        stage.show(); // Render the stage.
        userInput.requestFocus();
        dialogContainer.getChildren().add(
                DialogBox.getJonathanDialog(
                        "Hello! I'm Jonathan. What can I do for you?",
                        yodaImage));


    }

    /**
     * Creates a dialog box containing user input, and appends it to
     * the dialog container. Clears the user input after processing.
     */
    private void handleUserInput() {
        String userText = userInput.getText().trim();
        if (userText.isEmpty()) {
            return;
        }
        String jonathanText = jonathan.getResponse(userText);

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(userText, userImage),
                DialogBox.getJonathanDialog(jonathanText, yodaImage)
        );

        userInput.clear();
        if (userText.equalsIgnoreCase("bye")) {
            Platform.exit();
        }
    }



}
