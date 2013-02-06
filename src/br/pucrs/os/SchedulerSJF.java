/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.pucrs.os;

import java.util.ArrayList;
import br.pucrs.os.Process;

/**
 *
 * @author 10109144
 */
public class SchedulerSJF extends Scheduler {
    
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

    //escolhe o procsso com menor status
    public Process getNext() {
        super.setReadyProcess();
        ArrayList<Process> lista = super.getReadyProcesses();
        int value = Integer.MAX_VALUE;
        Process result = null;
        for (Process p : lista) {
            if (p.getStatus() < value) {
                result = p;
                value = p.getStatus();
            }

        }
        return result;
    }


}
