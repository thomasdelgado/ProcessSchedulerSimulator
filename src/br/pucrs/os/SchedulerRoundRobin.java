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
public class SchedulerRoundRobin extends Scheduler {
    private int quantum,cursorQuantum,cursorReady;
    Process lastExecuted = null;
    
    public SchedulerRoundRobin(int quantum){
        super();
        this.quantum = quantum;
        cursorQuantum = 1;
        cursorReady = -1;
    }
    //escolhe o processo e executa um tick e espera o restante
    @Override
    public Process nextTick() {
        Process atual = getNext();
        if (atual != null) {
            atual.execute();
            super.waitProcesses(atual);
        }
        super.nextTick();
        super.checkForIO(atual);
        return atual;
    }
    
    //escolhe o processo conforme a regra do roundrobin
    //cursorQuantum = contador para executar 5vzs
    //lastExecuted ultimo processo executado
    public Process getNext() {
        super.setReadyProcess();
        ArrayList<Process> pList = super.getReadyProcesses();
        // se não executou a quantidade de quantum e ainda está na fila de ready, executa
        if ( (cursorQuantum < quantum) && (pList.contains(lastExecuted)) ){
            cursorQuantum++;
            return lastExecuted;
        } else { 
            //reinicia e escolhe outro processo
            cursorQuantum = 1;
            cursorReady++;
            if(cursorReady + 1 > pList.size())
                cursorReady = 0;
            if (pList.size() == 0)
                lastExecuted = null;
            else
                lastExecuted = pList.get(cursorReady);
            return lastExecuted;
        }    

    }

    public void setQuantum(int quantum) {
        this.quantum = quantum;
    }
    
    
    

}
