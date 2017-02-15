package quant.test.server.validator;

import com.jfoenix.validation.base.ValidatorBase;
import javafx.beans.DefaultProperty;
import javafx.scene.control.TextInputControl;

/**
 * Created by cz on 2017/2/15.
 */
@DefaultProperty(value="icon")
public class AdbPathValidator extends ValidatorBase {
    final String path;
    public AdbPathValidator() {
        String fileSeparator=System.getProperty("file.separator");
        path="android"+fileSeparator+"sdk"+fileSeparator+"platform-tools";
    }
    /**
     * {@inheritDoc}
     */
    @Override
    protected void eval() {
        if(srcControl.get() instanceof TextInputControl){
            evalTextInputField();
        }
    }

    protected void evalTextInputField(){
        TextInputControl textField = (TextInputControl) srcControl.get();
        if(null!=textField){
            hasErrors.set(!textField.getText().contains(path));
        }
    }
}
