package hospital_assingma;

import java.util.LinkedList;

public class MyStack {
    private LinkedList<patient> stack;

    public MyStack() {
        this.stack = new LinkedList<>();
    }

    public void push(patient element) {
        stack.push(element);
    }

    public patient pop() {
        return stack.pop();
    }

    public patient peek() {
        return stack.peek();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public int size() {
        return stack.size();
    }
    

    public LinkedList<patient> getStack() {
		return stack;
	}

	public void displayStack() {
        System.out.println("Elements in the stack: " + stack);
    }
}
