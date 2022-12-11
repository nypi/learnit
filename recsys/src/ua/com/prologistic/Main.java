package ua.com.prologistic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(
                "src/ua/com/prologistic/movie.csv"));
        String line = null;
        Scanner scanner = null;
        int index = 0;
        List<Movie> empList = new ArrayList<>();

        while ((line = reader.readLine()) != null) {
            Movie emp = new Movie();
            scanner = new Scanner(line);
            scanner.useDelimiter(",");
            while (scanner.hasNext()) {
                String data = scanner.next();
                System.out.println(index);
                System.out.println(data);
                if (index == 4)
                    emp.setName(data);
                else if (index == 5)
                    emp.setYear(data);
                else if (index == 8)
                    emp.setGenre(data);
                else if (index == 8)
                    emp.setRating(data);
                else if (index == 10)
                    emp.setOverview(data);
                else if (index == 9)
                    emp.setDirector(data);
                index++;
            }
            index = 0;
            empList.add(emp);
        }

        //закрываем наш ридер
        reader.close();

//        System.out.println(empList);
    }
}


