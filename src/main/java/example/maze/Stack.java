package example.maze;

public class Stack<E> {
    private StackFrame<E> head;

    public Stack() {
        this.head = null;
    }

    public boolean isEmpty() {
        return this.head == null;
    }

    public void push(E element) {
        this.head = new StackFrame<>(element, this.head);
    }

    public E pop() {
        StackFrame<E> obsolete = this.head;
        this.head = obsolete.previous;
        E ret = obsolete.element;
        obsolete.element = null;
        obsolete.previous = null;
        return ret;
    }

    public E peek() {
        return this.head == null ? null : this.head.element;
    }

    private static class StackFrame<E> {
        private E element;
        private StackFrame<E> previous;

        public StackFrame(E element, StackFrame<E> previous) {
            this.element = element;
            this.previous = previous;
        }
    }
}
