/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;
import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author 2108419
 */
public class IpMaliciosasThread extends Thread{

    int a;
    int b;    
    int checkedListsCount = 0;
    String ip;
    boolean flag = true;
    int busquedas = 0;
    HostBlacklistsDataSourceFacade skds=HostBlacklistsDataSourceFacade.getInstance();
    LinkedList<Integer> blackListOcurrences;
    AtomicInteger ocurrencias;
   
    public IpMaliciosasThread(int a, int b, String ip, LinkedList<Integer> blackListOcurrences, AtomicInteger ocurrencias){
        this.a = a;
        this.b = b;
        this.ip = ip;      
        this.blackListOcurrences = blackListOcurrences;
        this.ocurrencias = ocurrencias;
    }
   
    public void run(){
        while (flag){
            for (int i=a;i<b ;i++){            
                if (skds.isInBlackListServer(i, ip)){
                    blackListOcurrences.add(i);
                    ocurrencias.getAndIncrement();
                }
                if (ocurrencias.get() == 5){
                    break;
                }
                busquedas++;
                checkedListsCount++;
            }          
            flag = false;   
        }
    }

    public AtomicInteger getOcurrencias() {
        return ocurrencias;
    }
    
    
    public int getCheckedListsCount(){
        return checkedListsCount;
    }
    
    public LinkedList<Integer> getBlackListOcurrence(){
        return blackListOcurrences;
    }

    
}
