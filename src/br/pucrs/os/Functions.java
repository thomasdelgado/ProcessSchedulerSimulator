/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.pucrs.os;

import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.JOptionPane;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author mac01-lexsislexsis
 */
public class Functions {

    static final String TAG_PROCESS = "[Process]";
    static final String TAG_DEVICES = "[Devices]";
    static final String TAG_IO = "[IO]";

    public static double log2(double value) {
        return Math.log(value) / Math.log(2);
    }

    public static ArrayList<Process> ReadProcess(String absolutePath) {
        String linha;
        ArrayList<Process> res = new ArrayList<Process>();
        try {
            BufferedReader buff = new BufferedReader(new FileReader(absolutePath));
            try {
                linha = buff.readLine();
                linha = buff.readLine();
                while (!linha.equals(TAG_DEVICES)) {
                    if (!linha.substring(0, 2).equals("//")){
                        String[] dados = linha.split(";");
                        Process p = new Process(dados[0],
                                Integer.parseInt(dados[1]),
                                Integer.parseInt(dados[2]));
                        res.add(p);
                    }    
                    linha = buff.readLine();
                }
            } finally {
                buff.close();
            }
        } catch (Exception e) {
            String erro = e.getMessage();
            JOptionPane.showMessageDialog(null, erro, "Aviso", 3);
            return null;
        }
        return res;
    }

    public static ArrayList<Device> ReadDevices(String absolutePath) {
        String linha;
        ArrayList<Device> res = new ArrayList<Device>();
        try {
            BufferedReader buff = new BufferedReader(new FileReader(absolutePath));
            try {
                linha = buff.readLine();
                while (!linha.equals(TAG_DEVICES)) {
                    linha = buff.readLine();
                }
                linha = buff.readLine();
                while (!linha.equals(TAG_IO)) {
                    String[] dados = linha.split(";");
                    Device d = new Device(dados[0],
                            Integer.parseInt(dados[1]));
                    res.add(d);
                    linha = buff.readLine();
                }
            } finally {
                buff.close();
            }
        } catch (Exception e) {
            String erro = e.getMessage();
            JOptionPane.showMessageDialog(null, erro, "Aviso", 3);
            return null;
        }
        return res;
    }

    public static void ReadIO(String absolutePath, Scheduler scheduler) {
        String linha;
        try {
            BufferedReader buff = new BufferedReader(new FileReader(absolutePath));
            try {
                linha = buff.readLine();
                while (!linha.equals(TAG_IO)) {
                    linha = buff.readLine();
                }
                linha = buff.readLine();
                Process p = null;
                while (linha != null) {
                    String[] dados = linha.split(";");
                    for(int i = 0; i < dados.length;i++){
                        if(i == 0){
                            String id = dados[i];
                            p = scheduler.getProcess(id);
                        } else {
                            if(i%2 == 0){
                                Integer index = Integer.parseInt(dados[i]);
                                Device dev = scheduler.getDevice(dados[i-1]);
                                if ((index >= 0) && (index < p.getIoList().size()) )
                                    p.getIoList().add(index, dev);
                            }
                                
                        }            
                      
                    }
                    linha = buff.readLine();
                }
            } finally {
                buff.close();
            }
        } catch (Exception e) {
            String erro = e.getMessage();
            JOptionPane.showMessageDialog(null, erro, "Aviso", 3);
        }
        //return res;
    }
    
    public static class TxtFilter extends javax.swing.filechooser.FileFilter {

        @Override
        public boolean accept(File file) {
            String filename = file.getName();
            return filename.endsWith(".txt");
        }

        @Override
        public String getDescription() {
            return "*.txt";
        }
    }

    static public int binToDec(String bin) {
        return Integer.parseInt(bin, 2);
    }

    static public void geraArquivo(int lines) {
        ArrayList<String> end = new ArrayList<String>();
        Random gen = new Random();
        String adr;
        for (int x = 0; x < lines; x++) {
            adr = "";
            for (int y = 0; y < 32; y++) {
                adr += "" + (gen.nextInt(2));
            }
            end.add(adr);
        }

        FileWriter arquivo;
        try {
            arquivo = new FileWriter(new File("Arquivo.txt"));
            for (int x = 0; x < lines; x++) {
                arquivo.write(end.get(x) + "\n");
            }
            arquivo.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
