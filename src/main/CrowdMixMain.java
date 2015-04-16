import TextUI.TextUIEngine;

/**
 * Created by arundudani on 16/04/2015.
 *
 *
 */
public class CrowdMixMain {
    public static void main(String[] args) {
        TextUIEngine textUIEngine = new TextUIEngine();
        try {
            textUIEngine.startEngine();
        } catch(Exception e) {
            System.out.println("Exception in text ui engine");
            e.printStackTrace();
        }
    }
}
