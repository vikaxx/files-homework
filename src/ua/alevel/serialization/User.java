package ua.alevel.serialization;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class User implements Serializable {
    private String name;
    private String email;
    private String mobileNumber;

    public static void main(String[] args) {
        String filePath = "C:\\Users\\User\\IdeaProjects\\serializationhometask\\src\\data\\USER_NAME.dat";

        User user = new User("lina alina", "google@g.com", "+380979797997");
        User user1 = new User("vika viktoria", "vika@g.com", "+38097888887");
        User user2 = new User("kate katie", "kate@g.com", "+380998080567");

        user.serializeUser(filePath);
        user1.serializeUser(filePath);
        user2.serializeUser(filePath);

        System.out.println("now here are next users:");
        System.out.println(new User().deserializeUser("lina alina", filePath).toString());
        System.out.println(new User().deserializeUser("vika viktoria", filePath).toString());
        System.out.println(new User().deserializeUser("kate katie", filePath).toString());

        while (true) {
            System.out.println("\nwould you like to create(1) or to search(2) user?");
            Scanner in = new Scanner(System.in);
            String choose = in.nextLine();
            switch (choose) {
                case "0":
                    System.out.println("\nbye!");
                    System.exit(0);
                case "1":
                    new User().create(filePath);
                    break;
                case "2":
                    new User().show(filePath);
                    break;
                default:
                    System.out.println("input 1 or 2.\ninput 0 to quit");
            }
        }
    }

    public User() {
    }

    public User(String name, String email, String mobileNumber) {
        this.name = name;
        this.email = email;
        this.mobileNumber = mobileNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                '}';
    }

    public void serializeUser(String path) {
        List<User> users = deserializeAllUsers(path);

        this.setName(this.name.toUpperCase().replace(' ', '_'));
        users.add(this);

        serializeAllUsers(users, path);
    }

    public User deserializeUser(String userName, String path) {
        List<User> users = deserializeAllUsers(path);
        for (User current : users) {
            if (current.name.equals(userName.toUpperCase().replace(' ', '_'))) {
                return current;
            }
        }
        return null;
    }

    private void serializeAllUsers(List<User> users, String path) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            for (User current : users) {
                oos.writeObject(current);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<User> deserializeAllUsers(String path) {
        ArrayList<User> users = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            while (true) {
                User temp = (User) ois.readObject();
                users.add(temp);
            }
        } catch (EOFException e) {
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public void create(String path) {
        User user = new User();
        Scanner scanner = new Scanner(System.in);
        String input;

        System.out.println("input name:");
        input = scanner.nextLine();
        user.setName(input);

        System.out.println("input email:");
        input = scanner.nextLine();
        user.setEmail(input);

        System.out.println("input mobile number:");
        input = scanner.nextLine();
        user.setMobileNumber(input);

        user.serializeUser(path);
    }

    public void show(String path) {
        Scanner scanner = new Scanner(System.in);
        String input;

        System.out.println("input name:");
        input = scanner.nextLine();

        try {
            System.out.println(new User().deserializeUser(input, path).toString());
        } catch (NullPointerException e) {
            System.out.println("User " + input + " does not exist.");
        }
    }

}
