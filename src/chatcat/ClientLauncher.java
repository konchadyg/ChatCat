/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatcat;

/**
 *
 * @author konch
 */
public class ClientLauncher {
    
    public static void main (String[] args)
    {
        String[] s1 = new String[3];
        s1[0]="1225";
        s1[1]="1226";
        s1[2]=" <CLIENT-1>";
        ChatCat1.main(s1);
        
        String[] s2 = new String[3];
        s2[0]="1226";
        s2[1]="1225";
        s2[2]=" <CLIENT-2>";
        ChatCat11.main(s2);
    }
}
