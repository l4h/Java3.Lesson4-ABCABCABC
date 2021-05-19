import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    static char nextLetter='A';
    private static Object lock = new Object();
    public static void main(String[] args) {

        ExecutorService es = Executors.newFixedThreadPool(3);
        es.submit(()->{
            for (int i = 0; i < 5; i++) {
                printLetter('A');
            }
        });
        es.submit(()->{
            for (int i = 0; i < 5; i++) {
                printLetter('B');
            }
        });
        es.submit(()->{
            for (int i = 0; i < 5; i++) {
                printLetter('C');
            }
        });


        es.shutdown();


    }

    static void printLetter(char letter){
        synchronized (lock){
            while (letter!=nextLetter){
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.print(letter);
            if(letter=='C'){
                nextLetter='A';
            } else {
                nextLetter++;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.notifyAll();
        }
    }

}
