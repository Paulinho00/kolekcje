package tb.soft;

import java.util.*;

public class CollectionsContainer {
    private Set<Person> hashSet1;
    private Set<PersonEqualsHash> hashSet2;

    private Set<Person> treeSet1;
    private Set<PersonEqualsHash> treeSet2;

    private List<Person> arrayList1;
    private List<PersonEqualsHash> arrayList2;

    private List<Person> linkedList1;
    private List<PersonEqualsHash> linkedList2;

    private Map<Person, Integer> hashMap1;
    private Map<PersonEqualsHash, Integer> hashMap2;

    private Map<Person, Integer> treeMap1;
    private Map<PersonEqualsHash, Integer> treeMap2;

    public CollectionsContainer(){
        hashSet1 = new HashSet<>();
        hashSet2 = new HashSet<>();
        treeSet1 = new TreeSet<>();
        treeSet2 = new TreeSet<>();
        arrayList1 = new ArrayList<>();
        arrayList2 = new ArrayList<>();
        linkedList1 = new LinkedList<>();
        linkedList2 = new LinkedList<>();
        hashMap1 = new HashMap();
        hashMap2 = new HashMap<>();
        treeMap1 = new TreeMap<>();
        treeMap2 = new TreeMap<>();
    }

    public void add(Person person) throws PersonException{
        PersonEqualsHash personEqualsHash = new PersonEqualsHash(person);
        hashSet1.add(person);
        hashSet2.add(personEqualsHash);
        treeSet1.add(person);
        treeSet2.add(personEqualsHash);
        arrayList1.add(person);
        arrayList2.add(personEqualsHash);
        linkedList1.add(person);
        linkedList2.add(personEqualsHash);
        hashMap1.put(person,hashMap1.size()+1 );
        hashMap2.put(personEqualsHash, hashMap2.size()+1);
        treeMap1.put(person, treeMap1.size()+1);
        treeMap2.put(personEqualsHash, treeMap2.size()+1);
    }

    public void remove(Person person) throws PersonException{
        PersonEqualsHash personEqualsHash = new PersonEqualsHash(person);
        hashSet1.remove(person);
        hashSet2.remove(personEqualsHash);
        treeSet1.remove(person);
        treeSet2.remove(personEqualsHash);
        arrayList1.remove(person);
        arrayList2.remove(personEqualsHash);
        linkedList1.remove(person);
        linkedList2.remove(personEqualsHash);
        hashMap1.remove(person);
        hashMap2.remove(personEqualsHash);
        treeMap1.remove(person);
        treeMap2.remove(personEqualsHash);
    }

    public Set<Person> getHashSet1() {
        return hashSet1;
    }

    public Set<PersonEqualsHash> getHashSet2() {
        return hashSet2;
    }

    public Set<Person> getTreeSet1() {
        return treeSet1;
    }

    public Set<PersonEqualsHash> getTreeSet2() {
        return treeSet2;
    }

    public List<Person> getArrayList1() {
        return arrayList1;
    }

    public List<PersonEqualsHash> getArrayList2() {
        return arrayList2;
    }

    public List<Person> getLinkedList1() {
        return linkedList1;
    }

    public List<PersonEqualsHash> getLinkedList2() {
        return linkedList2;
    }

    public Map<Person, Integer> getHashMap1() {
        return hashMap1;
    }

    public Map<PersonEqualsHash, Integer> getHashMap2() {
        return hashMap2;
    }

    public Map<Person, Integer> getTreeMap1() {
        return treeMap1;
    }

    public Map<PersonEqualsHash, Integer> getTreeMap2() {
        return treeMap2;
    }
}
