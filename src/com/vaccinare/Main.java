package com.vaccinare;

import java.util.concurrent.Semaphore;

class Masina extends Thread {

    public static Integer test = new Integer(5);

    public static synchronized void inscriereInPlatforma(String threadName) {
        System.out.println(threadName + ": Autentificare in sistem");
        System.out.println(threadName + ": Inscriere vaccin");
        System.out.println(threadName + ": Salvare date");
        System.out.println(threadName + ": Delogare");
    }

    @Override
    public void run() {
        try {
            Main.semaphore.acquire(); // busy waiting
            System.out.println("Masina " + this.getName() + " a intrat in zona de vaccinare");
            System.out.println(this.getName() + ": Mai sunt disponibile + " + Main.semaphore.availablePermits() + " locuri");
//            System.out.println("Masina " + this.getName() + " a intrat in zona de vaccinare. Mai sunt disponibile + " + Main.semaphore.availablePermits() + " locuri");
//            Masina.inscriereInPlatforma(this.getName());
            synchronized (Masina.class) {
                System.out.println(this.getName() + ": Autentificare in sistem");
                System.out.println(this.getName() + ": Inscriere vaccin");
                System.out.println(this.getName() + ": Salvare date");
                System.out.println(this.getName() + ": Delogare");
            }
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
        System.out.println("Masina " + this.getName() + " s-a vaccinat");
        Main.semaphore.release();
    }
}

public class Main {

    public static Semaphore semaphore;

    public static void main(String[] args) {
        int noTents = 3;
        int noCars = 25;
        semaphore = new Semaphore(noTents);

        Masina[] masini = new Masina[noCars];
        for (int i = 0; i < noCars; i++) {
            masini[i] = new Masina();
        }
        for (int i = 0; i < noCars; i++) {
            masini[i].start();
        }


    }
}
