/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.threads;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;
import java.util.LinkedList;

/**
 *
 * @author 2108419
 */
public class IpMaliciosasThread extends Thread{
    
    int ocurrencias = 0;
    int a;
    int b;    
    int checkedListsCount = 0;
    String ip;
    
    HostBlacklistsDataSourceFacade skds=HostBlacklistsDataSourceFacade.getInstance();
    LinkedList<Integer> blackListOcurrences;
    
    public IpMaliciosasThread(int a, int b, String ip, LinkedList<Integer> blackListOcurrences){
        this.a = a;
        this.b = b;
        this.ip = ip;      
        this.blackListOcurrences = blackListOcurrences;
    }
    
    public void run(){
        for (int i=a;i<b ;i++){            
            checkedListsCount++;            
            if (skds.isInBlackListServer(i, ip)){ 
                System.out.println(i);
                blackListOcurrences.add(i);                
                ocurrencias++;                
            }
        }               
    }
    
    public int getOcurrencias(){
        return ocurrencias;
    }
    
    public int getCheckedListsCount(){
        return checkedListsCount;
    }
    
    public LinkedList<Integer> getBlackListOcurrence(){
        return blackListOcurrences;
    }
    
    
}
