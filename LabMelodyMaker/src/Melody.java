import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Melody {
    private Queue<Note> notes;
    private Queue<Note> tempQueue;

    public Melody(Queue<Note> song) {
        this.notes = new LinkedList<>();
        int size = song.size();
        for (int i = 0; i < size; i++) {
            notes.offer(song.peek());
            song.offer(song.poll());
        }
    }

    public double getTotalDuration() {
        double length = 0.0;
        boolean repeat = false;
        int size = notes.size();
        for (int i = 0; i < size; i++) {
            Note note = notes.peek();
            if (!repeat) {
                if (note.isRepeat()) {
                    repeat = true;
                    tempQueue = new LinkedList<>();
                    int repeats = 0;
                    for (int j = 0; j < size; j++) {
                        Note n = notes.poll();
                        if (repeats < 2)
                            tempQueue.offer(n);
                        if (n.isRepeat())
                            repeats++;
                        notes.offer(n);
                    }
                    while (!tempQueue.isEmpty()) {
                        length += tempQueue.poll().getDuration();
                    }
                }
            }
            else if (note.isRepeat())
                repeat = false;
            length += note.getDuration();
            notes.offer(notes.poll());
        }
        return length;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        int size = notes.size();
        for (int i = 0; i < size; i++) {
            Note n = notes.poll();
            sb.append(n).append('\n');
            notes.offer(n);
        }
        return sb.toString();
    }

    public void changeTempo(double tempo) {
        int size = notes.size();
        for (int i = 0; i < size; i++) {
            Note n = notes.poll();
            n.setDuration(n.getDuration() * tempo);
            notes.offer(n);
        }
    }

    public void reverse() {
        Stack<Note> stack = new Stack<>();
        while (!notes.isEmpty())
            stack.push(notes.poll());
        while (!stack.isEmpty())
            notes.offer(stack.pop());
    }

    public void append(Melody other) {
        int size = other.notes.size();
        for (int i = 0; i < size; i++) {
            Note n = other.notes.poll();
            notes.offer(n);
            other.notes.offer(n);
        }
    }

    public void play() {
        boolean repeat = false;
        int size = notes.size();
        for (int i = 0; i < size; i++) {
            Note note = notes.peek();
            if (!repeat) {
                if (note.isRepeat()) {
                    repeat = true;
                    tempQueue = new LinkedList<>();
                    int repeats = 0;
                    for (int j = 0; j < size; j++) {
                        Note n = notes.poll();
                        if (repeats < 2)
                            tempQueue.offer(n);
                        if (n.isRepeat())
                            repeats++;
                        notes.offer(n);
                    }
                    while (!tempQueue.isEmpty()) {
                        tempQueue.poll().play();
                    }
                }
            }
            else if (note.isRepeat())
                repeat = false;
            note.play();
            notes.offer(notes.poll());
        }
    }

    public Queue<Note> getNotes() {
        return notes;
    }
}

// bad piggies is at 156 bpm (2.6)