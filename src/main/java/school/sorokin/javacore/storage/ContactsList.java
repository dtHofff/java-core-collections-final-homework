package school.sorokin.javacore.storage;

import school.sorokin.javacore.model.Contact;

import java.util.*;

public class ContactsList {
    final List<Contact> contactsList;
    final Set<Contact> contactsSet;
    final Map<String, List<Contact>> contactsMap;

    public ContactsList() {
        this.contactsList = new ArrayList<>();
        this.contactsSet = new HashSet<>();
        this.contactsMap = new HashMap<>();
    }

    public boolean add(final String name, final String phone, final String email, final String group) {
        Contact contact = new Contact(name, phone, email, group);
        if (contactsSet.add(contact)) {
            contactsList.add(contact);

            // Добавляем контакт в Map, если группа не существует, создаем новый список
            // и добавляем контакт в этот список
            // Если группа уже существует, просто добавляем контакт в существующий список
            contactsMap.put(group.toLowerCase(),
                contactsMap.getOrDefault(group.toLowerCase(), new ArrayList<>()));
            contactsMap.get(group.toLowerCase()).add(contact);

            return true; // Возвращаем true, если контакт успешно добавлен
        }
        return false; // Возвращаем false, если контакт уже существует
    }

    public String getContactsList() {
        StringBuilder sb = new StringBuilder();
        Iterator<Contact> iterator = contactsList.iterator();
        while (iterator.hasNext()) {
            Contact contact = iterator.next();
            sb.append(contact).append("\n");
        }
        return sb.toString();
    }

    public Contact searchByName(final String name) {
        if (name == null || name.trim().isEmpty()) {
            return null; // Возвращаем null, если имя пустое или null
        }
        // Используем Set для быстрого поиска контакта по имени
        Iterator <Contact> iterator = contactsSet.iterator();
        while (iterator.hasNext()) {
            Contact contact = iterator.next();
            if (contact.getName()
                       .trim()
                       .equalsIgnoreCase(name)) {
                return contact; // Возвращаем найденный контакт
            }
        }
        return null; // Возвращаем null, если контакт не найден
    }

    public boolean removeByName(final String name) {
        if (name == null || name.trim().isEmpty()) {
            return false; // Возвращаем false, если имя пустое или null
        }
        // Ищем контакт по имени
        Contact contactToRemove = searchByName(name);
        if (contactToRemove != null) {
            contactsSet.remove(contactToRemove); // Удаляем из Set
            contactsList.remove(contactToRemove); // Удаляем из List
            contactsMap.get(contactToRemove.getGroup().toLowerCase()).remove(contactToRemove);
            return true; // Возвращаем true, если контакт успешно удален
        }
        return false; // Возвращаем false, если контакт не найден
    }

    public String listGroupContacts(final String group) {
        if (group == null || group.isBlank()) {
            return null;
        }
        List<Contact> contacts = contactsMap.get(group.toLowerCase());
        if (contacts == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Контакты в группе \"").append(group).append("\":\n");
        Iterator<Contact> iterator = contacts.iterator();
        while (iterator.hasNext()) {
            Contact contact = iterator.next();
            sb.append(contact).append("\n");
        }
        return sb.toString();
    }
}
