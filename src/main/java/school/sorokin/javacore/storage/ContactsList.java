package school.sorokin.javacore.storage;

import school.sorokin.javacore.model.Contact;

import java.util.*;

public class ContactsList {
   private final List<Contact> contactList;
   private final Set<Contact> contactSet;
   private final Map<String, List<Contact>> contactMap;

    public ContactsList() {
        this.contactList = new ArrayList<>() {
            @Override
            public String toString() {
                StringBuilder sb = new StringBuilder();
                for (Contact contact : contactList) {
                    sb.append(contact)
                      .append("\n");
                }
                return sb.toString();
            }
        };

        this.contactSet = new HashSet<>() {
            @Override
            public String toString() {
                StringBuilder sb = new StringBuilder();
                for (Contact contact : contactSet) {
                    sb.append(contact)
                      .append("\n");
                }
                return sb.toString();
            }
        };

        this.contactMap = new HashMap<>() {
            @Override
            public String toString() {
                StringBuilder sb = new StringBuilder();
                for (Entry<String, List<Contact>> entry : contactMap.entrySet()) {
                    sb.append("\nГруппа: ")
                      .append(entry.getKey())
                      .append("\n");
                    List<Contact> contactList = entry.getValue();
                    for (Contact contact : contactList) {
                        sb.append(contact)
                          .append("\n");
                    }
                }
                return sb.toString();
            }
        };
    }

    public boolean add(final String name, final String phone, final String email, final String group) {
        Contact contact = new Contact(name, phone, email, group);
        if (contactSet.add(contact)) {
            contactList.add(contact);

            // Добавляем контакт в Map, если группа не существует, создаем новый список
            // и добавляем контакт в этот список
            // Если группа уже существует, просто добавляем контакт в существующий список
            contactMap.put(group.toLowerCase(),
                contactMap.getOrDefault(group.toLowerCase(), new ArrayList<>()));
            contactMap.get(group.toLowerCase()).add(contact);

            return true; // Возвращаем true, если контакт успешно добавлен
        }
        return false; // Возвращаем false, если контакт уже существует
    }

    public String getContactList() {
        return this.contactList.toString();
    }


    public List<Contact> findAllByName(final String name) {
        if (name == null || name.trim().isEmpty()) {
            return null; // Возвращаем null, если имя пустое или null
        }
        List<Contact> foundContacts = new ArrayList<>();
        // Ищем все контакты с заданным именем
        for (Contact contact : contactSet) {
            if (contact.getName()
                       .trim()
                       .equalsIgnoreCase(name)) {
                foundContacts.add(contact);
            }
        }
        return foundContacts; // Возвращаем список найденных контактов
    }

    public boolean removeByName(final String name) {
        if (name == null || name.trim().isEmpty()) {
            return false; // Возвращаем false, если имя пустое или null
        }
        // Ищем контакт по имени
        Contact contactToRemove = searchByName(name);
        if (contactToRemove != null) {
            contactSet.remove(contactToRemove); // Удаляем из Set
            contactList.remove(contactToRemove); // Удаляем из List
            contactMap.get(contactToRemove.getGroup().toLowerCase()).remove(contactToRemove);
            return true; // Возвращаем true, если контакт успешно удален
        }
        return false; // Возвращаем false, если контакт не найден
    }

    public String listGroupContacts(final String group) {
        if (group == null || group.isBlank()) {
            return null;
        }
        List<Contact> contacts = contactMap.get(group.toLowerCase());
        if (contacts == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
                for (Contact contact : contacts) {
                    sb.append(contact).append("\n");
                }
                return sb.toString();
    }

    private Contact searchByName(final String name) {
        if (name == null || name.trim().isEmpty()) {
            return null; // Возвращаем null, если имя пустое или null
        }
        // Используем Set для быстрого поиска контакта по имени
        for (Contact contact : contactSet) {
            if (contact.getName()
                       .trim()
                       .equalsIgnoreCase(name)) {
                return contact; // Возвращаем найденный контакт
            }
        }
        return null; // Возвращаем null, если контакт не найден
    }


}
