/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.pucrs.os;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author 10109144
 */
public class Device {

    private String id;
    private int responseTime, remaingTime;
    private LinkedList<Process> processes;

    public Device(String id, int responseTime) {
        this.id = id;
        this.responseTime = responseTime;
        remaingTime = responseTime;
        processes = new LinkedList<Process>();
    }

    public String getId() {
        return id;
    }

    public LinkedList<Process> getProcesses() {
        return processes;
    }

    public void addProcess(Process process) {
        processes.addLast(process);
    }

    public int getResponseTime() {
        return responseTime;
    }

    public int getRemaingTime() {
        return remaingTime;
    }

    public void increaseBlockedTime() {
        for (Process p : processes) {
            p.incBlockedTime();
        }
    }

    //atualiza processos, e quando possui algum que terminou, retorna para o usuario o processo
    public Process updateProcess() {
        if (processes.size() > 0) {
            remaingTime--;
            if (remaingTime == 0) {
                remaingTime = responseTime;
                Process p = processes.poll();
                p.unBlock();
                return p;
            }
        }
        return null;
    }
}
