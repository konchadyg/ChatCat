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
public class BuddyList {
    
    String[] uname,hname;
    int[] id,port;
    int num;
    
    BuddyList()
    {
        
       
    }
    BuddyList(BuddyList bl)
    {
       // for(int i=0;i<num;i++)
        {
        this.uname=bl.uname;
        this.hname=bl.hname;
        this.id=bl.id;
        this.port=bl.port;
        }
    }
    
    
    
    
}
