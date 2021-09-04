package unsw.loopmania;

import java.io.File;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.control.ListView;

public class FileSelector extends ListView<String> {
    
    public void setDirectory(File file) {
        if (file.isDirectory()) {
            for (File selectedFile: file.listFiles()) {
                if (!selectedFile.isDirectory()) {
                    this.getItems().add(selectedFile.getName());
                }
            }
        }
    }

    public ReadOnlyObjectProperty<String> getSelectedProperty() {
        return this.getSelectionModel().selectedItemProperty();
    }
}
