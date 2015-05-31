import javax.swing.*;
import java.awt.*;


public class MySeparator extends JSeparator{

    Color myGray = new Color(247,247,247);

    MySeparator(){
        setOpaque(true);
        setBackground(myGray);
        setForeground(Color.LIGHT_GRAY);
    }

}
