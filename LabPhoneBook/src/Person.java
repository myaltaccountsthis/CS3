import java.util.Objects;

public class Person {
    private String name;

    public Person(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person person)) return false;
        return name.equals(person.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
