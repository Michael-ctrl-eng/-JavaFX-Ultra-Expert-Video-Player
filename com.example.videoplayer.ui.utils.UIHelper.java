package com.example.videoplayer.ui.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

/**
 * Helper class to display user interface alerts
 */
public class UIHelper {
    /**
     * Method to display alert to user
     * @param title String title of the dialog
     * @param header String header of the dialog
     * @param message String message of the dialog
     */
    public static void showAlert(String title, String header, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
         alert.showAndWait();
    }

     /**
      * Method to display confirmation dialog
      * @param title String title of the dialog
      * @param header String header of the dialog
      * @param message String message of the dialog
      * @return boolean value if the user confirms.
     */
    public static boolean showConfirmation(String title, String header, String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);

       Optional<ButtonType> result = alert.showAndWait();
       return result.isPresent() && result.get() == ButtonType.OK;
    }
}
