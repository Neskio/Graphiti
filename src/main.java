import javax.swing.*;
import java.awt.*;

public class main{

    public static void main(String args[]) throws Exception {
        //Handler handler = new Handler();

        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get (key);
            if (value != null && value instanceof javax.swing.plaf.FontUIResource)
                UIManager.put (key, new javax.swing.plaf.FontUIResource("Segoe UI",Font.TRUETYPE_FONT ,12));
        }

        UIManager.put("PopupMenu.border", BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(204,204,204)));

        Handler handler = new Handler();
    }



}
