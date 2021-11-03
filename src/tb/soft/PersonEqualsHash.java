package tb.soft;

public class PersonEqualsHash extends Person{

    public PersonEqualsHash(Person person) throws PersonException{
        super(person.getFirstName(), person.getLastName());
        birthYear = person.getBirthYear();
        job = person.getJob();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonEqualsHash person = (PersonEqualsHash) o;

        if (getBirthYear() != person.getBirthYear()) return false;
        if (getFirstName() != null ? !getFirstName().equals(person.getFirstName()) : person.getFirstName() != null)
            return false;
        return getLastName().equals(person.getLastName());
    }

    @Override
    public int hashCode() {
        int result = getFirstName() != null ? getFirstName().hashCode() : 0;
        result = 31 * result + getLastName().hashCode();
        result = 31 * result + getBirthYear();
        return result;
    }

}
