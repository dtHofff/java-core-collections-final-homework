package school.sorokin.javacore.model;

import java.util.Objects;

public class Contact {
    private final String name;
    private final String phone;
    private final String email;
    private final String group;

    public Contact(String name, String phone, String email, String group) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.group = group;
    }

    @Override
    public boolean equals(Object obj) {
        // Оптимизация: если ссылки совпадают, объекты равны
        if (this == obj) return true;
        // Если объект null или принадлежит другому классу, возвращаем false
        if (obj == null || getClass() != obj.getClass()) return false;

        Contact other = (Contact) obj;

        if (!Objects.equals(phone, other.phone)) return false;
        return (name != null ? name.equals(other.name) : other.name == null);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phone);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s : %s, %s", group, name, phone, email);
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getGroup() {
        return group;
    }
}
