package school.sorokin.javacore.storage;

import school.sorokin.javacore.model.Contact;

import java.util.*;

public class ContactsList {
    final List<Contact> contactsList;
    final Set<Contact> contactsSet;
    final Map<String, List<Contact>> contactsMap;

    public ContactsList() {
        this.contactsList = new ArrayList<>() {
            @Override
            public String toString() {
                StringBuilder sb = new StringBuilder();
                Iterator<Contact> iterator = contactsList.iterator();
                while (iterator.hasNext()) {
                    Contact contact = iterator.next();
                    sb.append(contact).append("\n");
                }
                return sb.toString();
            }
        };

        this.contactsSet = new HashSet<>() {
            @Override
            public String toString() {
                StringBuilder sb = new StringBuilder();
                Iterator<Contact> iterator = contactsSet.iterator();
                while (iterator.hasNext()) {
                    Contact contact = iterator.next();
                    sb.append(contact).append("\n");
                }
                return sb.toString();
            }
        };

        this.contactsMap = new HashMap<>() {
            @Override
            public String toString() {
                StringBuilder sb = new StringBuilder();
                Iterator<Map.Entry<String, List<Contact>>> iterator = contactsMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, List<Contact>> entry = iterator.next();
                    sb.append("\nГруппа: ").append(entry.getKey()).append("\n");
                    List<Contact> contactList = entry.getValue();
                    for (Contact contact : contactList) {
                        sb.append(contact).append("\n");
                    }
                }
                return sb.toString();
            }
        };
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
        return this.contactsList.toString();
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

    public List<Contact> findAllByName(final String name) {
        if (name == null || name.trim().isEmpty()) {
            return null; // Возвращаем null, если имя пустое или null
        }
        List<Contact> foundContacts = new ArrayList<>();
        // Ищем все контакты с заданным именем
        Iterator <Contact> iterator = contactsSet.iterator();
        while (iterator.hasNext()) {
            Contact contact = iterator.next();
            if (contact.getName().trim().equalsIgnoreCase(name)) {
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
                for (Contact contact : contacts) {
                    sb.append(contact).append("\n");
                }
                return sb.toString();
    }

    public String getContactsSet() {
        return this.contactsSet.toString();
    }

    public String getContactsMap() {
        return this.contactsMap.toString();
    }
}
