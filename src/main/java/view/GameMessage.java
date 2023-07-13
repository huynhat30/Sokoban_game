package view;

import javax.swing.JOptionPane;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.Effect;
import javafx.scene.effect.MotionBlur;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 * Show message of game: victory, level finished, warning....
 * @author Huy Nhat Giang
 *
 */
public class GameMessage{
	/**
	 * current stage of game
	 */
	private Stage primaryStage;
	
	/**
     * private Object instance (apply Singleton pattern)
     */
    private static GameMessage instance;

	/**
	 * private constructor of class(apply Singleton pattern)
	 */
	private GameMessage() {
		super();
	}
	
	/**
	 * other class can access to Object instance
	 * @return instance of Object
	 */
	public static GameMessage getInstance(){
        if(instance == null){
            synchronized(GameMessage.class){
                if(instance == null){
                   instance = new GameMessage();
                }
            }
        }
        return instance;
     }
	
	/**
	 *show message after finish 1 level
	 */
	public void levelCompleteMessage() {
		JOptionPane.showMessageDialog(null, "Level complete\n");
	}
	
	/**
	 * show winning message after finish all levels
	 */
	public void showVictoryMessage() {
        String dialogTitle = "Game Over!";
        String dialogMessage = "Congratulation!! You complete the Game";
        MotionBlur mb = new MotionBlur(2, 3);
        newDialog(dialogTitle, dialogMessage, mb);
    }
	
	
	/**
	 * create a window to show message
	 * @param dialogTitle    Title of message
	 * @param dialogMessage  Content of message
	 * @param dialogMessageEffect Effect of message
	 */
	public void newDialog(String dialogTitle, String dialogMessage, Effect dialogMessageEffect) {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        dialog.setResizable(false);
        dialog.setTitle(dialogTitle);

        Text text1 = new Text(dialogMessage);
        text1.setTextAlignment(TextAlignment.CENTER);
        text1.setFont(javafx.scene.text.Font.font(14));

        if (dialogMessageEffect != null) {
            text1.setEffect(dialogMessageEffect);
        }

        VBox dialogVbox = new VBox(20);
        dialogVbox.setAlignment(Pos.CENTER);
        dialogVbox.setBackground(Background.EMPTY);
        dialogVbox.getChildren().add(text1);

        Scene dialogScene = new Scene(dialogVbox, 350, 150);
        dialog.setScene(dialogScene);
        dialog.show();
	}
	
}
