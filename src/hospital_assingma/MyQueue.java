package hospital_assingma;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


public class MyQueue {
    private Queue<patient> queue;

    public MyQueue() {
        this.queue = new LinkedList<>();
    }

    public void enqueue(patient element) {
        queue.add(element);
    }

    public patient dequeue() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public int size() {
        return queue.size();
    }

    public void displayQueue() {
        System.out.println("Elements in the queue: " + queue);
    }

    public Queue<patient> getQueue() {
        return queue;
    }
    public void removePatient(String name) {
        ArrayList<patient> patientsToRemove = new ArrayList<>();

        for (patient patient : queue) {
            if (name.equals(patient.getName())) {
                patientsToRemove.add(patient);
            }
        }

        queue.removeAll(patientsToRemove);
    }


    public void addPatient(int position, patient newPatient) {
        if (position >= 0 && position <= queue.size()) {
            LinkedList<patient> tempList = new LinkedList<>(queue);
            tempList.add(position, newPatient);
            queue = new LinkedList<>(tempList);
        } else {
            System.out.println("Invalid position to add patient.");
        }
    }
}
