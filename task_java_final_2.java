import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class UserData {
    private final String surname;
    private final String name;
    private final String patronymic;
    private final LocalDate birthDate;
    private final long phoneNumber;
    private final char gender;

    public UserData(String surname, String name, String patronymic, LocalDate birthDate, long phoneNumber, char gender) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }

    public static UserData fromInputString(String inputString) throws IllegalArgumentException {
        String[] parts = inputString.split(" ");
        if (parts.length != 6) {
            throw new IllegalArgumentException("Invalid number of arguments");
        }
        String surname = parts[0];
        String name = parts[1];
        String patronymic = parts[2];
        LocalDate birthDate;
        try {
            birthDate = LocalDate.parse(parts[3], DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format");
        }
        long phoneNumber;
        try {
            phoneNumber = Long.parseLong(parts[4]);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid phone number");
        }
        char gender;
        try {
            gender = parts[5].charAt(0);
            if (!(gender == 'm' || gender == 'f')) {
                throw new IllegalArgumentException("Invalid gender");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid gender");
        }
        return new UserData(surname, name, patronymic, birthDate, phoneNumber, gender);
    }

    public void writeToFile() throws IOException {
        String filename = surname + ".txt";
        FileWriter fw = new FileWriter(filename, true);
        fw.write(toString() + "\n");
        fw.close();
    }

    public String getSurname() {
        return surname;
    }

    @Override
    public String toString() {
        return surname + " " + name + " " + patronymic + " " + birthDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) +
                " " + phoneNumber + " " + gender;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter data: ");
        String inputString = scanner.nextLine();
        try {
            UserData userData = UserData.fromInputString(inputString);
            userData.writeToFile();
            System.out.println("Data saved to file " + userData.getSurname() + ".txt");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
