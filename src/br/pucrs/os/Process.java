/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.pucrs.os;

import java.util.ArrayList;

/**
 *
 * @author 10109144
 */
public class Process {
    private String id;
    private int processorTime,arrivalTime,status,waitingTime,blockedTime;
    private boolean executed,blocked;
    private ArrayList<Device> ioList;

    public Process(String id,int processorTime,int arrivalTime){
        status = processorTime;
        this.processorTime = processorTime;
        this.id = id;
        this.arrivalTime = arrivalTime;
        blockedTime = 0;
        executed = false;
        ioList = new ArrayList<Device>(processorTime);
        for(int i = 0; i < processorTime; i++)
            ioList.add(null);
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public boolean isExecuted() {
        return executed;
    }
    //reinicia o processo
    public void restartProcess(){
        executed = false;
        status = processorTime;
        waitingTime = 0;
        blockedTime = 0;
    }
    
    public String getId() {
        return id;
    }
 
    public int getProcessorTime() {
        return processorTime;
    }

    public void setProcessorTime(int processorTime) {
        this.processorTime = processorTime;
    }

    public ArrayList<Device> getIoList() {
        return ioList;
    }

    public int getStatus() {
        return status;
    }
    //executa um tick do processo
    public void execute() {
        this.status--;
        if (status == 0)
            executed = true;
    }

    public double getTurnaroundTime(){
        return waitingTime + processorTime;
    }

    public double getWaitingTime(){
        return waitingTime;
    }
    //aumenta em um tick a espera do processo
    void incWait() {
        waitingTime++;
    }
    //aumenta em um tick a espera do processo
    void incBlockedTime() {
        blockedTime++;
    }

    public int getBlockedTime() {
        return blockedTime;
    }

    public void block() {
        this.blocked = true;
    }
    
    public void unBlock() {
        this.blocked = false;
    }    

    @Override
    public String toString(){
        return "Id: " + id + " Status: " + status;
    };

    public boolean isBlocked() {
        return blocked == true;
    }



    
}
