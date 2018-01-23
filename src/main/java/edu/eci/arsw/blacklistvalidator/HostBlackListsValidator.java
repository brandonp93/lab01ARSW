/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;
import edu.eci.arsw.threads.IpMaliciosasThread;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hcadavid
 */
public class HostBlackListsValidator {

    private static final int BLACK_LIST_ALARM_COUNT=5;
    
    /**
     * Check the given host's IP address in all the available black lists,
     * and report it as NOT Trustworthy when such IP was reported in at least
     * BLACK_LIST_ALARM_COUNT lists, or as Trustworthy in any other case.
     * The search is not exhaustive: When the number of occurrences is equal to
     * BLACK_LIST_ALARM_COUNT, the search is finished, the host reported as
     * NOT Trustworthy, and the list of the five blacklists returned.
     * @param ipaddress suspicious host's IP address.
     * @param n
     * @return  Blacklists numbers where the given host's IP address was found.
     * @throws java.lang.InterruptedException
     */
    public List<Integer> checkHost(String ipaddress, int n) throws InterruptedException{
        
                
        LinkedList<Integer> blackListOcurrences=new LinkedList<>();
        ArrayList<IpMaliciosasThread> hilos = new ArrayList<>();
        
        int ocurrencesCount=0;        
        
        HostBlacklistsDataSourceFacade skds=HostBlacklistsDataSourceFacade.getInstance();
        
        int checkedListsCount=0;        
        
        int rango=skds.getRegisteredServersCount()/n;        
        
        for(int i=0; i<n;i+=rango){
            IpMaliciosasThread hilo = new IpMaliciosasThread(i,i+rango,ipaddress);            
            hilos.add(hilo);            
        }
        
        for(IpMaliciosasThread h: hilos){
            h.start();
        }        
        
        for(IpMaliciosasThread h: hilos){
            h.join();
            ocurrencesCount += h.getOcurrencias();
            
        }                                                                              
        
        if (ocurrencesCount>=BLACK_LIST_ALARM_COUNT){
            skds.reportAsNotTrustworthy(ipaddress);
        }
        else{
            skds.reportAsTrustworthy(ipaddress);
        }                
        
        System.out.println(ocurrencesCount);
        
        LOG.log(Level.INFO, "Checked Black Lists:{0} of {1}", new Object[]{checkedListsCount, skds.getRegisteredServersCount()});
        
        return blackListOcurrences;
    }
    
    
    private static final Logger LOG = Logger.getLogger(HostBlackListsValidator.class.getName());
    
    
    
}
