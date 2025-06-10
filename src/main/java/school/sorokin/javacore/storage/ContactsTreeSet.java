package school.sorokin.javacore.storage;

import school.sorokin.javacore.model.Contact;

import java.util.Comparator;
import java.util.TreeSet;

public class ContactsTreeSet {

    private final TreeSet<Contact> contacts;

    public ContactsTreeSet() {
        this.contacts = new TreeSet<>(Comparator.comparing(Contact::getName)
                                                .thenComparing(Contact::getPhone));
    }

    public boolean add(final String name, final String phone, final String email, final String group) {
        Contact contact = new Contact(name, phone, email, group);
        return contacts.add(contact);
    }

    public boolean remove(final String name, final String phone, final String email, final String group) {
        Contact contact = new Contact(name, phone, email, group);
        return contacts.remove(contact);
    }

    public String getContacts() {
        StringBuilder sb = new StringBuilder();
        for (Contact contact : contacts) {
            sb.append(contact).append("\n");
        }
        return sb.toString();
    }
}
