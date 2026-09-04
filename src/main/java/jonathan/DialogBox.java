package jonathan;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/** Represents a message and its associated character image in the GUI. */
public class DialogBox extends HBox {

    private Label text;
    private ImageView displayPicture;

    /**
     * Creates a dialog box containing the specified message and image.
     *
     * @param s message to display
     * @param i image to display beside the message
     */
    public DialogBox(String s, Image i) {
        text = new Label(s);
        displayPicture = new ImageView(i);

        text.setWrapText(true);
        text.setMaxWidth(250.0);
        displayPicture.setPreserveRatio(true);
        displayPicture.setFitWidth(100.0);
        displayPicture.setFitHeight(100.0);
        this.setSpacing(10.0);
        this.setAlignment(Pos.TOP_RIGHT);
        this.getChildren().addAll(text, displayPicture);
    }

    /**
     * Moves the image to the left and the text to the right.
     */
    private void flip() {
        this.setAlignment(Pos.TOP_LEFT);

        ObservableList<Node> reversedChildren =
                FXCollections.observableArrayList(this.getChildren());

        FXCollections.reverse(reversedChildren);
        this.getChildren().setAll(reversedChildren);
    }

    /**
     * Creates a dialog box for user input.
     *
     * @param message the user's message
     * @param image the user's image
     * @return a user dialog box
     */
    public static DialogBox getUserDialog(String message, Image image) {
        return new DialogBox(message, image);
    }

    /**
     * Creates a dialog box for Jonathan's response.
     *
     * @param message Jonathan's response
     * @param image Jonathan's image
     * @return a Jonathan dialog box
     */
    public static DialogBox getJonathanDialog(String message, Image image) {
        DialogBox dialogBox = new DialogBox(message, image);
        dialogBox.flip();
        return dialogBox;
    }
}
