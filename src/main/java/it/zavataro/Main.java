package it.zavataro;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Socket mySocket;
            mySocket = new Socket("localhost", 3000);
            BufferedReader in = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
            DataOutputStream out = new DataOutputStream(mySocket.getOutputStream());
            Scanner sc = new Scanner(System.in);
            while (true) {
                System.out.println("Inserisci uno Username : ");
                String username = sc.nextLine();
                out.writeBytes(username + "\n");
                if (in.readLine().equals("REG")) {
                    System.out.println("Username Disponibile");
                    break;
                } else {
                    System.out.println("Username non disponibile, selezionane un altro.");
                };
            }
            System.out.println("Connessione effettuata. Digita ESCI per uscire.");
            System.out.println("Inserisci la nota da memorizzare o digita LISTA per visualizzare le note salvate.");
            System.out.println("Per eliminare una nota digita CANCELLA e inserisci il contenuto della nota da eliminare.");
            do {
                String outputString = sc.nextLine();
                if (outputString.equals("CANCELLA")) {
                    out.writeBytes("#" + "\n");
                    System.out.println("Inserisci il valore della stringa : ");
                    out.writeBytes(sc.nextLine() + "\n");
                    if (in.readLine().equals("OK")) {
                        System.out.println("Valore rimosso");
                    } else {
                        System.out.println("Valore non presente nella lista");
                    }
                } else if (outputString.equals("ESCI")) {
                    out.writeBytes("!" + "\n");
                    System.out.println("USCITA...");
                    mySocket.close();
                    sc.close();
                    System.out.println("Disconnessione Effettuata");
                    break;
                } else if (outputString.equals("LISTA")) {
                    System.out.println("Ecco la tua lista : " + "\n");
                    out.writeBytes("?" + "\n");
                    while (true) {
                        String inputString = in.readLine();
                        if (!inputString.equals("@")) {
                            System.out.println(inputString);
                        } else {
                            break;
                        }
                    }
                    System.out.println("Adesso puoi continuare ad inserire altre note");
                } else {
                    out.writeBytes(outputString + "\n");
                    if (in.readLine().equals("OK")) {
                        System.out.println("Nota Salvata");
                    } else {
                        System.out.println("NOTA NON SALVATA");
                    }
                }
            } while(true);
        } catch (Exception e) { 
            System.out.println("ERRORE");
            e.printStackTrace();
        }
    }
}