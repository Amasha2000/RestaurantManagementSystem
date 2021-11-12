package util;

import com.jfoenix.controls.JFXButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class Validation {
    public static Object validate(LinkedHashMap<TextField, Pattern> map, JFXButton btn) {
        btn.setDisable(true);
        for (TextField textFieldKey : map.keySet()) {
            Pattern patternValue = map.get(textFieldKey);
            if (!patternValue.matcher(textFieldKey.getText()).matches()) {
                if (!textFieldKey.getText().isEmpty()) {
                    textFieldKey.getParent().setStyle("-fx-border-color: red;"+"-fx-border-width:2;"+"-fx-border-radius:8;");
                    ((AnchorPane) textFieldKey.getParent()).getChildren().get(1).setStyle("-fx-text-fill: red;"+"-fx-background-color: white;");
                }
                return textFieldKey;
            }
            textFieldKey.getParent().setStyle("-fx-border-color: green;"+"-fx-border-width:2;"+"-fx-border-radius:8;");
            ((AnchorPane) textFieldKey.getParent()).getChildren().get(1).setStyle("-fx-text-fill: green;"+"-fx-background-color: white;");
        }
        btn.setDisable(false);
        return true;
    }
}
