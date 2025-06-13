package school.sorokin.javacore.view;

import school.sorokin.javacore.model.Contact;
import school.sorokin.javacore.storage.ContactsList;

import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class ConsoleView {

    private static final String EMPTY_STRING_NOTIFICATION = "Введено пустое значение. Пожалуйста, попробуйте снова.";
    private static final String INPUT_REQUEST_WORD = "Введите";
    private static final String NAME_REQUEST = " имя контакта: ";
    private static final String NUMBER_REQUEST = " номер телефона контакта: ";
    private static final String EMAIL_REQUEST = " email контакта: ";
    private static final String GROUP_REQUEST = " группу контакта: ";
    private static final String CONTACT_ALREADY_EXIST_NOTIFICATION = "Контакт с таким именем и номером телефона уже существует.";
    private static final String INVALID_ITEM_NOTIFICATION = "Неверный пункт меню. Пожалуйста, выберите другой пункт.";
    private static final String CONTACT_ADDED_NOTIFICATION = "Контакт успешно добавлен: ";
    private static final String CONTACT_NOT_FOUND_NOTIFICATION = "Контакт не найден.";
    private static final String CONTACTS_LIST_EMPTY_NOTIFICATION = "Список контактов пуст.";
    private static final String CONTACTS_LIST_HEADER = "Список контактов:";

    private static final int ADD_CONTACT_NUM = 1;
    private static final String ADD_CONTACT_ITEM = "Добавить контакт";
    private static final int DELETE_CONTACT_NUM = 2;
    private static final String DELETE_CONTACT_ITEM = "Удалить контакт";
    private static final int FIND_BY_NAME_NUM = 3;
    private static final String FIND_BY_NAME_ITEM = "Найти контакт по имени";
    private static final int SHOW_CONTACTS_NUM = 4;
    private static final String SHOW_CONTACTS_ITEM = "Показать список контактов";
    private static final int SHOW_GROUP_NUM = 5;
    private static final String SHOW_GROUP_ITEM = "Показать контакты в группе";
    private static final int EXIT_NUM = 6;
    private static final String EXIT_ITEM = "Выход";

    private static final String FOUNDED_CONTACTS_HEADER = "Найденные контакты:";
    private static final String WRONG_EMAIL_NOTIFICATION = "Неверный формат email. Пожалуйста, попробуйте снова.";
    private static final String MENU_HEADER = "\nВыберите пункт меню:";

    private static final List<String> MENU_ITEMS = List.of(
            formatMenuItem(ADD_CONTACT_NUM, ADD_CONTACT_ITEM),
            formatMenuItem(FIND_BY_NAME_NUM, FIND_BY_NAME_ITEM),
            formatMenuItem(SHOW_CONTACTS_NUM, SHOW_CONTACTS_ITEM),
            formatMenuItem(SHOW_GROUP_NUM, SHOW_GROUP_ITEM),
            formatMenuItem(EXIT_NUM, EXIT_ITEM),
            formatMenuItem(DELETE_CONTACT_NUM, DELETE_CONTACT_ITEM)
    );

    private static final Set<String> MENU_ITEMS_SET = new TreeSet<>(MENU_ITEMS);



    private final ContactsList contacts;

    private final Scanner sc;

    public ConsoleView() {
        contacts = new ContactsList();
        sc = new Scanner(System.in);
    }
    
    public void start() {
        while (true) {
            showMenu();
            choseMenuItem();
        }
    }

    private static String formatMenuItem(final int number, final String item) {
        return "%d. %s".formatted(number, item);
    }

    private void showMenu() {
        System.out.println(MENU_HEADER);
        MENU_ITEMS_SET.forEach(System.out::println);
        System.out.println();
    }

    private void choseMenuItem() {
        int item = getValidatedMenuInput();
        switch (item) {
            case ADD_CONTACT_NUM -> addContact();
            case FIND_BY_NAME_NUM -> searchContactsByName();
            case SHOW_CONTACTS_NUM -> showContactsList();
            case SHOW_GROUP_NUM -> showGroupContacts();
            case EXIT_NUM -> System.exit(0);
            case DELETE_CONTACT_NUM -> deleteContactByName();
            default -> System.out.println(INVALID_ITEM_NOTIFICATION);
        }
    }

    private int getValidatedMenuInput() {
        while (true) {
            try {
                System.out.print("> ");
                int input = Integer.parseInt(sc.nextLine()
                                               .trim());
                if (input >= ADD_CONTACT_NUM && input <= EXIT_NUM) {
                    return input;
                } else {
                    System.out.println(INVALID_ITEM_NOTIFICATION);
                }
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите число.");
            }
        }
    }

    private String getStringInput(String prompt) {
        System.out.printf("%s %s", INPUT_REQUEST_WORD, prompt);
        String res = sc.nextLine()
                       .trim();
        while (res.isEmpty()) {
            System.out.println(EMPTY_STRING_NOTIFICATION);
            System.out.print(prompt);
            res = sc.nextLine()
                    .trim();
        }
        return res;
    }

    private String getValidatedPhoneNumber() {
        String phone;
        while (true) {
            phone = getStringInput(ConsoleView.NUMBER_REQUEST);
            if (phone.matches("\\+?\\d{10,15}")) { // Simple validation for phone number
                return phone;
            } else {
                System.out.println("Неверный формат номера телефона. Пожалуйста, попробуйте снова.");
            }
        }
    }

    private String getValidatedEmail() {
        String email;
        while (true) {
            email = getStringInput(ConsoleView.EMAIL_REQUEST);
            if (email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) { // Simple validation for email
                return email;
            } else {
                System.out.println(WRONG_EMAIL_NOTIFICATION);
            }
        }
    }

    private void addContact() {
        String name = getStringInput(NAME_REQUEST);
        String phone = getValidatedPhoneNumber();
        String email = getValidatedEmail();
        String group = getStringInput(GROUP_REQUEST);
        // Add contact
        if (!contacts.add(name, phone, email, group)) {
            System.out.println();
            System.out.println(CONTACT_ALREADY_EXIST_NOTIFICATION);
            return;
        }
        System.out.println();
        System.out.println(CONTACT_ADDED_NOTIFICATION + name);
        System.out.println();
    }

    private void showContactsList() {
        String contactsList = contacts.getContactsList();
        if (contactsList.isEmpty()) {
            System.out.println(CONTACTS_LIST_EMPTY_NOTIFICATION);
        } else {
            System.out.println(CONTACTS_LIST_HEADER);
            System.out.println(contactsList);
        }
    }

    private void showGroupContacts() {
        String group = getStringInput(GROUP_REQUEST);
        if (group == null || group.isBlank()) {
            System.out.println(EMPTY_STRING_NOTIFICATION);
            return;
        }
        String contactsList = contacts.listGroupContacts(group);
        if (contactsList == null || contactsList.isEmpty()) {
            System.out.printf("Нет контактов в группе '%s'.%n", group);
        } else {
            System.out.printf("Контакты в группе '%s':%n%s%n", group, contactsList);
        }
    }

    private void searchContactsByName() {
        String name = getStringInput(NAME_REQUEST);
        if (name == null || name.isBlank()) {
            System.out.println(EMPTY_STRING_NOTIFICATION);
            return;
        }
        List<Contact> foundContacts = contacts.findAllByName(name);
        if (foundContacts == null || foundContacts.isEmpty()) {
            System.out.println(CONTACT_NOT_FOUND_NOTIFICATION);
        } else {
            System.out.println(FOUNDED_CONTACTS_HEADER);
            foundContacts.forEach(System.out::println);
        }
    }

    private void deleteContactByName() {
        String name = getStringInput(NAME_REQUEST);
        if (name == null || name.isBlank()) {
            System.out.println(EMPTY_STRING_NOTIFICATION);
            return;
        }
        if (contacts.removeByName(name)) {
            System.out.printf("Контакт '%s' успешно удален.%n", name);
        } else {
            System.out.println(CONTACT_NOT_FOUND_NOTIFICATION);
        }
    }

    
}
