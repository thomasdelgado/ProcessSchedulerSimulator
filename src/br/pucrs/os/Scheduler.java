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
public class Scheduler {

    private int tick;
    private ArrayList<Process> processes = new ArrayList<Process>();
    private ArrayList<Process> ready = new ArrayList<Process>();
    private ArrayList<Device> devices = new ArrayList<Device>();

    public void setProcesses(ArrayList<Process> processes) {
        if (processes != null) {
            this.processes = (ArrayList<Process>) processes.clone();
        } else {
            this.processes = new ArrayList<Process>();
        }
        ready = new ArrayList<Process>();
        tick = 0;
        setDevices(new ArrayList<Device>());
    }

    public void setDevices(ArrayList<Device> devices) {
        if (devices != null) {
            this.devices = (ArrayList<Device>) devices.clone();
        } else {
            this.devices = new ArrayList<Device>();
        }
//        this.processes.get(0).getIoList().add(2, this.devices.get(2));
//        this.processes.get(1).getIoList().add(5, this.devices.get(0));
//        this.processes.get(2).getIoList().add(3, this.devices.get(2));
//        this.processes.get(3).getIoList().add(4, this.devices.get(2));
    }
    
    
    //Retorna apenas os processos que não foram executados.
    public int getProcessesCount() {
        int result = 0;
        for (Process p : processes) {
            if (!p.isExecuted() || p.isBlocked()) {
                result++;
            }
        }
        return result;
    }
    
    //reinicia a fila de processos 
    public void restartProcesses() {
        tick = 0;
        ready = new ArrayList<Process>();
        for (Process p : processes) {
            p.restartProcess();
        }
    }

    public ArrayList<Process> getProcesses() {
        return processes;
    }
    
    public Process getProcess(String id){
        for(Process p : processes){
            if(p.getId().equals(id))
                return p;
        }
        return null;
    }

    public Device getDevice(String id){
        for(Device d : devices){
            if(d.getId().equals(id))
                return d;
        }
        return null;
    }
    
    public ArrayList<Process> getReadyProcesses() {
        return ready;
    }

    public ArrayList<Device> getDevices() {
        return devices;
    }
    
    //calculo do turnaround
    public double getTunaroundTimeAVG() {
        double total = 0;
        for (Process p : processes) {
            total += p.getTurnaroundTime();
        }
        return total / processes.size();
    }
    
    //incrementa um tick
    public Process nextTick() {
        tick++;
        //Atualiza IO
        for(Device device : devices){
            device.increaseBlockedTime();
            Process p = device.updateProcess();
            if (p != null)
                if (!p.isExecuted())
                    ready.add(p);
        }
        return null;
    }

    public int getTick() {
        return tick;
    }
    
    public void checkForIO(Process p){
        if (p != null){
            int position = p.getProcessorTime() - p.getStatus() - 1;
            if(p.getIoList().get(position) != null){
                Device device = p.getIoList().get(position);
                ready.remove(p);
                device.addProcess(p);
                p.block();
            }
        }     
        
    }
    
    //atualiza fila de processos executados
    public void setReadyProcess() {
        //Remove executed process
        Process checkProcess = null;
        for (int x = ready.size() - 1; x >= 0; x--) {
            checkProcess = ready.get(x);
            if (checkProcess.isExecuted()) {
                ready.remove(checkProcess);
            }
        }
        //add new processes
        for (Process p : processes) {
            if ((p.getArrivalTime() <= tick) && (!p.isExecuted()) && (!p.isBlocked()) ) {
                if (!this.ready.contains(p)) {
                    this.ready.add(p);
                }
            }
        }
    }
    
    //incrementa a espera de todos os processos da fila ready, tirando o processo enviado por parametro
    public void waitProcesses(Process atual) {
        for (Process p : ready) {
            if (atual != p) {
                p.incWait();
            }
        }
    }

    //Adicionar device
    public void addDevice(Device device){
        devices.add(device);
    }

    public Device getWhereProcessIs(Process p) {
        for(Device device : devices){
            for(Process process : device.getProcesses()){
                if(process == p)
                    return device;
            }
        }
        return null;
    }
}
